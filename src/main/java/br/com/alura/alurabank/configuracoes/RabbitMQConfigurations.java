package br.com.alura.alurabank.configuracoes;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@EnableRabbit
@Configuration
public class RabbitMQConfigurations {

    // Queue
    @Bean
    public Queue filaDeEmail() {
        return QueueBuilder
                .durable("envio-de-emails")
                .build();
    }

    // Exchange
    @Bean
    public DirectExchange exchangeTransacao() {
        return new DirectExchange("transacao");
    }


    // Binding
    @Bean
    public Binding bindingTransacaoEEnvioDeEmail() {
        return BindingBuilder.bind(filaDeEmail())
                .to(exchangeTransacao()).withQueueName();
    }
}
