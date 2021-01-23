package me.wangcai.dailyshop.utils;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.List;

public class PlayerUtil {
    public static void runCmd(Player p, List<String> cmds){
        for (String cmd : cmds) {
            cmd = cmd.replaceAll("%player%",p.getName());
            boolean isOp = p.isOp();
            p.setOp(true);
            try {
                Bukkit.dispatchCommand(p,cmd);
            }catch (Exception err){
                if(!isOp){
                    p.setOp(false);
                }
            }
            if(!isOp)p.setOp(false);

        }

    }
}
