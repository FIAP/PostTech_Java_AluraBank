package br.com.alura.alurabank.factories;

import br.com.alura.alurabank.dominio.TransferenciaIntent;
import br.com.alura.alurabank.events.CreatedTransfer;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

@Component
public class SseEmitterFactory {
    private final ConnectionFactory connectionFactory;
    private final AmqpAdmin amqpAdmin;
    private final DirectExchange transferExchange;
    private final SimpleRabbitListenerContainerFactory listenerContainerFactory;
    private final Jackson2JsonMessageConverter messageConverter;

    public SseEmitterFactory(ConnectionFactory connectionFactory, AmqpAdmin amqpAdmin, @Qualifier("pix") DirectExchange transferExchange, SimpleRabbitListenerContainerFactory listenerContainerFactory, Jackson2JsonMessageConverter messageConverter) {
        this.connectionFactory = connectionFactory;
        this.amqpAdmin = amqpAdmin;
        this.transferExchange = transferExchange;
        this.listenerContainerFactory = listenerContainerFactory;
        this.messageConverter = messageConverter;
    }


    public SseEmitter factory(TransferenciaIntent intent) {
        var hash = intent.getHash();
        ensureQueueExists(hash);

        var emitter = new SseEmitter(15000L);

        var container = listenerContainerFactory.createListenerContainer();

        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(hash);

        Consumer<CreatedTransfer> consumer = (message) -> {
            try {
                emitter.send(message, MediaType.APPLICATION_JSON);
                emitter.complete();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        };


        var adapter = new MessageListenerAdapter(consumer);
        adapter.setDefaultListenerMethod("accept");
        adapter.setMessageConverter(messageConverter);


        container.setMessageListener(adapter);

        emitter.onCompletion(container::stop);
        emitter.onTimeout(container::stop);
        emitter.onError((error) -> container.stop());

        var executor = Executors.newSingleThreadExecutor();

        executor.submit(container::start);

        return emitter;
    }

    private void ensureQueueExists(String hash) {
        var info = amqpAdmin.getQueueInfo(hash);

        if (info == null) {
            var temporaryQueue = QueueBuilder.nonDurable(hash).exclusive().autoDelete().build();
            var binding = BindingBuilder
                    .bind(temporaryQueue)
                    .to(transferExchange)
                    .withQueueName();

            amqpAdmin.declareQueue(temporaryQueue);
            amqpAdmin.declareBinding(binding);
        }
    }
}
