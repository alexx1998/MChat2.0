package com.mordonia.mchat.util;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

public class NameTag {

    /**
     * Changes, creates or destroys a player's name through teams/scoreboards
     *
     * @param player The username of the player who's name is going to be changed
     * @param prefix The prefix of the player's name (usually the first name)
     * @param suffix The suffix of the player's name (usually the last name)
     * @param action The action taken (create, destroy, update)
     */
    public static void changePlayerName(Player player, String prefix, String suffix, TeamAction action) {
        Team team;
        Scoreboard scoreboard;


        if (player.getScoreboard() == null || prefix == null || suffix == null || action == null) {
            return;
        }

        scoreboard = player.getScoreboard();

        if (scoreboard.getTeam(player.getName()) == null) {
            scoreboard.registerNewTeam(player.getName());
        }

        team = scoreboard.getTeam(player.getName());
        team.setPrefix(Color(prefix));
        team.setSuffix(Color(suffix));
        team.setDisplayName(Color(player.getDisplayName()));
        team.setOption(Team.Option.NAME_TAG_VISIBILITY, Team.OptionStatus.ALWAYS);

        switch (action) {
            case CREATE:
                team.addEntry(player.getDisplayName());
                break;
            case UPDATE:
                team.unregister();
                scoreboard.registerNewTeam(player.getName());
                team = scoreboard.getTeam(player.getName());
                team.setPrefix(Color(prefix));
                team.setSuffix(Color(suffix));
                team.setOption(Team.Option.NAME_TAG_VISIBILITY, Team.OptionStatus.ALWAYS);
                team.addEntry(player.getDisplayName());
                break;
            case DESTROY:
                team.unregister();
                break;
        }
    }
    private static String Color(String input) {
        return ChatColor.translateAlternateColorCodes('&', input);
    }


}