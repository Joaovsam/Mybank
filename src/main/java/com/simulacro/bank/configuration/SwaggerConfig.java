package com.simulacro.bank.configuration;

import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API do Banco Simulacro")
                        .version("1.0")
                        .description("Documentação da API para operações bancárias, incluindo contas, transações e investimentos.")
                );
    }

}
