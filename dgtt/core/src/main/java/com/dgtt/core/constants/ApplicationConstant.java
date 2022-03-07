package com.dgtt.core.constants;

/**
 *
 * @author sadfsafbhsaid
 */
public class ApplicationConstant {

    private ApplicationConstant() {

    }

    public static final String AUTHENTICATION_SCHEME_NAME = "Authorization";
    public static final String AUTHENTICATION_VIETTEL = "API-KEY";

    public static final String CONTENT_TYPE = "Content-Type";

    public static final String PARTNER_ID = "Partner-Id";
    public static final String TOKEN_BEARER = "Bearer ";
    public static final String TOKEN_BASIC = "Basic ";
    public static final String USER_DEPT_ROLE_ID = "userDeptRoleId";

    public static final String ANONYMOUS = "anonymousUser";

    public static final String ADMIN = "ADM";
    public static final String MODERATOR = "MOD";
    public static final String CONTRACT = "CON";
    public static final String SALE = "SAL";

    public interface SECURITY_CONFIG {

        String[] WEB_IGNORING = {"/test","/actuator/**", "/csrf", "/my/docs", "/v2/api-docs/**", "/swagger.json", "/configuration/security", "/configuration/ui", "/swagger-resources/**",
            "/configuration/**","/swagger-ui/**", "/swagger-ui.html", "/webjars/**", "/account-managers/login"};

    }

    public interface SECURITY_CONFIG_PUBLIC {

        String[] WEB_IGNORING = {"/actuator/**", "/csrf", "/my/docs", "/v2/api-docs/**", "/swagger.json", "/configuration/security", "/configuration/ui", "/swagger-resources/**",
            "/configuration/**","/swagger-ui/**", "/swagger-ui.html", "/webjars/**", "/account-managers/login"};

    }
}
