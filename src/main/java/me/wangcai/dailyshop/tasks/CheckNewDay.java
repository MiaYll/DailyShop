package me.wangcai.dailyshop.tasks;

import me.wangcai.dailyshop.manager.ShopManager;
import me.wangcai.dailyshop.utils.TimeUtil;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.IOException;

public class CheckNewDay extends BukkitRunnable {


    @Override
    public void run() {
        if(TimeUtil.getDay() != ShopManager.getShopManager().getToday()){
            ShopManager.getShopManager().updateShop();
            try {
                ShopManager.saveDate();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
