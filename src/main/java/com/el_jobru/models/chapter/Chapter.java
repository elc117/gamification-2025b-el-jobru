package com.el_jobru.models.chapter;

import com.el_jobru.models.diary.Diary;
import jakarta.persistence.*;

@Entity
@Table(name = "chapters")
public class Chapter {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column
    private String title;

    @Lob
    @Column
    private String content;

    @ManyToOne
    @JoinColumn(name = "diaryId")
    private Diary diary;

    public Chapter() {}

    public Chapter(String title, String content, Diary diary) {
        if (title.isEmpty()) {
            this.title = "Untitled";
        } else {
            this.title = title;
        }
        this.content = content;
        this.diary = diary;
    }

    public Long getId() { return id; }

    public String getContent() { return content; }

    public void setContent(String content) { this.content = content; }

    public String getTitle() { return title; }

    public void setTitle(String title) { this.title = title; }

    public Diary getDiary() { return diary; }
}
