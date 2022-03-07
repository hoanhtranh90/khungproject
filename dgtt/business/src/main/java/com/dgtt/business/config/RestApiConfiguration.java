package com.dgtt.business.config;

import com.dgtt.core.utils.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Arrays;
import java.util.List;

/**
 *
 * @author DELL
 */
@Configuration
@EnableSwagger2
@Import(BeanValidatorPluginsConfiguration.class)
public class RestApiConfiguration {

    private static final long MAX_AGE_SECS = 3600;

    @Value("${allowedOrigins}")
    private String allowedOrigins;

    @Value("${readTimeout}")
    private Integer readTimeout;

    @Value("${connectionTimeout}")
    private Integer connectionTimeout;

    @Bean
    public RestTemplate createRestTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        SimpleClientHttpRequestFactory requestFactory = (SimpleClientHttpRequestFactory) restTemplate.getRequestFactory();
        requestFactory.setReadTimeout(readTimeout * 10000);
        requestFactory.setConnectTimeout(connectionTimeout * 10000);
        return restTemplate;
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins(
                                (null == allowedOrigins || StringUtils.EMPTY.equals(allowedOrigins)) ? "*"
                                : allowedOrigins)
                        .allowedHeaders("X-Requested-With","pwd", "Origin", "Content-Type", "Accept", "responseType", "Authorization", "Access-Control-Request-Method", "Access-Control-Request-Headers")
                        .allowedOriginPatterns("*")
                        .exposedHeaders("Access-Control-Allow-Origin", "Access-Control-Allow-Credentials")
                        .allowedMethods("HEAD", "OPTIONS", "GET", "POST", "PUT", "PATCH", "DELETE")
                        .allowCredentials(true).maxAge(MAX_AGE_SECS);
            }
        };
    }

    @Bean
    public Docket api() {

        return new Docket(DocumentationType.SWAGGER_2).select()
                .apis(RequestHandlerSelectors.basePackage("com.dgtt.business.Controller"))
                .paths(PathSelectors.any()).build().securitySchemes(Arrays.asList(apiKey()))
                //.paths(PathSelectors.ant("/swagger/")).build().securitySchemes(Arrays.asList(apiKey()))
                .securityContexts(Arrays.asList(securityContext())).apiInfo(getApiInformation());

    }

    private ApiKey apiKey() {
        return new ApiKey(StringUtils.API_KEY, StringUtils.AUTHORIZATION, StringUtils.HEADER);
    }

    private SecurityContext securityContext() {
        return SecurityContext.builder().securityReferences(defaultAuth()).forPaths(PathSelectors.any())
                .build();
    }

    private List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[]{authorizationScope};
        return Arrays.asList(new SecurityReference(StringUtils.API_KEY, authorizationScopes));
    }

    private ApiInfo getApiInformation() {

        return new ApiInfoBuilder().title("Swagger  REST API")
                .description("This is document Department Frequency API created using Spring Boot v2.5.5")
                .contact(new Contact("Developer sang : .......", "https://swagger.io/",
                        ""))
                .license("Apache 2.0").licenseUrl("http://www.apache.org/licenses/LICENSE-2.0.html")
                .version("1.0.0").build();
    }
}
