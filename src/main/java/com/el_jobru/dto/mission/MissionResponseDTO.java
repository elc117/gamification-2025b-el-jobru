package com.el_jobru.dto.mission;


import com.el_jobru.dto.level.LevelResponseDTO;
import com.el_jobru.models.mission.Mission;

public record MissionResponseDTO(
        Integer id,
        String title,
        String description,
        Integer reward,
        LevelResponseDTO minLevel
) {
    public MissionResponseDTO(Mission mission) {
        this(
                mission.getId(),
                mission.getTitle(),
                mission.getDescription(),
                mission.getReward(),
                mission.getMinLevel() != null
                        ? new LevelResponseDTO(mission.getMinLevel())
                        : null
        );
    }
}
