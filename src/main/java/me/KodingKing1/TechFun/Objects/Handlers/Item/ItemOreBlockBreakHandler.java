package me.KodingKing1.TechFun.Objects.Handlers.Item;

import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;

/**
 * Created by Dylan Inwood on 13/03/2018.
 */
public interface ItemOreBlockBreakHandler extends ItemHandler {

    void onBlockBroken(Player p, BlockBreakEvent e);

}
