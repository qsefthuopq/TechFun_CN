package me.KodingKing1.TechFun.Events;

import me.KodingKing1.TechFun.Objects.Handlers.Item.ItemAttackHandler;
import me.KodingKing1.TechFun.Objects.Handlers.Item.ItemHandler;
import me.KodingKing1.TechFun.Objects.ItemBase;
import me.KodingKing1.TechFun.Startup.Registry;
import me.KodingKing1.TechFun.Startup.TechFunStartup;
import me.KodingKing1.TechFun.Util.InvUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerJoinEvent;

/**
 * Created by dylan on 28/02/2017.
 */
public class PlayerEvents implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e){
        if(!e.getPlayer().hasPlayedBefore()){
            e.getPlayer().getInventory().addItem(Registry.getItem("TechFunGuide").getItem());
        }
    }

    @EventHandler
    public void onEntityAttacked(EntityDamageByEntityEvent e){
        if(e.getDamager() instanceof Player){
            Player p = (Player) e.getDamager();
            for(ItemBase item : Registry.getItems()){
                if(InvUtil.isSimilarItem(item.getItem(), p.getInventory().getItemInMainHand())) {
                    for (ItemHandler handler : item.getHandlers()) {
                        if(handler instanceof ItemAttackHandler){
                            ItemAttackHandler iah = (ItemAttackHandler) handler;
                            iah.onAttack(e, p, p.getInventory().getItemInMainHand());
                        }
                    }
                }
            }
        }
    }

}
