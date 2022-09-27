package br.com.alura.alurabank.service;

import br.com.alura.alurabank.controller.exceptions.ContaNaoEncontradaException;
import br.com.alura.alurabank.controller.exceptions.SaldoInsuficienteException;
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
import br.com.alura.alurabank.repositorio.ContasCorrenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class ContaService {

    private final ContasCorrenteRepository contasCorrenteRepository;
    private final CorrentistaRepository correntistaRepository;
    private final MovimentacaoRepository movimentacaoRepository;
    private final ContaFactory factory;
    private final DadosDaContaCoverter dadosDaContaConverter;

    private final EmailService emailService;


    public ContaService(ContasCorrenteRepository contasCorrenteRepository, CorrentistaRepository correntistaRepository, MovimentacaoRepository movimentacaoRepository, ContaFactory factory, DadosDaContaCoverter dadosDaContaConverter, EmailService emailService, ExtratoConverter extratoConverter) {
        this.contasCorrenteRepository = contasCorrenteRepository;
        this.correntistaRepository = correntistaRepository;
        this.movimentacaoRepository = movimentacaoRepository;
        this.factory = factory;
        this.dadosDaContaConverter = dadosDaContaConverter;
        this.emailService = emailService;
        this.extratoConverter = extratoConverter;
    }

    @Autowired
    private ExtratoConverter extratoConverter;

    public ExtratoView consultarExtrato(ContaCorrenteForm form) {
        ContaCorrente conta = buscaContaPor(form.toDadosDaConta());

        List<MovimentacaoDeConta> movimentacoes = movimentacaoRepository.findAllByConta(conta);

        return extratoConverter.convert(conta.getDadosDaConta(), movimentacoes);
    }


    public DadosDaContaView criarConta(CorrentistaForm form) {
        Correntista correntista = form.toCorrentista();

        correntistaRepository.save(correntista);

        ContaCorrente conta = factory.criarConta(correntista);

        contasCorrenteRepository.save(conta);

        DadosDaConta dadosDaConta = conta.getDadosDaConta();
        return dadosDaContaConverter.convert(dadosDaConta);
    }

    public void fecharConta(DadosDaConta dadosDaConta) {
        ContaCorrente conta = buscaContaPor(dadosDaConta);

        contasCorrenteRepository.delete(conta);
    }

    @Transactional
    public void movimentar(MovimentacaoForm form) {
        movimentarConta(form.getDadosDaConta(), form.getValor(), form.getOperacao());
    }

    @Transactional
    public void transferir(TransferenciaForm form) {
        BigDecimal valor = form.getValor();

        DadosDaConta origem = form.getDadosDeOrigem();
        movimentarConta(origem, valor, Operacao.SAQUE);

        DadosDaConta destino = form.getDadosDeDestino();
        movimentarConta(destino, valor, Operacao.DEPOSITO);
    }

    private ContaCorrente buscaContaPor(DadosDaConta dadosDaConta) {
        Optional<ContaCorrente> opContaCorrente = contasCorrenteRepository
                .findByDadosDaConta(dadosDaConta);


        if (opContaCorrente.isEmpty()) {
            throw new ContaNaoEncontradaException("Conta não encontrada");
        }

        return opContaCorrente.get();
    }

    private void movimentarConta(DadosDaConta dadosDaConta, BigDecimal valor, Operacao operacao) {
        ContaCorrente contaCorrente = buscaContaPor(dadosDaConta);

        if (operacao.equals(Operacao.SAQUE)) {
            BigDecimal saldo = movimentacaoRepository
                    .findAllByConta(contaCorrente)
                    .stream()
                    .map(MovimentacaoDeConta::getValor)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            if (saldo.compareTo(valor) < 0 ) {
                throw new SaldoInsuficienteException("Saldo insuficiente para a operação");
            }
        }

        MovimentacaoDeConta movimentacao = new MovimentacaoDeConta(contaCorrente, valor, operacao);

        movimentacaoRepository.save(movimentacao);

        // INFO: Envio de notificação por email
        var correntista = contaCorrente.getCorrentista();

        var mail = new EmailService.TransactionMail(
                correntista.getNome(),
                correntista.getEmail(),
                movimentacao.getOperacao(),
                movimentacao.getValor(),
                movimentacao.getData()
        );
        emailService.sendTransactionMail(mail);

    }
}
