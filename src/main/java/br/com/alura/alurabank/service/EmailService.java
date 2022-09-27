package br.com.alura.alurabank.service;

import br.com.alura.alurabank.dominio.Operacao;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class EmailService {
    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    static class TransactionMail {
        private String nome;
        private String email;
        private Operacao operacao;
        private BigDecimal valor;
        private LocalDateTime data;

        TransactionMail(String nome, String email, Operacao operacao, BigDecimal valor, LocalDateTime data) {
            this.nome = nome;
            this.email = email;
            this.operacao = operacao;
            this.valor = valor;
            this.data = data;
        }

        public String getNome() {
            return nome;
        }

        public String getEmail() {
            return email;
        }

        public Operacao getOperacao() {
            return operacao;
        }

        public BigDecimal getValor() {
            return valor;
        }

        public LocalDateTime getData() {
            return data;
        }
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
