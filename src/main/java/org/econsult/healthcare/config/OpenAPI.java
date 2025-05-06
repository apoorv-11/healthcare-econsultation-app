package org.econsult.healthcare.config;

import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPI {

    @Bean
    public io.swagger.v3.oas.models.OpenAPI customOpenAPI() {
        return new io.swagger.v3.oas.models.OpenAPI()
            .info(new Info()
                .title("Healthcare Appointment API")
                .version("1.0")
                .description("API documentation for managing doctor appointments, patients, and consultations."));
    }
}
