package com.springboot.microservicio.growby.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springdoc.core.GroupedOpenApi;

/**
 * Configuración para la documentación de la API utilizando OpenAPI.
 */
@Configuration
public class OpenApiConfig {

    /**
     * Configura el grupo de la API para la documentación.
     *
     * @return Un objeto GroupedOpenApi que agrupa todas las rutas de la API.
     */
    @Bean
    public GroupedOpenApi api() {
        return GroupedOpenApi.builder()
                .group("api")
                .pathsToMatch("/**")
                .build();
    }
}
