package br.com.alura.alurabank.infra.http.clients;

import br.com.alura.alurabank.configuracoes.keycloak.oauth.KeycloakAuthInterceptor;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name="keycloak", url = "${keycloak.user.api.url}", configuration = KeycloakAuthInterceptor.class)
public interface KeycloakClient {

    @PostMapping("/users")
    ResponseEntity<?> createUser(@RequestBody KeycloakCreateUserForm form);

    @PutMapping("/users/{id}/reset-password")
    ResponseEntity<?> updatePassword(@RequestBody KeycloakUpdatePasswordForm form);
}
