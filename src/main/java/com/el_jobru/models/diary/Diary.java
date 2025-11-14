package com.el_jobru.models.diary;

import com.el_jobru.models.chapter.Chapter;
import com.el_jobru.models.user.User;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "diaries")
public class Diary {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column
    private String title;

    @ManyToOne
    @JoinColumn(name = "authorId")
    private User author;

    @OneToMany(mappedBy = "diary")
    private Set<Chapter> chapters = new HashSet<>();

    public Diary() {}

    public Diary(String title, User author) {
        if(title.isEmpty()) {
            this.title = "Untitled";
        } else {
            this.title = title;
        }
        this.author = author;
    }

    public Long getId() { return id; }

    public User getAuthor() { return author; }

    public String getTitle() { return title; }

    public void setTitle(String title) { this.title = title; }

    public Set<Chapter> getChapters() { return chapters; }

    public User getUser() { return author; }
}
