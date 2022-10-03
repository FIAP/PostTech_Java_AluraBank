package br.com.alura.alurabank.consumers;

import br.com.alura.alurabank.commands.CreateUser;
import br.com.alura.alurabank.commands.TransactionMail;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class CriaUsuarioConsumer {

    private RestTemplate rest;

    @RabbitListener(queues = "create-user")
    public void handle(CreateUser command){
        // Buscar o correntista pelo ID

        // Efetuar o request POST para criar usuario no Keycloak

        // Efetuar o request PUT para definir a senha do usuario no Keycloak

        //Definir o Internet Bank Account para o correntista

        //Salvar o correntista
    }
}
