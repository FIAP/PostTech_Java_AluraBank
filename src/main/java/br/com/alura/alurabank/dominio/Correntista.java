package br.com.alura.alurabank.dominio;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDate;

@Entity
public class Correntista {


    @Id
    private Integer id;

    private String cpf;
    private String nome;

    private LocalDate dataEntrada = LocalDate.now();

    protected Correntista(){}

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
