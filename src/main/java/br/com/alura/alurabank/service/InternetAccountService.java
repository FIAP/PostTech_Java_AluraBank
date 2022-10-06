package br.com.alura.alurabank.service;

import br.com.alura.alurabank.commands.CreateUser;
import br.com.alura.alurabank.controller.form.InternetBankAccountForm;
import br.com.alura.alurabank.repositorio.ContasCorrenteRepository;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class InternetAccountService {

    private final ContasCorrenteRepository contasCorrenteRepository;
    private final RabbitTemplate rabbitTemplate;

    public InternetAccountService(ContasCorrenteRepository contasCorrenteRepository, RabbitTemplate rabbitTemplate) {
        this.contasCorrenteRepository = contasCorrenteRepository;
        this.rabbitTemplate = rabbitTemplate;
    }

    public void createUser(InternetBankAccountForm form) {
        if (form.rePasswordMatch()) {
            var contaCorrente = contasCorrenteRepository.findByDadosDaConta(form.getDadosDaConta()).orElseThrow();

            var correntista = contaCorrente.getCorrentista();

            var command = new CreateUser(correntista.getId(), form.getEmail(), form.getPassword());

            rabbitTemplate.convertAndSend("create-user", "create-user", command);
        }
    }
}
