package br.com.alura.alurabank.controller;

import br.com.alura.alurabank.controller.form.ContaCorrenteForm;
import br.com.alura.alurabank.controller.form.FiltroMovimentacaoForm;
import br.com.alura.alurabank.controller.view.MovimentacaoView;
import br.com.alura.alurabank.dominio.DadosDaConta;
import br.com.alura.alurabank.dominio.Operacao;
import br.com.alura.alurabank.service.MovimentacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("movimentacoes")
public class MovimentacoesController {

    @Autowired
    private MovimentacaoService service;

    @GetMapping
    ResponseEntity<?> filter(FiltroMovimentacaoForm form) {

        DadosDaConta dadosDaConta = form.toDadosDaConta();
        Operacao operacao = form.getOperacao();
        LocalDateTime inicio = form.toInicio();
        LocalDateTime fim = form.toFim();

        List<MovimentacaoView> movimentacoes = service.filtar(dadosDaConta, operacao, inicio, fim);


        return ResponseEntity.ok(movimentacoes);
    }
}
