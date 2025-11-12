package com.el_jobru.dto.level;

import com.el_jobru.models.level.Level;

public record LevelResponseDTO(Integer id, String name, long minXp, long maxXp) {
    public LevelResponseDTO(Level level) {
        this(
            level.getId(),
            level.getName(),
            level.getMinXp(),
            level.getMaxXp()
        );
    }
}
