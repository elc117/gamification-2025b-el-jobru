//package com.el_jobru.controller;
//
//import com.el_jobru.dto.diary.DiaryResponseDTO;
//import com.el_jobru.dto.diary.RegisterDiaryDTO;
//import com.el_jobru.models.diary.Diary;
//import com.el_jobru.service.DiaryService;
//import io.javalin.http.BadRequestResponse;
//import io.javalin.http.Context;
//
//import java.util.List;
//
//public class DiaryController {
//    private final DiaryService diaryService;
//
//    public DiaryController(DiaryService diaryService) { this.diaryService = diaryService; }
//
//    public void register(Context ctx) {
//        try {
//            RegisterDiaryDTO dto = ctx.bodyAsClass(RegisterDiaryDTO.class);
//
//            Diary diary = diaryService.registerDiary(dto, ctx.attribute("currentUser"));
//
//            DiaryResponseDTO diaryResponse = new DiaryResponseDTO(diary);
//
//            ctx.status(200).json(diaryResponse);
//        } catch (Exception e) {
//            throw new BadRequestResponse("Erro: " + e);
//        }
//    }
//
//    public void update(Context ctx) {
//        try {
//            DiaryResponseDTO diaryDTO = ctx.bodyAsClass(DiaryResponseDTO.class);
//
//            Diary diary = diaryService.updateDiary(diaryDTO);
//
//            ctx.status(200).json(diary);
//        } catch (Exception e) {
//            throw new BadRequestResponse("Erro: " + e);
//        }
//    }
//
//    public void delete(Context ctx) {
//        try {
//            DiaryResponseDTO diaryDTO = ctx.bodyAsClass(DiaryResponseDTO.class);
//
//            diaryService.deleteDiary(diaryDTO);
//
//            ctx.status(200).json(diaryDTO);
//        } catch(Exception e){
//            throw new BadRequestResponse("Erro: " + e);
//        }
//    }
//
//    public void getAll(Context ctx) {
//        try {
//            List<DiaryResponseDTO> diaries = diaryService.getAll(ctx.attribute("currentUser"));
//
//            ctx.status(200).json(diaries);
//        } catch (Exception e) {
//            throw new BadRequestResponse("Erro: " + e);
//        }
//    }
//
//}
