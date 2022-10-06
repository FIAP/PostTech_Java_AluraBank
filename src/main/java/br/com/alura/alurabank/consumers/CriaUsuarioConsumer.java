package br.com.alura.alurabank.consumers;

import br.com.alura.alurabank.commands.CreateUser;
import br.com.alura.alurabank.dominio.InternetBankAccount;
import br.com.alura.alurabank.infra.http.clients.KeycloakClient;
import br.com.alura.alurabank.infra.http.clients.KeycloakCreateUserForm;
import br.com.alura.alurabank.infra.http.clients.KeycloakUpdatePasswordForm;
import br.com.alura.alurabank.repositorio.CorrentistaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriTemplate;

@Component
public class CriaUsuarioConsumer {

    private static final Logger LOG = LoggerFactory.getLogger(CriaUsuarioConsumer.class);

    private final CorrentistaRepository repository;
    private final KeycloakClient keycloakClient;
    private final String template;

    public CriaUsuarioConsumer(CorrentistaRepository repository, KeycloakClient keycloakClient, @Value("${keycloak.user.api.created-user-uri-template}") String template) {
        this.repository = repository;
        this.keycloakClient = keycloakClient;
        this.template = template;
    }

    @RabbitListener(queues = "create-user")
    public void handle(CreateUser command) {
        LOG.info("Rebendo um comando para criar uma conta de internet {}", command);

        var correntista = repository
                .findById(command.getCorrentistaId())
                .orElseThrow();


//        if (correntista.hasInternetBank()) {
//            return;
//        }

        var email = command.getEmail();
        var createUserForm = new KeycloakCreateUserForm(email);

        var createUserResponse = keycloakClient.createUser(createUserForm);

        var location = createUserResponse.getHeaders().getLocation();
        var locationTemplate = new UriTemplate(template);
        var params = locationTemplate.match(location.toString());

        var externalId = params.get("id");

        var passwordForm = new KeycloakUpdatePasswordForm(command.getPassword());
        keycloakClient.updatePassword(externalId, passwordForm);

        var internetBankAccount = new InternetBankAccount(externalId, email);
        correntista.setInternetBankAccount(internetBankAccount);

        repository.save(correntista);

        LOG.info("Conta de internet para acesso ao banco criada com sucesso, {}", internetBankAccount);
    }
}
