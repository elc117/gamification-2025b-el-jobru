package com.el_jobru.models.mission;

import com.el_jobru.models.level.Level;
import jakarta.persistence.*;

@Entity
@Table(name = "missions")
public class Mission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String title;

    @Column
    private String description;

    @Column
    private Integer reward;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "min_level_id")
    private Level minLevel;

    public Mission() {}

    public Mission(String title, String description, Integer reward, Level minLevel) {
        this.title = title;
        this.description = description;
        this.reward = reward;
        this.minLevel = minLevel;
    }

    public Integer getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getReward() {
        return reward;
    }

    public void setReward(Integer reward) {
        this.reward = reward;
    }

    public Level getMinLevel() {
        return minLevel;
    }

    public void setMinLevel(Level minLevel) {
        this.minLevel = minLevel;
    }
}
