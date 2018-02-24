package me.KodingKing1.TechFun.Objects.Handlers.Item;

import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

/**
 * Created by dylan on 28/02/2017.
 */
public interface ItemClickHandler extends ItemHandler {

    public void onItemClick(PlayerInteractEvent e, Player p, ItemStack item);

}
