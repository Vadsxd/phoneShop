package com.shop.phoneshop.config;

import com.shop.phoneshop.security.jwt.JwtAuthentication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseBuilder;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.ArrayList;
import java.util.List;

import static springfox.documentation.service.ApiInfo.DEFAULT_CONTACT;

@Configuration
public class SwaggerConfig {
    @Bean
    public Docket api() {
        ResponseBuilder builder = new ResponseBuilder();
        Response unauthorizedResponse = builder.code("401").description("Не авторизован").build();
        List<Response> unauthorizedResponses = List.of(unauthorizedResponse);
        List<Response> adminResponses = List.of(
                unauthorizedResponse,
                builder.code("400").description("Ошибка валидации данных").build()
        );

        return new Docket(DocumentationType.SWAGGER_2)
                .useDefaultResponseMessages(false)
                .globalResponses(HttpMethod.GET, unauthorizedResponses)
                .globalResponses(HttpMethod.DELETE, unauthorizedResponses)
                .globalResponses(HttpMethod.POST, adminResponses)
                .globalResponses(HttpMethod.PUT, adminResponses)
                .ignoredParameterTypes(JwtAuthentication.class)
                .securityContexts(List.of(securityContext()))
                .securitySchemes(List.of(apiKey()))
                .select()
                .apis(RequestHandlerSelectors.withClassAnnotation(RestController.class))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo());
    }

    private ApiKey apiKey() {
        return new ApiKey("JWT", "Authorization", "header");
    }

    private List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return List.of(new SecurityReference("JWT", authorizationScopes));
    }

    private SecurityContext securityContext() {
        return SecurityContext.builder().securityReferences(defaultAuth()).build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfo(
                "Phone Shop",
                "Тестовый проект интернет - магазина",
                "1.0",
                "urn:tos",
                DEFAULT_CONTACT,
                "Apache 2.0",
                "http://www.apache.org/licenses/LICENSE-2.0",
                new ArrayList<>()
        );
    }
}

