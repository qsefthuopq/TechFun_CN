package me.KodingKing1.TechFun.Objects;

import me.KodingKing1.TechFun.Util.TFUtil;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

/**
 * Created by dylan on 28/02/2017.
 */
public enum CraftingStation {

    MagicalCraftingTable(TFUtil.makeItem("Magical Crafting Table", new String[]{"The very beginning of TechFun!"}, Material.WORKBENCH)),
    Smeltry(TFUtil.makeItem("Smeltry", new String[]{"Used to smelt items into its products."}, Material.FURNACE)),
    Ore(TFUtil.makeItem("Ore", new String[]{"This item is obtained by breaking", "an ore!"}, Material.IRON_ORE)),
    Forge(TFUtil.makeItem("Forge", new String[]{"Lets you craft things like weapons and tools!"}, Material.ANVIL)),
    CookingBench(TFUtil.makeItem("Cooking Bench", new String[]{"Lets you cook different foods!"}, Material.BURNING_FURNACE)),
    Compressor(TFUtil.makeItem("Compressor", new String[]{"Compresses items into its products."}, Material.PISTON_BASE)),
    ManufacturingTable(TFUtil.makeItem("Manufacturing Table", new String[]{"Lets you create complex machines!"}, Material.ANVIL)),
    DefaultCraftingTable(TFUtil.makeItem("Crafting Table", new String[]{"Crafted in the default crafting table of Minecraft!", "No fanciness here!"}, Material.WORKBENCH));

    public ItemStack icon;

    CraftingStation(ItemStack icon) {
        this.icon = icon;
    }

    public ItemStack getIcon() {
        return icon;
    }
}
