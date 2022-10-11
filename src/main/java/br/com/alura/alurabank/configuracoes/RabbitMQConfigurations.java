package br.com.alura.alurabank.configuracoes;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@EnableRabbit
@Configuration
public class RabbitMQConfigurations {
    @Bean
    public RabbitTemplate rabbitTemplate(final ConnectionFactory connectionFactory, Jackson2JsonMessageConverter converter) {
        final var rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(converter);
        return rabbitTemplate;
    }

    @Bean
    public Jackson2JsonMessageConverter producerJackson2MessageConverter(ObjectMapper mapper) {
        return new Jackson2JsonMessageConverter(mapper);
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
                .withArgument("x-dead-letter-exchange", "")
                .withArgument("x-dead-letter-routing-key", "create-user-dlq")
                .build();
    }

    @Bean
    public Queue filaDLQDeCriacaoDeUsuario() {
        return QueueBuilder
                .durable("create-user-dlq")
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



    @Bean
    @Qualifier("pix")
    public DirectExchange exchangePix() {
        return new DirectExchange("transfer");
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
