package me.KodingKing1.TechFun.Events;

import me.KodingKing1.TechFun.Objects.Handlers.Item.ItemBlockBreakHandler;
import me.KodingKing1.TechFun.Objects.Handlers.Item.ItemClickHandler;
import me.KodingKing1.TechFun.Objects.Handlers.Item.ItemHandler;
import me.KodingKing1.TechFun.Objects.ItemBase;
import me.KodingKing1.TechFun.Startup.Registry;
import me.KodingKing1.TechFun.TechFunMain;
import me.KodingKing1.TechFun.Util.DataManager;
import me.KodingKing1.TechFun.Util.InvUtil;
import me.KodingKing1.TechFun.Util.TextUtil;
import org.bukkit.Sound;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

/**
 * Created by dylan on 28/02/2017.
 */
public class ItemEvents implements Listener {

    @EventHandler
    public void onItemClick(PlayerInteractEvent e){
        if(e.hasItem()){
            for(ItemBase item : Registry.getItems()){
                if(InvUtil.isSimilarItem(item.getItem(), e.getItem())) {
                    for (ItemHandler handler : item.getHandlers()) {
                        e.setCancelled(true);
                        if (handler instanceof ItemBlockBreakHandler) {
                            e.setCancelled(false);
                        }
                        if (handler instanceof ItemClickHandler) {
                            ItemClickHandler ich = (ItemClickHandler) handler;
                            Boolean unlocked;
                            if (DataManager.getPlayerData(e.getPlayer(), "Guide.Items." + item.getName() + ".Unlocked") != null) {
                                unlocked = (Boolean) DataManager.getPlayerData(e.getPlayer(), "Guide.Items." + item.getName() + ".Unlocked");
                            } else {
                                DataManager.setPlayerData(e.getPlayer(), "Guide.Items." + item.getName() + ".Unlocked", false);
                                unlocked = (Boolean) DataManager.getPlayerData(e.getPlayer(), "Guide.Items." + item.getName() + ".Unlocked");
                            }
                            if(!unlocked){
                                TechFunMain.getPluginLogger().sendMessage(e.getPlayer(), TextUtil.Level.Error, "You do not have the knowledge to understand this...");
                                e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.BLOCK_REDSTONE_TORCH_BURNOUT, 1.0F, 1.0F);
                                continue;
                            }
                            ich.onItemClick(e, e.getPlayer(), e.getItem());
                            return;
                        }
                    }
                }
            }
        }
    }

}
