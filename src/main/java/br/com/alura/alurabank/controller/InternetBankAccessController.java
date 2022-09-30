package br.com.alura.alurabank.controller;

import br.com.alura.alurabank.controller.form.InternetBankAccountForm;
import br.com.alura.alurabank.service.InternetAccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class InternetBankAccessController {

    private InternetAccountService service;

    public InternetBankAccessController(InternetAccountService service) {
        this.service = service;
    }

    @PostMapping("internet-bank")
    public ResponseEntity<?> createAccount(@RequestBody InternetBankAccountForm form) {

        service.createUser(form);

        return ResponseEntity.accepted().build();

    }
}
