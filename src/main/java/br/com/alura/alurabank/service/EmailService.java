package br.com.alura.alurabank.service;

import br.com.alura.alurabank.commands.TransactionMail;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;

@Service
public class EmailService {
    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendTransactionMail(TransactionMail mail) {
        var mimeMessage = mailSender.createMimeMessage();

        MimeMessageHelper helper = null;
        try {
            helper = new MimeMessageHelper(mimeMessage, true);
            helper.setFrom("Alura Bank<no-reply@alurabank.com.br>");
            helper.setTo(mail.getNome() + " <"+ mail.getEmail() +">");
            helper.setSubject("Nova Transação");
            helper.setText("<h1>Testando 1234</h1>", true);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }

        mailSender.send(mimeMessage);
    }
}
