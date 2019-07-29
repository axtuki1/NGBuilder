package io.github.axtuki1.ngbuilder.system;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TeamData {

    ChatColor color;
    String name;
    List<UUID> players;

    public TeamData(String name, ChatColor color){
        this.color = color;
        this.name = name;
        players = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public ChatColor getColor() {
        return color;
    }

    public List<UUID> getPlayers() {
        return players;
    }

    public void addPlayer(UUID uuid){
        players.add(uuid);
    }

    public void addPlayer(Player p){
        addPlayer(p.getUniqueId());
    }

    public void removePlayer(UUID uuid){
        players.remove(uuid);
    }

    public void removePlayer(Player p){
        removePlayer(p.getUniqueId());
    }
}
