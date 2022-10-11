package br.com.alura.alurabank.controller.view;

import org.springframework.http.ResponseEntity;

public class TransferenciaIntentView  {
    private String url;

    TransferenciaIntentView() {
    }

    public TransferenciaIntentView(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }
}
