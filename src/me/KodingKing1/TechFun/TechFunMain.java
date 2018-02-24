package me.KodingKing1.TechFun;

import com.bobacadodl.imgmessage.ImageChar;
import com.bobacadodl.imgmessage.ImageMessage;
import me.KodingKing1.TechFun.Startup.Registry;
import me.KodingKing1.TechFun.Startup.TechFunGuide;
import me.KodingKing1.TechFun.Startup.TechFunStartup;
import me.KodingKing1.TechFun.Util.CoolDownManager;
import me.KodingKing1.TechFun.Util.ResourceExtractor;
import me.KodingKing1.TechFun.Util.TextUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

/**
 * Created by dylan on 27/02/2017.
 */
public class TechFunMain extends JavaPlugin {

    private static TextUtil logger;

    private static FileConfiguration data;
    private static File dataFile;

    @Override
    public void onEnable() {
        long time = System.nanoTime();
        dataFile = new File(getDataFolder(), "data.yml");
        logger = new TextUtil(ChatColor.DARK_AQUA + "[" + ChatColor.AQUA + "TechFun" + ChatColor.DARK_AQUA + "]");
        loadData();
        TechFunStartup.init(this);
        try {
            ResourceExtractor extr = new ResourceExtractor(this, getDataFolder(), "Res", ".*\\.(png)$");
            extr.extract();
            File logoFile = new File(getDataFolder(), "Logo-Pixel.png");
            BufferedImage image = ImageIO.read(logoFile);
            new ImageMessage(image, 8, '#', true)
                    .appendCenteredText("",
                                "",
                                "TechFun has loaded " + Registry.length() + " items",
                                "in " + String.valueOf((System.nanoTime() - time) / 1000000) + "ms!",
                                "Initialization is complete and",
                                "plugin is fully loaded!",
                                "",
                                "")
                    .sendToConsole();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void loadData() {
        if(!dataFile.exists()){
            try{
                dataFile.createNewFile();
            }catch(Exception e){
                e.printStackTrace();
            }
        }
        data = YamlConfiguration.loadConfiguration(dataFile);
    }

    public static void saveData(){
        try {
            data.save(dataFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static File getDataFile() {
        return dataFile;
    }

    @Override
    public void onDisable() {
        saveData();
    }

    public static TextUtil getPluginLogger() {
        return logger;
    }

    public static FileConfiguration getData() {
        return data;
    }
}
