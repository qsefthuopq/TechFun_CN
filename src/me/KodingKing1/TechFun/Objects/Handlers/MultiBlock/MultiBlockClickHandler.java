package me.KodingKing1.TechFun.Objects.Handlers.MultiBlock;

import me.KodingKing1.TechFun.Objects.MultiBlock.MultiBlock;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;

/**
 * Created by dylan on 11/01/2017.
 */
public interface MultiBlockClickHandler extends MultiBlockHandler {

    public void click(MultiBlock multiBlock, Player player, PlayerInteractEvent e);

}
