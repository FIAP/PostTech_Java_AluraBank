package br.com.alura.alurabank.hexagonal.transport.rest;

import br.com.alura.alurabank.hexagonal.core.interactors.ReadProfile;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.stereotype.Component;

@Component
public class OidcUserInfoToUserInfo {
    public ReadProfile.UserInfo convert(OidcUserInfo oidcUserInfo) {
        return new ReadProfile.UserInfo(oidcUserInfo.getSubject(), oidcUserInfo.getFullName(), oidcUserInfo.getEmail());
    }
}
