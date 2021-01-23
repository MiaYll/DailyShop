package me.wangcai.dailyshop.manager;

import me.wangcai.dailyshop.config.Config;
import me.wangcai.dailyshop.model.Buyer;
import me.wangcai.dailyshop.model.Commodity;
import me.wangcai.dailyshop.model.ListedCommodity;
import me.wangcai.dailyshop.utils.TimeUtil;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

public class ShopManager {

    private static ShopManager shopManager;

    private int today;
    private Plugin plugin;

    private List<Commodity> commodityList;
    private List<ListedCommodity> listedCommodityList;

    public static ShopManager getShopManager() {
        if(shopManager == null){
            shopManager = new ShopManager();
        }
        return shopManager;
    }


    public void init(Plugin plugin) throws IOException {
        commodityList = new ArrayList<>();
        listedCommodityList = new ArrayList<>();
        this.plugin = plugin;
        File file = new File(plugin.getDataFolder() + "/items.yml");
        if(!file.exists()){
            plugin.saveResource("items.yml",false);
        }
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
        for (String index : config.getKeys(false)) {
            commodityList.add(new Commodity(index,config.getConfigurationSection(index)));
        }
        commodityList.sort((c1, c2) -> c2.getChance() - c1.getChance());

        loadData();
    }

    public void updateShop(){
        listedCommodityList.clear();
        today = TimeUtil.getDay();
        if(commodityList.size() <= Config.SETTINGS_AMOUNT){
            for (Commodity commodity : commodityList) {
                commodity.put();
            }
            return;
        }
        //最多寻找1k次,还找不出来就算了
        int now = 0;
        ArrayList<Integer> upIndexList = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            now ++;
            if(now == commodityList.size()) now = 0;
            if(upIndexList.contains(now)){
                continue;
            }
            Commodity commodity = commodityList.get(now);
            if(commodity.getChance() > new Random().nextInt(100)){
                listedCommodityList.add(new ListedCommodity(commodityList.get(now)));
                upIndexList.add(now);
            }
            if(listedCommodityList.size() == Config.SETTINGS_AMOUNT){
                break;
            }
        }
    }


    public List<ListedCommodity> getListedCommodityList() {
        return listedCommodityList;
    }

    public static void saveDate() throws IOException {
        File file = new File(getShopManager().plugin.getDataFolder() + "/dailyshop.yml");
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
        int index = 0;
        for (ListedCommodity commodity : getShopManager().listedCommodityList) {
            index ++;
            config.set(index + ".name",commodity.getName());
            config.set(index +".nowServerBuy",commodity.getNowServerBuy());
            for (Buyer buyer : commodity.getBuyerList()) {
                config.set(index + ".buyerlist." + buyer.getPlayerName(),buyer.getTimes());
            }
        }
        config.set("today",TimeUtil.getDay());
        config.save(file);
    }

    private void loadData() throws IOException {
        listedCommodityList.clear();
        File file = new File(plugin.getDataFolder() + "/dailyshop.yml");
        if(!file.exists()){
            updateShop();
            saveDate();
            return;
        }
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
        if(!config.getKeys(false).contains("today") || config.getInt("today") != TimeUtil.getDay()){
            updateShop();
            saveDate();
            return;
        }
        today = config.getInt("today");
        for (String key : config.getKeys(false)) {
            if(key.equals("today")){
                continue;
            }
            String name = config.getConfigurationSection(key).getString("name");
            for (Commodity commodity : commodityList) {
                if(commodity.getName().equals(name)){
                    ListedCommodity listedCommodity = new ListedCommodity(commodity);
                    listedCommodity.loadData(config.getConfigurationSection(key));
                    listedCommodityList.add(listedCommodity);
                    break;
                }
            }
        }
    }

    public int getToday() {
        return today;
    }
}
