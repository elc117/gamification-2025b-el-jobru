package com.el_jobru.dto.mission;

public record MissionDTO (
    String title,
    String description,
    Integer reward,
    Integer minLevelId
) {}
