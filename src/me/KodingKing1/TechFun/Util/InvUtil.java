package me.KodingKing1.TechFun.Util;

import me.KodingKing1.TechFun.Objects.CraftingStation;
import me.KodingKing1.TechFun.Objects.CustomRecipe;
import me.KodingKing1.TechFun.Objects.Handlers.Item.ItemCraftHandler;
import me.KodingKing1.TechFun.Objects.Handlers.Item.ItemHandler;
import me.KodingKing1.TechFun.Objects.ItemBase;
import me.KodingKing1.TechFun.Objects.MultiBlock.MultiBlock;
import me.KodingKing1.TechFun.Startup.Registry;
import me.KodingKing1.TechFun.TechFunMain;
import org.bukkit.*;
import org.bukkit.block.Dispenser;
import org.bukkit.block.Dropper;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Iterator;

/**
 * Created by dylan on 27/02/2017.
 */
public class InvUtil {

    public static boolean isInvSimilar(Inventory inv1, Inventory inv2){
        int amountwrong = 0;
        int i = 0;
        for(ItemStack item : inv1.getStorageContents()){
            if(item != null){
                if(inv2.getItem(i) != null){
                    if(!item.isSimilar(inv2.getItem(i))){
                        amountwrong++;
                    }
                }
            }
            i++;
        }
        return amountwrong <= 1;
    }

    public static boolean hasMinItems(Inventory inv, int min){
        int amountwrong = 0;
        for(ItemStack item : inv.getStorageContents()){
            if(item != null){
                amountwrong++;
            }
        }
        return amountwrong >= min;
    }


    public static void craftItem(Dropper dispenser, MultiBlock mb, Player player, PlayerInteractEvent e, JavaPlugin plugin, CraftingStation craftingStation){
        e.setCancelled(true);
        boolean crafted = false;
        for(CustomRecipe recipe : Registry.getCustomRecipes()){
            boolean isCorrect = true;
            Inventory inv = dispenser.getInventory();
            for(int i = 0; i < 9; i++){
                ItemStack invi = inv.getItem(i);
                ItemStack reci = recipe.getRecipe()[i];
                if (!isSimilarItem(invi, reci)) {
                    isCorrect = false;
                    break;
                }
//                if(invi != null && reci != null){
//                    if(!invi.getType().equals(reci.getType())){
//                        isCorrect = false;
//                        break;
//                    }
//                    if(invi.hasItemMeta() && reci.hasItemMeta()) {
//                        if (!(ChatColor.stripColor(invi.getItemMeta().getDisplayName().toLowerCase()).contentEquals(ChatColor.stripColor(reci.getItemMeta().getDisplayName().toLowerCase())))) {
//                            isCorrect = false;
//                            break;
//                        }
//                    }
//                    if (invi.hasItemMeta() && !reci.hasItemMeta() || !invi.hasItemMeta() && reci.hasItemMeta()) {
//                        isCorrect = false;
//                        break;
//                    }
//                    if (invi.getItemMeta().hasDisplayName() && !reci.getItemMeta().hasDisplayName() || !invi.getItemMeta().hasDisplayName() && reci.getItemMeta().hasDisplayName()) {
//                        isCorrect = false;
//                        break;
//                    }
//                }else if((invi == null && reci != null) || (invi != null && reci == null)){
//                    isCorrect = false;
//                    break;
//                }
            }
            if(isCorrect){
                if(recipe.getStation() != craftingStation)
                    continue;
                for(int i = 0; i < 9; i++){
                    if(dispenser.getInventory().getItem(i) != null) {
                        if(dispenser.getInventory().getItem(i).getAmount() > 1){
                            dispenser.getInventory().setItem(i, new ItemStack(dispenser.getInventory().getItem(i).getType(), dispenser.getInventory().getItem(i).getAmount() - 1));
                        }else{
                            dispenser.getInventory().setItem(i, null);
                        }
                    }
                }
                for (int i = 0; i < recipe.getOut().getAmount(); i++) {
                    dispenser.getWorld().dropItemNaturally(dispenser.getLocation().add(0, 3, 0), recipe.getOut());
                }
                dispenser.getWorld().playSound(dispenser.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1);
                new BukkitRunnable() {
                    int i = 1;
                    @Override
                    public void run() {
                        dispenser.getWorld().spawnParticle(Particle.TOTEM, dispenser.getLocation().add(0, 1, 0), 50);
                        if(i == 0){
                            this.cancel();
                            return;
                        }else{
                            i--;
                        }
                    }
                }.runTaskTimer(plugin, 0, 3);
                crafted = true;
            }
        }
        for(ItemBase item : Registry.getItems()){
            boolean isCorrect = true;
            Inventory inv = dispenser.getInventory();
            for(int i = 0; i < 9; i++){
                ItemStack invi = inv.getItem(i);
                ItemStack reci = item.getRecipe()[i];
                if (!isSimilarItem(invi, reci)) {
                    isCorrect = false;
                    break;
                }
//                if(invi != null && reci != null){
//                    if(!invi.getType().equals(reci.getType())){
//                        isCorrect = false;
//                        break;
//                    }
//                    if(invi.hasItemMeta() && reci.hasItemMeta()) {
//                        if (!(ChatColor.stripColor(invi.getItemMeta().getDisplayName().toLowerCase()).contentEquals(ChatColor.stripColor(reci.getItemMeta().getDisplayName().toLowerCase())))) {
//                            isCorrect = false;
//                            break;
//                        }
//                    }
//                }else if((invi == null && reci != null) || (invi != null && reci == null)){
//                    isCorrect = false;
//                    break;
//                }
            }
            if(isCorrect){
                if(item.getCraftingStation() != craftingStation)
                    continue;
                Boolean unlocked;
                if (DataManager.getPlayerData(player, "Guide.Items." + item.getName() + ".Unlocked") != null) {
                    unlocked = (Boolean) DataManager.getPlayerData(player, "Guide.Items." + item.getName() + ".Unlocked");
                } else {
                    DataManager.setPlayerData(player, "Guide.Items." + item.getName() + ".Unlocked", false);
                    unlocked = (Boolean) DataManager.getPlayerData(player, "Guide.Items." + item.getName() + ".Unlocked");
                }
                if(!unlocked){
                    TechFunMain.getPluginLogger().sendMessage(player, TextUtil.Level.Error, "You do not have the knowledge to understand this...");
                    return;
                }
                for(int i = 0; i < 9; i++){
                    if(dispenser.getInventory().getItem(i) != null) {
                        if(dispenser.getInventory().getItem(i).getAmount() > 1){
                            dispenser.getInventory().setItem(i, new ItemStack(dispenser.getInventory().getItem(i).getType(), dispenser.getInventory().getItem(i).getAmount() - 1));
                        }else{
                            dispenser.getInventory().setItem(i, null);
                        }
                    }
                }
                dispenser.getWorld().dropItemNaturally(dispenser.getLocation().add(0, 3, 0), item.getItem());
                dispenser.getWorld().playSound(dispenser.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1);
                new BukkitRunnable() {
                    int i = 1;
                    @Override
                    public void run() {
                        dispenser.getWorld().spawnParticle(Particle.TOTEM, dispenser.getLocation().add(0, 1, 0), 50);
                        if(i == 0){
                            this.cancel();
                            return;
                        }else{
                            i--;
                        }
                    }
                }.runTaskTimer(plugin, 0, 3);
                TechFunMain.getPluginLogger().sendMessage(player, TextUtil.Level.Success, "You have successfully crafted the item " + item.getName() + ChatColor.GREEN + "!");
                crafted = true;
                for(ItemHandler handler : item.getHandlers()){
                    if(handler instanceof ItemCraftHandler){
                        ItemCraftHandler ich = (ItemCraftHandler) handler;
                        ich.onItemCraft(player, item);
                    }
                }
            }
        }
        if(!crafted){
            new BukkitRunnable() {
                int i = 5;
                @Override
                public void run() {
                    dispenser.getWorld().playSound(dispenser.getLocation(), Sound.BLOCK_NOTE_BASS, 1, 1);
                    dispenser.getWorld().spawnParticle(Particle.SMOKE_LARGE, dispenser.getLocation().add(0, 1, 0), 50);
                    if(i == 0){
                        this.cancel();
                        return;
                    }else{
                        i--;
                    }
                }
            }.runTaskTimer(plugin, 0, 3);
            TechFunMain.getPluginLogger().sendMessage(player, TextUtil.Level.Error, "Recipe not recognised!");
        }
    }

    public static boolean isSimilarItem(ItemStack item1, ItemStack item2) {
        boolean isCorrect = true;
        ItemStack invi = item1;
        ItemStack reci = item2;
//        invi.setAmount(1);
//        reci.setAmount(1);
        if (invi != null && reci != null) {
            if (!invi.getType().equals(reci.getType())) {
                isCorrect = false;
            }
            if (invi.hasItemMeta() && reci.hasItemMeta()) {
                if (!(ChatColor.stripColor(invi.getItemMeta().getDisplayName().toLowerCase()).contentEquals(ChatColor.stripColor(reci.getItemMeta().getDisplayName().toLowerCase())))) {
                    isCorrect = false;
                }
            }
            if (invi.hasItemMeta() && !reci.hasItemMeta() || !invi.hasItemMeta() && reci.hasItemMeta()) {
                isCorrect = false;
            }
            if (invi.getItemMeta().hasDisplayName() && !reci.getItemMeta().hasDisplayName() || !invi.getItemMeta().hasDisplayName() && reci.getItemMeta().hasDisplayName()) {
                isCorrect = false;
            }
        } else if ((invi == null && reci != null) || (invi != null && reci == null)) {
            isCorrect = false;
        }
//        if(invi != null && reci != null){
//            if(!invi.getType().equals(reci.getType())){
//                isCorrect = false;
//            }
//            if(invi.hasItemMeta() && invi.getItemMeta().hasDisplayName() && reci.hasItemMeta() && reci.getItemMeta().hasDisplayName()) {
//                if (!(ChatColor.stripColor(invi.getItemMeta().getDisplayName().toLowerCase()).contentEquals(ChatColor.stripColor(reci.getItemMeta().getDisplayName().toLowerCase())))) {
//                    isCorrect = false;
//                }
//            }
//            if(!invi.equals(reci)){
//                isCorrect = false;
//            }
//        }else if((invi == null && reci != null) || (invi != null && reci == null)){
//            isCorrect = false;
//        }
        return isCorrect;
    }

    public static ItemStack getFurnaceRecipeResult(Material blockType) {
        ItemStack result = null;
        Iterator<Recipe> iter = Bukkit.recipeIterator();
        while (iter.hasNext()) {
            Recipe recipe = iter.next();
            if (!(recipe instanceof FurnaceRecipe)) continue;
            if (((FurnaceRecipe) recipe).getInput().getType() != blockType) continue;
            result = recipe.getResult();
            break;
        }
        return result;
    }

    public static void decrementItem(ItemStack item) {
        item.setAmount(item.getAmount() - 1);
        if (item.getAmount() == 0) {
            item.setType(Material.AIR);
        }
    }
}
