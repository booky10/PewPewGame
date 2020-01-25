package tk.t11e.pewpew.listener;
// Created by booky10 in pewPewGame (20:01 25.01.20)

import org.bukkit.GameMode;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import tk.t11e.pewpew.manager.PewPewManager;

public class InventoryListener implements Listener {

    @EventHandler
    public void onDrop(PlayerDropItemEvent event) {
        if(!event.getPlayer().getWorld().getName().equals(PewPewManager.getWorld().getName())) return;
        if(event.getPlayer().getGameMode().equals(GameMode.CREATIVE)) return;

        event.setCancelled(true);
    }

    @EventHandler
    public void onSwap(PlayerSwapHandItemsEvent event) {
        if(!event.getPlayer().getWorld().getName().equals(PewPewManager.getWorld().getName())) return;
        if(event.getPlayer().getGameMode().equals(GameMode.CREATIVE)) return;

        event.setCancelled(true);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if(!event.getWhoClicked().getWorld().getName().equals(PewPewManager.getWorld().getName())) return;
        if(event.getWhoClicked().getGameMode().equals(GameMode.CREATIVE)) return;

        event.setCancelled(true);
    }
}