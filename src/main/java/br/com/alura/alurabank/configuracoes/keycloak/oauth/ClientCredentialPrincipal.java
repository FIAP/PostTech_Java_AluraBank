package br.com.alura.alurabank.configuracoes.keycloak.oauth;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Collections;

public class ClientCredentialPrincipal implements Authentication {
    private final String clientId;

    public ClientCredentialPrincipal(String clientId) {
        this.clientId = clientId;
    }

    public static ClientCredentialPrincipal withClientId(String clientId) {
        return new ClientCredentialPrincipal(clientId);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptySet();
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getDetails() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return this;
    }

    @Override
    public boolean isAuthenticated() {
        return false;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {

    }

    @Override
    public String getName() {
        return clientId;
    }
}
