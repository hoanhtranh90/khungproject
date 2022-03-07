package com.dgtt.business.authencation;


import com.dgtt.business.model.JwtAuthDto;
import com.dgtt.business.utils.EncryptUtils;
import com.dgtt.core.constants.ApplicationConstant;
import com.dgtt.core.exception.UnauthorizedException;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

/**
 *
 * @author Nguyen_Toan
 */
@Component
@Slf4j
@Qualifier("JwtTokenProvider_Main")
@Primary
public class JwtTokenProvider {

    private static final String USER_ID = "ui";
    private static final String USER_NAME = "uname";
    private static final String ROLE = "rol";
    private static final String US_AGENT = "us-agent";
    private static final String IP = "ip";
    private static final String EXPIRATION = "exp";
    private static final String EMAIL_KEY = "mail";
    private static final String FULLNAME_KEY = "fullname";
    private static final String SESSION = "ss";

    @Value("${jwt.iss}")
    private String jwtIss;

    @Value("${jwt.ttlInDays}")
    private int jwtTtlInDays;

    @Value("${sso.privateKeyFile}")
    private String privateKeyFile;

    @Value("${username_service_default}")
    private String usernameDefault;

    @Value("${password_service_default}")
    private String passwordDefault;

    private byte[] publicKey;

    public void setPublicKey(byte[] publicKey) {
        this.publicKey = publicKey;
    }

    public String getUsernameDefault() {
        return usernameDefault;
    }

    public String getPasswordDefault() {
        return passwordDefault;
    }

    public String generateToken(CustomUserDetails userDetails) throws NoSuchAlgorithmException, IOException, InvalidKeySpecException {

        LocalDateTime currentTime = LocalDateTime.now();
        final Date expiryDate
                = Date.from(currentTime.plusDays(jwtTtlInDays).atZone(ZoneId.systemDefault()).toInstant());
        String roleCode = "";
        if (!userDetails.getRoleOfUser().isEmpty()) {
            roleCode = userDetails.getUser().getUserRoleList().get(0).getRole().getRoleCode();
        }
        return Jwts.builder().setIssuer(jwtIss)
                .setIssuedAt(Date.from(currentTime.atZone(ZoneId.systemDefault()).toInstant()))
                .setExpiration(expiryDate).claim(USER_ID, userDetails.getUser().getUserId())
                .claim(USER_NAME, userDetails.getUser().getUserName())
                .claim(ROLE, roleCode)
                .claim(EMAIL_KEY, userDetails.getUser().getEmail())
                .claim(FULLNAME_KEY, userDetails.getUser().getFullName())
                .claim(SESSION, userDetails.getSession())
                //                .signWith(SignatureAlgorithm.HS256, getSigningKey())
                .signWith(getPrivateSigningKey(), SignatureAlgorithm.RS256)
                .compact();
    }

    @SuppressWarnings("unchecked")
    public JwtAuthDto getJWTInfor(String authToken) throws InvalidKeySpecException, NoSuchAlgorithmException {

        Claims body = Jwts.parser().setSigningKey(getPublicSigningKey()).parseClaimsJws(authToken).getBody();

        return JwtAuthDto.builder().issuer(body.getIssuer()).ui(body.get(USER_ID, Long.class))
                .role(body.get(ROLE, String.class)).expi(body.get(EXPIRATION, Date.class))
                .uname(body.get(USER_NAME, String.class)).ip(body.get(IP, String.class))
                .usAgent(body.get(US_AGENT, String.class))
                .email(body.get(EMAIL_KEY, String.class))
                .fullname(body.get(FULLNAME_KEY, String.class))
                .ss(body.get(SESSION, String.class))
                .build();
    }

    public boolean validateToken(String authToken) throws InvalidKeySpecException, NoSuchAlgorithmException {

        try {
            Jwts.parser().setSigningKey(getPublicSigningKey()).parseClaimsJws(authToken);
            return true;
        } catch (ExpiredJwtException e) {
            log.error(" Expired and must be rejected: " + e.getMessage());
            throw new UnsupportedJwtException("Expired and must be rejected: " + e.getMessage());
        } catch (UnsupportedJwtException e) {
            log.error("Unsupported Jwt:" + e.getMessage());
            throw new UnsupportedJwtException("Unsupported Jwt:" + e.getMessage());
        } catch (MalformedJwtException e) {
            log.error("Malformed Jwt : " + e.getMessage());
            throw new UnsupportedJwtException("Malformed Jwt : " + e.getMessage());
        } catch (IllegalArgumentException e) {
            log.error("Illegal Argument: " + e.getMessage());
            throw new UnsupportedJwtException("Illegal Argument: " + e.getMessage());
        }
    }

    public boolean validateTokenBasic(String authToken) throws UnauthorizedException {
        try {
            String usernameAndPassword = EncryptUtils.encryptBase64(new StringBuilder(usernameDefault).append(":").append(passwordDefault).toString());
            if (!usernameAndPassword.equals(authToken)) {
                throw new UnsupportedJwtException("JwtException Token Basic.");
            }
            return true;
        } catch (UnsupportedJwtException e) {
            log.error(" JwtException: " + e.getMessage());
            throw new UnauthorizedException("JwtException Token Basic.");
        }
    }

    private Key getPublicSigningKey() throws InvalidKeySpecException, NoSuchAlgorithmException {
        KeyFactory kf = KeyFactory.getInstance("RSA");
        PublicKey key = kf.generatePublic(new X509EncodedKeySpec(publicKey));
        return key;
    }

    private Key getPrivateSigningKey() throws InvalidKeySpecException, NoSuchAlgorithmException, IOException {
        InputStream is = new ClassPathResource(this.privateKeyFile, this.getClass().getClassLoader()).getInputStream();
        byte[] privateKey = IOUtils.toByteArray(is);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        PrivateKey key = kf.generatePrivate(new PKCS8EncodedKeySpec(privateKey));
        return key;
    }
//    private Key getSigningKey() {
//
//        if (signingKey == null) {
//            // Create key for sign the jwt
//            byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(jwtSecretKey);
//            signingKey = new SecretKeySpec(apiKeySecretBytes, SignatureAlgorithm.HS256.getJcaName());
//        }
//        return signingKey;
//    }

    public String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader(ApplicationConstant.AUTHENTICATION_SCHEME_NAME);
        return getJwtFromHeader(bearerToken);
    }

    public String getJwtFromHeader(String bearerToken) {
        if (StringUtils.hasText(bearerToken)
                && bearerToken.startsWith(ApplicationConstant.TOKEN_BEARER)) {
            return bearerToken.substring(ApplicationConstant.TOKEN_BEARER.length(),
                    bearerToken.length());
        } else if (StringUtils.hasText(bearerToken)
                && bearerToken.startsWith(ApplicationConstant.TOKEN_BASIC)) {
            return bearerToken.substring(ApplicationConstant.TOKEN_BASIC.length(),
                    bearerToken.length());
        }
        return org.apache.commons.lang3.StringUtils.EMPTY;
    }

}
