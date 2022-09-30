package br.com.alura.alurabank.controller;

import br.com.alura.alurabank.controller.form.CorrentistaForm;
import br.com.alura.alurabank.controller.view.DadosDaContaView;
import br.com.alura.alurabank.controller.view.ValidationErrorView;
import br.com.alura.alurabank.dominio.ContaCorrente;
import br.com.alura.alurabank.dominio.DadosDaConta;
import br.com.alura.alurabank.repositorio.ContasCorrenteRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.context.WebApplicationContext;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.blankOrNullString;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@ActiveProfiles("test")
@WebAppConfiguration
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@Testcontainers
class ContaControllerTest {

    @Autowired
    ContasCorrenteRepository repository;


    @Autowired
    ObjectMapper mapper;
    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;

    @BeforeEach
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }


    @Test
    void deveSalvarUmaContaCorrente() throws Exception {

        String name = "Fulano";
        String cpf = "62669747035";
        String email = "email@example.com";

        var payload = new CorrentistaForm(name, cpf, email);


        assertThat(repository.count()).isEqualTo(0);

        AtomicReference<DadosDaContaView> response = new AtomicReference<>(null);

        mvc.perform(post("/contas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsBytes(payload))
                        .with(jwt())
                )
                .andExpectAll(
                        status().isCreated(),
                        jsonPath("$.banco", is(not(blankOrNullString()))),
                        jsonPath("$.agencia", is(not(blankOrNullString()))),
                        jsonPath("$.numero", is(not(blankOrNullString())))
                )
                .andDo(result -> {
                    response.set(mapper.readValue(result.getResponse().getContentAsString(), DadosDaContaView.class));
                });

        assertThat(repository.count()).isEqualTo(1);

        assertThat(response.get().getBanco()).isNotBlank();
        assertThat(response.get().getAgencia()).isNotBlank();
        assertThat(response.get().getNumero()).isNotBlank();

        var dadosDaConta = new DadosDaConta(
                response.get().getBanco(),
                response.get().getAgencia(),
                response.get().getNumero()
        );

        Optional<ContaCorrente> optionalContaCorrente = repository.findByDadosDaConta(dadosDaConta);

        assertThat(optionalContaCorrente).isPresent();

        ContaCorrente contaCorrente = optionalContaCorrente.get();

        assertThat(contaCorrente.getDadosDaConta()).isEqualTo(dadosDaConta);

        assertThat(contaCorrente.getCorrentista().getNome()).isEqualTo(name);
        assertThat(contaCorrente.getCorrentista().getCpf()).isEqualTo(cpf);

    }

    @Test
    void deveRetornarUmErroAoCriarContaCorrenteComCPFErrado() throws Exception {

        String name = "Fulano";
        String cpf = "123412340";
        String email = "email@example.com";

        var payload = new CorrentistaForm(name, cpf, email);
        AtomicReference<ValidationErrorView> response = new AtomicReference<>(null);

        assertThat(repository.count()).isEqualTo(0);

        mvc.perform(
                        post("/contas")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(mapper.writeValueAsString(payload))
                                .with(jwt())
                )
                .andExpect(status().isBadRequest())
                .andDo(result -> {
                    response.set(mapper.readValue(result.getResponse().getContentAsString(), ValidationErrorView.class));
                });

        assertThat(repository.count()).isEqualTo(0);

        ValidationErrorView body = response.get();

        assertThat(body.getFieldErrors()).hasSize(1);
        assertThat(body.getFieldErrors()).containsKey("cpf");
        assertThat(body.getGlobalErrors()).hasSize(0);


    }
}
