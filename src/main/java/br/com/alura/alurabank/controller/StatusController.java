package br.com.alura.alurabank.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StatusController {

    @GetMapping(path = "/status")
    public String status(){
        return "ok!";
    }
}
