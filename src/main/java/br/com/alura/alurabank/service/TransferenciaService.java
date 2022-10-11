package br.com.alura.alurabank.service;

import br.com.alura.alurabank.controller.form.EfetivaTransferenciaForm;
import br.com.alura.alurabank.controller.form.TransferenciaForm;
import br.com.alura.alurabank.controller.form.TransferenciaIntentForm;
import br.com.alura.alurabank.dominio.TransferenciaIntent;
import br.com.alura.alurabank.events.CreatedTransfer;
import br.com.alura.alurabank.events.CreatedTransferStatus;
import br.com.alura.alurabank.factories.SseEmitterFactory;
import br.com.alura.alurabank.repositorio.ContasCorrenteRepository;
import br.com.alura.alurabank.repositorio.TransferenciaIntentRepository;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.Optional;
import java.util.UUID;

@Service
public class TransferenciaService {

    private final ContasCorrenteRepository contasCorrenteRepository;
    private final TransferenciaIntentRepository transferenciaIntentRepository;
    private final ContaService contaService;

    private final SseEmitterFactory emitterFactory;

    private final RabbitTemplate rabbitTemplate;

    public TransferenciaService(ContasCorrenteRepository contasCorrenteRepository, TransferenciaIntentRepository transferenciaIntentRepository, ContaService contaService, SseEmitterFactory emitterFactory, RabbitTemplate rabbitTemplate) {
        this.contasCorrenteRepository = contasCorrenteRepository;
        this.transferenciaIntentRepository = transferenciaIntentRepository;
        this.contaService = contaService;
        this.emitterFactory = emitterFactory;
        this.rabbitTemplate = rabbitTemplate;
    }


    public String createIntent(TransferenciaIntentForm form) {
        var hash = UUID.randomUUID().toString();
        var dadosDaConta = form.getDadosDaConta();
        var contaCorrente = contasCorrenteRepository.findByDadosDaConta(dadosDaConta).orElseThrow();

        var intent = new TransferenciaIntent(contaCorrente, form.getValor(), hash);

        transferenciaIntentRepository.save(intent);

        return "/qr-code/" + hash;

    }

    public void transfer(Integer id, EfetivaTransferenciaForm form) {
        var intent = transferenciaIntentRepository.findById(id).orElseThrow();

        if (intent.isEfetivada()) {
            throw new IllegalStateException("Intenção já efetivada");
        }

        var origem = form.getDadosDaConta();
        var destino = intent.getDadosDaConta();
        var valor = intent.getValor();

        var transferenciaForm = new TransferenciaForm(origem, destino, valor);

        CreatedTransfer createdTransfer = null;


        try {
            contaService.transferir(transferenciaForm);
            createdTransfer = new CreatedTransfer(id, CreatedTransferStatus.SUCCESS);
            intent.efetivar();
            transferenciaIntentRepository.save(intent);
        } catch (Exception e) {
            createdTransfer = new CreatedTransfer(id, CreatedTransferStatus.FAIL, e);
        }


        rabbitTemplate.convertAndSend("transfer", intent.getHash(), createdTransfer);
    }

    public Optional<SseEmitter> createEmitter(String hash) {
        return transferenciaIntentRepository.findByHash(hash)
                .map(emitterFactory::factory);
    }
}
