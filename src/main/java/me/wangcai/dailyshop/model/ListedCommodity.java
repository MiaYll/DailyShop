package me.wangcai.dailyshop.model;

import me.wangcai.dailyshop.config.Lang;
import me.wangcai.dailyshop.hook.PlayerPointsHook;
import me.wangcai.dailyshop.hook.VaultHook;
import me.wangcai.dailyshop.manager.ShopManager;
import me.wangcai.dailyshop.utils.ItemUtil;
import me.wangcai.dailyshop.utils.PlayerUtil;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ListedCommodity extends Commodity {

    private List<Buyer> buyerList;
    private int nowServerBuy;

    public ListedCommodity(Commodity commodity){
        this.value = commodity.value;
        this.isPoint = commodity.isPoint;
        this.cmds = commodity.cmds;
        this.selfMaxBuy = commodity.selfMaxBuy;
        this.serverMaxBuy = commodity.serverMaxBuy;
        this.chance = commodity.chance;
        this.icon = commodity.icon;
        this.name = commodity.name;
        this.nowServerBuy = 0;
        buyerList = new ArrayList<>();
    }

    public boolean buy(Player p) throws IOException {
        Buyer buyer = new Buyer(p);
        boolean has = false;
        for (Buyer buyer1 : buyerList) {
            if(buyer1.playerName.equals(p.getName())){
                has = true;
                buyer = buyer1;
                break;
            }
        }
        if(!has) buyerList.add(buyer);

        if(this.getSelfMaxBuy() != -1 &&this.getSelfMaxBuy() <= buyer.times){
            p.sendMessage(Lang.getLang("failOfSelfMax",true));
            return false;
        }
        if(this.getServerMaxBuy() != -1 &&this.getServerMaxBuy() <= this.nowServerBuy){
            p.sendMessage(Lang.getLang("failOfServerMax",true));
            return false;
        }
        if(this.isPoint()){
             if(!PlayerPointsHook.checkBuy(p,this.getValue())) {
                 p.sendMessage(Lang.getLang("failOfNoPoints",true));
                 return false;
             }
        }else{
            if(!VaultHook.checkBuy(p,this.getValue())){
                p.sendMessage(Lang.getLang("failOfNoMoney",true));
                return false;
            }
        }
        PlayerUtil.runCmd(p,getCmds());
        p.sendMessage(Lang.getLang("success",true));
        p.closeInventory();
        buyer.addTimes();
        nowServerBuy++;
        ShopManager.saveDate();
        return true;
    }

    public ItemStack toIcon(Player p){
        ItemStack item = this.getIcon().clone();
        ItemMeta meta = item.getItemMeta();
        List<String> lore = meta.getLore();
        lore.addAll(Lang.getLangList("lore"));
        lore = ItemUtil.replaceLore(this,lore,p);
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

    public void loadData(ConfigurationSection config){
        nowServerBuy = config.getInt("nowServerBuy");
        ConfigurationSection buyerListConfig = config.getConfigurationSection("buyerlist");
        if(buyerListConfig == null){
            return;
        }
        for (String key : buyerListConfig.getKeys(false)) {
            buyerList.add(new Buyer(key,buyerListConfig.getInt(key)));
        }

    }

    public List<Buyer> getBuyerList() {
        return buyerList;
    }

}
