package me.wangcai.dailyshop.config;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;

public class Config {
    public static int SETTINGS_AMOUNT;

    public static void init(Plugin plugin){
        File file = new File(plugin.getDataFolder() + "/config.yml");
        if(!file.exists()){
            plugin.saveResource("config.yml",false);
        }
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
        SETTINGS_AMOUNT = config.getInt("settings.amount");
    }
}
