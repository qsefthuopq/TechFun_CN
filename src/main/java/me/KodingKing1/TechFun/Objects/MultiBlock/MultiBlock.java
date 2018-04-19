package me.KodingKing1.TechFun.Objects.MultiBlock;

import me.KodingKing1.TechFun.Objects.Category.Category;
import me.KodingKing1.TechFun.Objects.Handlers.MultiBlock.MultiBlockHandler;
import me.KodingKing1.TechFun.Startup.Registry;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dylan on 11/01/2017.
 */
public class MultiBlock {

    public Material[] materials;
    public ItemStack icon;
    int xpToUnlock;
    public List<MultiBlockHandler> handlers = new ArrayList<>();

    public Material[] getMaterials() {
        return materials;
    }

    public void setMaterials(Material[] materials) {
        this.materials = materials;
    }

    public ItemStack getIcon() {
        return icon;
    }

    public void setIcon(ItemStack icon) {
        this.icon = icon;
    }

    public int getXpToUnlock() {
        return xpToUnlock;
    }

    public void setXpToUnlock(int xpToUnlock) {
        this.xpToUnlock = xpToUnlock;
    }

    public void registerHandler(MultiBlockHandler handler){
        handlers.add(handler);
    }

    public void register(){
        Registry.registerMultiBlock(this);
    }
}
