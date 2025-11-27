package com.el_jobru.service;

import com.el_jobru.db.HibernateUtil;
import com.el_jobru.dto.mission.MissionDTO;
import com.el_jobru.dto.mission.MissionResponseDTO;
import com.el_jobru.models.level.Level;
import com.el_jobru.models.mission.Mission;
import com.el_jobru.repository.MissionRepository;
import io.javalin.http.NotFoundResponse;
import jakarta.persistence.EntityManager;

import java.util.List;
import java.util.stream.Collectors;

public class MissionService {

    private final MissionRepository missionRepository;

    public MissionService(MissionRepository missionRepository) {
        this.missionRepository = missionRepository;
    }

    public MissionResponseDTO registerMission(MissionDTO missionDTO) {
        Level minLevel = null;

        if (missionDTO.minLevelId() != null) {
            try (EntityManager em = HibernateUtil.getEntityManager()) {
                em.getTransaction().begin();

                minLevel = em.find(Level.class, missionDTO.minLevelId());

                if (minLevel == null) {
                    em.getTransaction().rollback();
                    throw new NotFoundResponse("Nível com id " + missionDTO.minLevelId() + " não encontrado");
                }
//                String sql = "SELECT l FROM Level l WHERE l.id = :number";
//
//                minLevel = em.createQuery(sql, Level.class)
//                        .setParameter("number", missionDTO.minLevelId())
//                        .getSingleResult();
            }

        }

        Mission mission = new Mission(missionDTO.title(), missionDTO.description(), missionDTO.reward(), minLevel);

        Mission savedMission = missionRepository.saveOrUpdate(mission);

        return new MissionResponseDTO(savedMission);
    }

    public List<MissionResponseDTO> getAllMissions() {
        List<Mission> missionsList = missionRepository.findAll();

        return missionsList.stream()
                .map(MissionResponseDTO::new)
                .collect(Collectors.toList());
    }
}
