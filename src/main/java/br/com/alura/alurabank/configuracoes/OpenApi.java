package br.com.alura.alurabank.configuracoes;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "API para gerenciar contas correntes",
                description = "Com ela é possivel cadastrar contas correntes"
        )
)
public class OpenApi {
}
