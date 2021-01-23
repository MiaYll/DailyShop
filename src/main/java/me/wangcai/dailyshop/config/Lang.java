package me.wangcai.dailyshop.config;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.util.List;

public class Lang {

    private static YamlConfiguration lang;
    private static String prefix;

    public static String getLang(String key,boolean hasPrefix){
        if(hasPrefix){
            return prefix + lang.getString(key).replaceAll("&","§");
        }
        return lang.getString(key).replaceAll("&","§");
    }

    public static List<String> getLangList(String key){
        return lang.getStringList(key);
    }

    public static void init(Plugin plugin){
        File file = new File(plugin.getDataFolder() + "/lang.yml");
        if(!file.exists()){
            plugin.saveResource("lang.yml",false);
        }
        lang = YamlConfiguration.loadConfiguration(file);
        prefix = lang.getString("prefix").replaceAll("&","§");
    }

}
