package br.com.alura.alurabank.commands;

import br.com.alura.alurabank.dominio.Operacao;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class TransactionMail implements Serializable {
    private String nome;
    private String email;
    private Operacao operacao;
    private BigDecimal valor;
    private LocalDateTime data;

    public TransactionMail() {
    }

    public TransactionMail(String nome, String email, Operacao operacao, BigDecimal valor, LocalDateTime data) {
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
