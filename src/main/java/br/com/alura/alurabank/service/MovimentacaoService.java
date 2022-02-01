package br.com.alura.alurabank.service;

import br.com.alura.alurabank.controller.view.MovimentacaoView;
import br.com.alura.alurabank.converters.MovimentacaoConverter;
import br.com.alura.alurabank.dominio.DadosDaConta;
import br.com.alura.alurabank.dominio.MovimentacaoDeConta;
import br.com.alura.alurabank.dominio.Operacao;
import br.com.alura.alurabank.repositorio.MovimentacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MovimentacaoService {

    @Autowired
    private MovimentacaoRepository repository;

    @Autowired
    private MovimentacaoConverter converter;


    public List<MovimentacaoView> filtar(DadosDaConta dadosDaConta, Operacao operacao,
                                  LocalDateTime inicio, LocalDateTime fim) {

        List<MovimentacaoDeConta> movimentacoes;

        if (operacao == null && inicio == null && fim == null) {
            movimentacoes = repository.filterAllByDadosDaConta(dadosDaConta);
        } else {
            movimentacoes = repository.filterAllByDadosDaContaAndOperacaoAndDataDeCriacaoBetween(dadosDaConta, operacao, inicio, fim);
        }

        return movimentacoes.stream().map(converter::convert).collect(Collectors.toList());
    }

}
