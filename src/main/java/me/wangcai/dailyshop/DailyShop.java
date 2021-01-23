package me.wangcai.dailyshop;

import me.wangcai.dailyshop.config.Config;
import me.wangcai.dailyshop.config.Lang;
import me.wangcai.dailyshop.manager.ShopManager;
import me.wangcai.dailyshop.tasks.CheckNewDay;
import me.wangcai.dailyshop.view.ShopView;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;

public final class DailyShop extends JavaPlugin {


    private CheckNewDay task;

    @Override
    public void onEnable() {
        Lang.init(this);
        Config.init(this);
        try {
            ShopManager.getShopManager().init(this);
        } catch (IOException e) {
            e.printStackTrace();
        }

        task = new CheckNewDay();
        task.runTaskTimer(this,120,120);
        Bukkit.getPluginCommand("dailyshop").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player)){
            reload();
            sender.sendMessage("§b重载成功!");
            return true;
        }
        Player p = (Player) sender;
        new ShopView(this,p).openMenu(p);
        return true;
    }

    private void reload(){

    }
}
