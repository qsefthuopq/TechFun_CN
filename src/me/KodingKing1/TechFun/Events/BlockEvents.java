package me.KodingKing1.TechFun.Events;

import me.KodingKing1.TechFun.Objects.CraftingStation;
import me.KodingKing1.TechFun.Objects.ItemBase;
import me.KodingKing1.TechFun.Startup.Registry;
import me.KodingKing1.TechFun.Util.DataManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.Random;

/**
 * Created by dylan on 28/02/2017.
 */
public class BlockEvents implements Listener {

    @EventHandler
    public void onBlockBroken(BlockBreakEvent e){
        for(ItemBase item : Registry.getItems()){
            if(item.getCraftingStation() == CraftingStation.Ore){
                if(item.getRecipe()[0] != null){
                    if(item.getRecipe()[0].getType() == e.getBlock().getType()){
                        Boolean unlocked;
                        if (DataManager.getPlayerData(e.getPlayer(), "Guide.Items." + item.getName() + ".Unlocked") != null) {
                            unlocked = (Boolean) DataManager.getPlayerData(e.getPlayer(), "Guide.Items." + item.getName() + ".Unlocked");
                        } else {
                            DataManager.setPlayerData(e.getPlayer(), "Guide.Items." + item.getName() + ".Unlocked", false);
                            unlocked = (Boolean) DataManager.getPlayerData(e.getPlayer(), "Guide.Items." + item.getName() + ".Unlocked");
                        }
                        if(!unlocked){
                            continue;
                        }
                        Random rand = new Random();
                        if(rand.nextInt(100) < 20){
                            e.getBlock().getWorld().dropItemNaturally(e.getBlock().getLocation(), item.getItem());
                        }
                    }
                }
            }
        }
    }

}
