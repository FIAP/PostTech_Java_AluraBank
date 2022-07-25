package br.com.alura.alurabank.hexagonal.core.repository;

import br.com.alura.alurabank.hexagonal.core.entities.AccountInfo;

import java.util.Optional;

public interface AccountInfoRepository {
    Optional<AccountInfo> findBySubject(String id);
}
