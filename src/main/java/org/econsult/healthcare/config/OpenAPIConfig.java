package org.econsult.healthcare.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.OpenAPI;

@Configuration
public class OpenAPIConfig {

    @Bean
    public OpenAPI healthcareOpenAPI() {
        return new OpenAPI().info(new Info()
                .title("Healthcare API")
                .version("1.0")
                .description("API documentation for the Healthcare application"));
    }
}
