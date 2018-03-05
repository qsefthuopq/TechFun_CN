package me.KodingKing1.TechFun.Objects;

import me.KodingKing1.TechFun.Objects.Category.Category;
import me.KodingKing1.TechFun.Objects.Machine.Machine;
import me.KodingKing1.TechFun.Objects.MultiBlock.MultiBlock;
import me.KodingKing1.TechFun.Util.TFUtil;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dylan on 27/02/2017.
 */
public class Factory {

    public static ItemBase makeItem(String registryName, String itemName, String[] lore, Material material, Object[] recipe, CraftingStation station, int xpToUnlock){
        ItemBase item = new ItemBase();
        //Objects
        ItemStack itemStack = new ItemStack(material);
        ItemMeta meta = itemStack.getItemMeta();
        meta.setDisplayName(ChatColor.AQUA + itemName);
        List<String> lore2 = new ArrayList<>();
        for(String line : lore){
            lore2.add(ChatColor.RESET + "" + ChatColor.GREEN + line);
        }
        meta.setLore(lore2);
        itemStack.setItemMeta(meta);
        item.setItem(itemStack);
        item.setRecipe(recipe);
        item.setName(registryName);
        item.setCraftingStation(station);
        item.setXpToUnlock(xpToUnlock);
        return item;
    }

    public static ItemBase makeItem(String registryName, ItemStack itemStack, Object[] recipe, CraftingStation station, int xpToUnlock){
        ItemBase item = new ItemBase();
        item.setItem(itemStack);
        item.setRecipe(recipe);
        item.setName(registryName);
        item.setCraftingStation(station);
        item.setXpToUnlock(xpToUnlock);
        return item;
    }

    public static ItemBase makeItem(String registryName, String playerName, String itemName, String[] lore, Object[] recipe, CraftingStation station, int xpToUnlock){
        ItemBase item = new ItemBase();
        //Objects
        ItemStack itemStack = TFUtil.makePlayerHead(playerName, itemName, lore);
        item.setItem(itemStack);
        item.setRecipe(recipe);
        item.setName(registryName);
        item.setCraftingStation(station);
        item.setXpToUnlock(xpToUnlock);
        return item;
    }

    public static ItemBase makeItem(String registryName, String itemName, String[] lore, Material material, Object[] recipe){
        ItemBase item = new ItemBase();
        //Objects
        ItemStack itemStack = new ItemStack(material);
        ItemMeta meta = itemStack.getItemMeta();
        meta.setDisplayName(ChatColor.AQUA + itemName);
        List<String> lore2 = new ArrayList<>();
        for(String line : lore){
            lore2.add(ChatColor.RESET + "" + ChatColor.GREEN + line);
        }
        meta.setLore(lore2);
        itemStack.setItemMeta(meta);
        item.setItem(itemStack);
        item.setRecipe(recipe);
        item.setName(registryName);
        item.setCraftingStation(CraftingStation.MagicalCraftingTable);
        item.setXpToUnlock(0);
        return item;
    }

    public static ItemBase[] makeArmourSet(String[] registryNames, String setName, String[] lore, Material[] materials, ItemStack[][] recipe, CraftingStation station, int[] xpToUnlock){
        ItemBase[] items = new ItemBase[4];
        String[] pieces = new String[] {"Helmet", "Chestplate", "Leggings", "Boots"};
        for(int i = 0; i < 4; i++){
            ItemBase item = new ItemBase();
            //Objects
            ItemStack itemStack = new ItemStack(materials[i]);
            ItemMeta meta = itemStack.getItemMeta();
            meta.setDisplayName(ChatColor.AQUA + setName + " " + pieces[i]);
            List<String> lore2 = new ArrayList<>();
            for(String line : lore){
                lore2.add(ChatColor.RESET + "" + ChatColor.GREEN + line);
            }
            meta.setLore(lore2);
            itemStack.setItemMeta(meta);
            item.setItem(itemStack);
            item.setRecipe(recipe[i]);
            item.setName(registryNames[i]);
            item.setCraftingStation(station);
            item.setXpToUnlock(xpToUnlock[i]);
            items[i] = item;
        }
        return items;
    }

    public static ItemBase[] makeArmourSet(String[] registryNames, String setName, String[] lore, Color[] colors, ItemStack[][] recipe, CraftingStation station, int[] xpToUnlock){
        ItemBase[] items = new ItemBase[4];
        String[] pieces = new String[] {"Helmet", "Chestplate", "Leggings", "Boots"};
        Material[] mats = new Material[] {Material.LEATHER_HELMET, Material.LEATHER_CHESTPLATE, Material.LEATHER_LEGGINGS, Material.LEATHER_BOOTS};
        for(int i = 0; i < 4; i++){
            ItemBase item = new ItemBase();
            //Objects
            ItemStack itemStack = new ItemStack(mats[i]);
            LeatherArmorMeta meta = (LeatherArmorMeta) itemStack.getItemMeta();
            meta.setDisplayName(ChatColor.AQUA + setName + " " + pieces[i]);
            List<String> lore2 = new ArrayList<>();
            for(String line : lore){
                lore2.add(ChatColor.RESET + "" + ChatColor.GREEN + line);
            }
            meta.setLore(lore2);
            meta.setColor(colors[i]);
            itemStack.setItemMeta(meta);
            item.setItem(itemStack);
            item.setRecipe(recipe[i]);
            item.setName(registryNames[i]);
            item.setCraftingStation(station);
            item.setXpToUnlock(xpToUnlock[i]);
            items[i] = item;
        }
        return items;
    }

    public static Category makeCategory(String registryName, String itemName, String[] lore, Material material){
        Category category = new Category();
        //Objects
        ItemStack itemStack = new ItemStack(material);
        ItemMeta meta = itemStack.getItemMeta();
        meta.setDisplayName(ChatColor.AQUA + itemName);
        List<String> lore2 = new ArrayList<>();
        for(String line : lore){
            lore2.add(ChatColor.RESET + "" + ChatColor.GREEN + line);
        }
        meta.setLore(lore2);
        itemStack.setItemMeta(meta);
        category.setItem(itemStack);
        category.setName(registryName);
        category.setXpToUnlock(0);
        return category;
    }

    public static Category makeCategory(String registryName, String itemName, String[] lore, Material material, int xpToUnlock){
        Category category = new Category();
        //Objects
        ItemStack itemStack = new ItemStack(material);
        ItemMeta meta = itemStack.getItemMeta();
        meta.setDisplayName(ChatColor.AQUA + itemName);
        List<String> lore2 = new ArrayList<>();
        for(String line : lore){
            lore2.add(ChatColor.RESET + "" + ChatColor.GREEN + line);
        }
        meta.setLore(lore2);
        itemStack.setItemMeta(meta);
        category.setItem(itemStack);
        category.setName(registryName);
        category.setXpToUnlock(xpToUnlock);
        return category;
    }

    public static Category makeCategory(String registryName, ItemStack itemStack, int xpToUnlock){
        Category category = new Category();
        category.setItem(itemStack);
        category.setName(registryName);
        category.setXpToUnlock(xpToUnlock);
        return category;
    }

    public static MultiBlock makeMultiBlock(String itemName, String[] lore, Material material, Material[] structure){
        MultiBlock multiBlock = new MultiBlock();
        //Objects
        ItemStack itemStack = new ItemStack(material);
        ItemMeta meta = itemStack.getItemMeta();
        meta.setDisplayName(ChatColor.AQUA + itemName);
        List<String> lore2 = new ArrayList<>();
        for(String line : lore){
            lore2.add(ChatColor.RESET + "" + ChatColor.GREEN + line);
        }
        meta.setLore(lore2);
        itemStack.setItemMeta(meta);
        multiBlock.setIcon(itemStack);
        multiBlock.setMaterials(structure);
        multiBlock.setXpToUnlock(0);
        return multiBlock;
    }

    public static MultiBlock makeMultiBlock(String itemName, String[] lore, Material material, Material[] structure, int xpToUnlock){
        MultiBlock multiBlock = new MultiBlock();
        //Objects
        ItemStack itemStack = new ItemStack(material);
        ItemMeta meta = itemStack.getItemMeta();
        meta.setDisplayName(ChatColor.AQUA + itemName);
        List<String> lore2 = new ArrayList<>();
        for(String line : lore){
            lore2.add(ChatColor.RESET + "" + ChatColor.GREEN + line);
        }
        meta.setLore(lore2);
        itemStack.setItemMeta(meta);
        multiBlock.setIcon(itemStack);
        multiBlock.setMaterials(structure);
        multiBlock.setXpToUnlock(xpToUnlock);
        return multiBlock;
    }

    public static Machine makeMachine(String registryName, String itemName, String[] lore, Material material, Object[] recipe, CraftingStation craftingStation, int xpToUnlock){
        Machine machine = new Machine();
        //Objects
        ItemStack itemStack = new ItemStack(material);
        ItemMeta meta = itemStack.getItemMeta();
        meta.setDisplayName(ChatColor.AQUA + itemName);
        List<String> lore2 = new ArrayList<>();
        for(String line : lore){
            lore2.add(ChatColor.RESET + "" + ChatColor.GREEN + line);
        }
        meta.setLore(lore2);
        itemStack.setItemMeta(meta);
        machine.setAmount(itemStack.getAmount());
        machine.setItem(itemStack);
        machine.setRecipe(recipe);
        machine.setXpToUnlock(xpToUnlock);
        machine.setName(registryName);
        machine.setCraftingStation(craftingStation);
        return machine;
    }

    public static Machine makeMachine(String registryName, ItemStack itemStack, Object[] recipe, CraftingStation craftingStation, int xpToUnlock){
        Machine machine = new Machine();
        machine.setAmount(itemStack.getAmount());
        machine.setItem(itemStack);
        machine.setRecipe(recipe);
        machine.setXpToUnlock(xpToUnlock);
        machine.setName(registryName);
        machine.setCraftingStation(craftingStation);
        return machine;
    }

    public static CustomRecipe makeCustomRecipe(ItemStack out, ItemStack[] recipe, CraftingStation craftingStation, Category category) {
        return new CustomRecipe(out, recipe, craftingStation, category);
    }

}
