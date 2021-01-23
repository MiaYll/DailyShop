package me.wangcai.dailyshop.model;

import me.wangcai.dailyshop.config.Lang;
import me.wangcai.dailyshop.hook.PlayerPointsHook;
import me.wangcai.dailyshop.hook.VaultHook;
import me.wangcai.dailyshop.utils.ItemUtil;
import me.wangcai.dailyshop.utils.PlayerUtil;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class ListedCommodity extends Commodity {

    private List<Buyer> buyerList;
    private int nowServerBuy;

    public ListedCommodity(){
        this.buyerList = new ArrayList<>();
        this.nowServerBuy = 0;
    }

    public boolean buy(Player p){
        Buyer buyer = new Buyer(p);
        for (Buyer buyer1 : buyerList) {
            if(buyer.playerName.equals(p.getName())){
                buyer = buyer1;
                break;
            }
        }

        if(this.getSelfMaxBuy() != -1 &&this.getSelfMaxBuy() <= buyer.times){
            return false;
        }
        if(this.getServerMaxBuy() != -1 &&this.getServerMaxBuy() <= this.nowServerBuy){
            return false;
        }
        if(this.isPoint()){
             if(!PlayerPointsHook.checkBuy(p,this.getValue())) return false;
        }else{
            if(!VaultHook.checkBuy(p,this.getValue())) return false;
        }
        PlayerUtil.runCmd(p,(String[]) getCmds().toArray());
        buyer.addTimes();
        nowServerBuy++;
        return true;
    }

    public ItemStack toIcon(Player p){
        ItemStack item = this.getIcon().clone();
        ItemMeta meta = item.getItemMeta();
        List<String> lore = meta.getLore();
        lore.addAll(ItemUtil.replaceLore(this,Lang.getLangList("lore"),p));
        meta.setLore(lore);
        item.setItemMeta(meta);
        return item;
    }

    public int getPlayerBuyCount(Player p){
        for (Buyer buyer : buyerList) {
            if(buyer.playerName.equals(p.getName())){
                return buyer.times;
            }
        }
        return 0;
    }

    public int getNowServerBuy() {
        return nowServerBuy;
    }
}
