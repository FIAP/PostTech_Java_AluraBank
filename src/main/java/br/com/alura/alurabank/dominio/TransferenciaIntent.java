package br.com.alura.alurabank.dominio;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
public class TransferenciaIntent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private BigDecimal valor;

    private LocalDateTime data = LocalDateTime.now();

    @ManyToOne
    private ContaCorrente conta;

    private String hash;

    private boolean efetivada;

    TransferenciaIntent() {
    }

    public TransferenciaIntent(ContaCorrente conta, BigDecimal valor, String hash) {
        this.conta = conta;
        this.valor = valor;
        this.hash = hash;
    }

    public Integer getId() {
        return id;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public ContaCorrente getConta() {
        return conta;
    }

    public LocalDateTime getData() {
        return data;
    }

    public String getHash() {
        return hash;
    }

    public DadosDaConta getDadosDaConta() {
        return conta.getDadosDaConta();
    }

    public boolean isEfetivada() {
        return efetivada;
    }

    public void efetivar() {
        efetivada = true;
    }
}
