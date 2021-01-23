package me.wangcai.dailyshop.model;

import me.wangcai.dailyshop.manager.ShopManager;
import me.wangcai.dailyshop.utils.ItemUtil;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class Commodity {
    protected int value;
    protected boolean isPoint;
    protected List<String> cmds;
    protected int  selfMaxBuy;
    protected int serverMaxBuy;
    protected int chance;
    protected ItemStack icon;
    protected boolean isPut;
    protected String name;


    public Commodity(String name, ConfigurationSection config){
        this.name = name;
        this.value = config.getInt("value");
        this.isPoint = config.getBoolean("isPoint");
        this.cmds = config.getStringList("cmds");
        this.selfMaxBuy = config.getInt("selfMaxBuy");
        this.serverMaxBuy = config.getInt("serverMaxBuy");
        this.chance = config.getInt("chance");
        int id = config.getInt("id");
        String itemName = config.getString("name").replaceAll("&","§");
        List<String> lore = config.getStringList("lore");
        this.icon = ItemUtil.createItem(id,itemName,lore);
        isPut = false;
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

    public boolean put(){
        if(isPut){
            return false;
        }
        ShopManager.getShopManager().getListedCommodityList().add(new ListedCommodity(this));
        return true;
    }

    public String getName() {
        return name;
    }
}
