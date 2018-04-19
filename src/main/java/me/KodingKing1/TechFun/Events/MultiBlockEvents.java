package me.KodingKing1.TechFun.Events;

import me.KodingKing1.TechFun.Objects.Handlers.MultiBlock.MultiBlockClickHandler;
import me.KodingKing1.TechFun.Objects.Handlers.MultiBlock.MultiBlockHandler;
import me.KodingKing1.TechFun.Objects.MultiBlock.MultiBlock;
import me.KodingKing1.TechFun.Startup.Registry;
import me.KodingKing1.TechFun.TechFunMain;
import me.KodingKing1.TechFun.Util.DataManager;
import me.KodingKing1.TechFun.Util.TextUtil;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

/**
 * Created by dylan on 27/02/2017.
 */
public class MultiBlockEvents implements Listener {

    //0 |1 |2 |
    //3 |4 |5 |
    //6 |7 |8 |

    @EventHandler
    public void onClick(PlayerInteractEvent e){
        if(e.hasBlock()){
            Block startBlock = e.getClickedBlock();
            for(MultiBlock mb : Registry.getMultiBlocks()){
                if(startBlock.getType() == mb.materials[4] && startBlock.getRelative(BlockFace.DOWN).getType() == mb.materials[7] &&
                        startBlock.getRelative(BlockFace.UP).getType() == mb.materials[1]){
                    if(e.getBlockFace() == BlockFace.NORTH){
                        if(startBlock.getRelative(BlockFace.EAST).getType() == mb.materials[5] && startBlock.getRelative(BlockFace.WEST).getType() == mb.materials[3] &&
                                startBlock.getRelative(BlockFace.EAST).getRelative(BlockFace.UP).getType() == mb.materials[2] &&
                                startBlock.getRelative(BlockFace.EAST).getRelative(BlockFace.DOWN).getType() == mb.materials[8] &&
                                startBlock.getRelative(BlockFace.WEST).getRelative(BlockFace.UP).getType() == mb.materials[0] &&
                                startBlock.getRelative(BlockFace.WEST).getRelative(BlockFace.DOWN).getType() == mb.materials[6]){
                            mbFound(mb, e.getPlayer(), e);
                        }
                    }
                    if(e.getBlockFace() == BlockFace.SOUTH){
                        if(startBlock.getRelative(BlockFace.WEST).getType() == mb.materials[5] && startBlock.getRelative(BlockFace.EAST).getType() == mb.materials[3] &&
                                startBlock.getRelative(BlockFace.WEST).getRelative(BlockFace.UP).getType() == mb.materials[2] &&
                                startBlock.getRelative(BlockFace.WEST).getRelative(BlockFace.DOWN).getType() == mb.materials[8] &&
                                startBlock.getRelative(BlockFace.EAST).getRelative(BlockFace.UP).getType() == mb.materials[0] &&
                                startBlock.getRelative(BlockFace.EAST).getRelative(BlockFace.DOWN).getType() == mb.materials[6]){
                            mbFound(mb, e.getPlayer(), e);
                        }
                    }
                    if(e.getBlockFace() == BlockFace.EAST){
                        if(startBlock.getRelative(BlockFace.SOUTH).getType() == mb.materials[5] && startBlock.getRelative(BlockFace.NORTH).getType() == mb.materials[3] &&
                                startBlock.getRelative(BlockFace.SOUTH).getRelative(BlockFace.UP).getType() == mb.materials[2] &&
                                startBlock.getRelative(BlockFace.SOUTH).getRelative(BlockFace.DOWN).getType() == mb.materials[8] &&
                                startBlock.getRelative(BlockFace.NORTH).getRelative(BlockFace.UP).getType() == mb.materials[0] &&
                                startBlock.getRelative(BlockFace.NORTH).getRelative(BlockFace.DOWN).getType() == mb.materials[6]){
                            mbFound(mb, e.getPlayer(), e);
                        }
                    }
                    if(e.getBlockFace() == BlockFace.WEST){
                        if(startBlock.getRelative(BlockFace.NORTH).getType() == mb.materials[5] && startBlock.getRelative(BlockFace.SOUTH).getType() == mb.materials[3] &&
                                startBlock.getRelative(BlockFace.NORTH).getRelative(BlockFace.UP).getType() == mb.materials[2] &&
                                startBlock.getRelative(BlockFace.NORTH).getRelative(BlockFace.DOWN).getType() == mb.materials[8] &&
                                startBlock.getRelative(BlockFace.SOUTH).getRelative(BlockFace.UP).getType() == mb.materials[0] &&
                                startBlock.getRelative(BlockFace.SOUTH).getRelative(BlockFace.DOWN).getType() == mb.materials[6]){
                            mbFound(mb, e.getPlayer(), e);
                        }
                    }
                }
            }
        }
    }

    private void mbFound(MultiBlock mb, Player player, PlayerInteractEvent e){
        for(MultiBlockHandler handler : mb.handlers){
            if(handler instanceof MultiBlockClickHandler){
                Boolean unlocked;
                if (DataManager.getPlayerData(player, "Guide.Items." + ChatColor.stripColor(mb.getIcon().getItemMeta().getDisplayName()) + ".Unlocked") != null) {
                    unlocked = (Boolean) DataManager.getPlayerData(player, "Guide.Items." + ChatColor.stripColor(mb.getIcon().getItemMeta().getDisplayName()) + ".Unlocked");
                } else {
                    DataManager.setPlayerData(player, "Guide.Items." + ChatColor.stripColor(mb.getIcon().getItemMeta().getDisplayName()) + ".Unlocked", false);
                    unlocked = (Boolean) DataManager.getPlayerData(player, "Guide.Items." + ChatColor.stripColor(mb.getIcon().getItemMeta().getDisplayName()) + ".Unlocked");
                }
                if(!unlocked){
                    TechFunMain.getPluginLogger().sendMessage(player, TextUtil.Level.Error, "You do not have the knowledge to understand this...");
                    e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.BLOCK_REDSTONE_TORCH_BURNOUT, 1.0F, 1.0F);
                    return;
                }
                ((MultiBlockClickHandler) handler).click(mb, player, e);
            }
        }
    }

}
