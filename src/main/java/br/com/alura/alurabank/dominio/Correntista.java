package br.com.alura.alurabank.dominio;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;

public class Correntista {

    @JsonProperty
    private String cpf;
    @JsonProperty
    private String nome;

    private LocalDate dataEntrada = LocalDate.now();

    public Correntista(String cpf, String nome) {
        this.cpf = cpf;
        this.nome = nome;
    }
}
