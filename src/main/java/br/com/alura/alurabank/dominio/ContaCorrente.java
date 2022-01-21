package br.com.alura.alurabank.dominio;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@EqualsAndHashCode(of = {"dadosDaConta"})
public class ContaCorrente {

    @Getter
    @Setter
    private DadosDaConta dadosDaConta;

    private Correntista correntista;

    private BigDecimal saldo;

    public ContaCorrente(String banco, String agencia, String numero, Correntista correntista){
        this();
        this.dadosDaConta = new DadosDaConta(banco, agencia, numero);

        this.correntista = correntista;
    }

    public ContaCorrente() {
        this.saldo = BigDecimal.ZERO;
    }

    public int obterNumeroConta() {
        return Integer.parseInt(dadosDaConta.getNumero());
    }

    public boolean identificadaPor(String banco, String agencia, String numero) {
        return this.dadosDaConta.equals(new DadosDaConta(banco, agencia, numero));
    }

    public BigDecimal lerSaldo() {
        return saldo;
    }

    public void executar(Operacao operacao, BigDecimal valor) {
        saldo = operacao.executar(saldo, valor);
    }

    public String getBanco() {
        return dadosDaConta.getBanco();
    }

    public String getAgencia() {
        return dadosDaConta.getAgencia();
    }

    public String getNumero() {
        return dadosDaConta.getNumero();
    }
}
