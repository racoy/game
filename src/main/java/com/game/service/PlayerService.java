package com.game.service;

import com.game.controller.PlayerOrder;
import com.game.entity.Player;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class PlayerService {
    public static void sortPlayer(List<Player> players, PlayerOrder order) {
        if (order != null) {
            players.sort((player1, player2) -> {
                switch (order) {
                    case NAME: return player1.getName().compareTo(player2.getName());
                    case LEVEL: return player1.getLevel().compareTo(player2.getLevel());
                    case EXPERIENCE: return player1.getExperience().compareTo(player2.getExperience());
                    case BIRTHDAY: return player1.getBirthday().compareTo(player2.getBirthday());
                    default: return 0;
                }
            });
        }
    }

    public static boolean checkPlayer(Player player) {
        if (player.getBanned() == null) player.setBanned(false);
        return checkName(player.getName()) && checkTitle(player.getTitle()) && checkBirthday(player.getBirthday()) &&
                checkExperience(player.getExperience()) && (player.getRace() != null) && (player.getProfession() != null);
    }

    public static boolean checkName(String name) {
        if (name == null) return false;
        return (!name.equals("")) && (name.length() <= 12);
    }

    public static boolean checkTitle(String title) {
        if (title == null) return false;
        return title.length() <= 30;
    }

    public static boolean checkExperience(Integer experience) {
        if (experience == null) return false;
        return (experience > 0) && (experience <= 10000000);
    }

    public static boolean checkBirthday(Date birthday) {
        if (birthday == null) return false;
        return (birthday.after(new GregorianCalendar(2000, Calendar.JANUARY, 1).getTime()))
                && birthday.before(new GregorianCalendar(3000, Calendar.DECEMBER, 31).getTime());
    }

    public static Integer calculateLevel(Player player) {
        return (int) ((Math.sqrt(2500 + 200 * player.getExperience()) - 50) / 100.0);
    }

    public static Integer calculateUntilNextLevel(Player player) {
        return 50*(player.getLevel() + 1)*(player.getLevel() + 2) - player.getExperience();
    }

}
