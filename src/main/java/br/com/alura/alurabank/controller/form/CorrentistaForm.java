package br.com.alura.alurabank.controller.form;

import br.com.alura.alurabank.dominio.Correntista;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.br.CPF;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class CorrentistaForm {

    @JsonProperty
    @CPF(message = "CPF invalido")
    @NotNull(message = "CPF é um campo obrigatório")
    private String cpf;
    @JsonProperty
    @NotBlank(message = "Nome do Correntista é um campo obrigatório e não pode estar em branco")
    private String nome;

    @JsonProperty
    @NotBlank(message = "Email do correntista é um campo obrigatório")
    private String email;

    CorrentistaForm() {
    }

    public CorrentistaForm(String nome, String cpf, String email) {
        this.cpf = cpf;
        this.nome = nome;
        this.email = email;
    }

    public Correntista toCorrentista(){
        return new Correntista(cpf, nome, email);
    }
}
