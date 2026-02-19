package com.valetparker.reparkingservice.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {

        Info info = new Info()
                .title("Reparking Service API")
                .description("Reparking Service API")
                .version("1.0.0");

        String jwtSchemeName = "jwtAuth";

        SecurityRequirement securityRequirement = new SecurityRequirement()
                .addList(jwtSchemeName);

        Components components = new Components()
                .addSecuritySchemes(jwtSchemeName,
                        new SecurityScheme()
                                .name(jwtSchemeName)
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")
                );

        Server gatewayServer = new Server()
                .url("http://localhost:8000/api/v1/valetparker-reparking-service")
                .description("Gateway Server");

        Server localServer = new Server()
                .url("/")
                .description("Direct Access");

        return new OpenAPI()
                .info(info)
                .servers(List.of(gatewayServer, localServer))
                .addSecurityItem(securityRequirement)
                .components(components);
    }
}