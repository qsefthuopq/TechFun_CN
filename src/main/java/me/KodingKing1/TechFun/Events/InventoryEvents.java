package me.KodingKing1.TechFun.Events;

import me.KodingKing1.TechFun.TechFunMain;
import me.KodingKing1.TechFun.Util.DataManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;

/**
 * Created by djite on 22/05/2017.
 */
public class InventoryEvents implements Listener {

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent e){
        for(Player p : Bukkit.getOnlinePlayers()){
            if(e.getInventory().getTitle().startsWith(p.getUniqueId().toString())){
                DataManager.saveInventory(e.getInventory());
            }
        }
    }

}
