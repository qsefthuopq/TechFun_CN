package me.KodingKing1.TechFun.Objects.Inv;

import org.bukkit.inventory.Inventory;

/**
 * Created by djite on 16/05/2017.
 */
public class Page {

    private int id;
    private Inventory inv;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Inventory getInv() {
        return inv;
    }

    public void setInv(Inventory inv) {
        this.inv = inv;
    }
}
