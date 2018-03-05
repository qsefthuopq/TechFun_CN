package me.KodingKing1.TechFun.Objects.Category;

import me.KodingKing1.TechFun.Objects.ItemBase;
import me.KodingKing1.TechFun.Objects.Machine.Machine;
import me.KodingKing1.TechFun.Objects.MultiBlock.MultiBlock;
import me.KodingKing1.TechFun.Startup.Registry;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dylan on 27/02/2017.
 */
public class Category {

    String name = "No Name Set";
    ItemStack item = new ItemStack(Material.REDSTONE);
    int xpToUnlock = 0;
    List<ItemBase> items = new ArrayList<>();
    List<MultiBlock> multiBlocks = new ArrayList<>();
    List<Machine> machines = new ArrayList<>();

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
    }

    public int getXpToUnlock() {
        return xpToUnlock;
    }

    public void setXpToUnlock(int xpToUnlock) {
        this.xpToUnlock = xpToUnlock;
    }

    public List<ItemBase> getItems() {
        return items;
    }

    public void registerItem(ItemBase item){
        getItems().add(item);
    }

    public List<MultiBlock> getMultiBlocks() {
        return multiBlocks;
    }

    public void registerMultiBlock(MultiBlock mb){
        getMultiBlocks().add(mb);
    }

    public List<Machine> getMachines() {
        return machines;
    }

    public void registerMachine(Machine m) {
        getMachines().add(m);
    }

    public void register(){
        Registry.registerCategory(this);
    }
}
