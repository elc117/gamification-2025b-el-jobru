package com.el_jobru.dto;

public record RegisterDTO(
        String name,
        String email,
        String password,
        int age
) {}
