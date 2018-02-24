package me.KodingKing1.TechFun.Objects.Handlers.Item;

import me.KodingKing1.TechFun.Objects.ItemBase;
import org.bukkit.entity.Player;

/**
 * Created by dylan on 28/02/2017.
 */
public interface ItemCraftHandler extends ItemHandler {

    public void onItemCraft(Player p, ItemBase item);

}
