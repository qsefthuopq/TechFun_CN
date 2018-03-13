package me.KodingKing1.TechFun.Util;

import com.deanveloper.skullcreator.SkullCreator;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import me.KodingKing1.TechFun.Objects.Factory;
import me.KodingKing1.TechFun.TechFunMain;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Skull;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.plugin.Plugin;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;

import javax.xml.bind.DatatypeConverter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

/**
 * Created by djite on 16/05/2017.
 */
public class TFUtil {

    public static Method getWorldHandle = null;
    public static Method getWorldTileEntity = null;
    public static Method setGameProfile = null;

    static {
        if (getWorldHandle == null || getWorldTileEntity == null || setGameProfile == null) {
            try {
                getWorldHandle = getCraftClass("CraftWorld").getMethod("getHandle");
                getWorldTileEntity = getMCClass("WorldServer").getMethod("getTileEntity", getMCClass("BlockPosition"));
                setGameProfile = getMCClass("TileEntitySkull").getMethod("setGameProfile", GameProfile.class);
            } catch (NoSuchMethodException | SecurityException e) {
                e.printStackTrace();
            }
        }
    }

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

    public static void eatFood(Player p, int food, int saturation) {
        p.setFoodLevel(Math.min(20, p.getFoodLevel() + food));
        p.setSaturation(Math.min(20, p.getSaturation() + saturation));
        p.getWorld().playSound(p.getLocation(), Sound.ENTITY_GENERIC_EAT, 1, 1);
        String[] messages = { "You really enjoyed that!", "Mmm, that was nice!", "Mmm, tasty!", "That was delicious!" };
        Random r = new Random();
        TechFunMain.getPluginLogger().sendMessage(p, TextUtil.Level.Success, messages[r.nextInt(messages.length - 1)]);
    }

    public static boolean checkForPermission(String permission, CommandSender sender) {
        if (!sender.hasPermission(permission)) {
            TechFunMain.getPluginLogger().sendMessage(sender, TextUtil.Level.Error, "You do not have permission for this command.");
            return false;
        }
        return true;
    }

    public static void setHeadBlock(Location loc, String base64, BlockFace face) {
        Block b = loc.getBlock();
        b.setTypeIdAndData(Material.SKULL.getId(), (byte) 1, true);
        Skull skull = (Skull) b.getState();
        skull.setSkullType(SkullType.PLAYER);
        skull.setRotation(face);
        GameProfile profile = new GameProfile(UUID.randomUUID(), null);
        try {
            profile.getProperties().put("textures", new Property("textures", Base64Coder.encodeString(new String(Base64.getDecoder().decode(base64), "UTF-8"))));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        try {
            Object world = getWorldHandle.invoke(skull.getWorld());
            Object tileSkull = getWorldTileEntity.invoke(world, getMCClass("BlockPosition").getConstructor(double.class, double.class, double.class).newInstance(loc.getX(), loc.getY(), loc.getZ()));
            setGameProfile.invoke(tileSkull, profile);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        skull.getWorld().refreshChunk(skull.getChunk().getX(), skull.getChunk().getZ());
    }

    // Refletion
    private static Class<?> getMCClass(String name) {
        String version = Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3] + ".";
        String className = "net.minecraft.server." + version + name;
        Class<?> clazz = null;
        try {
            clazz = Class.forName(className);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return clazz;
    }

    // Refletion
    private static Class<?> getCraftClass(String name) {
        String version = Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3] + ".";
        String className = "org.bukkit.craftbukkit." + version + name;
        Class<?> clazz = null;
        try {
            clazz = Class.forName(className);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return clazz;
    }

}
