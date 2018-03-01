package me.KodingKing1.TechFun.Startup;

import me.KodingKing1.TechFun.Events.*;
import me.KodingKing1.TechFun.Objects.Category.Category;
import me.KodingKing1.TechFun.Objects.CraftingStation;
import me.KodingKing1.TechFun.Objects.CustomRecipe;
import me.KodingKing1.TechFun.Objects.Factory;
import me.KodingKing1.TechFun.Objects.Handlers.Item.ItemAttackHandler;
import me.KodingKing1.TechFun.Objects.Handlers.Item.ItemBlockBreakHandler;
import me.KodingKing1.TechFun.Objects.Handlers.Item.ItemClickHandler;
import me.KodingKing1.TechFun.Objects.Handlers.MultiBlock.MultiBlockClickHandler;
import me.KodingKing1.TechFun.Objects.ItemBase;
import me.KodingKing1.TechFun.Objects.MultiBlock.MultiBlock;
import me.KodingKing1.TechFun.TechFunMain;
import me.KodingKing1.TechFun.Util.Cooldown;
import me.KodingKing1.TechFun.Util.InvUtil;
import me.KodingKing1.TechFun.Util.TFUtil;
import me.KodingKing1.TechFun.Util.TextUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Dropper;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Random;

/**
 * Created by dylan on 27/02/2017.
 */
public class TechFunStartup {

    private static TechFunMain plugin;

    public static void init(TechFunMain techfun) {
        plugin = techfun;
        registerAll();
        registerEvents();
        registerCommands();
        Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new RunnableMain(), 1L, 1L);
    }

    private static void registerAll() {
        Category materials = Factory.makeCategory("TFMaterials", "Materials", new String[]{"Lots of things used to make lots of other", "things!"}, Material.DIAMOND, 0);

        ItemBase woodenCore = Factory.makeItem("WoodenCore", "Wooden Core", new String[]{"The very first item core! Just made of wood."}, Material.LOG, new Object[]{
                Material.LOG, Material.WOOD, Material.LOG,
                Material.WOOD, Material.LEATHER, Material.WOOD,
                Material.LOG, Material.WOOD, Material.LOG
        }, CraftingStation.MagicalCraftingTable, 0);

        woodenCore.register();

        materials.registerItem(woodenCore);

        ItemBase stoneCore = Factory.makeItem("StoneCore", "Stone Core", new String[]{"The second tier of item cores!", "Its made of stone!"}, Material.STONE, new Object[]{
                Material.STONE, Material.COBBLESTONE, Material.STONE,
                Material.COBBLESTONE, woodenCore.getItem(), Material.COBBLESTONE,
                Material.STONE, Material.COBBLESTONE, Material.STONE
        }, CraftingStation.MagicalCraftingTable, 2);

        stoneCore.register();

        materials.registerItem(stoneCore);

        ItemBase ironCore = Factory.makeItem("IronCore", "Iron Core", new String[]{"The third tier of item cores! Made out of silver shiny stuff! :-D"}, Material.IRON_INGOT, new Object[]{
                Material.IRON_INGOT, Material.STONE, Material.IRON_INGOT,
                Material.STONE, stoneCore.getItem(), Material.STONE,
                Material.IRON_INGOT, Material.STONE, Material.IRON_INGOT
        }, CraftingStation.MagicalCraftingTable, 4);

        ironCore.register();

        materials.registerItem(ironCore);

        ItemBase goldCore = Factory.makeItem("GoldCore", "Gold Core", new String[]{"The forth tier of item cores! Made out of beautiful rose gold! XD"}, Material.GOLD_INGOT, new Object[]{
                Material.GOLD_INGOT, Material.RED_ROSE, Material.GOLD_INGOT,
                Material.IRON_INGOT, ironCore.getItem(), Material.IRON_INGOT,
                Material.GOLD_INGOT, Material.RED_ROSE, Material.GOLD_INGOT
        }, CraftingStation.MagicalCraftingTable, 6);

        goldCore.register();

        materials.registerItem(goldCore);

        ItemBase diamondCore = Factory.makeItem("DiamondCore", "Diamond Core", new String[]{"The second best tier of item cores! Made out of blue shiny stuff (Diamonds)!"}, Material.DIAMOND, new Object[]{
                Material.DIAMOND, Material.GOLD_INGOT, Material.DIAMOND,
                Material.GOLD_INGOT, goldCore.getItem(), Material.GOLD_INGOT,
                Material.DIAMOND, Material.GOLD_INGOT, Material.DIAMOND
        }, CraftingStation.MagicalCraftingTable, 8);

        diamondCore.register();

        materials.registerItem(diamondCore);

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

        ItemBase portableCrafter = Factory.makeItem("PortableCrafter", "Portable Crafter", new String[]{"Lets you craft on the go!"}, Material.WORKBENCH, new Object[]{
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

        basicMachinesCategory.register();

        Category weaponsCategory = Factory.makeCategory("TFWeapons", "Weapons", new String[]{"Want to battle your enemies and conquer the world!", "Well here is the place for you!"}, Material.DIAMOND_SWORD, 0);

        ItemBase soulDagger = Factory.makeItem("SoulDagger", "Soul Dagger", new String[]{"Has a chance of gaining 2 hearts of life-steal when", "hitting another player."}, Material.GOLD_SWORD, new Object[]{
                Material.GOLD_INGOT, Material.GOLD_SWORD, Material.GOLD_INGOT,
                null, ironCore.getItem(), null,
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
        }, CraftingStation.MagicalCraftingTable, 2);

        smackyStick.getItem().addUnsafeEnchantment(Enchantment.KNOCKBACK, 5);

        smackyStick.register();

        weaponsCategory.registerItem(smackyStick);

        ItemBase swordOfBlinding = Factory.makeItem("SwordOfBlinding", "Sword of Blinding", new String[]{"Has a chance to blind the enemy when hit."}, Material.DIAMOND_SWORD, new Object[]{
                null, Material.REDSTONE_BLOCK, null,
                null, Material.DIAMOND_SWORD, null,
                Material.OBSIDIAN, diamondCore, Material.OBSIDIAN
        }, CraftingStation.MagicalCraftingTable, 10);

        swordOfBlinding.registerHandler(new ItemAttackHandler() {
            @Override
            public void onAttack(EntityDamageByEntityEvent e, Player p, ItemStack item) {
                Random r = new Random();
                int chance = r.nextInt(9);
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
                Cooldown cooldown = new Cooldown(p.getUniqueId(), "wandOfFire", 10);
                e.setCancelled(true);
                if (Cooldown.getTimeLeft(p.getUniqueId(), "wandOfFire") <= 0) {
                    Fireball fireball = (Fireball) p.launchProjectile(Fireball.class);
                    fireball.setVelocity(p.getLocation().getDirection().multiply(3));
                    cooldown.start();
                } else {
                    TechFunMain.getPluginLogger().sendMessage(p, TextUtil.Level.Error, "The item is still on cooldown! There is " + Cooldown.getTimeLeft(p.getUniqueId(), "wandOfFire") + " seconds left!");
                }
            }
        });

        wandOfFire.register();

        magicCategory.registerItem(wandOfFire);

        ItemBase playerBeheader = Factory.makeItem("PlayerBeheader", "Player Beheader", new String[]{"Takes the head right off players!"}, Material.REDSTONE, new Object[]{
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

        ItemBase xpToken = Factory.makeItem("XPToken", "XP Token", new String[]{"Gives you 5 levels when used."}, Material.EMERALD, new Object[]{
                Material.COBBLESTONE, Material.COAL_BLOCK, Material.COBBLESTONE,
                Material.COAL, stoneCore, Material.COAL,
                Material.COBBLESTONE, Material.GLASS_BOTTLE, Material.COBBLESTONE
        }, CraftingStation.MagicalCraftingTable, 10);

        xpToken.registerHandler(new ItemClickHandler() {
            @Override
            public void onItemClick(PlayerInteractEvent e, Player p, ItemStack item) {
                InvUtil.decrementItem(item);
                p.setLevel(p.getLevel() + 5);
                p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1);
            }
        });

        xpToken.register();

        magicCategory.registerItem(xpToken);

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

        CustomRecipe cowSpawner = Factory.makeCustomRecipe(TFUtil.makeMobSpawnEgg("Cow", 2, plugin), new ItemStack[]{
                null, new ItemStack(Material.RAW_BEEF), null,
                null, new ItemStack(Material.EGG), null,
                null, null, null
        }, CraftingStation.MagicalCraftingTable, spawnerCategory);

        cowSpawner.register();

        spawnerCategory.register();
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
