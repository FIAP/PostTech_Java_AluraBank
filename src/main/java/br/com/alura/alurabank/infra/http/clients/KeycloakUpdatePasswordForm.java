package br.com.alura.alurabank.infra.http.clients;

public class KeycloakUpdatePasswordForm {
    private final String type = "password";
    private final boolean temporary = false;
    private final String value;

    public KeycloakUpdatePasswordForm(String value) {
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public boolean isTemporary() {
        return temporary;
    }

    public String getValue() {
        return value;
    }
}
