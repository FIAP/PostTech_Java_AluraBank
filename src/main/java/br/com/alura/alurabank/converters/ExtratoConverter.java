package br.com.alura.alurabank.converters;

import br.com.alura.alurabank.controller.view.ExtratoView;
import br.com.alura.alurabank.dominio.DadosDaConta;
import br.com.alura.alurabank.dominio.MovimentacaoDeConta;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ExtratoConverter {

    public ExtratoView convert(DadosDaConta dadosDaConta, List<MovimentacaoDeConta> movimentacoes) {
        List<BigDecimal> valores = movimentacoes
                                        .stream()
                                        .map(MovimentacaoDeConta::getValor)
                                        .collect(Collectors.toList());

        BigDecimal saldo = movimentacoes.stream()
                .map(MovimentacaoDeConta::getValor)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return new ExtratoView(dadosDaConta, valores , saldo);
    }
}
