package me.wangcai.dailyshop.model;

import org.bukkit.entity.Player;

public class Buyer {
    int times;
    String playerName;

    public Buyer(Player p){
        this.times = 0;
        this.playerName = p.getName();
    }

    public void addTimes(){
        times ++;
    }
}
