package me.KodingKing1.TechFun.Objects.Handlers.Item;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;

/**
 * Created by Dylan Inwood on 28/02/2018.
 */
public interface ItemBlockBreakHandler {

    void onBlockBroken(Block b, Player p, BlockBreakEvent e);

}
