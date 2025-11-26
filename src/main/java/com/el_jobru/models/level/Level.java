package com.el_jobru.models.level;

import com.el_jobru.models.BaseObject;
import com.el_jobru.models.user.User;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "levels")
public class Level implements BaseObject<Integer> {

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

    @Override
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
