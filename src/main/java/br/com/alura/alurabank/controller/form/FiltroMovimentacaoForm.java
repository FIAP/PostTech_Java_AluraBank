package br.com.alura.alurabank.controller.form;

import br.com.alura.alurabank.dominio.DadosDaConta;
import br.com.alura.alurabank.dominio.Operacao;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@Setter
public class FiltroMovimentacaoForm {

    @JsonProperty
    private String agencia;

    @JsonProperty
    private String banco;

    @JsonProperty
    private String numero;

    @JsonProperty
    private Operacao operacao;

    @JsonProperty
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate inicio;

    @JsonProperty
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate fim;


    public DadosDaConta toDadosDaConta() {
        return new DadosDaConta(banco, agencia, numero);
    }


    public LocalDateTime toInicio() {
        return LocalDateTime.of(inicio, LocalTime.MIN);
    }

    public LocalDateTime toFim() {
        return  LocalDateTime.of(fim, LocalTime.MAX);
    }

}
