package br.com.alura.alurabank.controller;

import br.com.alura.alurabank.controller.form.CorrentistaForm;
import br.com.alura.alurabank.controller.view.DadosDaContaView;
import br.com.alura.alurabank.controller.view.ValidationErrorView;
import br.com.alura.alurabank.dominio.ContaCorrente;
import br.com.alura.alurabank.dominio.DadosDaConta;
import br.com.alura.alurabank.repositorio.ContasCorrenteRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class ContaControllerTest {

    RestTemplate template = new RestTemplate();


    @LocalServerPort
    private Long port;

    @Autowired
    ContasCorrenteRepository repository;


    @Autowired
    ObjectMapper mapper;


    @Test
    void deveSalvarUmaContaCorrente() {

        String name = "Fulano";
        String cpf = "62669747035";

        var payload = new CorrentistaForm(name, cpf);


        assertThat(repository.count()).isEqualTo(0);

        DadosDaContaView response = template.postForObject(
                "http://localhost:" + port.toString() + "/contas",
                payload,
                DadosDaContaView.class
        );

        assertThat(repository.count()).isEqualTo(1);

        assertThat(response.getBanco()).isNotBlank();
        assertThat(response.getAgencia()).isNotBlank();
        assertThat(response.getNumero()).isNotBlank();

        var dadosDaConta = new DadosDaConta(
                response.getBanco(),
                response.getAgencia(),
                response.getNumero()
        );

        Optional<ContaCorrente> optionalContaCorrente = repository.findByDadosDaConta(dadosDaConta);


        assertThat(optionalContaCorrente).isPresent();

        ContaCorrente contaCorrente = optionalContaCorrente.get();

        assertThat(contaCorrente.getDadosDaConta()).isEqualTo(dadosDaConta);

        assertThat(contaCorrente.getCorrentista().getNome()).isEqualTo(name);
        assertThat(contaCorrente.getCorrentista().getCpf()).isEqualTo(cpf);

    }

    @Test
    void deveRetornarUmErroAoCriarContaCorrenteComCPFErrado() throws JsonProcessingException {

        String name = "Fulano";
        String cpf = "123412340";

        var payload = new CorrentistaForm(name, cpf);


        assertThat(repository.count()).isEqualTo(0);

        try {

            template.postForEntity(
                    "http://localhost:" + port.toString() + "/contas",
                    payload,
                    Object.class
            );
        } catch (HttpClientErrorException response) {

            assertThat(repository.count()).isEqualTo(0);

            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);

            var errorString = response.getResponseBodyAsString();

            ValidationErrorView body = mapper.readValue(errorString, ValidationErrorView.class);

            assertThat(body.getFieldErrors()).hasSize(1);
            assertThat(body.getFieldErrors()).containsKey("cpf");
            assertThat(body.getGlobalErrors()).hasSize(0);

        }
    }
}