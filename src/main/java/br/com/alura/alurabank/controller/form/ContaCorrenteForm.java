package br.com.alura.alurabank.controller.form;

import br.com.alura.alurabank.dominio.DadosDaConta;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class ContaCorrenteForm {
    @JsonProperty
    @NotBlank(message = "banco é um campo obrigatório e não pode estar em branco")
    private String banco;
    @JsonProperty
    @NotBlank(message = "agência é um campo obrigatório e não pode estar em branco")
    private String agencia;
    @JsonProperty
    @NotBlank(message = "número é um campo obrigatório e não pode estar em branco")
    private String numero;

    public DadosDaConta toDadosDaConta() {
        return new DadosDaConta(banco, agencia, numero);
    }
}
