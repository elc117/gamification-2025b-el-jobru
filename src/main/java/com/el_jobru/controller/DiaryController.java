package com.el_jobru.controller;

import com.el_jobru.dto.diary.DiaryResponseDTO;
import com.el_jobru.dto.diary.RegisterDiaryDTO;
import com.el_jobru.models.diary.Diary;
import com.el_jobru.service.DiaryService;
import io.javalin.http.BadRequestResponse;
import io.javalin.http.Context;

public class DiaryController {
    private DiaryService diaryService;

    public DiaryController(DiaryService diaryService) { this.diaryService = diaryService; }

    public void register(Context ctx) {
        try {
            RegisterDiaryDTO dto = ctx.bodyAsClass(RegisterDiaryDTO.class);

            Diary diary = diaryService.registerDiary(dto);

            DiaryResponseDTO diaryResponse = new DiaryResponseDTO(diary);

            ctx.status(200).json(diaryResponse);
        } catch (Exception e) {
            throw new BadRequestResponse("Erro: " + e);
        }
    }

}
