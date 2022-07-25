package br.com.alura.alurabank.hexagonal.transport.rest;

import br.com.alura.alurabank.hexagonal.core.entities.Profile;
import br.com.alura.alurabank.hexagonal.core.interactors.ReadProfile;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProfileController {

    private final JwtToOidcUserInfo jwtConverter;
    private final OidcUserInfoToUserInfo userInfoConverter;

    private final ReadProfile readProfile;

    public ProfileController(JwtToOidcUserInfo jwtConverter, OidcUserInfoToUserInfo userInfoConverter, ReadProfile readProfile) {
        this.jwtConverter = jwtConverter;
        this.userInfoConverter = userInfoConverter;
        this.readProfile = readProfile;
    }


    @GetMapping("/me")
    public Profile me(@AuthenticationPrincipal Jwt jwt) {

        var oidcUserInfo = jwtConverter.convert(jwt);
        var userInfo = userInfoConverter.convert(oidcUserInfo);

        return readProfile.loadProfileBy(userInfo);
    }

}
