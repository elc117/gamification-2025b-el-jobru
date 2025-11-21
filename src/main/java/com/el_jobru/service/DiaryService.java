package com.el_jobru.service;

import com.el_jobru.dto.chapter.RegisterChapterDTO;
import com.el_jobru.dto.diary.RegisterDiaryDTO;
import com.el_jobru.models.chapter.Chapter;
import com.el_jobru.models.diary.Diary;
import com.el_jobru.models.user.User;
import com.el_jobru.repository.DiaryRepository;
import com.el_jobru.repository.UserRepository;

import java.util.Optional;

public class DiaryService {
    private DiaryRepository diaryRepository;

    public DiaryService(DiaryRepository diaryRepository) { this.diaryRepository = diaryRepository; }

    public Diary registerDiary(RegisterDiaryDTO diaryDTO, User author) {
        Diary diary = new Diary(diaryDTO.title(), author);
        return diaryRepository.save(diary);
    }
}
