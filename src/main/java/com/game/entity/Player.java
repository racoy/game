package com.game.entity;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.game.entity.Profession;
import com.game.entity.Race;

import javax.persistence.*;
import java.util.Date;

@JsonAutoDetect
@Entity
public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    String name;
    String title;
    @Enumerated(EnumType.STRING)
    Race race;
    @Enumerated(EnumType.STRING)
    Profession profession;
    Integer experience;
    Integer level;
    Integer untilNextLevel;
    @Temporal(TemporalType.DATE)
    Date birthday;
    Boolean banned;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getTitle() {
        return title;
    }

    public Race getRace() {
        return race;
    }

    public Profession getProfession() {
        return profession;
    }

    public Integer getExperience() {
        return experience;
    }

    public Integer getLevel() {
        return level;
    }

    public Integer getUntilNextLevel() {
        return untilNextLevel;
    }

    public Date getBirthday() {
        return birthday;
    }

    public Boolean getBanned() {
        return banned;
    }

    public void setBanned(Boolean banned) {
        this.banned = banned;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public void setUntilNextLevel(Integer untilNextLevel) {
        this.untilNextLevel = untilNextLevel;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setRace(Race race) {
        this.race = race;
    }

    public void setProfession(Profession profession) {
        this.profession = profession;
    }

    public void setExperience(Integer experience) {
        this.experience = experience;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    /*public Player(Long id, String name, String title, Race race, Profession profession,
                  Integer experience, Integer level, Integer untilNextLevel, Date birthday, Boolean banned) {
        this.id = id;
        this.name = name;
        this.title = title;
        this.race = race;
        this.profession = profession;
        this.experience = experience;
        this.level = level;
        this.untilNextLevel = untilNextLevel;
        this.birthday = birthday;
        this.banned = banned;
    }

    public Player(String name, String title, Race race, Profession profession, Integer experience, Date birthday, Boolean banned) {
        this.name = name;
        this.title = title;
        this.race = race;
        this.profession = profession;
        this.experience = experience;
        this.birthday = birthday;
        this.banned = banned;
    }

    public Player(String name, String title, Race race, Profession profession, Integer experience, Date birthday) {
        this.name = name;
        this.title = title;
        this.race = race;
        this.profession = profession;
        this.experience = experience;
        this.birthday = birthday;
    }*/

    public Player() {
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("id: " + id + '\n');
        stringBuilder.append("name: " + name+ '\n');
        stringBuilder.append("title: " + title+ '\n');
        stringBuilder.append("race: " + race+ '\n');
        stringBuilder.append("profession: " + profession+ '\n');
        stringBuilder.append("experience: " + experience+ '\n');
        stringBuilder.append("birthday: " + birthday+ '\n');
        stringBuilder.append("banned: " + banned+ '\n');
        stringBuilder.append("level: " + level+ '\n');
        stringBuilder.append("untilNextLevel: " + untilNextLevel+ '\n');
        return stringBuilder.toString();
    }
}
