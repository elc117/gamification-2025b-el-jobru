package com.el_jobru.dto.chapter;

import com.el_jobru.models.chapter.Chapter;

public record ChapterResponseDTO (String title, String content) {
    public ChapterResponseDTO (Chapter chapter) {
        this(chapter.getTitle(), chapter.getContent());
    }
}
