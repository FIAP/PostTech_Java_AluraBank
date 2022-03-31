package br.com.alura.alurabank.dominio;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotBlank;
import java.util.Objects;

@Embeddable
public class DadosDaConta {

    @NotBlank
    private String banco;
    @NotBlank
    private String agencia;
    @NotBlank
    private String numero;

    protected DadosDaConta() {
    }

    public DadosDaConta(String banco, String agencia, String numero) {
        this.banco = banco;
        this.agencia = agencia;
        this.numero = numero;
    }

    public String getBanco() {
        return banco;
    }

    public String getAgencia() {
        return agencia;
    }

    public String getNumero() {
        return numero;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DadosDaConta that = (DadosDaConta) o;
        return banco.equals(that.banco) && agencia.equals(that.agencia) && numero.equals(that.numero);
    }

    @Override
    public int hashCode() {
        return Objects.hash(banco, agencia, numero);
    }
}
