package me.wangcai.dailyshop.utils;

import me.wangcai.dailyshop.config.Lang;
import me.wangcai.dailyshop.model.ListedCommodity;
import org.bukkit.craftbukkit.v1_12_R1.inventory.CraftItemStack;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class ItemUtil {
    public static ItemStack createItem(int id, String name, List<String> lore){
        ItemStack item = new ItemStack(id);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        meta.setLore(lore);
        item.setItemMeta(meta);
        return item;

    }

    public static List<String> replaceLore(ListedCommodity lc, List<String> lore, Player p){
        for (int i = 0; i < lore.size(); i++) {
            String line = lore.get(i);
            lore.set(i,line.replaceAll("%money%", String.valueOf(lc.getValue()))
                    .replaceAll("%nowself%", String.valueOf(lc.getPlayerBuyCount(p)))
                    .replaceAll("%nowserver%", String.valueOf(lc.getNowServerBuy()))
                    .replaceAll("%selfmax%", String.valueOf(lc.getSelfMaxBuy()))
                    .replaceAll("%servermax%", String.valueOf(lc.getServerMaxBuy()))
                    .replaceAll("%currency%",lc.isPoint() ? Lang.getLang("points",false):Lang.getLang("money",false))
                    .replaceAll("&","§")
                );
        }
        return lore;
    }

}
