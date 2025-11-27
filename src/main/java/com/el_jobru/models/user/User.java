package com.el_jobru.models.user;

import com.el_jobru.models.BaseObject;
import com.el_jobru.models.book.Book;
import com.el_jobru.models.level.Level;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "users")
public class User implements BaseObject<UUID> {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false, unique = true)
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column
    private Long exp;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "email", nullable = false, unique = true))
    private Email email;

    @Embedded
    @AttributeOverride(name = "hash", column = @Column(name = "password", nullable = false))
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Password password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRole role;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "age"))
    private Age age;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "user_books",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "book_id")
    )
    private Set<Book> books = new HashSet<>();

    public User() {}

    public User(Age age, String name, Email email, Password password, UserRole role){
        this.age = age;
        this.name = name;
        this.exp = 0L;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    @Override
    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getExp() { return Objects.requireNonNullElse(exp, 0L); }

    public void setExp(Long exp) { this.exp = exp; }

    public Email getEmail() {
        return email;
    }

    public void setEmail(Email email) {
        this.email = email;
    }

    public Password getPassword() {
        return password;
    }

    public void setPassword(Password password) {
        this.password = password;
    }

    public Age getAge() {
        return age;
    }

    public void setAge(Age age) {
        this.age = age;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public Set<Book> getBooks() {
        return books;
    }


    public void addBook(Book book) {
        this.books.add(book);
        book.getUsers().add(this);
    }

    public void removeBook(Book book) {
        this.books.remove(book);
        book.getUsers().remove(this);
    }
}
