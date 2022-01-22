package br.com.alura.alurabank.factories;

import br.com.alura.alurabank.dominio.ContaCorrente;
import br.com.alura.alurabank.dominio.Correntista;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class ContaFactory {

    private static final String BANCO_DEFAULT = "333";
    private static final String AGENCIA_DEFAULT = "44444";

    private static final Random GERADOR_DE_NUMEROS = new Random();

    public ContaCorrente criarConta(Correntista correntista) {
        String numero = Integer.toString(gerarNumeroDaConta());
        return new ContaCorrente(BANCO_DEFAULT, AGENCIA_DEFAULT, numero, correntista);
    }

    private Integer gerarNumeroDaConta() {
        return GERADOR_DE_NUMEROS.nextInt(Integer.MAX_VALUE);
    }
}
