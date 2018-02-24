package me.KodingKing1.TechFun.Startup;

import com.bobacadodl.imgmessage.ImageChar;
import com.bobacadodl.imgmessage.ImageMessage;
import me.KodingKing1.TechFun.Objects.Category.Category;
import me.KodingKing1.TechFun.Objects.CustomRecipe;
import me.KodingKing1.TechFun.Objects.Inv.Page;
import me.KodingKing1.TechFun.Objects.ItemBase;
import me.KodingKing1.TechFun.Objects.MultiBlock.MultiBlock;
import me.KodingKing1.TechFun.TechFunMain;
import me.KodingKing1.TechFun.Util.DataManager;
import me.KodingKing1.TechFun.Util.InvUtil;
import me.KodingKing1.TechFun.Util.TFUtil;
import me.KodingKing1.TechFun.Util.TextUtil;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by dylan on 11/01/2017.
 */
public class TechFunGuide implements Listener {

    public HashMap<Integer, Page> homes = new HashMap<>();
    public static HashMap<Category, List<Page>> categoryInvs = new HashMap<>();

    public ItemStack prevpageitem = TFUtil.makeItem("Previous Page", new String[]{}, Material.STAINED_GLASS_PANE, 1, 5);
    public ItemStack nextpageitem = TFUtil.makeItem("Next Page", new String[]{}, Material.STAINED_GLASS_PANE, 1, 5);

    //0 |1 |2 |3 |4 |5 |6 |7 |8 |
    //9 |10|11|12|13|14|15|16|17|
    //18|19|20|21|22|23|24|25|26|
    //27|28|29|30|31|32|33|34|35|
    //36|37|38|39|40|41|42|43|44|
    //45|46|47|48|49|50|51|52|53|

    private static Player player;

    public static void open(Player p){
        player = p;
        p.openInventory(new TechFunGuide().homes.get(0).getInv());
    }


    public TechFunGuide() {
        int homenum = 0;
        int homecount = 0;
        boolean homedone = false;
        Inventory homeInv = Bukkit.createInventory(null, 54, ChatColor.AQUA + "TechFun Guide - Home");
        putBorder(homeInv);
        homeInv.setMaxStackSize(1);
        homeInv.setItem(49, TFUtil.makeItem("Page: " + String.valueOf(homecount + 1), new String[]{}, Material.STAINED_GLASS_PANE, 1, 3));
        for(Category category : Registry.getCategories()) {
            List<Page> pages = new ArrayList<>();
            int catnum = 0;
            int catcount = 0;
            Inventory catInv = Bukkit.createInventory(null, 54, ChatColor.AQUA + "TechFun Guide - Items");
            putBorder(catInv);
            catInv.setItem(49, TFUtil.makeItem("Page: " + String.valueOf(catcount + 1), new String[]{}, Material.STAINED_GLASS_PANE, 1, 3));
            catInv.setMaxStackSize(1);
            for (ItemBase item : category.getItems()) {
                if (player != null) {
                    Boolean unlocked;
                    if (DataManager.getPlayerData(player, "Guide.Items." + item.getName() + ".Unlocked") != null) {
                        unlocked = (Boolean) DataManager.getPlayerData(player, "Guide.Items." + item.getName() + ".Unlocked");
                    } else {
                        DataManager.setPlayerData(player, "Guide.Items." + item.getName() + ".Unlocked", false);
                        unlocked = (Boolean) DataManager.getPlayerData(player, "Guide.Items." + item.getName() + ".Unlocked");
                    }
                    if (!unlocked) {
                        if (item.getXpToUnlock() > 0) {
                            catInv.addItem(TFUtil.makeItem("Locked Objects - " + ChatColor.stripColor(item.getItem().getItemMeta().getDisplayName()), new String[]{"Xp to unlock: " + String.valueOf(item.getXpToUnlock())}, Material.REDSTONE_BLOCK));
                        } else {
                            catInv.addItem(item.getItem());
                        }
                    } else {
                        catInv.addItem(item.getItem());
                    }
                } else {
                    catInv.addItem(item.getItem());
                }
                catnum++;
                if (catnum == 35) {
                    catnum = 0;
                    Page page = new Page();
                    page.setId(catcount);
                    page.setInv(catInv);
                    pages.add(page);
                    catcount++;
                    catInv = Bukkit.createInventory(null, 54, ChatColor.AQUA + "TechFun Guide - Items");
                    putBorder(catInv);
                    catInv.setItem(49, TFUtil.makeItem("Page: " + String.valueOf(catcount + 1), new String[]{}, Material.STAINED_GLASS_PANE, 1, 3));
                    catInv.setMaxStackSize(1);
                }
            }
            for (MultiBlock item : category.getMultiBlocks()) {
                if (player != null) {
                    Boolean unlocked;
                    if (DataManager.getPlayerData(player, "Guide.Items." + ChatColor.stripColor(item.getIcon().getItemMeta().getDisplayName()) + ".Unlocked") != null) {
                        unlocked = (Boolean) DataManager.getPlayerData(player, "Guide.Items." + ChatColor.stripColor(item.getIcon().getItemMeta().getDisplayName()) + ".Unlocked");
                    } else {
                        DataManager.setPlayerData(player, "Guide.Items." + ChatColor.stripColor(item.getIcon().getItemMeta().getDisplayName()) + ".Unlocked", false);
                        unlocked = (Boolean) DataManager.getPlayerData(player, "Guide.Items." + ChatColor.stripColor(item.getIcon().getItemMeta().getDisplayName()) + ".Unlocked");
                    }
                    if (!unlocked) {
                        if (item.getXpToUnlock() > 0) {
                            catInv.addItem(TFUtil.makeItem("Locked MultiBlock - " + ChatColor.stripColor(item.getIcon().getItemMeta().getDisplayName()), new String[]{"Xp to unlock: " + String.valueOf(item.getXpToUnlock())}, Material.REDSTONE_BLOCK));
                        } else {
                            catInv.addItem(item.getIcon());
                        }
                    } else {
                        catInv.addItem(item.getIcon());
                    }
                } else {
                    catInv.addItem(item.getIcon());
                }
                catnum++;
                if (catnum == 35) {
                    catnum = 0;
                    Page page = new Page();
                    page.setId(catcount);
                    page.setInv(catInv);
                    pages.add(page);
                    catcount++;
                    catInv = Bukkit.createInventory(null, 54, ChatColor.AQUA + "TechFun Guide - Items");
                    putBorder(catInv);
                    catInv.setItem(49, TFUtil.makeItem("Page: " + String.valueOf(catcount + 1), new String[]{}, Material.STAINED_GLASS_PANE, 1, 3));
                    catInv.setMaxStackSize(1);
                }
            }
            for (CustomRecipe recipe : Registry.getCustomRecipes()) {
                if (category == recipe.getCategory()) {
                    if (player != null) {
                        catInv.addItem(recipe.getOut());
                    } else {
                        catInv.addItem(recipe.getOut());
                    }
                    catnum++;
                }
                if (catnum == 35) {
                    catnum = 0;
                    Page page = new Page();
                    page.setId(catcount);
                    page.setInv(catInv);
                    pages.add(page);
                    catcount++;
                    catInv = Bukkit.createInventory(null, 54, ChatColor.AQUA + "TechFun Guide - Items");
                    putBorder(catInv);
                    catInv.setItem(49, TFUtil.makeItem("Page: " + String.valueOf(catcount + 1), new String[]{}, Material.STAINED_GLASS_PANE, 1, 3));
                    catInv.setMaxStackSize(1);
                }
            }

            if (catnum > 0) {
                Page page = new Page();
                page.setId(catcount);
                page.setInv(catInv);
                pages.add(page);
            }
            categoryInvs.put(category, pages);
            if (player != null) {
                Boolean unlocked;
                if (DataManager.getPlayerData(player, "Guide.Categories." + category.getName() + ".Unlocked") != null) {
                    unlocked = (Boolean) DataManager.getPlayerData(player, "Guide.Categories." + category.getName() + ".Unlocked");
                } else {
                    DataManager.setPlayerData(player, "Guide.Categories." + category.getName() + ".Unlocked", false);
                    unlocked = (Boolean) DataManager.getPlayerData(player, "Guide.Categories." + category.getName() + ".Unlocked");
                }
                if (!unlocked) {
                    if (category.getXpToUnlock() > 0) {
                        homeInv.addItem(TFUtil.makeItem("Locked Category - " + category.getName(), new String[]{"Xp to unlock: " + String.valueOf(category.getXpToUnlock())}, Material.REDSTONE_BLOCK));
                    } else {
                        homeInv.addItem(category.getItem());
                    }
                } else {
                    homeInv.addItem(category.getItem());
                }
            } else {
                homeInv.addItem(category.getItem());
            }
            homenum++;
            if (homenum == 35) {
                homenum = 0;
                Page page = new Page();
                page.setId(homecount);
                page.setInv(homeInv);
                homes.put(homecount, page);
                homecount++;
                homeInv = Bukkit.createInventory(null, 54, ChatColor.AQUA + "TechFun Guide - Home");
                putBorder(homeInv);
                homeInv.setMaxStackSize(1);
                homeInv.setItem(49, TFUtil.makeItem("Page: " + String.valueOf(homecount + 1), new String[]{}, Material.STAINED_GLASS_PANE, 1, 3));
                homedone = true;
            }
        }
        if (homedone == false) {
            Page page = new Page();
            page.setId(homecount);
            page.setInv(homeInv);
            homeInv.setMaxStackSize(1);
            homeInv.setItem(49, TFUtil.makeItem("Page: " + String.valueOf(homecount + 1), new String[]{}, Material.STAINED_GLASS_PANE, 1, 3));
            homes.put(homecount, page);
        }
    }

    public void putBorder(Inventory inv){
        ItemStack glass = TFUtil.makeItem("", new String[]{}, Material.STAINED_GLASS_PANE);
        inv.setItem(0, glass);
        inv.setItem(1, glass);
        inv.setItem(2, glass);
        inv.setItem(3, glass);
        inv.setItem(4, glass);
        inv.setItem(5, glass);
        inv.setItem(6, glass);
        inv.setItem(7, glass);
        inv.setItem(8, glass);
        inv.setItem(45, glass);
        inv.setItem(46, glass);
        inv.setItem(47, glass);
        inv.setItem(48, prevpageitem);
        inv.setItem(49, glass);
        inv.setItem(50, nextpageitem);
        inv.setItem(51, glass);
        inv.setItem(52, glass);
        inv.setItem(53, glass);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e){
        if(e.getClickedInventory() == null || e.getCurrentItem() == null){
            return;
        }
        Player p = (Player) e.getWhoClicked();
        if(e.getClickedInventory().getTitle().toLowerCase().contains("TechFun Guide - Home".toLowerCase())){
            e.setCancelled(true);
            if(e.getCurrentItem().isSimilar(prevpageitem)){
                for(Page inv : homes.values()){
                    if(InvUtil.isInvSimilar(inv.getInv(), e.getClickedInventory())){
                        if((inv.getId() - 1) <= 0){
                            TechFunMain.getPluginLogger().sendMessage(p, TextUtil.Level.Error, "You are at the first page!");
                            return;
                        }else{
                            p.openInventory(inv.getInv());
                            p.playSound(p.getLocation(), Sound.ENTITY_BAT_TAKEOFF, 1.0F, 1.0F);
                        }
                        return;
                    }
                }
            }else if(e.getCurrentItem().isSimilar(nextpageitem)){
                for(Page inv : homes.values()){
                    if(InvUtil.isInvSimilar(inv.getInv(), e.getClickedInventory())){
                        if((inv.getId() + 1) >= homes.size()){
                            TechFunMain.getPluginLogger().sendMessage(p, TextUtil.Level.Error, "You are at the last page!");
                            return;
                        }else{
                            p.openInventory(inv.getInv());
                            p.playSound(p.getLocation(), Sound.ENTITY_BAT_TAKEOFF, 1.0F, 1.0F);
                        }
                        return;
                    }
                }
            }
            if(e.getCurrentItem().getType() == Material.STAINED_GLASS_PANE){
                return;
            }
            for(Category category : Registry.getCategories()){
                if(e.getCurrentItem().isSimilar(TFUtil.makeItem("Locked Category - " + category.getName(), new String[]{"Xp to unlock: " + String.valueOf(category.getXpToUnlock())}, Material.REDSTONE_BLOCK))){
                    if(p.getLevel() >= category.getXpToUnlock()){
                        p.setLevel(p.getLevel() - category.getXpToUnlock());
                        DataManager.setPlayerData(p, "Guide.Categories." + category.getName() + ".Unlocked", true);
                        TechFunGuide.open(p);
                        TechFunMain.getPluginLogger().sendMessage(p, TextUtil.Level.Success, "Category unlocked!");
                        p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.0F, 1.0F);
                    }else if(p.getGameMode() == GameMode.CREATIVE) {
                        DataManager.setPlayerData(p, "Guide.Categories." + category.getName() + ".Unlocked", true);
                        TechFunGuide.open(p);
                        TechFunMain.getPluginLogger().sendMessage(p, TextUtil.Level.Success, "Category unlocked!");
                        p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.0F, 1.0F);
                    }else{
                        TechFunMain.getPluginLogger().sendMessage(p, TextUtil.Level.Error, "Insufficient xp!");
                    }
                    return;
                }
                if(e.getCurrentItem().isSimilar(category.getItem())){
                    p.openInventory(categoryInvs.get(category).get(0).getInv());
                    p.playSound(p.getLocation(), Sound.ENTITY_BAT_TAKEOFF, 1.0F, 1.0F);
                }
            }
        }
        if(e.getClickedInventory().getTitle().toLowerCase().contains("TechFun Guide - Items".toLowerCase())) {
            e.setCancelled(true);
            if(!InvUtil.hasMinItems(e.getClickedInventory(), 19)){
                try {
                    BufferedImage imageToSend = ImageIO.read(new URL("http://www.clker.com/cliparts/1/9/1/9/13695058211712817073404-logo-md.png"));
                    new ImageMessage(imageToSend, 8, ImageChar.BLOCK.getChar()
                    ).appendText(
                            "",
                            "",
                            "You seem to have",
                            "opened a category",
                            "with nothing in",
                            "it. Hmmmmmm."
                    ).sendToPlayer(((Player) e.getWhoClicked()).getPlayer());
                }catch (Exception exception){
                    exception.printStackTrace();
                }
            }
            for (CustomRecipe recipe : Registry.getCustomRecipes()) {
                if (e.getCurrentItem().isSimilar(recipe.getOut())) {
                    if (e.getClick() == ClickType.RIGHT && p.isOp() && p.getGameMode() == GameMode.CREATIVE) {
                        p.getInventory().addItem(recipe.getOut());
                        return;
                    }
                    p.playSound(p.getLocation(), Sound.ENTITY_BAT_TAKEOFF, 1.0F, 1.0F);
                    Inventory inv = Bukkit.createInventory(null, 27, ChatColor.AQUA + "TechFun Guide - Objects Info");
                    if (recipe.getRecipe().length >= 1) {
                        if (recipe.getRecipe()[0] != null) {
                            inv.setItem(3, recipe.getRecipe()[0]);
                        }
                    }
                    if (recipe.getRecipe().length >= 2) {
                        if (recipe.getRecipe()[1] != null) {
                            inv.setItem(4, recipe.getRecipe()[1]);
                        }
                    }
                    if (recipe.getRecipe().length >= 3) {
                        if (recipe.getRecipe()[2] != null) {
                            inv.setItem(5, recipe.getRecipe()[2]);
                        }
                    }
                    if (recipe.getRecipe().length >= 4) {
                        if (recipe.getRecipe()[3] != null) {
                            inv.setItem(12, recipe.getRecipe()[3]);
                        }
                    }
                    if (recipe.getRecipe().length >= 5) {
                        if (recipe.getRecipe()[4] != null) {
                            inv.setItem(13, recipe.getRecipe()[4]);
                        }
                    }
                    if (recipe.getRecipe().length >= 6) {
                        if (recipe.getRecipe()[5] != null) {
                            inv.setItem(14, recipe.getRecipe()[5]);
                        }
                    }
                    if (recipe.getRecipe().length >= 7) {
                        if (recipe.getRecipe()[6] != null) {
                            inv.setItem(21, recipe.getRecipe()[6]);
                        }
                    }
                    if (recipe.getRecipe().length >= 8) {
                        if (recipe.getRecipe()[7] != null) {
                            inv.setItem(22, recipe.getRecipe()[7]);
                        }
                    }
                    if (recipe.getRecipe().length >= 9) {
                        if (recipe.getRecipe()[8] != null) {
                            inv.setItem(23, recipe.getRecipe()[8]);
                        }
                    }
                    inv.setItem(10, recipe.getStation().getIcon());
                    inv.setItem(16, recipe.getOut());
                    p.openInventory(inv);
                }
            }
            for (Category c : Registry.getCategories()) {
                if (e.getCurrentItem().isSimilar(prevpageitem)) {
                    for (Page inv : categoryInvs.get(c)) {
                        if (InvUtil.isInvSimilar(inv.getInv(), e.getClickedInventory())) {
                            if ((inv.getId() - 1) < 0) {
                                TechFunMain.getPluginLogger().sendMessage(p, TextUtil.Level.Error, "You are at the first page!");
                                return;
                            } else {
                                p.openInventory(categoryInvs.get(c).get(inv.getId() - 1).getInv());
                                p.playSound(p.getLocation(), Sound.ENTITY_BAT_TAKEOFF, 1.0F, 1.0F);
                            }
                            return;
                        }
                    }
                } else if (e.getCurrentItem().isSimilar(nextpageitem)) {
                    for (Page inv : categoryInvs.get(c)) {
                        if (InvUtil.isInvSimilar(inv.getInv(), e.getClickedInventory())) {
                            if ((inv.getId() + 1) >= categoryInvs.get(c).size()) {
                                TechFunMain.getPluginLogger().sendMessage(p, TextUtil.Level.Error, "You are at the last page!");
                                return;
                            } else {
                                p.openInventory(categoryInvs.get(c).get(inv.getId() + 1).getInv());
                                p.playSound(p.getLocation(), Sound.ENTITY_BAT_TAKEOFF, 1.0F, 1.0F);
                            }
                            return;
                        }
                    }
                }
                for (ItemBase item : c.getItems()) {
                    if (e.getCurrentItem().isSimilar(TFUtil.makeItem("Locked Objects - " + ChatColor.stripColor(item.getItem().getItemMeta().getDisplayName()), new String[]{"Xp to unlock: " + String.valueOf(item.getXpToUnlock())}, Material.REDSTONE_BLOCK))) {
                        if (p.getLevel() >= item.getXpToUnlock()) {
                            p.setLevel(p.getLevel() - item.getXpToUnlock());
                            DataManager.setPlayerData(p, "Guide.Items." + item.getName() + ".Unlocked", true);
                            TechFunGuide.open(p);
                            TechFunMain.getPluginLogger().sendMessage(p, TextUtil.Level.Success, "Objects unlocked!");
                            p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.0F, 1.0F);
                        } else if (p.getGameMode() == GameMode.CREATIVE) {
                            DataManager.setPlayerData(p, "Guide.Items." + item.getName() + ".Unlocked", true);
                            TechFunGuide.open(p);
                            TechFunMain.getPluginLogger().sendMessage(p, TextUtil.Level.Success, "Objects unlocked!");
                            p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.0F, 1.0F);
                        } else {
                            TechFunMain.getPluginLogger().sendMessage(p, TextUtil.Level.Error, "Insufficient xp!");
                        }
                        return;
                    }
                    if (e.getCurrentItem().isSimilar(item.getItem())) {
                        if (e.getClick() == ClickType.RIGHT && p.isOp() && p.getGameMode() == GameMode.CREATIVE) {
                            p.getInventory().addItem(item.getItem());
                            return;
                        }
                        p.playSound(p.getLocation(), Sound.ENTITY_BAT_TAKEOFF, 1.0F, 1.0F);
                        Inventory inv = Bukkit.createInventory(null, 27, ChatColor.AQUA + "TechFun Guide - Objects Info");
                        if (item.getRecipe().length >= 1) {
                            if (item.getRecipe()[0] != null) {
                                inv.setItem(3, item.getRecipe()[0]);
                            }
                        }
                        if (item.getRecipe().length >= 2) {
                            if (item.getRecipe()[1] != null) {
                                inv.setItem(4, item.getRecipe()[1]);
                            }
                        }
                        if (item.getRecipe().length >= 3) {
                            if (item.getRecipe()[2] != null) {
                                inv.setItem(5, item.getRecipe()[2]);
                            }
                        }
                        if (item.getRecipe().length >= 4) {
                            if (item.getRecipe()[3] != null) {
                                inv.setItem(12, item.getRecipe()[3]);
                            }
                        }
                        if (item.getRecipe().length >= 5) {
                            if (item.getRecipe()[4] != null) {
                                inv.setItem(13, item.getRecipe()[4]);
                            }
                        }
                        if (item.getRecipe().length >= 6) {
                            if (item.getRecipe()[5] != null) {
                                inv.setItem(14, item.getRecipe()[5]);
                            }
                        }
                        if (item.getRecipe().length >= 7) {
                            if (item.getRecipe()[6] != null) {
                                inv.setItem(21, item.getRecipe()[6]);
                            }
                        }
                        if (item.getRecipe().length >= 8) {
                            if (item.getRecipe()[7] != null) {
                                inv.setItem(22, item.getRecipe()[7]);
                            }
                        }
                        if (item.getRecipe().length >= 9) {
                            if (item.getRecipe()[8] != null) {
                                inv.setItem(23, item.getRecipe()[8]);
                            }
                        }
                        inv.setItem(10, item.getCraftingStation().getIcon());
                        inv.setItem(16, item.getItem());
                        p.openInventory(inv);
                    }
                }
                for (MultiBlock item : c.getMultiBlocks()) {
                    if (e.getCurrentItem().isSimilar(prevpageitem)) {
                        for (Page inv : categoryInvs.get(c)) {
                            if (InvUtil.isInvSimilar(inv.getInv(), e.getClickedInventory())) {
                                if ((inv.getId() - 1) < 0) {
                                    TechFunMain.getPluginLogger().sendMessage(p, TextUtil.Level.Error, "You are already at the last page!");
                                    return;
                                } else {
                                    p.openInventory(inv.getInv());
                                    p.playSound(p.getLocation(), Sound.ENTITY_BAT_TAKEOFF, 1.0F, 1.0F);
                                }
                            }
                        }
                    } else if (e.getCurrentItem().isSimilar(nextpageitem)) {
                        for (Page inv : categoryInvs.get(c)) {
                            if (InvUtil.isInvSimilar(inv.getInv(), e.getClickedInventory())) {
                                if ((inv.getId() + 1) >= categoryInvs.get(c).size()) {
                                    TechFunMain.getPluginLogger().sendMessage(p, TextUtil.Level.Error, "You are already at the last page!");
                                    return;
                                } else {
                                    p.openInventory(inv.getInv());
                                    p.playSound(p.getLocation(), Sound.ENTITY_BAT_TAKEOFF, 1.0F, 1.0F);
                                }
                            }
                        }
                    }
                    if (e.getCurrentItem().isSimilar(TFUtil.makeItem("Locked MultiBlock - " + ChatColor.stripColor(item.getIcon().getItemMeta().getDisplayName()), new String[]{"Xp to unlock: " + String.valueOf(item.getXpToUnlock())}, Material.REDSTONE_BLOCK))) {
                        if (p.getLevel() >= item.getXpToUnlock()) {
                            p.setLevel(p.getLevel() - item.getXpToUnlock());
                            DataManager.setPlayerData(p, "Guide.Items." + ChatColor.stripColor(item.getIcon().getItemMeta().getDisplayName()) + ".Unlocked", true);
                            TechFunGuide.open(p);
                            TechFunMain.getPluginLogger().sendMessage(p, TextUtil.Level.Success, "MultiBlock unlocked!");
                            p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.0F, 1.0F);
                        } else if (p.getGameMode() == GameMode.CREATIVE) {
                            DataManager.setPlayerData(p, "Guide.Items." + ChatColor.stripColor(item.getIcon().getItemMeta().getDisplayName()) + ".Unlocked", true);
                            TechFunGuide.open(p);
                            TechFunMain.getPluginLogger().sendMessage(p, TextUtil.Level.Success, "MultiBlock unlocked!");
                            p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.0F, 1.0F);
                        } else {
                            TechFunMain.getPluginLogger().sendMessage(p, TextUtil.Level.Error, "Insufficient xp!");
                        }
                        return;
                    }
                    if (e.getCurrentItem().isSimilar(item.getIcon())) {
                        p.playSound(p.getLocation(), Sound.ENTITY_BAT_TAKEOFF, 1.0F, 1.0F);
                        Inventory inv = Bukkit.createInventory(null, 27, ChatColor.AQUA + "TechFun Guide - MultiBlock Info");
                        if (item.getMaterials()[0] != null) {
                            inv.setItem(3, new ItemStack(item.getMaterials()[0]));
                        }
                        if (item.getMaterials()[1] != null) {
                            inv.setItem(4, new ItemStack(item.getMaterials()[1]));
                        }
                        if (item.getMaterials()[2] != null) {
                            inv.setItem(5, new ItemStack(item.getMaterials()[2]));
                        }
                        if (item.getMaterials()[3] != null) {
                            inv.setItem(12, new ItemStack(item.getMaterials()[3]));
                        }
                        if (item.getMaterials()[4] != null) {
                            inv.setItem(13, new ItemStack(item.getMaterials()[4]));
                        }
                        if (item.getMaterials()[5] != null) {
                            inv.setItem(14, new ItemStack(item.getMaterials()[5]));
                        }
                        if (item.getMaterials()[6] != null) {
                            inv.setItem(21, new ItemStack(item.getMaterials()[6]));
                        }
                        if (item.getMaterials()[7] != null) {
                            inv.setItem(22, new ItemStack(item.getMaterials()[7]));
                        }
                        if (item.getMaterials()[8] != null) {
                            inv.setItem(23, new ItemStack(item.getMaterials()[8]));
                        }
                        inv.setItem(10, TFUtil.makeItem("MultiBlock", new String[]{"Build in the world!"}, Material.CLAY_BRICK));
                        inv.setItem(16, item.getIcon());
                        p.openInventory(inv);
                    }
                }
            }
        }
        if(e.getClickedInventory().getTitle().toLowerCase().contains("TechFun Guide - Objects Info".toLowerCase())){
            e.setCancelled(true);
            for(ItemBase item : Registry.getItems()){
                if(e.getCurrentItem().isSimilar(item.getItem())){
                    p.playSound(p.getLocation(), Sound.ENTITY_BAT_TAKEOFF, 1.0F, 1.0F);
                    Inventory inv = Bukkit.createInventory(null, 27, ChatColor.AQUA + "TechFun Guide - Objects Info");
                    if(item.getRecipe().length >= 1){
                        if(item.getRecipe()[0] != null){
                            inv.setItem(3, item.getRecipe()[0]);
                        }
                    }
                    if(item.getRecipe().length >= 2){
                        if(item.getRecipe()[1] != null) {
                            inv.setItem(4, item.getRecipe()[1]);
                        }
                    }
                    if(item.getRecipe().length >= 3){
                        if(item.getRecipe()[2] != null) {
                            inv.setItem(5, item.getRecipe()[2]);
                        }
                    }
                    if(item.getRecipe().length >= 4){
                        if(item.getRecipe()[3] != null) {
                            inv.setItem(12, item.getRecipe()[3]);
                        }
                    }
                    if(item.getRecipe().length >= 5){
                        if(item.getRecipe()[4] != null) {
                            inv.setItem(13, item.getRecipe()[4]);
                        }
                    }
                    if(item.getRecipe().length >= 6){
                        if(item.getRecipe()[5] != null) {
                            inv.setItem(14, item.getRecipe()[5]);
                        }
                    }
                    if(item.getRecipe().length >= 7){
                        if(item.getRecipe()[6] != null) {
                            inv.setItem(21, item.getRecipe()[6]);
                        }
                    }
                    if(item.getRecipe().length >= 8){
                        if(item.getRecipe()[7] != null) {
                            inv.setItem(22, item.getRecipe()[7]);
                        }
                    }
                    if(item.getRecipe().length >= 9){
                        if(item.getRecipe()[8] != null) {
                            inv.setItem(23, item.getRecipe()[8]);
                        }
                    }
                    inv.setItem(10, item.getCraftingStation().getIcon());
                    inv.setItem(16, item.getItem());
                    p.openInventory(inv);
                }
            }
        }
        if(e.getClickedInventory().getTitle().toLowerCase().contains("TechFun Guide - MultiBlock Info".toLowerCase())){
            e.setCancelled(true);
            for(ItemBase item : Registry.getItems()) {
                if (e.getCurrentItem().isSimilar(item.getItem())) {
                    p.playSound(p.getLocation(), Sound.ENTITY_BAT_TAKEOFF, 1.0F, 1.0F);
                    Inventory inv = Bukkit.createInventory(null, 27, ChatColor.AQUA + "TechFun Guide - Objects Info");
                    if (item.getRecipe().length >= 1) {
                        if (item.getRecipe()[0] != null) {
                            inv.setItem(3, item.getRecipe()[0]);
                        }
                    }
                    if (item.getRecipe().length >= 2) {
                        if (item.getRecipe()[1] != null) {
                            inv.setItem(4, item.getRecipe()[1]);
                        }
                    }
                    if (item.getRecipe().length >= 3) {
                        if (item.getRecipe()[2] != null) {
                            inv.setItem(5, item.getRecipe()[2]);
                        }
                    }
                    if (item.getRecipe().length >= 4) {
                        if (item.getRecipe()[3] != null) {
                            inv.setItem(12, item.getRecipe()[3]);
                        }
                    }
                    if (item.getRecipe().length >= 5) {
                        if (item.getRecipe()[4] != null) {
                            inv.setItem(13, item.getRecipe()[4]);
                        }
                    }
                    if (item.getRecipe().length >= 6) {
                        if (item.getRecipe()[5] != null) {
                            inv.setItem(14, item.getRecipe()[5]);
                        }
                    }
                    if (item.getRecipe().length >= 7) {
                        if (item.getRecipe()[6] != null) {
                            inv.setItem(21, item.getRecipe()[6]);
                        }
                    }
                    if (item.getRecipe().length >= 8) {
                        if (item.getRecipe()[7] != null) {
                            inv.setItem(22, item.getRecipe()[7]);
                        }
                    }
                    if (item.getRecipe().length >= 9) {
                        if (item.getRecipe()[8] != null) {
                            inv.setItem(23, item.getRecipe()[8]);
                        }
                    }
                    inv.setItem(10, TFUtil.makeItem("MultiBlock", new String[]{"Build in the world!"}, Material.CLAY_BRICK));
                    inv.setItem(16, item.getItem());
                    p.openInventory(inv);
                }
            }
        }
    }
}
