package me.wangcai.dailyshop.view;

import me.wangcai.dailyshop.config.Config;
import me.wangcai.dailyshop.config.Lang;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class ShopView{
    public static void openMenu(Player p){
        Inventory inv = Bukkit.createInventory(p, (Config.SETTINGS_AMOUNT / 7 + 2) * 9, Lang.getLang("title"));
        for (ItemStack itemStack : inv) {
            itemStack.setType(Material.STAINED_GLASS_PANE);
            itemStack.setDurability((short) 5);
        }
        p.openInventory(inv);
    }
}
