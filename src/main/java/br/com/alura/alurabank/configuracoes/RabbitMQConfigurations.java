package br.com.alura.alurabank.configuracoes;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@EnableRabbit
@Configuration
public class RabbitMQConfigurations {
    @Bean
    public RabbitTemplate rabbitTemplate(final ConnectionFactory connectionFactory) {
        final var rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(producerJackson2MessageConverter());
        return rabbitTemplate;
    }

    @Bean
    public Jackson2JsonMessageConverter producerJackson2MessageConverter() {
        return new Jackson2JsonMessageConverter();
    }


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
