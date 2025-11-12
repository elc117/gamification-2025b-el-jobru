package com.el_jobru.controller;

import com.el_jobru.dto.level.LevelDTO;
import com.el_jobru.dto.level.LevelResponseDTO;
import com.el_jobru.models.level.Level;
import com.el_jobru.service.LevelService;
import io.javalin.http.BadRequestResponse;
import io.javalin.http.Context;
import io.javalin.http.HttpStatus;

import java.util.List;

public class LevelController {
    private final LevelService levelService;

    public LevelController(LevelService levelService) {
        this.levelService = levelService;
    }

    public void register(Context ctx) {
        try {
            LevelDTO levelDTO = ctx.bodyAsClass(LevelDTO.class);

            Level newLevel = levelService.registerLevel(levelDTO);

            ctx.status(HttpStatus.OK).json(newLevel);
        } catch (Exception e) {
            throw new BadRequestResponse("Erro: " + e);
        }
    }

    public void getAll(Context ctx) {
        try {
            List<LevelResponseDTO> levels = levelService.getAllLevels();

            ctx.status(HttpStatus.OK).json(levels);
        } catch (Exception e) {
            throw new BadRequestResponse("Erro: " + e);
        }
    }
}
