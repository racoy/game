package com.game.controller;

import com.game.dao.PlayerDAO;
import com.game.entity.Player;
import com.game.entity.Profession;
import com.game.entity.Race;
import com.game.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/rest")
public class PlayerController {
    private final PlayerDAO playerDAO;

    @Autowired
    public PlayerController(PlayerDAO playerDAO) {
        this.playerDAO = playerDAO;
    }

    @GetMapping("/players")
    public List<Player> sayRequest(@RequestParam(value = "name", required = false) String name,
                                   @RequestParam(value = "title", required = false) String title,
                                   @RequestParam(value = "race", required = false) Race race,
                                   @RequestParam(value = "profession", required = false) Profession profession,
                                   @RequestParam(value = "after", required = false) Long after,
                                   @RequestParam(value = "before", required = false) Long before,
                                   @RequestParam(value = "banned", required = false) Boolean banned,
                                   @RequestParam(value = "minExperience", required = false) Integer minExperience,
                                   @RequestParam(value = "maxExperience", required = false) Integer maxExperience,
                                   @RequestParam(value = "minLevel", required = false) Integer minLevel,
                                   @RequestParam(value = "maxLevel", required = false) Integer maxLevel,
                                   @RequestParam(value = "order", required = false) PlayerOrder order,
                                   @RequestParam(value = "pageNumber", required = false) Integer pageNumber,
                                   @RequestParam(value = "pageSize", required = false) Integer pageSize) {

        pageNumber = (pageNumber == null) ? 0 : pageNumber;
        pageSize = (pageSize == null) ? 3 : pageSize;
        List<Player> players = playerDAO.getPlayers(name, title, race, profession, after, before, banned,
                minExperience, maxExperience, minLevel, maxLevel);

        PlayerService.sortPlayer(players, order);

        int from = pageNumber*pageSize;
        int to = Integer.min(players.size(), from + pageSize);
        if (to < from) return new ArrayList<>();
        return players.subList(from, to);
    }

    @GetMapping("/players/count")
    public int count(@RequestParam(value = "name", required = false) String name,
                     @RequestParam(value = "title", required = false) String title,
                     @RequestParam(value = "race", required = false) Race race,
                     @RequestParam(value = "profession", required = false) Profession profession,
                     @RequestParam(value = "after", required = false) Long after,
                     @RequestParam(value = "before", required = false) Long before,
                     @RequestParam(value = "banned", required = false) Boolean banned,
                     @RequestParam(value = "minExperience", required = false) Integer minExperience,
                     @RequestParam(value = "maxExperience", required = false) Integer maxExperience,
                     @RequestParam(value = "minLevel", required = false) Integer minLevel,
                     @RequestParam(value = "maxLevel", required = false) Integer maxLevel) {

        return playerDAO.getPlayers(name, title, race, profession, after, before, banned,
                minExperience, maxExperience, minLevel, maxLevel).size();
    }

    @GetMapping("/players/{id}")
    public ResponseEntity<Player> getPlayer(@PathVariable(value = "id") String pathId) {
        try {
            long id = Long.parseLong(pathId);
            if (id < 1) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            Player player = playerDAO.getPlayer(id);
            if (player == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            return new ResponseEntity<>(player, HttpStatus.OK);
        } catch (NumberFormatException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/players")
    @ResponseBody
    public ResponseEntity<Player> createPlayer(@RequestBody Player player) {
        //System.out.println(player);
        if (!PlayerService.checkPlayer(player)) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        player.setLevel(PlayerService.calculateLevel(player));
        player.setUntilNextLevel(PlayerService.calculateUntilNextLevel(player));
        playerDAO.savePlayer(player);
        return new ResponseEntity<>(player, HttpStatus.OK);
    }

    @PostMapping("/players/{id}")
    public ResponseEntity<Player> updatePlayer(@PathVariable(value = "id") String pathId,
                                               @RequestBody Player newPlayer) {
        try {
            long id = Long.parseLong(pathId);
            if (id < 1) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            Player player = playerDAO.getPlayer(id);
            if (player == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            if (PlayerService.checkName(newPlayer.getName())) player.setName(newPlayer.getName());
            else if (newPlayer.getName() != null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            if (PlayerService.checkTitle(newPlayer.getTitle())) player.setTitle(newPlayer.getTitle());
            else if (newPlayer.getTitle() != null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            if (newPlayer.getRace() != null) player.setRace(newPlayer.getRace());
            if (newPlayer.getProfession() != null) player.setProfession(newPlayer.getProfession());
            if (PlayerService.checkBirthday(newPlayer.getBirthday())) player.setBirthday(newPlayer.getBirthday());
            else if (newPlayer.getBirthday() != null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            if (PlayerService.checkExperience(newPlayer.getExperience())) {
                player.setExperience(newPlayer.getExperience());
                player.setLevel(PlayerService.calculateLevel(player));
                player.setUntilNextLevel(PlayerService.calculateUntilNextLevel(player));
            }
            else if (newPlayer.getExperience() != null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            if (newPlayer.getBanned() != null) player.setBanned(newPlayer.getBanned());
            playerDAO.savePlayer(player);
            return new ResponseEntity<>(player, HttpStatus.OK);
        } catch (NumberFormatException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/players/{id}")
    public ResponseEntity deletePlayer(@PathVariable(value = "id") String pathId) {
        try {
            long id = Long.parseLong(pathId);
            if (id < 1) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            if (playerDAO.getPlayer(id) == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            playerDAO.deletePlayer(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (NumberFormatException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
