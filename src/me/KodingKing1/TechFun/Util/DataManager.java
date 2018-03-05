package me.KodingKing1.TechFun.Util;

import me.KodingKing1.TechFun.Objects.ItemBase;
import me.KodingKing1.TechFun.Startup.TechFunGuide;
import me.KodingKing1.TechFun.TechFunMain;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by dylan on 1/03/2017.
 */
public class DataManager {

    public static Object getItemConfig(ItemBase item, String path){
        ConfigurationSection section = checkSectionMain("ItemConfig." + item.getName());
        return getData(section, path);
    }

    public static Object getPlayerData(Player p, String path){
        ConfigurationSection section = checkSection(p.getUniqueId().toString());
        return getData(section, path);
    }

    public static Object getData(ConfigurationSection section, String path) {
        return section.get(path);
    }

    public static void setPlayerData(Player p, String path, Object object){
        ConfigurationSection section = checkSection(p.getUniqueId().toString());
        section.set(path, object);
        TechFunMain.saveData();
    }

    public static ConfigurationSection checkSection(String path){
        ConfigurationSection section = TechFunMain.getData().getConfigurationSection(path);
        if(section == null){
            TechFunMain.getData().createSection(path);
            section = TechFunMain.getData().getConfigurationSection(path);
        }
        return section;
    }

    public static ConfigurationSection checkSectionMain(String path){
        ConfigurationSection section = TechFunMain.getData().getConfigurationSection(path);
        if(section == null){
            TechFunMain.getData().createSection(path);
            section = TechFunMain.getData().getConfigurationSection(path);
        }
        return section;
    }

    public static void saveBlockData(Block b, String path, Object value) {
        ConfigurationSection section = checkSection("Blocks." + b.getX() + "-" + b.getY() + "-" + b.getZ() + "-" + b.getWorld());
        section.set(path, value);
        TechFunMain.saveData();
    }

    public static Object getBlockData(Block b, String path, Object defaultValue) {
        ConfigurationSection section = checkSection("Blocks." + b.getX() + "-" + b.getY() + "-" + b.getZ() + "-" + b.getWorld());
        if (section.get(path) == null) {
            section.set(path, defaultValue);
        }
        return section.get(path);
    }

    public static Object getBlockData(Block b, String path) {
        ConfigurationSection section = checkSection("Blocks." + b.getX() + "-" + b.getY() + "-" + b.getZ() + "-" + b.getWorld());
        return section.get(path);
    }

    public static void deleteBlockData(Block b) {
        if (TechFunMain.getData().isConfigurationSection( "Blocks." + b.getX() + "-" + b.getY() + "-" + b.getZ() + "-" + b.getWorld())) {
            TechFunMain.getData().set("Blocks." + b.getX() + "-" + b.getY() + "-" + b.getZ() + "-" + b.getWorld(), null);
        }
        TechFunMain.saveData();
    }

//    public static void saveInventory(Inventory inv){
//        ConfigurationSection section = checkSection("Invs." + ChatColor.stripColor(inv.getTitle()));
//        section.set("Type", inv.getType().getDefaultTitle().toUpperCase());
//        section.set("Size", inv.getSize());
//        for(int i = 0; i < inv.getSize(); i++){
//            section.set(String.valueOf("Items." + i), inv.getItem(i));
//        }
//        TechFunMain.saveData();
//    }
//
//    public static Inventory loadInventory(Inventory defaultInv, String title){
//        ConfigurationSection section = TechFunMain.getData().getConfigurationSection("Invs." + ChatColor.stripColor(title));
//        if(section == null){
//            saveInventory(defaultInv);
//            section = TechFunMain.getData().getConfigurationSection("Invs." + ChatColor.stripColor(title));
//        }
//        Inventory inv;
//        InventoryType type = InventoryType.valueOf(section.getString("Type"));
//        int size = section.getInt("Size");
//        if(type != InventoryType.CHEST)
//            inv = Bukkit.createInventory(defaultInv.getHolder(), type, title);
//        else
//            inv = Bukkit.createInventory(defaultInv.getHolder(), size, title);
//        if(section.getConfigurationSection("Items") == null)
//            return inv;
//        for(int i = 0; i < section.getConfigurationSection("Items").getKeys(false).size(); i++){
//            inv.setItem(i, section.getItemStack(String.valueOf(i)));
//        }
//        return inv;
//    }

    public static void saveInventory(Inventory inv) {
        ItemStack[] i = inv.getContents();
        int pos = 0;
        TechFunMain.getData().set(ChatColor.stripColor(inv.getTitle()) + ".Size", i.length);
        for (ItemStack stack : i) {
            TechFunMain.getData().set(ChatColor.stripColor(inv.getTitle()) + ".Items." + pos, stack);
            pos++;
        }
    }

    public static Inventory loadInventory(String title) {
        Set<String> sl = TechFunMain.getData().getConfigurationSection(ChatColor.stripColor(title) + ".Items").getKeys(false);

        Inventory inv = Bukkit.createInventory(null, TechFunMain.getData().getConfigurationSection(ChatColor.stripColor(title)).getInt("Size"), title);
        for (String s : sl) {
            inv.setItem(Integer.parseInt(s), TechFunMain.getData().getItemStack(title + ".Items." + s));
        }
        return inv;
    }

    public enum DataType {
        Item
    }

    public static boolean hasUnlockedData(Player p, DataType type, String name){
        switch(type){
            case Item:
                return (Boolean) getPlayerData(p, "Guide.Items." + name + ".Unlocked");
        }
        return false;
    }

}
