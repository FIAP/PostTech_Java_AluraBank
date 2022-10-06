package br.com.alura.alurabank.consumers;

import br.com.alura.alurabank.commands.TransactionMail;
import br.com.alura.alurabank.service.EmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class EnvioDeEmailConsumer {

    private static final Logger LOG = LoggerFactory.getLogger(EnvioDeEmailConsumer.class);

    private final EmailService emailService;

    public EnvioDeEmailConsumer(EmailService emailService) {
        this.emailService = emailService;
    }


    @RabbitListener(queues = "envio-de-emails")
    public void handle(TransactionMail mail){
        LOG.info("Chegou uma nova mensagem da fila");

        emailService.sendTransactionMail(mail);
    }
}
