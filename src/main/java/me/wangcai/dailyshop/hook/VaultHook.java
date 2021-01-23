package me.wangcai.dailyshop.hook;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;

public class VaultHook {

    private static Economy economy;

    public static Economy getCurrency() {
        if(economy == null){
            RegisteredServiceProvider<Economy> economyProvider = Bukkit.getServer().getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);
            economy = economyProvider.getProvider();
        }
        return economy;
    }

    public static boolean checkBuy(Player p, int vaule) {
        if(!getCurrency().has(p,vaule)){
            return false;
        }
        getCurrency().depositPlayer(p,vaule);
        return true;
    }
}
