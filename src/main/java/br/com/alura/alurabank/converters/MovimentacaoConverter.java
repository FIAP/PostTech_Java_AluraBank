package br.com.alura.alurabank.converters;

import br.com.alura.alurabank.controller.view.MovimentacaoView;
import br.com.alura.alurabank.dominio.MovimentacaoDeConta;
import org.springframework.stereotype.Component;

@Component
public class MovimentacaoConverter {
    public MovimentacaoView convert(MovimentacaoDeConta movimentacaoDeConta) {
        return new MovimentacaoView(
                movimentacaoDeConta.getData(),
                movimentacaoDeConta.getValor());
    }
}
