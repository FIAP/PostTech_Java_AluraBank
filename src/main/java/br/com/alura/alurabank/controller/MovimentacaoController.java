package br.com.alura.alurabank.controller;

import br.com.alura.alurabank.controller.form.FiltraMovimentacaoForm;
import br.com.alura.alurabank.controller.view.MovimentacaoView;
import br.com.alura.alurabank.dominio.DadosDaConta;
import br.com.alura.alurabank.dominio.Operacao;
import br.com.alura.alurabank.service.MovimentacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("movimentacoes")
public class MovimentacaoController {

    @Autowired
    private MovimentacaoService service;

    @GetMapping
    public ResponseEntity<?> filtrar(FiltraMovimentacaoForm form) {
        List<MovimentacaoView> movimentacoes = service.filtraMovimentacoes(form);

        return ResponseEntity.ok(movimentacoes);

    }

}
