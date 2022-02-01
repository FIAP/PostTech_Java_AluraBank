package br.com.alura.alurabank.controller.view;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class MovimentacaoView {

    private LocalDateTime data;
    private BigDecimal valor;

}
