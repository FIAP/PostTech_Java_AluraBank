package br.com.alura.alurabank.controller.view;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class MovimentacaoView {

    private final LocalDateTime data;
    private final BigDecimal valor;

    public MovimentacaoView(LocalDateTime data, BigDecimal valor) {
        this.data = data;
        this.valor = valor;
    }

    public LocalDateTime getData() {
        return data;
    }

    public BigDecimal getValor() {
        return valor;
    }
}
