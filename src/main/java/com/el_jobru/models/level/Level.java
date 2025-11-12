package com.el_jobru.models.level;

import jakarta.persistence.*;

@Entity
@Table(name = "levels")
public class Level {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String name;

    @Column(name = "min_xp")
    private long minXp;

    @Column(name = "max_xp")
    private long maxXp;

    public Level() {}

    public Level(String name, long minXp, long maxXp) {
        this.name = name;
        this.minXp = minXp;
        this.maxXp = maxXp;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getMinXp() {
        return minXp;
    }

    public void setMinXp(long minXp) {
        this.minXp = minXp;
    }

    public long getMaxXp() {
        return maxXp;
    }

    public void setMaxXp(long maxXp) {
        this.maxXp = maxXp;
    }
}
