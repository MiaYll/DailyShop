package me.wangcai.dailyshop.model;

import org.bukkit.entity.Player;

public class Buyer {
    int times;
    String playerName;

    public Buyer(Player p){
        this.times = 0;
        this.playerName = p.getName();
    }

    public Buyer(String name, int times) {
        this.times = times;
        this.playerName = name;
    }

    public void addTimes(){
        times ++;
    }

    public int getTimes() {
        return times;
    }

    public String getPlayerName() {
        return playerName;
    }
}
