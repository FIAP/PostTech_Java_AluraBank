package br.com.alura.alurabank.hexagonal.core.interactors;

import br.com.alura.alurabank.hexagonal.core.entities.Profile;
import br.com.alura.alurabank.hexagonal.core.repository.AccountInfoRepository;
import org.springframework.stereotype.Component;

@Component
public class ReadProfileImpl  implements ReadProfile{

    private final UserInfoToProfile converter;
    private final AccountInfoRepository repository;

    public ReadProfileImpl(UserInfoToProfile converter, AccountInfoRepository repository) {
        this.converter = converter;
        this.repository = repository;
    }


    @Override
    public Profile loadProfileBy(UserInfo userInfo) {
        var profile = converter.convert(userInfo);

        repository
                .findBySubject(userInfo.getSubject())
                .ifPresent(profile::withAccount);


        return profile;
    }
}
