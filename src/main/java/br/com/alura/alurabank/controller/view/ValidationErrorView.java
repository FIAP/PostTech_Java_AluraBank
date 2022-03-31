package br.com.alura.alurabank.controller.view;

import java.util.List;
import java.util.Map;

public class ValidationErrorView {
    Map<String, List<String>> fieldErrors;
    List<String> globalErrors;

    public ValidationErrorView(Map<String, List<String>> fieldErrors, List<String> globalErrors) {
        this.fieldErrors = fieldErrors;
        this.globalErrors = globalErrors;
    }

    public Map<String, List<String>> getFieldErrors() {
        return fieldErrors;
    }

    public List<String> getGlobalErrors() {
        return globalErrors;
    }
}
