package me.KodingKing1.TechFun.Startup;

import me.KodingKing1.TechFun.Objects.Category.Category;
import me.KodingKing1.TechFun.Objects.ItemBase;
import me.KodingKing1.TechFun.Objects.Machine.Machine;
import me.KodingKing1.TechFun.Objects.MultiBlock.MultiBlock;
import me.KodingKing1.TechFun.TechFunMain;
import me.KodingKing1.TechFun.Util.DataManager;
import me.KodingKing1.TechFun.Util.TextUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by dylan on 1/03/2017.
 */
public class TechFunCommands implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if(cmd.getName().equalsIgnoreCase("tf") && args.length <= 0){
            TechFunMain.getPluginLogger().sendMessage(sender, TextUtil.Level.Info2, "================*TechFun*================");
            TechFunMain.getPluginLogger().sendMessage(sender, TextUtil.Level.Info,  "Making your servers modded, with no mods!");
            TechFunMain.getPluginLogger().sendMessage(sender, TextUtil.Level.Info,  "Type /tf commands for commands!");
            TechFunMain.getPluginLogger().sendMessage(sender, TextUtil.Level.Info2, "=========================================");
            return true;
        }else if(cmd.getName().equalsIgnoreCase("tf") && args[0].equalsIgnoreCase("commands")) {
            TechFunMain.getPluginLogger().sendMessage(sender, TextUtil.Level.Info2, "=================*TechFun*=================");
            TechFunMain.getPluginLogger().sendMessage(sender, TextUtil.Level.Info,  "Commands:");
            TechFunMain.getPluginLogger().sendMessage(sender, TextUtil.Level.Info,  " - /tf");
            TechFunMain.getPluginLogger().sendMessage(sender, TextUtil.Level.Info,  " - /tf commands");
            TechFunMain.getPluginLogger().sendMessage(sender, TextUtil.Level.Info,  " - /tf research <Player> <all/reset>");
            TechFunMain.getPluginLogger().sendMessage(sender, TextUtil.Level.Info,  " - /tf guide");
            TechFunMain.getPluginLogger().sendMessage(sender, TextUtil.Level.Info,  " - /tf resetconfig");
            TechFunMain.getPluginLogger().sendMessage(sender, TextUtil.Level.Info2, "==========================================");
            return true;
        }else if(cmd.getName().equalsIgnoreCase("tf") && args[0].equalsIgnoreCase("research")) {
            if(args.length <= 2){
                TechFunMain.getPluginLogger().sendMessage(sender, TextUtil.Level.Error, "Please supply more arguments!");
                return true;
            }
            if(Bukkit.getPlayer(args[1]) != null){
                Player p = Bukkit.getPlayer(args[1]);
                if(args[2].equalsIgnoreCase("reset")){
                    TechFunMain.getData().createSection(p.getUniqueId().toString());
                    TechFunMain.saveData();
                    TechFunMain.getPluginLogger().sendMessage(sender, TextUtil.Level.Success, "Successfully reset all data!");
                    return true;
                }else if(args[2].equalsIgnoreCase("all")){
                    for(Category category : Registry.getCategories()){
                        DataManager.setPlayerData(p, "Guide.Categories." + category.getName() + ".Unlocked", true);
                    }
                    for(ItemBase item : Registry.getItems()){
                        DataManager.setPlayerData(p, "Guide.Items." + item.getName() + ".Unlocked", true);
                    }
                    for(MultiBlock mb : Registry.getMultiBlocks()){
                        DataManager.setPlayerData(p, "Guide.Items." + ChatColor.stripColor(mb.getIcon().getItemMeta().getDisplayName()) + ".Unlocked", true);
                    }
                    for(Machine m : Registry.getMachines()){
                        DataManager.setPlayerData(p, "Guide.Machine." + ChatColor.stripColor(m.getItem().getItemMeta().getDisplayName()) + ".Unlocked", true);
                    }
                    TechFunMain.getPluginLogger().sendMessage(sender, TextUtil.Level.Success, "Successfully unlocked everything!");
                    return true;
                }else{
                    TechFunMain.getPluginLogger().sendMessage(sender, TextUtil.Level.Error, "Unrecognised sub-command!");
                    return true;
                }
            }
        }else if(cmd.getName().equalsIgnoreCase("tf") && args[0].equalsIgnoreCase("guide")) {
            if(sender instanceof Player){
                Player p = (Player) sender;
                p.getInventory().addItem(Registry.getItem("TechFunGuide").getItem());
                TechFunMain.getPluginLogger().sendMessage(p, TextUtil.Level.Success, "You have been given a guide!");
            }else{
                TechFunMain.getPluginLogger().sendMessage(sender, TextUtil.Level.Error, "You need to be in the game to get a guide!");
            }
            return true;
        }else if(cmd.getName().equalsIgnoreCase("tf") && args[0].equalsIgnoreCase("resetconfig")) {
            TechFunMain.getDataFile().delete();
            TechFunMain.loadData();
            TechFunMain.getPluginLogger().sendMessage(sender, TextUtil.Level.Success, "Config has been reset successfully!");
            return true;
        }else if(cmd.getName().equalsIgnoreCase("tf")){
            TechFunMain.getPluginLogger().sendMessage(sender, TextUtil.Level.Error, "Unrecognised command! Type /tf commands for commands!");
            return true;
        }
        return false;
    }
}
