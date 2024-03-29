package me.KodingKing1.TechFun.Startup;

import me.KodingKing1.TechFun.Events.*;
import me.KodingKing1.TechFun.Objects.Category.Category;
import me.KodingKing1.TechFun.Objects.CraftingStation;
import me.KodingKing1.TechFun.Objects.CustomRecipe;
import me.KodingKing1.TechFun.Objects.Factory;
import me.KodingKing1.TechFun.Objects.Handlers.Item.ItemAttackHandler;
import me.KodingKing1.TechFun.Objects.Handlers.Item.ItemBlockBreakHandler;
import me.KodingKing1.TechFun.Objects.Handlers.Item.ItemClickHandler;
import me.KodingKing1.TechFun.Objects.Handlers.Item.ItemOreBlockBreakHandler;
import me.KodingKing1.TechFun.Objects.Handlers.Machine.MachineClickHandler;
import me.KodingKing1.TechFun.Objects.Handlers.MultiBlock.MultiBlockClickHandler;
import me.KodingKing1.TechFun.Objects.ItemBase;
import me.KodingKing1.TechFun.Objects.Machine.Machine;
import me.KodingKing1.TechFun.Objects.MultiBlock.MultiBlock;
import me.KodingKing1.TechFun.TechFunMain;
import me.KodingKing1.TechFun.Util.*;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Dropper;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Created by dylan on 27/02/2017.
 */
public class TechFunStartup {

    private static TechFunMain plugin;
    public static double xpMultiplier = 1;
    public static int spawnEggAmountPeaceful = 1;

    public static void init(TechFunMain techfun) {
        plugin = techfun;
        registerAll();
        registerEvents();
        registerCommands();
        Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new RunnableMain(), 0L, 20L);
    }

    private static void registerAll() {
        /*
        TODO:
         - Make the spawner recipe harder.
         - Add more specific multiblocks.
         - Add more intermediate products.
         */


        String xpMultiplierPath = "XPMultiplier";
        if (!plugin.getConfig().contains(xpMultiplierPath)) {
            plugin.getConfig().set(xpMultiplierPath, 2);
            plugin.saveConfig();
        }
        xpMultiplier = plugin.getConfig().getDouble(xpMultiplierPath);

        String spawnEggPath = "SpawnEggAmountPeaceful";
        if (!plugin.getConfig().contains(spawnEggPath)) {
            plugin.getConfig().set(spawnEggPath, 1);
            plugin.saveConfig();
        }
        spawnEggAmountPeaceful = plugin.getConfig().getInt(spawnEggPath);

        Map<String, String> headBase64List = new HashMap<>();
        //Misc
        headBase64List.put("TrashCan", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYmIyZGFlYTZlYmI2OWE2ODJmNzFkZDhjZWY5ZmZmMDIwNWNjMzQ5ZWM2OTQ0N2E2MWYyNWQxYzA5YWJmNDIifX19");
        headBase64List.put("CraftingTable", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvY2U3ZDhjMjQyZDJlNGY4MDI4ZjkzMGJlNzZmMzUwMTRiMjFiNTI1NTIwOGIxYzA0MTgxYjI1NzQxMzFiNzVhIn19fQ");
        headBase64List.put("TNT", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZWI5OTRiNDFmMDdmODdiMzI4MTg2YWNmY2JkYWJjNjk5ZDViMTg0N2ZhYmIyZTQ5ZDVhYmMyNzg2NTE0M2E0ZSJ9fX0");
        headBase64List.put("Miner", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZDczYmQ2MGFjYTVjZGNiMjY4ZGU0YmQ0NjU2MmI4NmRkM2YxZDg2OGZjNWFkZmM1NjE3ZGI4MDg0NzVmMCJ9fX0");
        headBase64List.put("PlayerBeheader", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMWFlMzg1NWY5NTJjZDRhMDNjMTQ4YTk0NmUzZjgxMmE1OTU1YWQzNWNiY2I1MjYyN2VhNGFjZDQ3ZDMwODEifX19");
        headBase64List.put("XPJar", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMjQyZjY1NzZkNTFjNDY0YzNhNmY0NDViNzJhYWFkYWUzM2Q0NDgwYzkyZWNhYzY0M2MxNjRkYjE3MzA4ODcwIn19fQ");

        //Cores
        headBase64List.put("WoodenCore", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYTBlOWQyYmViODRiMzJlM2YxNWUzODBjYzJjNTUxMDY0MjkxMWE1MTIxMDVmYTJlYzY3OWJjNTQwZmQ4MTg0In19fQ");
        headBase64List.put("StoneCore", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMTk1NTM0ZTAyYzU5YjMzZWNlNTYxOTI4MDMzMTk3OTc3N2UwMjVmYTVmYTgxYWU3NWU5OWZkOGVmZGViYjgifX19");
        headBase64List.put("IronCore", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYmJhODQ1OTE0NWQ4M2ZmYzQ0YWQ1OGMzMjYwZTc0Y2E1YTBmNjM0YzdlZWI1OWExYWQzMjM0ODQ5YzkzM2MifX19");
        headBase64List.put("GoldCore", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYjZkMWNlNjk3ZTlkYmFhNGNjZjY0MjUxNmFhYTU5ODEzMzJkYWMxZDMzMWFmZWUyZWUzZGNjODllZmRlZGIifX19");
        headBase64List.put("DiamondCore", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYzAxNDYxOTczNjM0NTI1MTk2ZWNjNzU3NjkzYjE3MWFkYTRlZjI0YWE5MjgzNmY0MmVhMTFiZDc5YzNhNTAyZCJ9fX0");

        //Foods
        headBase64List.put("Apple", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvY2JiMzExZjNiYTFjMDdjM2QxMTQ3Y2QyMTBkODFmZTExZmQ4YWU5ZTNkYjIxMmEwZmE3NDg5NDZjMzYzMyJ9fX0");
        headBase64List.put("Hamburger", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvY2RhZGYxNzQ0NDMzZTFjNzlkMWQ1OWQyNzc3ZDkzOWRlMTU5YTI0Y2Y1N2U4YTYxYzgyYmM0ZmUzNzc3NTUzYyJ9fX0");
        headBase64List.put("Donut", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZDA3YjhjNTFhY2VjMmE1MDhiYjJmYTY1MmZiNmU0YTA4YjE5NDg1MTU5YTA5OWY1OTgyY2NiODhkZjFmZTI3ZSJ9fX0");

        //Materials
        headBase64List.put("CompressedCarbon", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMjI1MjNlMTVlOTk4NjM1NWExZjg1MWY0M2Y3NTBlZTNmMjNjODlhZTEyMzYzMWRhMjQxZjg3MmJhN2E3ODEifX19");
//        headBase64List.put("Uranium", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNThjMjA2ZTI5OTI0Yjk5MTZkNGQyNGRmYmJjMzhmMjhiNDRkNmQzY2ZhMjNhZGVjOWVkM2E4ZmNlMWI3YjIifX19");
        headBase64List.put("Uranium", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYzhiMjlhZmE2ZDZkYzkyM2UyZTEzMjRiZjgxOTI3NTBmN2JkYmRkYzY4OTYzMmEyYjZjMThkOWZlN2E1ZSJ9fX0");
        headBase64List.put("RawBronze", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNzc3ZDYxNmJjNDRhYzliMzczMGZlZDQ3ZjI5YTM3OGY4OGExNjcyOGM2NzA0OGMxYTM4N2QyMjllMWNiYSJ9fX0");
        headBase64List.put("Bronze", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNDhlMGE0NDAxNTg5ZDI5NTRlM2U1YzdlNjE1NTVlZjljZTFmZDI4OWNmNWM4NGFlZDE1YzMyMjBlYWY1ZDM5MCJ9fX0");

        //Utilities
        headBase64List.put("LampOn", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvN2ViNGIzNDUxOWZlMTU4NDdkYmVhNzIyOTE3OWZlZWI2ZWE1NzcxMmQxNjVkY2M4ZmY2Yjc4NWJiNTg5MTFiMCJ9fX0");
        headBase64List.put("LampOff", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZjNlNzFhZDkxOTUyM2VhY2U5Y2Q2MmEyNWIxOGU0ZTE3YWIzOGQxMjU2MjQxZjQyNjJkZmJhNzI5N2M0ZDkyIn19fQ");

        Category materials = Factory.makeCategory("TFMaterials", "Materials", new String[]{"Lots of things used to make lots of other", "things!"}, Material.DIAMOND, 0);

        ItemBase woodenCore = Factory.makeItem("WoodenCore", TFUtil.makeSkullWithBase64(headBase64List.get("WoodenCore"), "橡木之芯", new String[]{"最基础的物品核心! 由橡木构成."}), new Object[]{
                Material.LOG, Material.WOOD, Material.LOG,
                Material.WOOD, Material.LEATHER, Material.WOOD,
                Material.LOG, Material.WOOD, Material.LOG
        }, CraftingStation.MagicalCraftingTable, 0);

        woodenCore.register();

        materials.registerItem(woodenCore);

        ItemBase stoneCore = Factory.makeItem("StoneCore", TFUtil.makeSkullWithBase64(headBase64List.get("StoneCore"), "岩石之芯", new String[]{"第二级物品核心!", "由石头构成!"}), new Object[]{
                Material.STONE, Material.COBBLESTONE, Material.STONE,
                Material.COBBLESTONE, woodenCore.getItem(), Material.COBBLESTONE,
                Material.STONE, Material.COBBLESTONE, Material.STONE
        }, CraftingStation.MagicalCraftingTable, 2);

        stoneCore.register();

        materials.registerItem(stoneCore);

        ItemBase ironCore = Factory.makeItem("IronCore", TFUtil.makeSkullWithBase64(headBase64List.get("IronCore"), "钢铁之芯", new String[]{"第三级物品核心! 由铁锭构成! :-D"}), new Object[]{
                Material.IRON_INGOT, Material.STONE, Material.IRON_INGOT,
                Material.STONE, stoneCore.getItem(), Material.STONE,
                Material.IRON_INGOT, Material.STONE, Material.IRON_INGOT
        }, CraftingStation.MagicalCraftingTable, 4);

        ironCore.register();

        materials.registerItem(ironCore);

        ItemBase goldCore = Factory.makeItem("GoldCore", TFUtil.makeSkullWithBase64(headBase64List.get("GoldCore"), "黄金之芯", new String[]{"第四级物品核心!"}), new Object[]{
                Material.GOLD_INGOT, Material.IRON_INGOT, Material.GOLD_INGOT,
                Material.IRON_INGOT, ironCore.getItem(), Material.IRON_INGOT,
                Material.GOLD_INGOT, Material.IRON_INGOT, Material.GOLD_INGOT
        }, CraftingStation.MagicalCraftingTable, 6);

        goldCore.register();

        materials.registerItem(goldCore);

        ItemBase diamondCore = Factory.makeItem("DiamondCore", TFUtil.makeSkullWithBase64(headBase64List.get("DiamondCore"), "钻石之芯", new String[]{"最高级的物品核心! 由闪耀的钻石构成!"}), new Object[]{
                Material.DIAMOND, Material.GOLD_INGOT, Material.DIAMOND,
                Material.GOLD_INGOT, goldCore.getItem(), Material.GOLD_INGOT,
                Material.DIAMOND, Material.GOLD_INGOT, Material.DIAMOND
        }, CraftingStation.MagicalCraftingTable, 8);

        diamondCore.register();

        materials.registerItem(diamondCore);

        ItemBase compressedCarbon = Factory.makeItem("CompressedCarbon", TFUtil.makeSkullWithBase64(headBase64List.get("CompressedCarbon"), "压缩碳", new String[]{ "由碳压缩而成." }), new Object[]{
                null, Material.COAL, null,
                Material.COAL, Material.COAL_BLOCK, Material.COAL,
                null, Material.COAL, null
        }, CraftingStation.Compressor, 10);

        compressedCarbon.register();

        materials.registerItem(compressedCarbon);

        ItemBase uranium = Factory.makeItem("Uranium", TFUtil.makeSkullWithBase64(headBase64List.get("Uranium"), "铀", new String[]{ "辐射性\的物质, 我不推荐你\徒手触碰它." }), new Object[]{
                Material.STONE
        }, CraftingStation.Ore, 20);

        uranium.registerHandler(new ItemOreBlockBreakHandler() {
            @Override
            public void onBlockBroken(Player p, BlockBreakEvent e) {
                Random random = new Random();
                if (random.nextInt(125) == 0) {
                    e.getBlock().getWorld().dropItemNaturally(e.getBlock().getLocation(), uranium.getItem());
                }
            }
        });

        uranium.register();

        materials.registerItem(uranium);

        ItemBase rawBronze = Factory.makeItem("RawBronze", TFUtil.makeSkullWithBase64(headBase64List.get("RawBronze"), "生青铜", new String[]{ "The raw version of bronze. Simple." }), new Object[]{
                Material.IRON_ORE
        }, CraftingStation.Ore, 10);

        rawBronze.registerHandler(new ItemOreBlockBreakHandler() {
            @Override
            public void onBlockBroken(Player p, BlockBreakEvent e) {
                Random random = new Random();
                if (random.nextInt(17) == 0) {
                    e.getBlock().getWorld().dropItemNaturally(e.getBlock().getLocation(), rawBronze.getItem());
                }
            }
        });

        rawBronze.register();

        materials.registerItem(rawBronze);

        ItemBase bronze = Factory.makeItem("Bronze", TFUtil.makeSkullWithBase64(headBase64List.get("Bronze"), "Bronze", new String[]{ "Refined bronze, smelted fresh." }), new Object[]{
                Material.IRON_INGOT, rawBronze, null,
                null, null, null,
                null, null, null
        }, CraftingStation.Smeltry, 10);

        bronze.register();

        materials.registerItem(bronze);

        materials.register();

        Category utilitiesCategory = Factory.makeCategory("TFUtilities", "Utilities", new String[]{"Lots of very useful things in TechFun!"}, Material.BOOK, 0);

        ItemBase techFunGuide = Factory.makeItem("TechFunGuide", "Tech Fun Guide", new String[]{"The key to researching and unlocking the secrets", "of the magical world of TechFun!"}, Material.ENCHANTED_BOOK, new Object[]{
                Material.WOOD, Material.LOG, Material.WOOD,
                Material.LEATHER, Material.WOOL, Material.LEATHER,
                Material.WOOD, Material.WOOD, Material.WOOD
        }, CraftingStation.DefaultCraftingTable, 0);

        techFunGuide.registerHandler(new ItemClickHandler() {
            @Override
            public void onItemClick(PlayerInteractEvent e, Player p, ItemStack item) {
                TechFunGuide.open(p);
            }
        });

        techFunGuide.register();

        utilitiesCategory.registerItem(techFunGuide);

        ItemBase portableCrafter = Factory.makeItem("PortableCrafter", TFUtil.makeSkullWithBase64(headBase64List.get("CraftingTable"), "Portable Crafter", new String[]{"Lets you craft on the go!"}), new Object[]{
                Material.LOG, Material.REDSTONE, Material.LOG,
                Material.WOOD, Material.WORKBENCH, Material.WOOD,
                Material.LOG, ironCore, Material.LOG
        }, CraftingStation.MagicalCraftingTable, 2);

        portableCrafter.registerHandler(new ItemClickHandler() {
            @Override
            public void onItemClick(PlayerInteractEvent e, Player p, ItemStack item) {
                p.openWorkbench(p.getLocation(), true);
                e.setCancelled(true);
            }
        });

        portableCrafter.register();

        utilitiesCategory.registerItem(portableCrafter);

        ItemBase bedWarper = Factory.makeItem("BedWarper", "Bed Warper", new String[]{"Teleports you to your bed in times of need!", "Has a 2 minute cooldown!"}, Material.BED, new Object[]{
                Material.ENDER_PEARL, Material.BED, Material.ENDER_PEARL,
                goldCore, Material.EYE_OF_ENDER, goldCore,
                Material.ENDER_PEARL, Material.REDSTONE, Material.ENDER_PEARL
        }, CraftingStation.MagicalCraftingTable, 10);

        bedWarper.registerHandler(new ItemClickHandler() {
            @Override
            public void onItemClick(PlayerInteractEvent e, Player p, ItemStack item) {
                Cooldown cooldown = new Cooldown(p.getUniqueId(), "bedWarper", 60 * 2);
                e.setCancelled(true);
                if (Cooldown.getTimeLeft(p.getUniqueId(), "bedWarper") <= 0) {
                    if (p.getBedSpawnLocation() == null) {
                        TechFunMain.getPluginLogger().sendMessage(p, TextUtil.Level.Info, "You have been warped to spawn because you have not set a bed spawn!");
                        p.teleport(p.getWorld().getSpawnLocation());
                    } else {
                        TechFunMain.getPluginLogger().sendMessage(p, TextUtil.Level.Success, "You have been teleported to your bed!");
                        p.teleport(p.getBedSpawnLocation());
                    }
                    cooldown.start();
                } else {
                    TechFunMain.getPluginLogger().sendMessage(p, TextUtil.Level.Error, "Your item is on cooldown! It has " + Cooldown.getTimeLeft(p.getUniqueId(), "bedWarper") + " seconds left!");
                }
            }
        });

        bedWarper.register();

        utilitiesCategory.registerItem(bedWarper);

        ItemBase portableTrashCan = Factory.makeItem("PortableTrashCan", TFUtil.makeSkullWithBase64(headBase64List.get("TrashCan"), "Trash Can", new String[]{"Lets you void any unwanted items."}), new Object[]{
                null, null, null,
                Material.IRON_INGOT, stoneCore, Material.IRON_INGOT,
                null, Material.REDSTONE, null
        }, CraftingStation.MagicalCraftingTable, 10);

        portableTrashCan.registerHandler(new ItemClickHandler() {
            @Override
            public void onItemClick(PlayerInteractEvent e, Player p, ItemStack item) {
                p.openInventory(Bukkit.createInventory(null, 9 * 3, ChatColor.RED + "" + ChatColor.BOLD + "Trash Can"));
            }
        });

        portableTrashCan.register();

        utilitiesCategory.registerItem(portableTrashCan);

        Machine lamp = Factory.makeMachine("Lamp", TFUtil.makeSkullWithBase64(headBase64List.get("LampOff"), "Lamp", new String[]{ "You can turn this lamp on and off!" }), new Object[]{
                Material.GLOWSTONE, Material.REDSTONE, Material.GLOWSTONE,
                Material.REDSTONE, ironCore, Material.REDSTONE,
                null, Material.STICK, null
        }, CraftingStation.MagicalCraftingTable, 10);

        lamp.registerHandler(new MachineClickHandler() {
            @Override
            public void onMachineClick(Machine machine, Player player, PlayerInteractEvent e) {
                player.getWorld().playSound(player.getLocation(), Sound.BLOCK_LEVER_CLICK, 1, 1);
                if ((boolean) DataManager.getBlockData(e.getClickedBlock(), "On", false)) {
                    DataManager.saveBlockData(e.getClickedBlock(), "On", false);
                    TFUtil.setHeadBlock(e.getClickedBlock().getLocation(), headBase64List.get("LampOff"), BlockFace.NORTH);
                } else {
                    DataManager.saveBlockData(e.getClickedBlock(), "On", true);
                    TFUtil.setHeadBlock(e.getClickedBlock().getLocation(), headBase64List.get("LampOn"), BlockFace.NORTH);
                }
            }
        });

        lamp.register();

        utilitiesCategory.registerMachine(lamp);

        utilitiesCategory.register();

        Category basicMachinesCategory = Factory.makeCategory("TFBasicMachines", "Basic Machines", new String[]{"Basically machines that are very basic! ;-)"}, Material.FURNACE, 0);

        MultiBlock forge = Factory.makeMultiBlock("Forge", new String[]{"Lets you craft things like weapons and tools!"}, Material.ANVIL, new Material[]{
                Material.STEP, Material.STEP, Material.STEP,
                Material.STONE, Material.FURNACE, Material.STONE,
                Material.NETHER_BRICK, Material.DROPPER, Material.NETHER_BRICK
        }, 5);

        forge.registerHandler(new MultiBlockClickHandler() {
            @Override
            public void click(MultiBlock multiBlock, Player player, PlayerInteractEvent e) {
                InvUtil.craftItem((Dropper) e.getClickedBlock().getRelative(BlockFace.DOWN).getState(), multiBlock, player, e, plugin, CraftingStation.Forge);
            }
        });

        forge.register();

        basicMachinesCategory.registerMultiBlock(forge);

        MultiBlock magicalCraftingTable = Factory.makeMultiBlock("Magical Crafting Table", new String[]{"The very beginning of TechFun!"}, Material.WORKBENCH, new Material[]{
                Material.WOOD_STEP, Material.WOOD_STEP, Material.WOOD_STEP,
                Material.BOOKSHELF, Material.WORKBENCH, Material.BOOKSHELF,
                Material.LOG, Material.DROPPER, Material.LOG
        }, 5);

        magicalCraftingTable.registerHandler(new MultiBlockClickHandler() {
            @Override
            public void click(MultiBlock multiBlock, Player player, PlayerInteractEvent e) {
                InvUtil.craftItem((Dropper) e.getClickedBlock().getRelative(BlockFace.DOWN).getState(), multiBlock, player, e, plugin, CraftingStation.MagicalCraftingTable);
            }
        });

        magicalCraftingTable.register();

        basicMachinesCategory.registerMultiBlock(magicalCraftingTable);

        MultiBlock cookingBench = Factory.makeMultiBlock("Cooking Bench", new String[]{"Allows you to cook recipes!"}, Material.FURNACE, new Material[]{
                Material.AIR, Material.AIR, Material.AIR,
                Material.STEP, Material.WORKBENCH, Material.STEP,
                Material.IRON_BLOCK, Material.DROPPER, Material.IRON_BLOCK
        }, 5);

        cookingBench.registerHandler(new MultiBlockClickHandler() {
            @Override
            public void click(MultiBlock multiBlock, Player player, PlayerInteractEvent e) {
                InvUtil.craftItem((Dropper) e.getClickedBlock().getRelative(BlockFace.DOWN).getState(), multiBlock, player, e, plugin, CraftingStation.CookingBench);
            }
        });

        cookingBench.register();

        basicMachinesCategory.registerMultiBlock(cookingBench);

        MultiBlock smeltry = Factory.makeMultiBlock("Smeltry", new String[]{"Smelts certain items into other materials."}, Material.FURNACE, new Material[]{
                Material.STEP, Material.STEP, Material.STEP,
                Material.COBBLESTONE, Material.WORKBENCH, Material.COBBLESTONE,
                Material.NETHERRACK, Material.DROPPER, Material.NETHERRACK
        }, 10);

        smeltry.registerHandler(new MultiBlockClickHandler() {
            @Override
            public void click(MultiBlock multiBlock, Player player, PlayerInteractEvent e) {
                InvUtil.craftItem((Dropper) e.getClickedBlock().getRelative(BlockFace.DOWN).getState(), multiBlock, player, e, plugin, CraftingStation.Smeltry);
            }
        });

        smeltry.register();

        basicMachinesCategory.registerMultiBlock(smeltry);

        MultiBlock compressor = Factory.makeMultiBlock("Compressor", new String[]{ "Compresses items into others." }, Material.PISTON_BASE, new Material[]{
                Material.STEP, Material.STEP, Material.STEP,
                Material.COBBLESTONE, Material.ANVIL, Material.COBBLESTONE,
                Material.IRON_BLOCK, Material.DROPPER, Material.IRON_BLOCK
        }, 10);

        compressor.registerHandler(new MultiBlockClickHandler() {
            @Override
            public void click(MultiBlock multiBlock, Player player, PlayerInteractEvent e) {
                InvUtil.craftItem((Dropper) e.getClickedBlock().getRelative(BlockFace.DOWN).getState(), multiBlock, player, e, plugin, CraftingStation.Compressor);
            }
        });

        compressor.register();

        basicMachinesCategory.registerMultiBlock(compressor);

        MultiBlock manufacturingTable = Factory.makeMultiBlock("Manufacturing Table", new String[]{ "Lets you create complex machines!" }, Material.ANVIL, new Material[]{
                Material.STEP, Material.STEP, Material.STEP,
                Material.REDSTONE_BLOCK, Material.ANVIL, Material.REDSTONE_BLOCK,
                Material.IRON_BLOCK, Material.DROPPER, Material.IRON_BLOCK
        }, 20);


        manufacturingTable.registerHandler(new MultiBlockClickHandler() {
            @Override
            public void click(MultiBlock multiBlock, Player player, PlayerInteractEvent e) {
                InvUtil.craftItem((Dropper) e.getClickedBlock().getRelative(BlockFace.DOWN).getState(), multiBlock, player, e, plugin, CraftingStation.ManufacturingTable);
            }
        });

        manufacturingTable.register();

        basicMachinesCategory.registerMultiBlock(manufacturingTable);

        MultiBlock spawnerForge = Factory.makeMultiBlock("Spawner Forge", new String[]{ "Lets you create spawners and spawn eggs." }, Material.MOB_SPAWNER, new Material[]{
                Material.STEP, Material.STEP, Material.STEP,
                Material.GOLD_BLOCK, Material.FURNACE, Material.GOLD_BLOCK,
                Material.GLOWSTONE, Material.DROPPER, Material.GLOWSTONE
        }, 15);

        spawnerForge.registerHandler(new MultiBlockClickHandler() {
            @Override
            public void click(MultiBlock multiBlock, Player player, PlayerInteractEvent e) {
                InvUtil.craftItem((Dropper) e.getClickedBlock().getRelative(BlockFace.DOWN).getState(), multiBlock, player, e, plugin, CraftingStation.SpawnerForge);
            }
        });

        spawnerForge.register();

        basicMachinesCategory.registerMultiBlock(spawnerForge);

        Machine miner = Factory.makeMachine("Miner", TFUtil.makeSkullWithBase64(headBase64List.get("Miner"), "Miner", new String[]{"Mines blocks directly under the machine until bedrock."}), new Object[]{
                Material.STONE, Material.REDSTONE_BLOCK, Material.STONE,
                goldCore, Material.DIAMOND_PICKAXE, goldCore,
                Material.STONE, compressedCarbon, Material.STONE
        }, CraftingStation.ManufacturingTable, 15);

        miner.registerHandler(new MachineClickHandler() {
            @Override
            public void onMachineClick(Machine machine, Player player, PlayerInteractEvent e) {
                for (int y = e.getClickedBlock().getY() - 1; y > 0; y--) {
                    Block b = e.getClickedBlock().getWorld().getBlockAt(e.getClickedBlock().getX(), y, e.getClickedBlock().getZ());
                    if (b.getType() == Material.BEDROCK) {
                        TechFunMain.getPluginLogger().sendMessage(player, TextUtil.Level.Error, "Error: I cannot dig because I have hit bedrock!");
                        return;
                    }
                    if (b.getType() != Material.AIR) {
                        for (ItemStack drop : b.getDrops()) {
                            e.getClickedBlock().getWorld().dropItemNaturally(e.getClickedBlock().getLocation().add(0, 1, 0), drop);
                        }
                        b.setType(Material.AIR);
                        return;
                    }
                }
            }
        });

        miner.register();

        basicMachinesCategory.registerMachine(miner);

        basicMachinesCategory.register();

        Category weaponsCategory = Factory.makeCategory("TFWeapons", "Weapons", new String[]{"Want to battle your enemies and conquer the world?", "Well here is the place for you!"}, Material.DIAMOND_SWORD, 0);

        ItemBase soulDagger = Factory.makeItem("SoulDagger", "Soul Dagger", new String[]{"Has a chance of gaining 2 hearts of life-steal when", "hitting another player."}, Material.GOLD_SWORD, new Object[]{
                Material.GOLD_INGOT, Material.GOLD_SWORD, Material.GOLD_INGOT,
                uranium, ironCore.getItem(), uranium,
                Material.GOLD_INGOT, null, Material.GOLD_INGOT
        }, CraftingStation.Forge, 7);

        soulDagger.registerHandler(new ItemAttackHandler() {
            @Override
            public void onAttack(EntityDamageByEntityEvent e, Player p, ItemStack item) {
                Random random = new Random();
                if (random.nextInt(7) == 0) {
                    p.setHealth(Math.min(20, p.getHealth() + 4));
                }
            }
        });

        soulDagger.register();

        weaponsCategory.registerItem(soulDagger);

        ItemBase smackyStick = Factory.makeItem("SmackyStick", "Smacky Stick", new String[]{"It does what you might think. It smacks people", "away..."}, Material.STICK, new Object[]{
                Material.LEATHER, Material.LOG, Material.LEATHER,
                null, Material.STICK, null,
                null, Material.STICK, null
        }, CraftingStation.MagicalCraftingTable, 3);

        smackyStick.getItem().addUnsafeEnchantment(Enchantment.KNOCKBACK, 5);

        smackyStick.register();

        weaponsCategory.registerItem(smackyStick);

        ItemBase swordOfBlinding = Factory.makeItem("SwordOfBlinding", "Sword of Blinding", new String[]{"Has a chance to blind the enemy when hit."}, Material.DIAMOND_SWORD, new Object[]{
                Material.OBSIDIAN, Material.REDSTONE_BLOCK, Material.OBSIDIAN,
                Material.INK_SACK, Material.DIAMOND_SWORD, Material.INK_SACK,
                Material.OBSIDIAN, diamondCore, Material.OBSIDIAN
        }, CraftingStation.Forge, 10);

        swordOfBlinding.registerHandler(new ItemAttackHandler() {
            @Override
            public void onAttack(EntityDamageByEntityEvent e, Player p, ItemStack item) {
                Random r = new Random();
                int chance = r.nextInt(6);
                if (chance == 0 && e.getEntityType() == EntityType.PLAYER) {
                    Player attacked = (Player) e.getEntity();
                    attacked.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 60, 2, false, false));
                    TechFunMain.getPluginLogger().sendMessage(attacked, TextUtil.Level.Error, "You have been inflicted with blindness by " + p.getName() + "\'s Sword of Blinding!");
                    TechFunMain.getPluginLogger().sendMessage(p, TextUtil.Level.Success, "You have inflicted " + attacked.getName() + " with blindness!");
                }
            }
        });

        swordOfBlinding.register();

        weaponsCategory.registerItem(swordOfBlinding);

        weaponsCategory.register();

        Category magicCategory = Factory.makeCategory("TFMagic", "Magic", new String[]{"Things for magical wizards and mages!"}, Material.BLAZE_ROD);

        ItemBase flightCharm = Factory.makeItem("FlightCharm", "Charm Of Flight", new String[]{"Gives you flight temporarily!"}, Material.EMERALD, new Object[]{
                Material.OBSIDIAN, Material.REDSTONE_BLOCK, Material.OBSIDIAN,
                Material.REDSTONE, diamondCore.getItem(), Material.REDSTONE,
                Material.OBSIDIAN, Material.GOLD_BLOCK, Material.OBSIDIAN
        }, CraftingStation.MagicalCraftingTable, 20);

        flightCharm.registerHandler(new ItemClickHandler() {
            @Override
            public void onItemClick(PlayerInteractEvent e, Player p, ItemStack item) {
                Cooldown cooldown = new Cooldown(p.getUniqueId(), "flightCharm", 60 * 5);
                e.setCancelled(true);
                if (Cooldown.getTimeLeft(p.getUniqueId(), "flightCharm") <= 0) {
                    item.setAmount(item.getAmount() - 1);
                    if (item.getAmount() == 0) {
                        item.setType(Material.AIR);
                    }
                    p.setAllowFlight(true);
                    cooldown.start();
                    for (int i = 5; i > 0; i--) {
                        final int i2 = i;
                        Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
                            @Override
                            public void run() {
                                TechFunMain.getPluginLogger().sendMessage(p, TextUtil.Level.Warning, "WARNING: Flight has " + i2 + " seconds left!");
                                p.playSound(p.getLocation(), Sound.BLOCK_NOTE_PLING, 1, 1);
                            }
                        }, (20 * 60 * 5) - i2 * 20);
                    }
                    Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
                        @Override
                        public void run() {
                            TechFunMain.getPluginLogger().sendMessage(p, TextUtil.Level.Error, "Flight has now been disabled!");
                            p.playSound(p.getLocation(), Sound.BLOCK_NOTE_PLING, 1, 1);
                            p.setAllowFlight(false);
                        }
                    }, 20 * 60 * 5);
                    TechFunMain.getPluginLogger().sendMessage(p, TextUtil.Level.Success, "Charm activated, you now have flight for 5 minutes!");
                    p.playSound(p.getLocation(), Sound.BLOCK_NOTE_PLING, 1, 1);
                } else {
                    TechFunMain.getPluginLogger().sendMessage(p, TextUtil.Level.Error, "The item is still on cooldown! There is " + Cooldown.getTimeLeft(p.getUniqueId(), "flightCharm") + " seconds left!");
                }
            }
        });

        flightCharm.register();

        magicCategory.registerItem(flightCharm);

        ItemBase wandOfFire = Factory.makeItem("WandOfFire", "Wand Of Fire", new String[]{"Lets you shoot fire!"}, Material.BLAZE_ROD, new Object[]{
                Material.BLAZE_POWDER, Material.MAGMA_CREAM, Material.BLAZE_POWDER,
                Material.ENDER_PEARL, diamondCore, Material.ENDER_PEARL,
                Material.BLAZE_POWDER, Material.BLAZE_ROD, Material.BLAZE_POWDER
        }, CraftingStation.Forge, 10);

        wandOfFire.registerHandler(new ItemClickHandler() {
            @Override
            public void onItemClick(PlayerInteractEvent e, Player p, ItemStack item) {
                Cooldown cooldown = new Cooldown(p.getUniqueId(), "wandOfFire", 6);
                e.setCancelled(true);
                if (Cooldown.getTimeLeft(p.getUniqueId(), "wandOfFire") <= 0) {
                    Fireball fireball = (Fireball) p.launchProjectile(Fireball.class);
                    fireball.setYield(3);
                    fireball.setFireTicks(0);
                    fireball.setVelocity(p.getLocation().getDirection().multiply(3));
                    cooldown.start();
                } else {
                    TechFunMain.getPluginLogger().sendMessage(p, TextUtil.Level.Error, "The item is still on cooldown! There is " + Cooldown.getTimeLeft(p.getUniqueId(), "wandOfFire") + " seconds left!");
                }
            }
        });

        wandOfFire.register();

        magicCategory.registerItem(wandOfFire);

        ItemBase lightningRod = Factory.makeItem("LightningRod", "Lightning Rod", new String[]{"Lets you strike fear into your enemies eyes, literally."}, Material.STICK, new Object[]{
                Material.GOLD_BLOCK, Material.FLINT_AND_STEEL, Material.GOLD_BLOCK,
                Material.ENDER_PEARL, diamondCore, Material.ENDER_PEARL,
                Material.REDSTONE_BLOCK, Material.IRON_BLOCK, Material.REDSTONE_BLOCK
        }, CraftingStation.Forge, 10);

        lightningRod.registerHandler(new ItemClickHandler() {
            @Override
            public void onItemClick(PlayerInteractEvent e, Player p, ItemStack item) {
                Cooldown cooldown = new Cooldown(p.getUniqueId(), "lightningRod", 20);
                e.setCancelled(true);
                if (Cooldown.getTimeLeft(p.getUniqueId(), "lightningRod") <= 0) {
                    Block b = p.getTargetBlock(null, 200);
                    if (b == null) {
                        TechFunMain.getPluginLogger().sendMessage(p, TextUtil.Level.Error, "That block is too far away! Please try a little closer.");
                        return;
                    }
                    p.getWorld().playSound(b.getLocation(), Sound.ENTITY_EVOCATION_ILLAGER_PREPARE_WOLOLO, 1, 1);
                    p.getWorld().playSound(b.getLocation(), Sound.ENTITY_EVOCATION_ILLAGER_PREPARE_SUMMON, 0.5F, 0.5F);
                    Bukkit.broadcastMessage(org.bukkit.ChatColor.DARK_AQUA + "[" + org.bukkit.ChatColor.AQUA + "TechFun" + org.bukkit.ChatColor.DARK_AQUA + "]" + ChatColor.RED + "A wild wololo could be heard in the distance as " + p.getName() + " goes mad with power...");
//                    TNTPrimed tntPrimed = (TNTPrimed) p.getWorld().spawnEntity(p.getTargetBlock(null, 200).getLocation(), EntityType.PRIMED_TNT);
//                    tntPrimed.setFuseTicks(30);
//                    tntPrimed.setYield(3);
                    Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
                        @Override
                        public void run() {
                            for (int i = 0; i < 25; i++) {
                                p.getWorld().strikeLightning(b.getLocation());
                            }
                            for (int i = 0; i < 10; i++) {
                                p.getWorld().createExplosion(b.getLocation().getX(), b.getLocation().getY(), b.getLocation().getZ(), 5, false, false);
                            }
                        }
                    }, 30);
                    cooldown.start();
                } else {
                    TechFunMain.getPluginLogger().sendMessage(p, TextUtil.Level.Error, "The item is still on cooldown! There is " + Cooldown.getTimeLeft(p.getUniqueId(), "lightningRod") + " seconds left!");
                }
            }
        });

        lightningRod.register();

        magicCategory.registerItem(lightningRod);

        ItemBase playerBeheader = Factory.makeItem("PlayerBeheader", TFUtil.makeSkullWithBase64(headBase64List.get("PlayerBeheader"), "Player Beheader", new String[]{"Takes the head right off players!"}), new Object[]{
                Material.BLAZE_POWDER, null, Material.BLAZE_ROD,
                null, goldCore, null,
                Material.BLAZE_ROD, null, Material.BLAZE_POWDER
        }, CraftingStation.MagicalCraftingTable, 5);

        playerBeheader.registerHandler(new ItemAttackHandler() {
            @Override
            public void onAttack(EntityDamageByEntityEvent e, Player p, ItemStack item) {
                e.setCancelled(true);
                if (e.getEntity() instanceof Player) {
                    p.getInventory().setItemInMainHand(null);
                    p.getInventory().addItem(TFUtil.makePlayerHead(e.getEntity().getName(), e.getEntity().getName() + "\'s head", new String[]{"Its a players head. Hmmmm."}));
                    e.getEntity().getWorld().spawnParticle(Particle.CRIT_MAGIC, p.getLocation(), 100);
                } else {
                    TechFunMain.getPluginLogger().sendMessage(p, TextUtil.Level.Error, "You must click on a player!");
                }
            }
        });

        playerBeheader.register();

        magicCategory.registerItem(playerBeheader);

        ItemBase launchTnt = Factory.makeItem("LaunchTNT", TFUtil.makeSkullWithBase64(headBase64List.get("TNT"), "Launchable TNT", new String[]{"Right click this to launch a piece of TNT!"}), new Object[]{
                null, null, null,
                Material.REDSTONE, Material.TNT, Material.SULPHUR,
                null, null, null
        }, CraftingStation.MagicalCraftingTable, 10);

        launchTnt.registerHandler(new ItemClickHandler() {
            @Override
            public void onItemClick(PlayerInteractEvent e, Player p, ItemStack item) {
                if (p.getGameMode() != GameMode.CREATIVE) {
                    InvUtil.decrementItem(item);
                }
                TNTPrimed tntPrimed = (TNTPrimed) p.getWorld().spawnEntity(p.getLocation().add(0, 1, 0), EntityType.PRIMED_TNT);
                tntPrimed.setFuseTicks(30);
                tntPrimed.setVelocity(p.getLocation().getDirection().multiply(2));
            }
        });

        launchTnt.register();

        magicCategory.registerItem(launchTnt);

        ItemBase xpJar = Factory.makeItem("XPJar", TFUtil.makeSkullWithBase64(headBase64List.get("XPJar"), "XP Jar", new String[]{"Gives you 5 levels when used."}), new Object[]{
                Material.COBBLESTONE, Material.COAL_BLOCK, Material.COBBLESTONE,
                Material.COAL, stoneCore, Material.COAL,
                Material.COBBLESTONE, Material.GLASS_BOTTLE, Material.COBBLESTONE
        }, CraftingStation.MagicalCraftingTable, 10);

        xpJar.registerHandler(new ItemClickHandler() {
            @Override
            public void onItemClick(PlayerInteractEvent e, Player p, ItemStack item) {
                InvUtil.decrementItem(item);
                p.setLevel(p.getLevel() + 5);
                p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1);
            }
        });

        xpJar.register();

        magicCategory.registerItem(xpJar);

        magicCategory.register();

        Category toolsCategory = Factory.makeCategory("TFTools", "Tools", new String[]{"The category for tools in default TechFun."}, Material.DIAMOND_PICKAXE);

        ItemBase pickaxeOfSmelting = Factory.makeItem("PickaxeOfSmelting", "Pickaxe of Smelting", new String[]{"Automatically smelts ores mined."}, Material.DIAMOND_PICKAXE, new Object[]{
                Material.OBSIDIAN, Material.REDSTONE, Material.OBSIDIAN,
                Material.BOOK, diamondCore, Material.BOOK,
                Material.OBSIDIAN, Material.DIAMOND_PICKAXE, Material.OBSIDIAN
        }, CraftingStation.Forge, 10);

        pickaxeOfSmelting.registerHandler(new ItemBlockBreakHandler() {
            @Override
            public void onBlockBroken(Block b, Player p, BlockBreakEvent e) {
                Material blockType = e.getBlock().getType();
                ItemStack result = InvUtil.getFurnaceRecipeResult(blockType);
                if (result != null) {
                    e.setDropItems(false);
                    p.getWorld().dropItemNaturally(b.getLocation(), result);
                }
            }
        });

        pickaxeOfSmelting.register();

        toolsCategory.registerItem(pickaxeOfSmelting);

        toolsCategory.register();

        Category spawnerCategory = Factory.makeCategory("TFSpawner", "Spawners", new String[]{"Contains spawn eggs for entities."}, Material.MOB_SPAWNER);

        CustomRecipe mobSpawner = Factory.makeCustomRecipe(new ItemStack(Material.MOB_SPAWNER), new ItemStack[]{
                compressedCarbon.getItem(), uranium.getItem(), new ItemStack(Material.OBSIDIAN),
                new ItemStack(Material.IRON_BLOCK), diamondCore.getItem(), new ItemStack(Material.IRON_BLOCK),
                new ItemStack(Material.OBSIDIAN), uranium.getItem(), compressedCarbon.getItem()
        }, CraftingStation.SpawnerForge, spawnerCategory);

        mobSpawner.register();

        CustomRecipe cowSpawner = Factory.makeCustomRecipe(TFUtil.makeMobSpawnEgg("Cow", spawnEggAmountPeaceful, plugin), new ItemStack[]{
                null, new ItemStack(Material.RAW_BEEF), null,
                null, new ItemStack(Material.EGG), null,
                null, stoneCore.getItem(), null
        }, CraftingStation.SpawnerForge, spawnerCategory);

        cowSpawner.register();

        CustomRecipe chickenSpawner = Factory.makeCustomRecipe(TFUtil.makeMobSpawnEgg("Chicken", spawnEggAmountPeaceful, plugin), new ItemStack[]{
                null, new ItemStack(Material.FEATHER), null,
                null, new ItemStack(Material.EGG), null,
                null, stoneCore.getItem(), null
        }, CraftingStation.SpawnerForge, spawnerCategory);

        chickenSpawner.register();

        CustomRecipe pigSpawner = Factory.makeCustomRecipe(TFUtil.makeMobSpawnEgg("Pig", spawnEggAmountPeaceful, plugin), new ItemStack[]{
                null, new ItemStack(Material.PORK), null,
                null, new ItemStack(Material.EGG), null,
                null, stoneCore.getItem(), null
        }, CraftingStation.SpawnerForge, spawnerCategory);

        pigSpawner.register();

        CustomRecipe wolfSpawner = Factory.makeCustomRecipe(TFUtil.makeMobSpawnEgg("Wolf", spawnEggAmountPeaceful, plugin), new ItemStack[]{
                null, new ItemStack(Material.BONE), null,
                null, new ItemStack(Material.EGG), null,
                null, stoneCore.getItem(), null
        }, CraftingStation.SpawnerForge, spawnerCategory);

        wolfSpawner.register();

        CustomRecipe sheepSpawner = Factory.makeCustomRecipe(TFUtil.makeMobSpawnEgg("Sheep", spawnEggAmountPeaceful, plugin), new ItemStack[]{
                null, new ItemStack(Material.WOOL), null,
                null, new ItemStack(Material.EGG), null,
                null, stoneCore.getItem(), null
        }, CraftingStation.SpawnerForge, spawnerCategory);

        sheepSpawner.register();

        CustomRecipe squidSpawner = Factory.makeCustomRecipe(TFUtil.makeMobSpawnEgg("Squid", spawnEggAmountPeaceful, plugin), new ItemStack[]{
                null, new ItemStack(Material.INK_SACK), null,
                null, new ItemStack(Material.EGG), null,
                null, stoneCore.getItem(), null
        }, CraftingStation.SpawnerForge, spawnerCategory);

        squidSpawner.register();

        CustomRecipe ocelotSpawner = Factory.makeCustomRecipe(TFUtil.makeMobSpawnEgg("Ocelot", spawnEggAmountPeaceful, plugin), new ItemStack[]{
                null, new ItemStack(Material.RAW_FISH), null,
                null, new ItemStack(Material.EGG), null,
                null, stoneCore.getItem(), null
        }, CraftingStation.SpawnerForge, spawnerCategory);

        ocelotSpawner.register();

        spawnerCategory.register();

        Category foodCategory = Factory.makeCategory("TFFood", TFUtil.makeSkullWithBase64(headBase64List.get("Apple"), "Food", new String[]{"Contains all the food in default TechFun!"}), 0);

        Machine hamburger = Factory.makeMachine("Hamburger", TFUtil.makeSkullWithBase64(headBase64List.get("Hamburger"), "Hamburger", new String[]{"Place it down and click me to eat!"}), new Object[]{
                null, Material.BREAD, null,
                null, Material.COOKED_BEEF, null,
                null, Material.BREAD, null
        }, CraftingStation.CookingBench, 2);

        hamburger.registerHandler(new MachineClickHandler() {
            @Override
            public void onMachineClick(Machine machine, Player player, PlayerInteractEvent e) {
                Machine.removeMachine(e.getClickedBlock());
                TFUtil.eatFood(player, 20, 8);
            }
        });

        hamburger.register();

        foodCategory.registerMachine(hamburger);

        Machine donut = Factory.makeMachine("Donut", TFUtil.makeSkullWithBase64(headBase64List.get("Donut"), "Donut", new String[]{"A delicious treat for you!"}), new Object[]{
                null, null, null,
                null, Material.SUGAR, null,
                Material.WHEAT, Material.EGG, Material.WHEAT
        }, CraftingStation.CookingBench, 2);

        donut.registerHandler(new MachineClickHandler() {
            @Override
            public void onMachineClick(Machine machine, Player player, PlayerInteractEvent e) {
                Machine.removeMachine(e.getClickedBlock());
                TFUtil.eatFood(player, 7, 3);
                if (player.hasPotionEffect(PotionEffectType.SPEED)) {
                    final PotionEffect potionEffect = player.getPotionEffect(PotionEffectType.SPEED);
                    player.removePotionEffect(PotionEffectType.SPEED);
                    player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, potionEffect.getDuration() + 3 * 20, potionEffect.getAmplifier() + 1, false, false));
                } else {
                    player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 5 * 20, 2, false, false));
                }
            }
        });

        donut.register();

        foodCategory.registerMachine(donut);

        foodCategory.register();
    }

    private static void registerCommands() {
        plugin.getCommand("tf").setExecutor(new TechFunCommands());
    }

    private static void registerEvents() {
        Bukkit.getPluginManager().registerEvents(new MultiBlockEvents(), plugin);
        Bukkit.getPluginManager().registerEvents(new ItemEvents(), plugin);
        Bukkit.getPluginManager().registerEvents(new PlayerEvents(), plugin);
        Bukkit.getPluginManager().registerEvents(new BlockEvents(), plugin);
        Bukkit.getPluginManager().registerEvents(new TechFunGuide(), plugin);
        Bukkit.getPluginManager().registerEvents(new InventoryEvents(), plugin);
    }

}
