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


    @Bean
    public Queue filaDeCriacaoDeUsuario() {
        return QueueBuilder
                .durable("create-user")
                .build();
    }

    // Exchange
    @Bean
    public DirectExchange exchangeTransacao() {
        return new DirectExchange("transacao");
    }

    @Bean
    public DirectExchange exchangeCriacaoDeUsuario() {
        return new DirectExchange("create-user");
    }


    // Binding
    @Bean
    public Binding bindingTransacaoEEnvioDeEmail() {
        return BindingBuilder.bind(filaDeEmail())
                .to(exchangeTransacao()).withQueueName();
    }

    @Bean
    public Binding bindingCriacaoDeUsuario() {
        return BindingBuilder.bind(filaDeCriacaoDeUsuario())
                .to(exchangeCriacaoDeUsuario()).withQueueName();
    }



}
