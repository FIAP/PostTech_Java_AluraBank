package br.com.alura.alurabank.infra.http.clients;

public class KeycloakCreateUserForm {
    private final String email;
    private final String username;
    private final boolean enabled = true;
    private final boolean emailVerified = true;

    public KeycloakCreateUserForm(String email) {
        this.email = email;
        this.username = email;
    }


    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public boolean isEmailVerified() {
        return emailVerified;
    }
}
