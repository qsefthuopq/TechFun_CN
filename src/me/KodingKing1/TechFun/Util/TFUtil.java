package me.KodingKing1.TechFun.Util;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by djite on 16/05/2017.
 */
public class TFUtil {

    public static ItemStack makeItem(String name, String[] lore, Material material, int amount, int data){
        ItemStack item = new ItemStack(material, amount, (short) data);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.AQUA + name);
        List<String> lore2 = new ArrayList<>();
        for(String line : lore){
            lore2.add(ChatColor.RESET + "" + ChatColor.DARK_AQUA + line);
        }
        meta.setLore(lore2);
        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack makeItem(String name, String[] lore, Material material){
        return makeItem(name, lore, material, 1, 0);
    }

    public static ItemStack makePlayerHead(String playerName, String itemName, String[] lore){
        ItemStack item = makeItem(itemName, lore, Material.SKULL_ITEM, 1, 3);
        SkullMeta meta = (SkullMeta) item.getItemMeta();
        meta.setOwner(playerName);
        item.setItemMeta(meta);
        return item;
    }

}
