package br.com.alura.alurabank.controller;

import br.com.alura.alurabank.controller.form.EfetivaTransferenciaForm;
import br.com.alura.alurabank.controller.form.TransferenciaIntentForm;
import br.com.alura.alurabank.controller.view.TransferenciaIntentView;
import br.com.alura.alurabank.service.TransferenciaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.NoSuchElementException;

@RestController
public class TranferenciaController {

    private final TransferenciaService service;

    public TranferenciaController(TransferenciaService service) {
        this.service = service;
    }


    @PostMapping("receber")
    public TransferenciaIntentView criaIntencaoDeTransferencia(@RequestBody TransferenciaIntentForm form) {
        var url = service.createIntent(form);

        return new TransferenciaIntentView(url);
    }


    @PutMapping("/pagar/{id}")
    public ResponseEntity<?> efetivaIntencaoDeTransferencia(@PathVariable Integer id, @RequestBody EfetivaTransferenciaForm form) {

        try {
            service.transfer(id, form);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.GONE).build();
        }

        return ResponseEntity.accepted().build();
    }


    @GetMapping("/listen/{hash}")
    public ResponseEntity<SseEmitter> ouvirEventos(@PathVariable String hash) {
        return service.createEmitter(hash)
                .map(ResponseEntity::ok)
                .orElseGet(ResponseEntity.notFound()::build);
    }
}
