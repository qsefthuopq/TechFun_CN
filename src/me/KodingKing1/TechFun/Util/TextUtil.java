package me.KodingKing1.TechFun.Util;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

/**
 * Created by djite on 16/05/2017.
 */
public class TextUtil {

    private String PREFIX;

    public TextUtil(String PREFIX) {
        this.PREFIX = PREFIX;
    }

    public enum Level {

        Info(ChatColor.AQUA),
        Info2(ChatColor.DARK_AQUA),
        Success(ChatColor.GREEN),
        Warning(ChatColor.YELLOW),
        Error(ChatColor.RED);

        private ChatColor color;

        Level(ChatColor color) {
            this.color = color;
        }

        public ChatColor getColor() {
            return color;
        }
    }

    public void sendMessage(CommandSender sender, Level level, String text){
        sender.sendMessage(PREFIX + level.getColor() + text);
    }

    public void log(Level level, String message){
        Bukkit.getConsoleSender().sendMessage(PREFIX + level.getColor() + message);
    }

}
