package com.el_jobru.controller;

import com.el_jobru.dto.chapter.RegisterChapterDTO;
import com.el_jobru.models.chapter.Chapter;
import com.el_jobru.service.ChapterService;
import io.javalin.http.BadRequestResponse;
import io.javalin.http.Context;

public class ChapterController {
    private ChapterService chapterService;

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
}
