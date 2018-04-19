package me.KodingKing1.TechFun.Objects.Handlers.Machine;

import me.KodingKing1.TechFun.Objects.Machine.Machine;
import me.KodingKing1.TechFun.Objects.MultiBlock.MultiBlock;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;

public interface MachineClickHandler extends MachineHandler {

    public void onMachineClick(Machine machine, Player player, PlayerInteractEvent e);

}
