package com.meuportifolio.curso.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class ApiConfig {
    
    @Bean
    OpenAPI customOpenAPI() {
        return new OpenAPI()
        .info(new Info()
            .title("e-Shop Commerce")
            .version("v1.0.0")
            .description("API Rest desenvolvida durante o curso de Java + Orientação a Objetos que eu estou aprimorando conforme avanço nos estudos de Java e Spring")
            .contact(new Contact()
            .url("https://www.linkedin.com/in/matheus-freitas-0b27a8217/")
            .email("mf022rj@gmail.com")
            .name("Matheus Freitas")));
    }
}
