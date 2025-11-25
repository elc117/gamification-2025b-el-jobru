package com.el_jobru.service;

import com.el_jobru.dto.chapter.ChapterResponseDTO;
import com.el_jobru.dto.chapter.RegisterChapterDTO;
import com.el_jobru.dto.diary.DiaryResponseDTO;
import com.el_jobru.models.chapter.Chapter;
import com.el_jobru.models.diary.Diary;
import com.el_jobru.repository.ChapterRepository;
import com.el_jobru.repository.DiaryRepository;

import java.util.List;
import java.util.Optional;

public class ChapterService {
    private final ChapterRepository chapterRepository;

    public ChapterService(ChapterRepository chapterRepository) { this.chapterRepository = chapterRepository; }

    public Chapter registerChapter(RegisterChapterDTO chapterDTO) {
        DiaryRepository diaryRepository = new DiaryRepository();
        Optional<Diary> optDiary = diaryRepository.findById(chapterDTO.diaryId(), Diary.class);
        Diary diary = optDiary.orElseThrow(() -> new RuntimeException("Diário não encontrado no banco de dados"));

        Chapter chapter = new Chapter(chapterDTO.title(), chapterDTO.content(), diary);
        return chapterRepository.saveOrUpdate(chapter);
    }

    public List<ChapterResponseDTO> findDiaryChapters(DiaryResponseDTO diaryDTO) {
        List<ChapterResponseDTO> chapters = chapterRepository.findAllDiary(diaryDTO.getId())
                .stream()
                .map(ChapterResponseDTO::new)
                .toList();

        return chapters;
    }
}
