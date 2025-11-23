package com.el_jobru.service;

import com.el_jobru.dto.diary.DiaryResponseDTO;
// import com.el_jobru.dto.diary.RegisterDiaryDTO;
import com.el_jobru.controllerNew.dto.RegisterDiaryDTO; //essa é a DTO de teste
import com.el_jobru.models.diary.Diary;
import com.el_jobru.models.user.User;
import com.el_jobru.repository.DiaryRepository;

import java.util.List;
import java.util.Optional;

public class DiaryService {
    private final DiaryRepository diaryRepository;

    public DiaryService(DiaryRepository diaryRepository) { this.diaryRepository = diaryRepository; }

    public Diary registerDiary(RegisterDiaryDTO diaryDTO, User author) {
        Diary diary = new Diary(diaryDTO.getTitle(), author);
        return diaryRepository.save(diary);
    }

    public Diary updateDiary(DiaryResponseDTO diaryDTO) {
        Optional<Diary> optDiary = diaryRepository.findById(diaryDTO.getId());
        Diary diary = optDiary.orElseThrow(() -> new RuntimeException("Diário não existe."));

        return diaryRepository.update(diary);
    }

    //preciso deletar os capítulos junto dps
    public void deleteDiary(DiaryResponseDTO diaryDTO) {
        Optional<Diary> optDiary = diaryRepository.findById(diaryDTO.getId());
        Diary diaryDeleted = optDiary.orElseThrow(() -> new RuntimeException("Diário não encontrado no banco de dados"));

        diaryRepository.delete(diaryDeleted);
    }

    public List<DiaryResponseDTO> getAll(User user) {
        List<DiaryResponseDTO> diaries = diaryRepository.findAll(user)
                .stream()
                .map(DiaryResponseDTO::new)
                .toList();

        return diaries;
    }
}
