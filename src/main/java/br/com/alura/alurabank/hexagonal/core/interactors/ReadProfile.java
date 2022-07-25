package br.com.alura.alurabank.hexagonal.core.interactors;

import br.com.alura.alurabank.hexagonal.core.entities.Profile;

public interface ReadProfile {
    Profile loadProfileBy(UserInfo userInfo);

    class UserInfo {
        private String subject;
        private String name;
        private String email;

        public UserInfo(String subject, String name, String email) {
            this.subject = subject;
            this.name = name;
            this.email = email;
        }

        public String getEmail() {
            return email;
        }

        public String getName() {
            return name;
        }

        public String getSubject() {
            return subject;
        }
    }
}
