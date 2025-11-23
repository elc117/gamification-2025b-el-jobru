package com.el_jobru.controller;

import com.el_jobru.dto.chapter.ChapterResponseDTO;
import com.el_jobru.dto.chapter.RegisterChapterDTO;
import com.el_jobru.dto.diary.DiaryResponseDTO;
import com.el_jobru.models.chapter.Chapter;
import com.el_jobru.service.ChapterService;
import io.javalin.http.BadRequestResponse;
import io.javalin.http.Context;

import java.util.List;

public class ChapterController {
    private final ChapterService chapterService;

    public ChapterController(ChapterService chapterService) { this.chapterService = chapterService; }

    public void register(Context ctx) {
        try {
            RegisterChapterDTO chapterDTO = ctx.bodyAsClass(RegisterChapterDTO.class);

            Chapter chapter = chapterService.registerChapter(chapterDTO);

            ctx.status(200).json(chapter);
        } catch (Exception e) {
            throw new BadRequestResponse("Erro: " + e);
        }
    }

    public void update(Context ctx) {
        try {
            ChapterResponseDTO chapterDTO = ctx.bodyAsClass(ChapterResponseDTO.class);

            Chapter chapter = chapterService.updateChapter(chapterDTO);

            ctx.status(200).json(chapter);
        } catch (Exception e) {
            throw new BadRequestResponse("Erro: " + e);
        }
    }

    public void delete(Context ctx) {
        try {
            ChapterResponseDTO chapterDTO = ctx.bodyAsClass(ChapterResponseDTO.class);

            chapterService.deleteChapter(chapterDTO);

            ctx.status(200).json(chapterDTO);
        } catch (Exception e) {
            throw new BadRequestResponse("Erro: " + e);
        }
    }

    public void getDiaryAll(Context ctx) {
        try {
            DiaryResponseDTO diaryDTO =  ctx.bodyAsClass(DiaryResponseDTO.class);

            List<ChapterResponseDTO> chapters = chapterService.findDiaryChapters(diaryDTO);

            ctx.status(200).json(chapters);
        } catch (Exception e) {
            throw new BadRequestResponse("Erro: " + e);
        }
    }
}
