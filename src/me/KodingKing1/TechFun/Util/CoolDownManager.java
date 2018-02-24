package me.KodingKing1.TechFun.Util;

/**
 * Created by djite on 17/05/2017.
 */
import java.util.HashMap;
import java.util.Map;

import me.KodingKing1.TechFun.TechFunMain;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class CoolDownManager {

    private TechFunMain p;
    private Map<String, Integer> hashmap = new HashMap<>();

    public CoolDownManager(TechFunMain i) {
        p = i;
    }

    private int task;

    public void setCooldownLength(Player player, int time) {
        if(hashmap.containsKey(player.getName()))
            hashmap.remove(player.getName());
        hashmap.put(player.getName(), time);
    }

    public int getTimeLeft(Player player) {
        if(!hashmap.containsKey(player.getName())){
            setCooldownLength(player, 0);
        }
        int time = hashmap.get(player.getName());
        return time;
    }

    public void startCooldown(final Player player) {
        task = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(p, new BukkitRunnable() {
            public void run() {
                int time = hashmap.get(player.getName());
                if(time != 0) {
                    hashmap.put(player.getName(), time - 1);
                } else {
                    Bukkit.getServer().getScheduler().cancelTask(task);
                }
            }
        }, 0L, 20L);
    }

    public Map<String, Integer> getHashmap() {
        return hashmap;
    }
}
