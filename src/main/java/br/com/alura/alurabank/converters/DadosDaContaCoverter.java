package br.com.alura.alurabank.converters;

import br.com.alura.alurabank.controller.view.DadosDaContaView;
import br.com.alura.alurabank.dominio.DadosDaConta;
import org.springframework.stereotype.Component;

@Component
public class DadosDaContaCoverter {

    public DadosDaContaView convert(DadosDaConta dadosDaConta) {
        return new DadosDaContaView(
                dadosDaConta.getBanco(),
                dadosDaConta.getAgencia(),
                dadosDaConta.getNumero()
        );
    }

}
