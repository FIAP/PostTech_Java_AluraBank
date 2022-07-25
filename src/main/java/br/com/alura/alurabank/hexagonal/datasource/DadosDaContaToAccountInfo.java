package br.com.alura.alurabank.hexagonal.datasource;

import br.com.alura.alurabank.dominio.DadosDaConta;
import br.com.alura.alurabank.hexagonal.core.entities.AccountInfo;
import org.springframework.stereotype.Component;

@Component
public class DadosDaContaToAccountInfo {
    public AccountInfo convert(DadosDaConta dadosDaConta) {
        return new AccountInfo(dadosDaConta.getBanco(), dadosDaConta.getAgencia(), dadosDaConta.getNumero());
    }
}
