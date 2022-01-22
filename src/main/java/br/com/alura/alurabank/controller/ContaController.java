package br.com.alura.alurabank.controller;

import br.com.alura.alurabank.controller.form.ContaCorrenteForm;
import br.com.alura.alurabank.controller.form.CorrentistaForm;
import br.com.alura.alurabank.controller.form.MovimentacaoForm;
import br.com.alura.alurabank.controller.form.TransferenciaForm;
import br.com.alura.alurabank.dominio.ContaCorrente;
import br.com.alura.alurabank.dominio.Correntista;
import br.com.alura.alurabank.dominio.DadosDaConta;
import br.com.alura.alurabank.dominio.MovimentacaoDeConta;
import br.com.alura.alurabank.repositorio.RepositorioContasCorrente;
import br.com.alura.alurabank.service.ContaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/contas")
public class ContaController {

    @Autowired
    private RepositorioContasCorrente repositorioContasCorrente;

    @Autowired
    private ContaService service;

    @Autowired
    private Validator validator;

    @GetMapping
    public String consultarSaldo(@RequestParam(name = "banco") String banco,
                                 @RequestParam(name = "agencia") String agencia,
                                 @RequestParam(name = "numero") String numero){
        ContaCorrente contaEncontrada = repositorioContasCorrente.buscar(banco, agencia, numero).orElse(new ContaCorrente());

        return String.format("Banco: %s, Agência: %s, Conta: %s. Saldo: %s", banco, agencia, numero, contaEncontrada.getSaldo());
    }

    @PostMapping
    public ResponseEntity criarNovaConta(@RequestBody CorrentistaForm correntistaForm){
        Set<ConstraintViolation<CorrentistaForm>> violacoes = validator.validate(correntistaForm);
        Map<Object, Object> collect = validar(correntistaForm);
        if(!collect.isEmpty()) return ResponseEntity.badRequest().body(collect);
        
        Correntista correntista = correntistaForm.toCorrentista();
        String banco = "333";
        String agencia = "44444";
        String numero = Integer.toString(new Random().nextInt(Integer.MAX_VALUE));
        ContaCorrente conta = new ContaCorrente(banco, agencia, numero, correntista);

        repositorioContasCorrente.salvar(conta);

        return ResponseEntity.status(HttpStatus.CREATED).body(conta);
    }

    @DeleteMapping
    public ResponseEntity fecharConta(@RequestBody ContaCorrenteForm contaForm){
        Map<Object, Object> collect = validar(contaForm);
        if(!collect.isEmpty()) return ResponseEntity.badRequest().body(collect);

        Optional<ContaCorrente> opConta = repositorioContasCorrente
                .buscar(contaForm.getBanco(), contaForm.getAgencia(), contaForm.getNumero());

        if(opConta.isEmpty()) return ResponseEntity.notFound().build();

        ContaCorrente conta = opConta.get();
        repositorioContasCorrente.remover(conta);
        return ResponseEntity.ok("Conta removida com sucesso");
    }

    @PutMapping
    public ResponseEntity<String> movimentarConta(@RequestBody MovimentacaoForm form){
        try {
            service.movimentacao(form);
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }

        return ResponseEntity.ok("Movimentação realizada com sucesso");
    }

    @PutMapping("/transferir")
    public ResponseEntity<String> transferir(@RequestBody TransferenciaForm form){
        try {
            service.transferencia(form);
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }

        return ResponseEntity.ok("Transferência realizada com sucesso");
    }

    @GetMapping("/extrato")
    public ResponseEntity<String> extrato(DadosDaConta dadosDaConta){

        ContaCorrente conta;
        try {
            conta = service.extrato(dadosDaConta);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }

        StringBuilder sb = new StringBuilder();

        sb.append("Extrato - banco: ");
        sb.append(conta.getBanco());
        sb.append(" agência: ");
        sb.append(conta.getAgencia());
        sb.append(" conta: ");
        sb.append(conta.getNumero());
        sb.append("\n");

        sb.append("Movimentações: \n");
        conta.getMovimentacoes()
                .forEach(movimentacao -> sb.append("\t" + movimentacao.getValor() + "\n"));

        sb.append("Saldo: \t" + conta.getSaldo());

        return ResponseEntity.ok(sb.toString());
    }

    private <T> Map<Object, Object> validar(T form) {
        Set<ConstraintViolation<T>> violacoes = validator.validate(form);
        Map<Object, Object> collect = violacoes.stream()
                .collect(Collectors.toMap(violacao -> violacao.getPropertyPath(), violacao -> violacao.getMessage()));
        return collect;
    }


}




// curl -i -X DELETE http://localhost:8080/contas -H "Content-Type: application/json" -d '{"banco":"111","agencia":"222", "numero": "333"}'
// curl -i -X POST http://localhost:8080/contas -H "Content-Type: application/json" -d '{"nome":"Rodrigo Vieira","cpf":"90345210688"}'
// curl -i -X PUT http://localhost:8080/contas -H "Content-Type: application/json" -d '{"valor":"10.00", "operacao": 1, "conta": {"banco":"111","agencia":"222", "numero": "333"}}'
// curl -i -X GET "http://localhost:8080/contas?banco=111&agencia=222&numero=333"
