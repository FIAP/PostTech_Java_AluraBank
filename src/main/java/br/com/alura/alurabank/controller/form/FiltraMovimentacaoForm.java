package br.com.alura.alurabank.controller.form;

import br.com.alura.alurabank.dominio.DadosDaConta;
import br.com.alura.alurabank.dominio.Operacao;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@Setter
public class FiltraMovimentacaoForm {
    private String banco;
    private String agencia;
    private String numero;
    private Operacao operacao;
    private BigDecimal valor;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate inicio;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate fim;


    public DadosDaConta toDadosDaConta() {
        return new DadosDaConta(banco, agencia, numero);
    }

    public LocalDateTime toInicio() {
        return LocalDateTime.of(inicio, LocalTime.MIN);
    }

    public LocalDateTime toFim() {
        return LocalDateTime.of(fim, LocalTime.MAX);
    }

    public boolean hasFilter() {
        return inicio != null && fim != null && operacao != null;
    }

}
