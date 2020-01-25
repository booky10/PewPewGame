package tk.t11e.pewpew.main;
// Created by booky10 in pewPewGame (11:38 25.01.20)

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import tk.t11e.pewpew.commands.PewPewGame;
import tk.t11e.pewpew.listener.DamageListener;
import tk.t11e.pewpew.listener.InteractListener;
import tk.t11e.pewpew.listener.InventoryListener;
import tk.t11e.pewpew.listener.JoinLeaveListener;
import tk.t11e.pewpew.manager.PewPewManager;

import java.util.ArrayList;
import java.util.Objects;

public class Main extends JavaPlugin {

    public static final String PREFIX = "§7[§bT11E§7]§c ", NO_PERMISSION = PREFIX + "You don't have" +
            " the permissions for this!";

    @Override
    public void onEnable() {
        long milliseconds = System.currentTimeMillis();

        saveDefaultConfig();
        initCommands();
        initListener(Bukkit.getPluginManager());
        for (Player player : Bukkit.getOnlinePlayers())
            PewPewManager.cooldown.put(player.getUniqueId(),new ArrayList<>());

        milliseconds = System.currentTimeMillis() - milliseconds;
        System.out.println("[pewPewGame] It took " + milliseconds + "ms to initialize this plugin");
    }

    private void initListener(PluginManager pluginManager) {
        pluginManager.registerEvents(new InteractListener(), this);
        pluginManager.registerEvents(new JoinLeaveListener(), this);
        pluginManager.registerEvents(new DamageListener(), this);
        pluginManager.registerEvents(new InventoryListener(), this);
    }

    private void initCommands() {
        Objects.requireNonNull(getCommand("pewPewGame")).setExecutor(new PewPewGame());
    }
}