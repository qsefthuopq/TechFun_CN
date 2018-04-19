package me.KodingKing1.TechFun.Objects.Handlers.Item;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * Created by dylan on 6/03/2017.
 */
public interface ItemWornHandler extends ItemHandler {

    public void onItemWorn(Player p, ItemStack item);

}
