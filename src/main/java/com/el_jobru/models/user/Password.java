package com.el_jobru.models.user;

import jakarta.persistence.Embeddable;
import org.mindrot.jbcrypt.BCrypt;

@Embeddable
public record Password(String hash) {

    private static final String passwordRegex = "^(?=.*[A-Z])(?=.*[!#@$^%&])(?=.*[0-9])(?=.*[a-z]).{8,50}$";

    public Password {
        if (hash == null || hash.isBlank()) {
            throw new IllegalArgumentException("Hash não pode ser inválido");
        }
    }

    public static Password createPasswordFromPlainText(String value) {
        if (value == null || !isValid(value)) {
            throw new IllegalArgumentException("Senha deve ter ao menos 8 caracteres.");
        }
        String hash = BCrypt.hashpw(value, BCrypt.gensalt());
        return new Password(hash);
    }

    private static boolean isValid(String value) {
        return value.matches(passwordRegex);
    }

    public boolean matches(String value) {
        return BCrypt.checkpw(value, this.hash);
    }
}
