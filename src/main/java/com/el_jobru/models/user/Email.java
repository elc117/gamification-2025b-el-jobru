package com.el_jobru.models.user;

public record Email(String value) {

    private static final String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";

    public Email {
        if (value == null || !is_valid(value)) {
            throw new IllegalArgumentException("Email inválido: " + value);
        }
    }

    private boolean is_valid(String value) {
        return value.matches(emailRegex);
    }
}
