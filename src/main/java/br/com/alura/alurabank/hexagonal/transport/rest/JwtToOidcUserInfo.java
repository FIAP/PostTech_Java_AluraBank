package br.com.alura.alurabank.hexagonal.transport.rest;

import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

@Component
public class JwtToOidcUserInfo {
    public OidcUserInfo convert(Jwt jwt) {
        return new OidcUserInfo(jwt.getClaims());
    }
}
