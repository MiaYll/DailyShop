package me.wangcai.dailyshop;

import me.wangcai.dailyshop.config.Lang;
import me.wangcai.dailyshop.manager.ShopManager;
import me.wangcai.dailyshop.tasks.CheckNewDay;
import org.bukkit.plugin.java.JavaPlugin;

public final class DailyShop extends JavaPlugin {


    private CheckNewDay task;

    @Override
    public void onEnable() {
        ShopManager.getShopManager().init(this);
        Lang.init(this);
        task = new CheckNewDay();
        task.runTaskTimer(this,120,120);
    }


}
