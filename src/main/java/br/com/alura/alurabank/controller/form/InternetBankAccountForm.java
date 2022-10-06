package br.com.alura.alurabank.controller.form;

import br.com.alura.alurabank.dominio.DadosDaConta;

public class InternetBankAccountForm {
    private String email;
    private String password;
    private String rePassword;

    private DadosDaConta dadosDaConta;


    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getRePassword() {
        return rePassword;
    }


    public boolean rePasswordMatch() {
        return password.equals(rePassword);
    }

    public DadosDaConta getDadosDaConta() {
        return dadosDaConta;
    }
}
