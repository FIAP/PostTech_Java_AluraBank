package br.com.alura.alurabank.dominio;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class MovimentacaoDeContaTest {

    private static final String BANCO = "1234";
    private static final String AGENCIA = "1234";
    private static final String NUMERO = "1234";
    private static final Correntista CORRENTISTA = new Correntista("12341234", "Joao", "email@example.com");
    private static final ContaCorrente CONTA_CORRENTE = new ContaCorrente(BANCO, AGENCIA, NUMERO, CORRENTISTA);



    @Test
    void deveRetornarValorNegativoQuandoAOperacaoForDeSaque() {
        MovimentacaoDeConta movimentacao = new MovimentacaoDeConta(CONTA_CORRENTE, BigDecimal.valueOf(1234), Operacao.SAQUE);


        Assertions.assertEquals(BigDecimal.valueOf(-1234), movimentacao.getValor());
    }

    @Test
    void deveRetornarValorPositivoQuandoAOperacaoForDeDeposito() {
        MovimentacaoDeConta movimentacao = new MovimentacaoDeConta(CONTA_CORRENTE, BigDecimal.valueOf(1234), Operacao.DEPOSITO);


        Assertions.assertEquals(BigDecimal.valueOf(1234), movimentacao.getValor());
    }
}
