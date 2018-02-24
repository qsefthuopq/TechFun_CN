package me.KodingKing1.TechFun.Startup;

import me.KodingKing1.TechFun.Objects.Handlers.Item.ItemHandler;
import me.KodingKing1.TechFun.Objects.Handlers.Item.ItemWornHandler;
import me.KodingKing1.TechFun.Objects.ItemBase;
import me.KodingKing1.TechFun.TechFunMain;
import me.KodingKing1.TechFun.Util.DataManager;
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
            for(ItemBase item : Registry.getItems()){
                if(item.getXpToUnlock() == 0){
                    DataManager.setPlayerData(p, "Guide.Items." + item.getName() + ".Unlocked", true);
                }
                for(ItemStack is : p.getInventory().getArmorContents()){
                    boolean isCorrect = true;
                    ItemStack invi = is;
                    ItemStack reci = item.getItem();
                    if(invi != null && reci != null){
                        if(!invi.getType().equals(reci.getType())){
                            isCorrect = false;
                        }
                        if(invi.hasItemMeta() && invi.getItemMeta().hasDisplayName() && reci.hasItemMeta() && reci.getItemMeta().hasDisplayName()) {
                            if (!(ChatColor.stripColor(invi.getItemMeta().getDisplayName().toLowerCase()).contentEquals(ChatColor.stripColor(reci.getItemMeta().getDisplayName().toLowerCase())))) {
                                isCorrect = false;
                            }
                        }
                    }else if((invi == null && reci != null) || (invi != null && reci == null)){
                        isCorrect = false;
                    }
                    if(isCorrect) {
                        for (ItemHandler handler : item.getHandlers()) {
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
