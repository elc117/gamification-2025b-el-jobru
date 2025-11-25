package com.el_jobru.service;

import com.el_jobru.dto.diary.DiaryResponseDTO;
import com.el_jobru.dto.diary.RegisterDiaryDTO;
import com.el_jobru.models.diary.Diary;
import com.el_jobru.models.user.User;
import com.el_jobru.repository.DiaryRepository;

import java.util.List;

public class DiaryService {
    private final DiaryRepository diaryRepository;

    public DiaryService(DiaryRepository diaryRepository) { this.diaryRepository = diaryRepository; }

    public Diary registerDiary(RegisterDiaryDTO diaryDTO, User author) {
        Diary diary = new Diary(diaryDTO.title(), author);
        return diaryRepository.saveOrUpdate(diary);
    }

    public List<DiaryResponseDTO> getAll(User user) {
        List<DiaryResponseDTO> diaries = diaryRepository.findAll(user)
                .stream()
                .map(DiaryResponseDTO::new)
                .toList();

        return diaries;
    }
}
