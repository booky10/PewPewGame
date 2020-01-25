package tk.t11e.pewpew.listener;
// Created by booky10 in pewPewGame (11:57 25.01.20)

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerPickupArrowEvent;
import tk.t11e.pewpew.manager.PewPewManager;
import tk.t11e.pewpew.util.Weapon;

public class InteractListener implements Listener {

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        if (!event.getPlayer().getWorld().getName().equals(PewPewManager.getWorld().getName())) return;
        if (!(event.getAction().equals(Action.RIGHT_CLICK_BLOCK) || event.getAction()
                .equals(Action.RIGHT_CLICK_AIR))) return;
        if (event.getPlayer().getInventory().getItemInMainHand().getType().equals(Material.AIR)) return;
        if(PewPewManager.isLocationInSpawn(event.getPlayer().getLocation())) return;

        Player player = event.getPlayer();
        Weapon weapon;

        switch (player.getInventory().getItemInMainHand().getType()) {
            case STONE_HOE:
                weapon = Weapon.RIFLE;
                break;
            case WOODEN_HOE:
                weapon = Weapon.PISTOL;
                break;
            case IRON_HOE:
                weapon = Weapon.SHOTGUN;
                break;
            case DIAMOND_HOE:
                weapon = Weapon.MINIGUN;
                break;
            case STONE_SWORD:
                weapon = Weapon.ROCKET_LAUNCHER;
                break;
            default:
                return;
        }
        PewPewManager.shot(player.getEyeLocation(), player.getLocation().getDirection(), weapon,
                player.getUniqueId());
    }

    @EventHandler
    public void onPickup(PlayerPickupArrowEvent event) {
        if(!event.getArrow().getWorld().getName().equals(PewPewManager.getWorld().getName())) return;

        event.setCancelled(true);
    }
}