package com.valetparker.reservationservice.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI().info(swaggerInfo());
    }

    private Info swaggerInfo() {
        return new Info()
                .title("ParkingHub Reservation API")
                .description("Reservation 기능 swagger api입니다.")
                .version("1.0.0");
    }
}