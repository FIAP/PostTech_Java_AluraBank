package br.com.alura.alurabank.controller.view;

import java.util.ArrayList;
import java.util.List;

public class ErrorView {

    List<String> globalErrors = new ArrayList<>();


    public List<String> getGlobalErrors() {
        return globalErrors;
    }

    public void addError(String error) {
        this.globalErrors.add(error);
    }
}
