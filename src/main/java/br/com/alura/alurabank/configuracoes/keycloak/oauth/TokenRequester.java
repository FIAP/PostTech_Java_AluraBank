package br.com.alura.alurabank.configuracoes.keycloak.oauth;

import org.springframework.security.oauth2.client.OAuth2AuthorizeRequest;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.stereotype.Service;

import static java.util.Objects.isNull;

@Service
public class TokenRequester {
    private final OAuth2AuthorizedClientManager manager;

    public TokenRequester(OAuth2AuthorizedClientManager manager) {
        this.manager = manager;
    }


    private static  OAuth2AuthorizeRequest buildRequestWith(String registrationId, String clientId) {
        var principal = ClientCredentialPrincipal.withClientId(clientId);

        return OAuth2AuthorizeRequest
                .withClientRegistrationId(registrationId)
                .principal(principal)
                .build();
    }

    public String getAccessToken(ClientRegistration registration) {
        var registrationId = registration.getRegistrationId();
        var clientId = registration.getClientId();
        var request = buildRequestWith(registrationId, clientId);
        var response = manager.authorize(request);

        if (isNull(response)) {
            throw new RuntimeException("Não foi possivel obter o token");
        }

        return response.getAccessToken().getTokenValue();

    }
}
