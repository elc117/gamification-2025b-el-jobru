package com.el_jobru.service;

import com.el_jobru.db.HibernateUtil;
import com.el_jobru.dto.diary.DiaryResponseDTO;
import com.el_jobru.dto.diary.RegisterDiaryDTO;
import com.el_jobru.models.diary.Diary;
import com.el_jobru.models.user.User;
import com.el_jobru.repository.DiaryRepository;
import jakarta.persistence.EntityManager;

import java.util.List;
import java.util.stream.Collectors;

public class DiaryService {
    private DiaryRepository diaryRepository;

    public DiaryService(DiaryRepository diaryRepository) { this.diaryRepository = diaryRepository; }

    public Diary registerDiary(RegisterDiaryDTO diaryDTO, User author) {
        Diary diary = new Diary(diaryDTO.title(), author);
        return diaryRepository.save(diary);
    }

    public List<DiaryResponseDTO> getAll(User user) {
        List<DiaryResponseDTO> diaries = diaryRepository.findAll(user)
                .stream()
                .map(DiaryResponseDTO::new)
                .toList();

        return diaries;
    }
}
