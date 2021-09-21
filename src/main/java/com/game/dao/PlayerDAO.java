package com.game.dao;

import com.game.entity.Profession;
import com.game.entity.Race;
import com.game.entity.Player;
import com.game.repository.PlayerRepository;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class PlayerDAO {
    private List<Player> players;
    private final PlayerRepository playerRepository;

    public PlayerDAO(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    public List<Player> getPlayers(String name, String title, Race race, Profession profession, Long after,
                                   Long before, Boolean banned, Integer minExperience, Integer maxExperience,
                                   Integer minLevel, Integer maxLevel) {
        players = new ArrayList<Player>();
        final Date afterDate = after == null ? null : new Date(after);
        final Date beforeDate = before == null ? null : new Date(before);
        playerRepository.findAll().forEach(player -> {
            if (name != null && !player.getName().contains(name)) return;
            if (title != null && !player.getTitle().contains(title)) return;
            if ((race != null) && (player.getRace() != race)) return;
            if ((profession != null) && (player.getProfession() != profession)) return;
            if ((after != null) && (player.getBirthday().before(afterDate))) return;
            if ((before != null) && (player.getBirthday().after(beforeDate))) return;
            if ((banned != null) && !(player.getBanned() == banned)) return;
            if ((minExperience != null) && (player.getExperience() < minExperience)) return;
            if ((maxExperience != null) && (player.getExperience() > maxExperience)) return;
            if ((minLevel != null) && (player.getLevel() < minLevel)) return;
            if ((maxLevel != null) && (player.getLevel() > maxLevel)) return;
            players.add(player);
        });
        return players;
    }

    public Player getPlayer(Long id) {
        return playerRepository.findById(id).orElse(null);
    }

    public void savePlayer(Player player) {
        playerRepository.save(player);
    }

    public void deletePlayer(Long id) {
        playerRepository.deleteById(id);
    }
}
