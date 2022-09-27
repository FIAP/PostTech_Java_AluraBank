package br.com.alura.alurabank.controller;

import br.com.alura.alurabank.service.EmailService;
import lombok.val;
import org.springframework.data.repository.cdi.Eager;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;

@RestController
public class StatusController {

    private final EmailService emailService;

    public StatusController(EmailService mailSender) {
        this.emailService = mailSender;
    }

    @GetMapping(path = "/status")
    public String status(){
        return "ok!";
    }

    @GetMapping("/token")
    public Jwt token(@AuthenticationPrincipal Jwt jwt) {
        return jwt;
    }
}
