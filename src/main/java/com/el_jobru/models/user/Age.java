package com.el_jobru.models.user;

import com.fasterxml.jackson.annotation.JsonValue;
import jakarta.persistence.Embeddable;

@Embeddable
public record Age(int value) {
    public Age {
        if (value < 0 || value > 120) {
            throw new IllegalArgumentException("Idade inválida: " + value);
        }
    }

    @JsonValue
    @Override
    public int value() {
        return value;
    }

}
