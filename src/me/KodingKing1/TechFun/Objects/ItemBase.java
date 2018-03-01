package me.KodingKing1.TechFun.Objects;

import me.KodingKing1.TechFun.Objects.Handlers.Item.ItemHandler;
import me.KodingKing1.TechFun.Startup.Registry;
import me.KodingKing1.TechFun.TechFunMain;
import me.KodingKing1.TechFun.Util.TextUtil;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dylan on 27/02/2017.
 */
public class ItemBase {

    String name = "No Name Set";
    ItemStack item = new ItemStack(Material.COAL);
    ItemStack[] recipe;
    CraftingStation craftingStation;
    int xpToUnlock;
    List<ItemHandler> handlers = new ArrayList<>();
    int amount = 0;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ItemStack getItem() {
        return item;
    }

    public void setItem(ItemStack item) {
        this.item = item;
        this.amount = new ItemStack(item).getAmount();
    }

    public ItemStack[] getRecipe() {
        return recipe;
    }

    public void setRecipe(Object[] recipe) {
        List<ItemStack> items = new ArrayList<>();
        for(Object obj : recipe){
            if(obj instanceof ItemStack){
                items.add((ItemStack) obj);
            }else if(obj instanceof Material){
                items.add(new ItemStack((Material) obj));
            }else if(obj instanceof ItemBase){
                items.add(((ItemBase) obj).getItem());
            }else if(obj == null){
                items.add(null);
            }else{
                TechFunMain.getPluginLogger().log(TextUtil.Level.Error, "Could not add recipe for Item " + name + " because it contains an object that is not an item or material!");
            }
        }
        this.recipe = items.toArray(new ItemStack[]{});
    }

    public List<ItemHandler> getHandlers() {
        return handlers;
    }

    public CraftingStation getCraftingStation() {
        return craftingStation;
    }

    public int getAmount() {
        return amount;
    }

    public void setCraftingStation(CraftingStation craftingStation) {
        this.craftingStation = craftingStation;
    }

    public int getXpToUnlock() {
        return xpToUnlock;
    }

    public void setXpToUnlock(int xpToUnlock) {
        this.xpToUnlock = xpToUnlock;
    }

    public void registerHandler(ItemHandler handler){
        handlers.add(handler);
    }

    public void register(){
        Registry.registerItem(this);
    }
}
