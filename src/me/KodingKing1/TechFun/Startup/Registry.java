package me.KodingKing1.TechFun.Startup;

import me.KodingKing1.TechFun.Objects.Category.Category;
import me.KodingKing1.TechFun.Objects.CraftingStation;
import me.KodingKing1.TechFun.Objects.CustomRecipe;
import me.KodingKing1.TechFun.Objects.ItemBase;
import me.KodingKing1.TechFun.Objects.Machine.Machine;
import me.KodingKing1.TechFun.Objects.MultiBlock.MultiBlock;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.inventory.ShapedRecipe;

import javax.crypto.Mac;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dylan on 27/02/2017.
 */
public class Registry {

    private static List<ItemBase> items = new ArrayList<>();
    private static List<Category> categories = new ArrayList<>();
    private static List<MultiBlock> multiBlocks = new ArrayList<>();
    private static List<CustomRecipe> customRecipes = new ArrayList<>();
    private static List<Machine> machines = new ArrayList<>();

    public static List<ItemBase> getItems() {
        return items;
    }

    public static List<Category> getCategories() {
        return categories;
    }

    public static List<MultiBlock> getMultiBlocks() {
        return multiBlocks;
    }

    public static List<CustomRecipe> getCustomRecipes() {
        return customRecipes;
    }

    public static List<Machine> getMachines() {
        return machines;
    }

    public static int length(){
        return getItems().size() + getCategories().size() + getMultiBlocks().size() + getCustomRecipes().size() + getMachines().size();
    }

    public static void registerItem(ItemBase item){
        items.add(item);
        if(item.getCraftingStation() == CraftingStation.DefaultCraftingTable){
            ShapedRecipe recipe = new ShapedRecipe(item.getItem());
            recipe.shape("012", "345", "678");
            for(int i = 0; i < 9; i++){
                char character = Character.forDigit(i, 10);
                if(item.getRecipe()[i] != null)
                    recipe.setIngredient(character, item.getRecipe()[i].getType());
            }
            Bukkit.addRecipe(recipe);
        }
    }

    public static void registerCategory(Category c){
        categories.add(c);
    }

    public static void registerMultiBlock(MultiBlock multiBlock){
        multiBlocks.add(multiBlock);
    }

    public static void registerCustomRecipe(CustomRecipe recipe){
        customRecipes.add(recipe);
    }

    public static ItemBase getItem(String name){
        for(ItemBase item : getItems()){
            if(item.getName().toLowerCase().contains(name.toLowerCase())){
                return item;
            }
        }
        return null;
    }

    public static MultiBlock getMultiBlock(String name){
        for(MultiBlock mb : getMultiBlocks()){
            if(ChatColor.stripColor(mb.getIcon().getItemMeta().getDisplayName().toLowerCase()).contains(name.toLowerCase())){
                return mb;
            }
        }
        return null;
    }

    public static Category getCategory(String name){
        for(Category c : getCategories()){
            if(c.getName().toLowerCase().contains(name.toLowerCase())){
                return c;
            }
        }
        return null;
    }

    public static void registerArmourSet(ItemBase[] items){
        for(ItemBase item : items){
            item.register();
        }
    }

    public static void registerMachine(Machine machine) {
        machines.add(machine);
    }
}
