package me.KodingKing1.TechFun.Objects.Handlers.Item;

import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

/**
 * Created by djite on 18/05/2017.
 */
public interface ItemAttackHandler extends ItemHandler {

    public void onAttack(EntityDamageByEntityEvent e, Player p, ItemStack item);

}
