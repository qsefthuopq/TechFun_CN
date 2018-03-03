package me.KodingKing1.TechFun.Util;

import com.deanveloper.skullcreator.SkullCreator;
import me.KodingKing1.TechFun.Objects.Factory;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.plugin.Plugin;

import java.lang.reflect.InvocationTargetException;
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

    public static Class<?> getNmsClass(String className, Plugin plugin) throws ClassNotFoundException {

        return Class.forName("net.minecraft.server." + plugin.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3] + "." + className);
    }

    public static Class<?> getCraftbukkitClass(String className, Plugin plugin) throws ClassNotFoundException {
        return Class.forName("org.bukkit.craftbukkit." + plugin.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3] + "." + className);
    }

    public static ItemStack makeMobSpawnEgg(String entityName, Plugin plugin) {
        try {
            ItemStack egg = new ItemStack(Material.MONSTER_EGG, 2);
            Object nmsStack = TFUtil.getCraftbukkitClass("inventory.CraftItemStack", plugin).getMethod("asNMSCopy", ItemStack.class).invoke(null, egg);
            Object tag = (Boolean)nmsStack.getClass().getMethod("hasTag").invoke(nmsStack) ? nmsStack.getClass().getMethod("getTag").invoke(nmsStack) : TFUtil.getNmsClass("NBTTagCompound", plugin).newInstance();
            Object nested = TFUtil.getNmsClass("NBTTagCompound", plugin).newInstance();
            nested.getClass().getMethod("setString", String.class, String.class).invoke(nested, "id", entityName);
            tag.getClass().getMethod("set", String.class, TFUtil.getNmsClass("NBTBase", plugin)).invoke(tag, "EntityTag", nested);
            nmsStack.getClass().getMethod("setTag", TFUtil.getNmsClass("NBTTagCompound", plugin)).invoke(nmsStack, tag);
            ItemStack finalItem = (ItemStack) TFUtil.getCraftbukkitClass("inventory.CraftItemStack", plugin).getMethod("asBukkitCopy", TFUtil.getNmsClass("ItemStack", plugin)).invoke(null, nmsStack);
            return finalItem;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static ItemStack makeSkullWithBase64(String base64) {
        ItemStack item = SkullCreator.fromBase64(SkullCreator.Type.ITEM, base64);
        return item;
    }

    public static ItemStack makeSkullWithBase64(String base64, String name, String[] lore) {
        ItemStack item = makeSkullWithBase64(base64);
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

    public static ItemStack makeSkullWithBase64(String base64, String name, String[] lore, int amount) {
        ItemStack item = makeSkullWithBase64(base64, name, lore);
        item.setAmount(amount);
        return item;
    }

    public static ItemStack makeMobSpawnEgg(String entityName, int amount, Plugin plugin) {
        ItemStack egg = makeMobSpawnEgg(entityName, plugin);
        egg.setAmount(amount);
        return egg;
    }

}
