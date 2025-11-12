package com.el_jobru.service;

import com.el_jobru.db.HibernateUtil;
import com.el_jobru.dto.level.LevelDTO;
import com.el_jobru.dto.level.LevelResponseDTO;
import com.el_jobru.models.level.Level;
import com.el_jobru.repository.LevelRepository;
import jakarta.persistence.EntityManager;

import java.util.List;
import java.util.stream.Collectors;

public class LevelService {
    private final LevelRepository levelRepository;

    public LevelService(LevelRepository levelRepository) {
        this.levelRepository = levelRepository;
    }

    public Level registerLevel(LevelDTO levelDTO) throws Exception {
        long lastLevelMaxXp;

        try(EntityManager em = HibernateUtil.getEntityManager()) {
            String sql = "SELECT COALESCE(MAX(l.maxXp), 0) FROM Level l";

            lastLevelMaxXp = em.createQuery(sql, Long.class)
                    .getSingleResult();
        }

        long nextLevelMinXp = lastLevelMaxXp + 1;

        if (levelDTO.maxXp() <= lastLevelMaxXp) {
            throw new IllegalArgumentException("maxXp não pode ser um valor menor do que: " + nextLevelMinXp);
        }

        Level level = new Level(levelDTO.name(), nextLevelMinXp, levelDTO.maxXp());

        return levelRepository.save(level);
    }

    public List<LevelResponseDTO> getAllLevels() {
        List<Level> levelsList = levelRepository.findAll();

        return levelsList.stream()
                .map(LevelResponseDTO::new)
                .collect(Collectors.toList());
    }
}
