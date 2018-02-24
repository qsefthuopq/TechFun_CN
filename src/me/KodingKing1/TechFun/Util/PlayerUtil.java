package me.KodingKing1.TechFun.Util;

import me.KodingKing1.TechFun.Objects.ItemBase;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by dylan on 7/03/2017.
 */
public class PlayerUtil {

    public static boolean isWearingArmourSet(ItemBase[] armour, Player p){
        boolean bool = true;
        if(p.getInventory().getHelmet() != null){
            if(!p.getInventory().getHelmet().isSimilar(armour[0].getItem())){
                bool = false;
            }
        }else{
            bool = false;
        }
        if(p.getInventory().getChestplate() != null){
            if(!p.getInventory().getChestplate().isSimilar(armour[1].getItem())){
                bool = false;
            }
        }else{
            bool = false;
        }
        if(p.getInventory().getLeggings() != null){
            if(!p.getInventory().getLeggings().isSimilar(armour[2].getItem())){
                bool = false;
            }
        }else{
            bool = false;
        }
        if(p.getInventory().getBoots() != null){
            if(!p.getInventory().getBoots().isSimilar(armour[3].getItem())){
                bool = false;
            }
        }else{
            bool = false;
        }
        return bool;
    }

    public static Object[] reverse(Object[] arr) {
        List<Object> list = Arrays.asList(arr);
        Collections.reverse(list);
        return list.toArray();
    }

}
