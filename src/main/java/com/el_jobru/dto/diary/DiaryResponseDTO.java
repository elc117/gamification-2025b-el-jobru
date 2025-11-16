package com.el_jobru.dto.diary;

import com.el_jobru.dto.chapter.ChapterResponseDTO;
import com.el_jobru.models.diary.Diary;

import java.util.Set;
import java.util.stream.Collectors;

public class DiaryResponseDTO {
    private Long id;
    private String title;
    private String author;
    private Set<ChapterResponseDTO> chapters;

    public DiaryResponseDTO(Diary diary) {
        this.id = diary.getId();
        this.title = diary.getTitle();
        this.author = diary.getAuthor().getName();
        this.chapters = diary.getChapters().stream().map(ChapterResponseDTO :: new).collect(Collectors.toSet());
    }

    public Long getId() { return id; }

    public String getTitle() { return title; }

    public String getAuthor() { return author; }

    public Set<ChapterResponseDTO> getChapters() { return chapters; }
}
