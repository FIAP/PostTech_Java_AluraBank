package br.com.alura.alurabank.commands;

import java.io.Serializable;

public class CreateUser implements Serializable {
    private Integer correntistaId;
    private String email;
    private String password;

    CreateUser() {
    }

    public CreateUser(Integer correntistaId, String email, String password) {
        this.correntistaId = correntistaId;
        this.email = email;
        this.password = password;
    }

    public Integer getCorrentistaId() {
        return correntistaId;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return "CreateUser{" +
                "correntistaId=" + correntistaId +
                ", email='" + email + '\'' +
                '}';
    }
}
