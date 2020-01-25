package tk.t11e.pewpew.listener;
// Created by booky10 in pewPewGame (13:42 25.01.20)

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import tk.t11e.pewpew.manager.PewPewManager;

import java.util.ArrayList;

public class JoinLeaveListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        event.setJoinMessage("");
        PewPewManager.cooldown.put(event.getPlayer().getUniqueId(), new ArrayList<>());
        PewPewManager.respawn(event.getPlayer());

        event.getPlayer().setResourcePack("https://cdn.discordapp.com/attachments/605732645116051466" +
                "/670722227255246879/PewPewGame.zip", "97ed34b91ec334c62cc11857c35b89ce98d75b1e");
        event.getPlayer().sendTitle("Loading textures...", "takes 20 seconds...", 10,
                100, 0);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        event.setQuitMessage("");
        PewPewManager.cooldown.remove(event.getPlayer().getUniqueId());
    }
}