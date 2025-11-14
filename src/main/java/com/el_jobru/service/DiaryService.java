package com.el_jobru.service;

import com.el_jobru.dto.diary.RegisterDiaryDTO;
import com.el_jobru.models.diary.Diary;
import com.el_jobru.models.user.User;
import com.el_jobru.repository.DiaryRepository;
import com.el_jobru.repository.UserRepository;

import java.util.Optional;

public class DiaryService {
    private DiaryRepository diaryRepository;

    public DiaryService(DiaryRepository diaryRepository) { this.diaryRepository = diaryRepository; }

    public Diary registerDiary(RegisterDiaryDTO diaryDTO) {
        UserRepository userRepository = new UserRepository();
        Optional<User> optAuthor = userRepository.findById(diaryDTO.authorId());
        User author = optAuthor.orElseThrow(() -> new RuntimeException("Usuário não está registrado."));
        Diary diary = new Diary(diaryDTO.title(), author);
        return diaryRepository.save(diary);
    }
}
