package br.com.alura.alurabank.configuracoes.keycloak.oauth;

import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;

@Configuration
public class KeycloakAuthInterceptor {
    private final ClientRegistrationRepository repository;
    private final TokenRequester tokenRequester;


    public KeycloakAuthInterceptor(ClientRegistrationRepository repository, TokenRequester tokenRequester) {
        this.repository = repository;
        this.tokenRequester = tokenRequester;
    }

    @Bean
    public RequestInterceptor requestInterceptor() {
        var registration = repository.findByRegistrationId("keycloak");
        var accessToken = tokenRequester.getAccessToken(registration);


        return requestTemplate -> requestTemplate.header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken);
    }
}
