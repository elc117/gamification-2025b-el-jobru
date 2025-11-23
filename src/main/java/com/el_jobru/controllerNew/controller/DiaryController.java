package com.el_jobru.controllerNew.controller;

import com.el_jobru.controllerNew.dto.RegisterDiaryDTO;
import com.el_jobru.dto.diary.DiaryResponseDTO;
import com.el_jobru.models.diary.Diary;
import com.el_jobru.repository.DiaryRepository;
import com.el_jobru.service.DiaryService;
import io.javalin.http.Context;

public class DiaryController implements Controller<RegisterDiaryDTO, DiaryResponseDTO> {
    private final DiaryService diaryService;

    public DiaryController(DiaryService diaryService) { this.diaryService = diaryService; }

    public Class<RegisterDiaryDTO> getDTOClass() { return RegisterDiaryDTO.class; }

    public DiaryResponseDTO process(Context ctx, RegisterDiaryDTO diaryDTO){
        Diary diary = diaryService.registerDiary(diaryDTO, ctx.attribute("currentUser"));
        return new DiaryResponseDTO(diary);
    }
}

