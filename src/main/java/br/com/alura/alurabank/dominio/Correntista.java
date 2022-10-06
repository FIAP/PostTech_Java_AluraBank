package br.com.alura.alurabank.dominio;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;

@Entity
public class Correntista {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @SuppressWarnings("PMD.UnusedPrivateField")
    private Integer id;

    private String cpf;
    private String nome;

    private LocalDate dataEntrada = LocalDate.now();

    private String email;

    private String externalId;

    protected Correntista(){}

    public Correntista(String cpf, String nome, String email) {
        this.cpf = cpf;
        this.nome = nome;
        this.email = email;
    }

    public String getCpf() {
        return cpf;
    }

    public String getNome() {
        return nome;
    }

    public LocalDate getDataEntrada() {
        return dataEntrada;
    }

    public String getExternalId() {
        return externalId;
    }

    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }

    public String getEmail() {
        return email;
    }

    public void setInternetBankAccount(InternetBankAccount account) {
        this.email = account.getEmail();
        this.externalId = account.getId();
    }

    public Integer getId() {
        return id;
    }

    public boolean hasInternetBank() {
        return externalId != null && !externalId.isEmpty();
    }
}
