package br.com.alura.alurabank.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StatusController {

    @GetMapping(path = "/status")
    public String status(){
        return "ok!";
    }

    @GetMapping("/token")
    public Jwt token(@AuthenticationPrincipal Jwt jwt) {
        return jwt;
    }
}
