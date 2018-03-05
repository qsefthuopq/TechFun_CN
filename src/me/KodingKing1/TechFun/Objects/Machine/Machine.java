package me.KodingKing1.TechFun.Objects.Machine;

import me.KodingKing1.TechFun.Objects.CraftingStation;
import me.KodingKing1.TechFun.Objects.Handlers.Machine.MachineHandler;
import me.KodingKing1.TechFun.Objects.ItemBase;
import me.KodingKing1.TechFun.Startup.Registry;
import me.KodingKing1.TechFun.TechFunMain;
import me.KodingKing1.TechFun.Util.TextUtil;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class Machine {

    public ItemStack item;
    int xpToUnlock;
    public String name;
    public ItemStack[] recipe;
    public CraftingStation craftingStation;
    public int amount;
    public List<MachineHandler> handlers = new ArrayList<>();

    public List<MachineHandler> getHandlers() {
        return handlers;
    }

    public ItemStack getItem() {
        return item;
    }

    public void setItem(ItemStack item) {
        this.item = item;
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
                TechFunMain.getPluginLogger().log(TextUtil.Level.Error, "Could not add recipe for Machine " + name + " because it contains an object that is not an item or material!");
            }
        }
        this.recipe = items.toArray(new ItemStack[]{});
    }

    public int getXpToUnlock() {
        return xpToUnlock;
    }

    public void setXpToUnlock(int xpToUnlock) {
        this.xpToUnlock = xpToUnlock;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CraftingStation getCraftingStation() {
        return craftingStation;
    }

    public void setCraftingStation(CraftingStation craftingStation) {
        this.craftingStation = craftingStation;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void registerHandler(MachineHandler handler) {
        handlers.add(handler);
    }

    public void register() {
        Registry.registerMachine(this);
    }

}
