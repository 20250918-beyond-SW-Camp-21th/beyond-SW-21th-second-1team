package com.valetparker.paymentservice.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(swaggerInfo())
                .components(new Components()
                        .addSecuritySchemes("X-User-Email", new SecurityScheme()
                                .type(SecurityScheme.Type.APIKEY)
                                .in(SecurityScheme.In.HEADER)
                                .name("X-User-Email")
                                .description("User Email from Gateway"))
                        .addSecuritySchemes("X-User-Role", new SecurityScheme()
                                .type(SecurityScheme.Type.APIKEY)
                                .in(SecurityScheme.In.HEADER)
                                .name("X-User-Role")
                                .description("User Role from Gateway"))
                        .addSecuritySchemes("X-User-No", new SecurityScheme()
                                .type(SecurityScheme.Type.APIKEY)
                                .in(SecurityScheme.In.HEADER)
                                .name("X-User-No")
                                .description("User No from Gateway")))
                .addSecurityItem(new SecurityRequirement()
                        .addList("X-User-Email")
                        .addList("X-User-Role")
                        .addList("X-User-No"));
    }

    private Info swaggerInfo() {
        return new Info()
                .title("ParkingHub API")
                .description("SpringBoot Swagger 연동 테스트 입니다.")
                .version("1.0.0");
    }
}