package br.com.alura.alurabank.dominio;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(of = {"dadosDaConta"})
public class ContaCorrente {

    @Getter
    @Setter
    private DadosDaConta dadosDaConta;

    private Correntista correntista;

    private List<MovimentacaoDeConta> movimentacoes = new ArrayList<>();

    public ContaCorrente(String banco, String agencia, String numero, Correntista correntista){
        this.dadosDaConta = new DadosDaConta(banco, agencia, numero);
        this.correntista = correntista;
    }

    public ContaCorrente() {
    }

    public int obterNumeroConta() {
        return Integer.parseInt(dadosDaConta.getNumero());
    }

    public boolean identificadaPor(String banco, String agencia, String numero) {
        return this.dadosDaConta.equals(new DadosDaConta(banco, agencia, numero));
    }

    public BigDecimal getSaldo() {
        return movimentacoes
                .stream()
                .map(MovimentacaoDeConta::getValor)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public void movimentar(MovimentacaoDeConta movimentacao) {
        movimentacoes.add(movimentacao);
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

    public List<MovimentacaoDeConta> getMovimentacoes() {
        return movimentacoes;
    }
}
