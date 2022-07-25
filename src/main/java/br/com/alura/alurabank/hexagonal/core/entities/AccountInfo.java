package br.com.alura.alurabank.hexagonal.core.entities;

public class AccountInfo {
    private String bank;
    private String agency;
    private String number;


    public AccountInfo(String bank, String agency, String number) {
        this.bank = bank;
        this.agency = agency;
        this.number = number;
    }

    public String getBank() {
        return bank;
    }

    public String getAgency() {
        return agency;
    }

    public String getNumber() {
        return number;
    }
}
