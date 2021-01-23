package me.wangcai.dailyshop.view;

import me.wangcai.dailyshop.config.Config;
import me.wangcai.dailyshop.config.Lang;
import me.wangcai.dailyshop.manager.ShopManager;
import me.wangcai.dailyshop.model.ListedCommodity;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class ShopView implements Listener {

    private Plugin plugin;
    private Player player;

    public ShopView(Plugin plugin,Player player){
        this.plugin = plugin;
        this.player = player;
        Bukkit.getPluginManager().registerEvents(this,plugin);
    }

    private HashMap<Integer,ListedCommodity> listedCommoditys = new HashMap<>();

    public void openMenu(Player p){
        Inventory inv = Bukkit.createInventory(p, (Config.SETTINGS_AMOUNT / 7 + 2) * 9, Lang.getLang("title",false));
        ItemStack item = new ItemStack(Material.STAINED_GLASS_PANE);
        item.setDurability((short) 5);
        for (int i = 0; i < inv.getSize(); i++) {
            inv.setItem(i,item);
        }
        int slot = 10;
        List<ListedCommodity> commodityList = ShopManager.getShopManager().getListedCommodityList();
        for (ListedCommodity listedCommodity : commodityList) {
            inv.setItem(slot, listedCommodity.toIcon(p));
            listedCommoditys.put(slot, listedCommodity);
            slot++;
            if (slot % 9 == 0) {
                slot += 2;
            }
        }
        p.openInventory(inv);
    }



    @EventHandler
    private void onClick(InventoryClickEvent e) throws IOException {
        if(e.getWhoClicked() != player){
            return;
        }
        if(e.getClickedInventory() == null){
            return;
        }
        if(!e.getClickedInventory().getTitle().equals(Lang.getLang("title",false))){
            return;
        }
        e.setCancelled(true);
        if(listedCommoditys.containsKey(e.getSlot())){
            listedCommoditys.get(e.getSlot()).buy((Player) e.getWhoClicked());
        }

    }

    @EventHandler
    private void onClose(InventoryCloseEvent e){
        if(e.getPlayer() != player){
            return;
        }
        if(!e.getInventory().getTitle().equals(Lang.getLang("title",false))){
            return;
        }
        HandlerList.unregisterAll(this);
    }
}
