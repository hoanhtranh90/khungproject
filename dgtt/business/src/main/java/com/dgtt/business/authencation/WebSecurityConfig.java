package com.dgtt.business.authencation;

import com.dgtt.core.constants.ApplicationConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 *
 * @author Nguyen_Toan
 */
@Configuration
@EnableWebSecurity

public class WebSecurityConfig extends WebSecurityConfigurerAdapter implements WebMvcConfigurer {

    @Autowired
    UserDetailsServiceImpl userService;

//    @Value(value = "${upload.path}")
//    private String uploadPath;

    @Value("${sso.publicKeyFile}")
    private String publicKeyFile;

    @Bean(BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        // Get AuthenticationManager bean
        return super.authenticationManagerBean();
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        // Register resource handler for images
//        registry.addResourceHandler("/images/**").addResourceLocations("file:" + uploadPath)
//                .setCacheControl(CacheControl.maxAge(2, TimeUnit.HOURS).cachePublic());
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(passwordEncoder());
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(ApplicationConstant.SECURITY_CONFIG.WEB_IGNORING);

//        web.ignoring().antMatchers("/actuator/**","/my/docs", "/v2/api-docs/**", "/swagger.json","/configuration/security", "/configuration/ui", "/swagger-resources/**",
//                "/configuration/**", "/swagger-ui.html", "/webjars/**", "/account-managers/create-account-free",
//                "/account-managers/sign-account", "/account-managers/register",
//                "/account-managers/consumption", "/account-managers/catelogy",
//                "/account-managers/hobby-catelogy", "/account-managers/topic-catelogy",
//                "/account-managers/get-password", "/account-managers/list-user");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().cors().and().cors().and().authorizeRequests()
                .antMatchers("/account-managers/login").permitAll().anyRequest()
                .authenticated();
        // Thêm một lớp Filter kiểm tra jwt
        http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

        http.logout().logoutUrl("/logout").clearAuthentication(true).invalidateHttpSession(true);
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter(publicKeyFile);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
