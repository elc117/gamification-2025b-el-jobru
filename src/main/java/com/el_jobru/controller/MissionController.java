package com.el_jobru.controller;

import com.el_jobru.dto.mission.MissionDTO;
import com.el_jobru.dto.mission.MissionResponseDTO;
import com.el_jobru.dto.profile.ProfileResponseDTO;
import com.el_jobru.service.MissionService;
import io.javalin.http.BadRequestResponse;
import io.javalin.http.Context;
import io.javalin.http.HttpStatus;

import java.util.List;

public class MissionController {

    private final MissionService missionService;

    public MissionController(MissionService missionService) {
        this.missionService = missionService;
    }

    public void register(Context ctx) {
        try {
            MissionDTO missionDTO = ctx.bodyAsClass(MissionDTO.class);

            MissionResponseDTO missionResponseDTO = missionService.registerMission(missionDTO);

            ctx.status(HttpStatus.CREATED).json(missionResponseDTO);
        } catch (Exception e) {
            throw new BadRequestResponse("Erro: " + e);
        }
    }

    public void getAll(Context ctx) {
        try {
            List<MissionResponseDTO> missionResponseDTOList = missionService.getAllMissions();

            ctx.status(HttpStatus.OK).json(missionResponseDTOList);
        } catch (Exception e) {
            throw new BadRequestResponse("Erro: " + e);
        }
    }
}
