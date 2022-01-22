package br.com.alura.alurabank.dominio;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;

public class Correntista {

    private String cpf;
    private String nome;

    private LocalDate dataEntrada = LocalDate.now();

    public Correntista(String cpf, String nome) {
        this.cpf = cpf;
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public String getNome() {
        return nome;
    }

    public LocalDate getDataEntrada() {
        return dataEntrada;
    }
}
