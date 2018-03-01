package me.KodingKing1.TechFun.Objects;

import me.KodingKing1.TechFun.Objects.Category.Category;
import me.KodingKing1.TechFun.Startup.Registry;
import org.bukkit.inventory.ItemStack;

/**
 * Created by dylan on 6/03/2017.
 */
public class CustomRecipe {

    public ItemStack out;
    public ItemStack[] recipe;
    public CraftingStation station;
    public Category category;
    public int amount = 0;

    public CustomRecipe(ItemStack out, ItemStack[] recipe, CraftingStation station, Category category) {
        this.out = out;
        this.recipe = recipe;
        this.station = station;
        this.category = category;
        this.amount = new ItemStack(out).getAmount();
    }

    public int getAmount() {
        return amount;
    }

    public ItemStack getOut() {
        return out;
    }

    public ItemStack[] getRecipe() {
        return recipe;
    }

    public CraftingStation getStation() {
        return station;
    }

    public Category getCategory() {
        return category;
    }

    public void register() {
        Registry.registerCustomRecipe(this);
    }
}
