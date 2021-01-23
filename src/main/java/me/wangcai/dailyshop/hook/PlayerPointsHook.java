package me.wangcai.dailyshop.hook;


import org.black_ixx.playerpoints.PlayerPoints;
import org.black_ixx.playerpoints.PlayerPointsAPI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class PlayerPointsHook{
    private static PlayerPoints playerPoints;

    public static PlayerPointsAPI getCurrency() {
        if(playerPoints == null){
            Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin("PlayerPoints");
            playerPoints = PlayerPoints.class.cast(plugin);
        }
        return playerPoints.getAPI();
    }

    public static boolean checkBuy(Player p, int vaule) {
        return getCurrency().take(p.getUniqueId(),vaule);
    }
}
