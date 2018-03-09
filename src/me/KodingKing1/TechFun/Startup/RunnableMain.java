package me.KodingKing1.TechFun.Startup;

import me.KodingKing1.TechFun.Objects.Handlers.Item.ItemHandler;
import me.KodingKing1.TechFun.Objects.Handlers.Item.ItemWornHandler;
import me.KodingKing1.TechFun.Objects.ItemBase;
import me.KodingKing1.TechFun.TechFunMain;
import me.KodingKing1.TechFun.Util.DataManager;
import me.KodingKing1.TechFun.Util.InvUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * Created by dylan on 1/03/2017.
 */
public class RunnableMain implements Runnable {

    @Override
    public void run() {
        for(Player p : Bukkit.getOnlinePlayers()){
            for(ItemBase armor : Registry.getArmor()){
                if(armor.getXpToUnlock() == 0){
                    DataManager.setPlayerData(p, "Guide.Items." + armor.getName() + ".Unlocked", true);
                }
                for(ItemStack is : p.getInventory().getArmorContents()){
                    ItemStack invi = is;
                    ItemStack reci = armor.getItem();
                    if(InvUtil.isSimilarItem(invi, reci)) {
                        for (ItemHandler handler : armor.getHandlers()) {
                            if (handler instanceof ItemWornHandler) {
                                ItemWornHandler iwh = (ItemWornHandler) handler;
                                iwh.onItemWorn(p, is);
                            }
                        }
                    }
                }
            }
        }
    }
}
