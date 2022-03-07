package com.dgtt.business.authencation;

import com.dgtt.business.model.JwtAuthDto;
import com.dgtt.business.model.ResponseBody;
import com.dgtt.core.config.ApplicationConfig.MessageSourceVi;
import com.dgtt.core.constants.ApplicationConstant;
import com.dgtt.core.entity.User;
import com.dgtt.core.exception.UnauthorizedException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author Nguyen_Toan
 */
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    @Qualifier("JwtTokenProvider_Main")
    private JwtTokenProvider tokenProvider;

    @Autowired
    private UserDetailsServiceImpl customUserDetailsService;

    @Autowired
    private MessageSourceVi messageSourceVi;

    @Autowired
    private ObjectMapper mapper;

    private byte[] publicKey;

    private String publicKeyFile;

    public JwtAuthenticationFilter(String publicKeyFile) {
        this.publicKeyFile = publicKeyFile;
        this.readPublicKey();
    }

//    @Override
//    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
//    }
    public boolean checkIgnoring(HttpServletRequest request) {
        boolean isWebIgroring = false;
        String contextPath = request.getContextPath();
        for (String check : ApplicationConstant.SECURITY_CONFIG.WEB_IGNORING) {
            isWebIgroring = new AntPathMatcher().match(contextPath + check, request.getRequestURI());
            if (isWebIgroring) {
                break;
            }
        }
        return isWebIgroring;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {

        // log.info(new StringBuilder("Request from :::::::::: " + request.getRemoteAddr() + ":"
        // + request.getLocalPort() + request.getRequestURI()).toString());
        // log.info(request.getHeader("User-Agent"));
//         String jwt = vn.osp.adfilex.utils.StringUtils.reverseString(getJwtFromRequest(request));
        String auth = request.getHeader(ApplicationConstant.AUTHENTICATION_SCHEME_NAME);
        boolean isWebIgroring = checkIgnoring(request);
        try {
            if (!isWebIgroring) {
                if (auth != null && auth.startsWith(ApplicationConstant.TOKEN_BASIC)) {
                    String jwt = tokenProvider.getJwtFromRequest(request);
                    if (StringUtils.hasText(jwt) && tokenProvider.validateTokenBasic(jwt)) {
                        CustomUserDetails customUserDetails = new CustomUserDetails();
                        customUserDetails.setUser(new User(0L, tokenProvider.getUsernameDefault(), tokenProvider.getUsernameDefault()));
                        UsernamePasswordAuthenticationToken authentication
                                = new UsernamePasswordAuthenticationToken(customUserDetails,
                                        null, new ArrayList<>());
                        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                        SecurityContextHolder.getContext().setAuthentication(authentication);
                    }
                } else if (auth != null && auth.startsWith(ApplicationConstant.TOKEN_BEARER)) {
                    tokenProvider.setPublicKey(publicKey);
                    String jwt = tokenProvider.getJwtFromRequest(request);
                    JwtAuthDto jwtAuthDto = null;
                    if (StringUtils.hasText(jwt) && tokenProvider.validateToken(jwt)) {
                        jwtAuthDto = tokenProvider.getJWTInfor(jwt);

                        String roleCode = jwtAuthDto.getRole();
                        if (roleCode == null) {
                            roleCode = request.getHeader(ApplicationConstant.USER_DEPT_ROLE_ID);
                        }

                        if (roleCode == null) {
                            throw new UnauthorizedException("Quyền không được bỏ trống.");
                        }

                        // UserDetails userDetails=
                        // CustomUserDetails.builder().user(User.builder().userId(jwtAuthDto.getUi()).username(jwtAuthDto.getUname()).)
                        CustomUserDetails customUserDetails
                                = customUserDetailsService.loadCustomUserByUsername(jwtAuthDto.getUname(), roleCode);
                        customUserDetails.setSession(jwtAuthDto.getSs());

                        if (null != customUserDetails) {
                            if (!customUserDetails.isEnabled()) {
                                throw new UnauthorizedException("Tài khoản không hoạt động hoặc bị xóa.");
                            }
                            // Nếu người dùng hợp lệ, set thông tin cho Seturity Context
                            UsernamePasswordAuthenticationToken authentication
                                    = new UsernamePasswordAuthenticationToken(customUserDetails,
                                            customUserDetails.getPassword(), customUserDetails.getAuthorities());
                            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                            SecurityContextHolder.getContext().setAuthentication(authentication);
                        }
                    }
                } else {
                    throw new UnauthorizedException(messageSourceVi.getMessageVi("AUTHORIZATION_NOT_NULL"));
                }
            }

            filterChain.doFilter(request, response);
        } catch (Exception ex) {
            SecurityContextHolder.clearContext();
            String error = new StringBuilder(request.getMethod() + " " + request.getRemoteAddr() + ":"
                    + request.getLocalPort() + request.getRequestURI()).toString();
            log.error("jwtAuthentication - " + error + " - " + ex.getMessage());
            response.setContentType("application/json; charset=UTF-8");
            response.setCharacterEncoding("UTF-8");
            response.setHeader("Access-Control-Allow-Origin", "*");
            response.setHeader("Access-Control-Allow-Methods", "GET, HEAD, POST, PUT, DELETE, TRACE, OPTIONS, PATCH");
            response.setHeader("Access-Control-Allow-Headers", "X-Requested-With,pwd, Content-Type, Authorization, Origin, Accept, Access-Control-Request-Method, Access-Control-Request-Headers, RoleCode, DeptCode, DelegateFromUserId, UserDeptRoleId, OtpJwt");
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.getWriter().write(mapper.writeValueAsString(ResponseBody.builder().status(401).path(request.getRequestURI()).message(ex.getMessage())
                    .time(new Date()).build()));
        }
    }

    private void readPublicKey() {
        try {
            logger.info("readPublicKey: " + this.publicKeyFile);
            InputStream is = new ClassPathResource(this.publicKeyFile, this.getClass().getClassLoader()).getInputStream();
            publicKey = IOUtils.toByteArray(is);
        } catch (IOException e) {
            logger.error("readPublicKey: error", e);
            throw new RuntimeException(e);
        }
    }

}
