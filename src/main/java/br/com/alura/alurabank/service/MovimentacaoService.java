package br.com.alura.alurabank.service;

import br.com.alura.alurabank.controller.form.FiltraMovimentacaoForm;
import br.com.alura.alurabank.controller.view.MovimentacaoView;
import br.com.alura.alurabank.converters.MovimentacaoConverter;
import br.com.alura.alurabank.dominio.ContaCorrente;
import br.com.alura.alurabank.dominio.DadosDaConta;
import br.com.alura.alurabank.dominio.MovimentacaoDeConta;
import br.com.alura.alurabank.dominio.Operacao;
import br.com.alura.alurabank.repositorio.MovimentacaoRepository;
import br.com.alura.alurabank.repositorio.RepositorioContasCorrente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MovimentacaoService {

    @Autowired
    private MovimentacaoRepository repository;

    @Autowired
    private RepositorioContasCorrente contasCorrenteRepository;

    @Autowired
    private MovimentacaoConverter converter;

    public List<MovimentacaoView> filtraMovimentacoes(FiltraMovimentacaoForm form) {
        DadosDaConta dadosDaConta = form.toDadosDaConta();

        ContaCorrente conta = contasCorrenteRepository.findByDadosDaConta(dadosDaConta)
                .orElseThrow(() -> {throw new IllegalArgumentException("Conta não encontrada.");});


        List<MovimentacaoDeConta> movimentacoes;

        if (form.hasFilter()) {
            LocalDateTime inicio = form.toInicio();
            LocalDateTime fim = form.toFim();
            Operacao operacao = form.getOperacao();

            movimentacoes = repository.filterMovimentacoesBy(dadosDaConta, inicio, fim, operacao);
        } else  {
            movimentacoes = repository.findAllByConta(conta);
        }

        return movimentacoes.stream()
                .map(converter::convert)
                .collect(Collectors.toList());
    }
}
