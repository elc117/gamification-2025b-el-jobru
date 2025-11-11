package com.el_jobru.dto.profile;

import com.el_jobru.dto.book.BookResponseDTO;
import com.el_jobru.models.user.User;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public class ProfileResponseDTO {
    private UUID id;
    private String name;
    private String email;
    private String role;
    private int age;
    private Set<BookResponseDTO> books;

    public ProfileResponseDTO(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail().value();
        this.role = user.getRole().toString();
        this.age = user.getAge().value();

        this.books = user.getBooks().stream()
                .map(BookResponseDTO::new)
                .collect(Collectors.toSet());
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getRole() {
        return role;
    }

    public int getAge() {
        return age;
    }

    public Set<BookResponseDTO> getBooks() {
        return books;
    }
}
