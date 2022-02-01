package br.com.alura.alurabank.service;

import br.com.alura.alurabank.controller.form.ContaCorrenteForm;
import br.com.alura.alurabank.controller.form.CorrentistaForm;
import br.com.alura.alurabank.controller.form.MovimentacaoForm;
import br.com.alura.alurabank.controller.form.TransferenciaForm;
import br.com.alura.alurabank.controller.view.DadosDaContaView;
import br.com.alura.alurabank.controller.view.ExtratoView;
import br.com.alura.alurabank.converters.DadosDaContaCoverter;
import br.com.alura.alurabank.converters.ExtratoConverter;
import br.com.alura.alurabank.dominio.*;
import br.com.alura.alurabank.factories.ContaFactory;
import br.com.alura.alurabank.repositorio.CorrentistaRepository;
import br.com.alura.alurabank.repositorio.MovimentacaoRepository;
import br.com.alura.alurabank.repositorio.RepositorioContasCorrente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class ContaService {

    @Autowired
    private RepositorioContasCorrente repositorioContasCorrente;

    @Autowired
    private MovimentacaoRepository movimentacaoRepository;

    @Autowired
    private CorrentistaRepository correntistaRepository;

    @Autowired
    private ContaFactory factory;

    @Autowired
    private DadosDaContaCoverter dadosDaContaConverter;

    @Autowired
    private ExtratoConverter extratoConverter;

    public ExtratoView consultarExtrato(ContaCorrenteForm form) {
        ContaCorrente conta = buscaContaPor(form.toDadosDaConta());

        return extratoConverter.convert(conta);
    }


    public DadosDaContaView criarConta(CorrentistaForm form) {
        Correntista correntista = form.toCorrentista();

        correntistaRepository.save(correntista);

        ContaCorrente conta = factory.criarConta(correntista);

        repositorioContasCorrente.save(conta);

        DadosDaConta dadosDaConta = conta.getDadosDaConta();
        return dadosDaContaConverter.convert(dadosDaConta);
    }

    public void fecharConta(DadosDaConta dadosDaConta) {
        ContaCorrente conta = buscaContaPor(dadosDaConta);

        repositorioContasCorrente.delete(conta);
    }

    public void movimentar(MovimentacaoForm form) {
        movimentarConta(form.getDadosDaConta(), form.getValor(), form.getOperacao());
    }

    public void transferir(TransferenciaForm form) {
        BigDecimal valor = form.getValor();

        DadosDaConta origem = form.getDadosDeOrigem();
        movimentarConta(origem, valor, Operacao.SAQUE);

        DadosDaConta destino = form.getDadosDeDestino();
        movimentarConta(destino, valor, Operacao.DEPOSITO);
    }

    private ContaCorrente buscaContaPor(DadosDaConta dadosDaConta) {
        Optional<ContaCorrente> opContaCorrente = repositorioContasCorrente
                .findByDadosDaConta(dadosDaConta);


        if (opContaCorrente.isEmpty()) {
            throw new IllegalArgumentException("Conta não encontrada");
        }

        return opContaCorrente.get();
    }

    private void movimentarConta(DadosDaConta dadosDaConta, BigDecimal valor, Operacao operacao) {
        ContaCorrente contaCorrente = buscaContaPor(dadosDaConta);

        MovimentacaoDeConta movimentacao = new MovimentacaoDeConta(contaCorrente, valor, operacao);
        movimentacaoRepository.save(movimentacao);
    }
}
