package br.com.alura.alurabank.controller;

import br.com.alura.alurabank.controller.form.ContaCorrenteForm;
import br.com.alura.alurabank.controller.form.CorrentistaForm;
import br.com.alura.alurabank.controller.form.MovimentacaoForm;
import br.com.alura.alurabank.controller.form.TransferenciaForm;
import br.com.alura.alurabank.controller.view.DadosDaContaView;
import br.com.alura.alurabank.controller.view.ExtratoView;
import br.com.alura.alurabank.service.ContaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/contas")
public class ContaController {

    @Autowired
    private ContaService service;

    @PostMapping
    public ResponseEntity<?> criarNovaConta(@Valid @RequestBody CorrentistaForm correntistaForm){

        DadosDaContaView dadosDaConta = service.criarConta(correntistaForm);

        return ResponseEntity.status(HttpStatus.CREATED).body(dadosDaConta);
    }

    @DeleteMapping
    public ResponseEntity<?> fecharConta(@Valid @RequestBody ContaCorrenteForm contaForm){

        service.fecharConta(contaForm.toDadosDaConta());


        return ResponseEntity.noContent().build();
    }

    @PutMapping
    public ResponseEntity<String> movimentarConta(@Valid @RequestBody MovimentacaoForm form){

        service.movimentar(form);

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/transferir")
    public ResponseEntity<String> transferir(@Valid @RequestBody TransferenciaForm form){

        service.transferir(form);


        return ResponseEntity.noContent().build();
    }

    @GetMapping("/extrato")
    public ResponseEntity<?> extrato(@Valid ContaCorrenteForm form){
        ExtratoView extrato = service.consultarExtrato(form);

        return ResponseEntity.ok(extrato);
    }



}




// curl -i -X DELETE http://localhost:8080/contas -H "Content-Type: application/json" -d '{"banco":"111","agencia":"222", "numero": "333"}'
// curl -i -X POST http://localhost:8080/contas -H "Content-Type: application/json" -d '{"nome":"Rodrigo Vieira","cpf":"90345210688"}'
// curl -i -X PUT http://localhost:8080/contas -H "Content-Type: application/json" -d '{"valor":"10.00", "operacao": 1, "conta": {"banco":"111","agencia":"222", "numero": "333"}}'
// curl -i -X GET "http://localhost:8080/contas?banco=111&agencia=222&numero=333"
