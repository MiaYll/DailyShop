package me.wangcai.dailyshop.manager;

import me.wangcai.dailyshop.model.Commodity;
import me.wangcai.dailyshop.model.ListedCommodity;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ShopManager {

    private static ShopManager shopManager;

    private List<Commodity> commodityList;
    private List<ListedCommodity> listedCommodityList;

    public static ShopManager getShopManager() {
        if(shopManager == null){
            shopManager = new ShopManager();
        }
        return shopManager;
    }

    public void init(Plugin plugin){
        commodityList = new ArrayList<>();
        File file = new File(plugin.getDataFolder() + "/items.yml");
        if(!file.exists()){
            plugin.saveResource("items.yml",false);
        }
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
        for (String index : config.getKeys(false)) {
            commodityList.add(new Commodity(config.getConfigurationSection(index)));
        }
        commodityList.sort(Comparator.comparingInt(Commodity::getChance));
    }

    public void updateShop(){

    }

}
