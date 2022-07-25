package br.com.alura.alurabank.hexagonal.core.entities;

import java.util.Optional;

public class Profile {
    private final String name;
    private final String email;
    private AccountInfo accountInfo;

    public Profile(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public String getName() {
        return name;
    }


    public String getEmail() {
        return email;
    }

    public void withAccount(AccountInfo accountInfo) {
        this.accountInfo = accountInfo;
    }

    public Optional<AccountInfo> getAccountInfo() {
        return Optional.ofNullable(accountInfo);
    }
}
