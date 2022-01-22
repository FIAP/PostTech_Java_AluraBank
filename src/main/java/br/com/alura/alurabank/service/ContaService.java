package br.com.alura.alurabank.service;

import br.com.alura.alurabank.controller.form.MovimentacaoForm;
import br.com.alura.alurabank.controller.form.TransferenciaForm;
import br.com.alura.alurabank.dominio.ContaCorrente;
import br.com.alura.alurabank.dominio.DadosDaConta;
import br.com.alura.alurabank.dominio.MovimentacaoDeConta;
import br.com.alura.alurabank.dominio.Operacao;
import br.com.alura.alurabank.repositorio.RepositorioContasCorrente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class ContaService {

    @Autowired
    private RepositorioContasCorrente repositorioContasCorrente;


    public void movimentacao(MovimentacaoForm form) {
        movimentar(form.getDadosDaConta(), form.getValor(), form.getOperacao());
    }

    public void transferencia(TransferenciaForm form) {
        BigDecimal valor = form.getValor();

        DadosDaConta origem = form.getDadosDeOrigem();
        movimentar(origem, valor, Operacao.SAQUE);

        DadosDaConta destino = form.getDadosDeDestino();
        movimentar(destino, valor, Operacao.DEPOSITO);
    }

    public ContaCorrente extrato(DadosDaConta dadosDaConta) {
        Optional<ContaCorrente> opContaCorrente = repositorioContasCorrente
                .buscar(dadosDaConta.getBanco(), dadosDaConta.getAgencia(), dadosDaConta.getNumero());


        if (opContaCorrente.isEmpty()) {
            throw new IllegalArgumentException("Conta não encontrada");
        }

        ContaCorrente contaCorrente = opContaCorrente.get();

        return contaCorrente;
    }

    public void movimentar(DadosDaConta dadosDaConta, BigDecimal valor, Operacao operacao) {
        Optional<ContaCorrente> opContaCorrente = repositorioContasCorrente
                .buscar(dadosDaConta.getBanco(), dadosDaConta.getAgencia(), dadosDaConta.getNumero());


        if (opContaCorrente.isEmpty()) {
            throw new IllegalArgumentException("Conta não encontrada");
        }

        ContaCorrente contaCorrente = opContaCorrente.get();

        MovimentacaoDeConta movimentacao = new MovimentacaoDeConta(contaCorrente, valor, operacao);
        contaCorrente.movimentar(movimentacao);
        repositorioContasCorrente.salvar(contaCorrente);

    }
}
