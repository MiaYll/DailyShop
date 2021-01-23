package me.wangcai.dailyshop.model;

import me.wangcai.dailyshop.utils.ItemUtil;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class Commodity {
    private int value;
    private boolean isPoint;
    private List<String> cmds;
    private int  selfMaxBuy;
    private int serverMaxBuy;
    private int chance;
    private ItemStack icon;


    public Commodity(ConfigurationSection config){
        this.value = config.getInt("value");
        this.isPoint = config.getBoolean("isPoint");
        this.cmds = config.getStringList("cmds");
        this.selfMaxBuy = config.getInt("selfMaxBuy");
        this.serverMaxBuy = config.getInt("serverMaxBuy");
        this.chance = config.getInt("chance");
        int id = config.getInt("id");
        String name = config.getString("name");
        List<String> lore = config.getStringList("lore");
        this.icon = ItemUtil.createItem(id,name,lore);
    }

    public Commodity(){

    }

    public int getValue() {
        return value;
    }

    public boolean isPoint() {
        return isPoint;
    }

    public List<String> getCmds() {
        return cmds;
    }

    public int getSelfMaxBuy() {
        return selfMaxBuy;
    }

    public int getServerMaxBuy() {
        return serverMaxBuy;
    }

    public ItemStack getIcon() {
        return icon;
    }

    public int getChance() {
        return chance;
    }

    public void put(){

    }
}
