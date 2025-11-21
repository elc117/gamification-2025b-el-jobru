package com.el_jobru.service;

import com.el_jobru.dto.chapter.RegisterChapterDTO;
import com.el_jobru.models.chapter.Chapter;
import com.el_jobru.models.diary.Diary;
import com.el_jobru.repository.ChapterRepository;
import com.el_jobru.repository.DiaryRepository;

import java.util.Optional;

public class ChapterService {
    private ChapterRepository chapterRepository;

    public ChapterService(ChapterRepository chapterRepository) { this.chapterRepository = chapterRepository; }

    public Chapter registerChapter(RegisterChapterDTO chapterDTO) {
        DiaryRepository diaryRepository = new DiaryRepository();
        Optional<Diary> optDiary = diaryRepository.findById(chapterDTO.diaryId());
        Diary diary = optDiary.orElseThrow(() -> new RuntimeException("Diário não encontrado no banco de dados"));

        Chapter chapter = new Chapter(chapterDTO.title(), chapterDTO.content(), diary);
        return chapterRepository.save(chapter);
    }
}
