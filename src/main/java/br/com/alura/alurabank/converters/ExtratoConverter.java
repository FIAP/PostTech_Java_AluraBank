package br.com.alura.alurabank.converters;

import br.com.alura.alurabank.controller.view.ExtratoView;
import br.com.alura.alurabank.dominio.ContaCorrente;
import br.com.alura.alurabank.dominio.MovimentacaoDeConta;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ExtratoConverter {

    public ExtratoView convert(ContaCorrente conta) {
        List<BigDecimal> valores = conta.getMovimentacoes()
                                        .stream()
                                        .map(MovimentacaoDeConta::getValor)
                                        .collect(Collectors.toList());
        return new ExtratoView(conta.getDadosDaConta(), valores ,conta.getSaldo());
    }
}
