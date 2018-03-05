package me.KodingKing1.TechFun.Events;

import me.KodingKing1.TechFun.Objects.CraftingStation;
import me.KodingKing1.TechFun.Objects.Handlers.Item.ItemBlockBreakHandler;
import me.KodingKing1.TechFun.Objects.Handlers.Item.ItemClickHandler;
import me.KodingKing1.TechFun.Objects.Handlers.Item.ItemHandler;
import me.KodingKing1.TechFun.Objects.Handlers.Machine.MachineClickHandler;
import me.KodingKing1.TechFun.Objects.Handlers.Machine.MachineHandler;
import me.KodingKing1.TechFun.Objects.ItemBase;
import me.KodingKing1.TechFun.Objects.Machine.Machine;
import me.KodingKing1.TechFun.Startup.Registry;
import me.KodingKing1.TechFun.TechFunMain;
import me.KodingKing1.TechFun.Util.DataManager;
import me.KodingKing1.TechFun.Util.InvUtil;
import me.KodingKing1.TechFun.Util.TextUtil;
import org.bukkit.Sound;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

import java.util.Random;

/**
 * Created by dylan on 28/02/2017.
 */
public class BlockEvents implements Listener {

    @EventHandler
    public void onBlockBroken(BlockBreakEvent e){
        for(ItemBase item : Registry.getItems()){
            if(InvUtil.isSimilarItem(item.getItem(), e.getPlayer().getInventory().getItemInMainHand())) {
                for (ItemHandler handler : item.getHandlers()) {
                    if (handler instanceof ItemBlockBreakHandler) {
                        ItemBlockBreakHandler ich = (ItemBlockBreakHandler) handler;
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
                            e.setCancelled(true);
                            continue;
                        }
                        ich.onBlockBroken(e.getBlock(), e.getPlayer(), e);
                    }
                }
            }
        }
        for(ItemBase item : Registry.getItems()){
            if(item.getCraftingStation() == CraftingStation.Ore){
                if(item.getRecipe()[0] != null){
                    if(item.getRecipe()[0].getType() == e.getBlock().getType()){
                        Boolean unlocked;
                        if (DataManager.getPlayerData(e.getPlayer(), "Guide.Items." + item.getName() + ".Unlocked") != null) {
                            unlocked = (Boolean) DataManager.getPlayerData(e.getPlayer(), "Guide.Items." + item.getName() + ".Unlocked");
                        } else {
                            DataManager.setPlayerData(e.getPlayer(), "Guide.Items." + item.getName() + ".Unlocked", false);
                            unlocked = (Boolean) DataManager.getPlayerData(e.getPlayer(), "Guide.Items." + item.getName() + ".Unlocked");
                        }
                        if(!unlocked){
                            continue;
                        }
                        Random rand = new Random();
                        if(rand.nextInt(100) < 20){
                            e.getBlock().getWorld().dropItemNaturally(e.getBlock().getLocation(), item.getItem());
                        }
                    }
                }
            }
        }
        for (Machine m : Registry.getMachines()) {
            if (((String) DataManager.getBlockData(e.getBlock(), "Name", "")).equalsIgnoreCase(m.getName()) ) {
                DataManager.deleteBlockData(e.getBlock());
                e.setDropItems(false);
                e.getBlock().getWorld().dropItemNaturally(e.getBlock().getLocation(), m.getItem());
            }
        }
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e) {
        for (Machine m : Registry.getMachines()) {
            if (InvUtil.isSimilarItem(m.getItem(), e.getItemInHand())) {
                DataManager.saveBlockData(e.getBlock(), "Name", m.getName());
            }
        }
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e) {
        EquipmentSlot slot = e.getHand();
        if (!slot.equals(EquipmentSlot.HAND)) {
            return;
        }
        if (e.getAction() != Action.RIGHT_CLICK_BLOCK) {
            return;
        }
        if (e.hasBlock()) {
            for (Machine m : Registry.getMachines()) {
                String name = ((String) DataManager.getBlockData(e.getClickedBlock(), "Name")) != null ? ((String) DataManager.getBlockData(e.getClickedBlock(), "Name")) : "";
                if (name.equalsIgnoreCase(m.getName())) {
                    for (MachineHandler handler : m.getHandlers()) {
                        if (handler instanceof MachineClickHandler) {
                            Boolean unlocked;
                            if (DataManager.getPlayerData(e.getPlayer(), "Guide.Machine." + m.getName() + ".Unlocked") != null) {
                                unlocked = (Boolean) DataManager.getPlayerData(e.getPlayer(), "Guide.Machine." + m.getName() + ".Unlocked");
                            } else {
                                DataManager.setPlayerData(e.getPlayer(), "Guide.Machine." + m.getName() + ".Unlocked", false);
                                unlocked = (Boolean) DataManager.getPlayerData(e.getPlayer(), "Guide.Machine." + m.getName() + ".Unlocked");
                            }
                            if(!unlocked){
                                continue;
                            }
                            MachineClickHandler mch = (MachineClickHandler) handler;
                            mch.onMachineClick(m, e.getPlayer(), e);
                            return;
                        }
                    }
                }
            }
        }
    }

}
