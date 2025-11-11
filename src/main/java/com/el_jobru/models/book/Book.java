package com.el_jobru.models.book;

import com.el_jobru.models.user.User;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    @Column
    private String title;

    @Column
    private String author;

    @ManyToMany(mappedBy = "books")
    private Set<User> users = new HashSet<>();

    public Book() {}

    public Book(String title, String author) {
        this.title = title;
        this.author = author;
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Set<User> getUsers() {
        return users;
    }
}
