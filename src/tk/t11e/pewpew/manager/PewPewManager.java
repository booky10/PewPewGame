package tk.t11e.pewpew.manager;
// Created by booky10 in pewPewGame (20:04 24.01.20)


import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.attribute.Attribute;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.AbstractArrow;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.util.Vector;
import tk.t11e.pewpew.main.Main;
import tk.t11e.pewpew.util.Weapon;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@SuppressWarnings("RedundantIfStatement")
public class PewPewManager {

    public static final HashMap<UUID, List<Weapon>> cooldown = new HashMap<>();
    private final static Main main = Main.getPlugin(Main.class);

    public static void respawn(Player player) {
        player.sendMessage(Main.PREFIX+"You died!");

        player.setExp(0);
        player.getInventory().clear();
        addItems(player.getInventory());
        player.teleport(getSpawn());
        player.setHealth(Objects.requireNonNull(player
                .getAttribute(Attribute.GENERIC_MAX_HEALTH)).getValue());
    }

    public static void addItems(Inventory inventory) {
        for (Weapon weapon: Weapon.values())
            inventory.addItem(weapon.getItem());
    }

    public static void shot(Location location, Vector vector, Weapon weapon, UUID uuid) {
        if (cooldown.containsKey(uuid) && cooldown.get(uuid).contains(weapon)) return;
        cooldown.get(uuid).add(weapon);
        resetCooldown(uuid, weapon, weapon.getCooldown());

        if (!weapon.getExplosion()) {
            Arrow projectile = location.getWorld().spawnArrow(location, vector, 10, 0);
            projectile.setPickupStatus(AbstractArrow.PickupStatus.DISALLOWED);
            projectile.setCustomName(weapon.toString());
            projectile.setCustomNameVisible(false);
            projectile.setSilent(true);
            projectile.setShooter(Bukkit.getPlayer(uuid));
            projectile.setInvulnerable(true);
            projectile.setDamage(0);

            location.getWorld().playSound(location, Sound.ENTITY_ARROW_SHOOT, 1, 0);
        } else {
            Fireball projectile = location.getWorld().spawn(location, Fireball.class);
            projectile.setCustomName(weapon.toString());
            projectile.setCustomNameVisible(false);
            projectile.teleport(location.add(vector));
            projectile.setVelocity(vector.normalize().multiply(1.25));
            projectile.setSilent(true);
            projectile.setShooter(Bukkit.getPlayer(uuid));
            projectile.setInvulnerable(true);
            projectile.setIsIncendiary(false);

            location.getWorld().playSound(location, Sound.BLOCK_ANVIL_DESTROY, 1, 0);
        }
    }

    public static void resetCooldown(UUID uuid, Weapon weapon, long cooldown) {
        Bukkit.getScheduler().runTaskLater(Main.getPlugin(Main.class),
                () -> PewPewManager.cooldown.get(uuid).remove(weapon), cooldown);
    }

    public static Boolean isLocationInSpawn(Location location) {
        double X1 = getLoc1().getBlockX() - 0.125;
        double Y1 = getLoc1().getBlockY() - 0.125;
        double Z1 = getLoc1().getBlockZ() - 0.125;

        double X2 = getLoc2().getBlockX() + 0.125;
        double Y2 = getLoc2().getBlockY() + 0.125;
        double Z2 = getLoc2().getBlockZ() + 0.125;

        if (location.getBlockY() > Y1 && location.getBlockY() < Y2
                && location.getBlockX() > X1 && location.getBlockX() < X2
                && location.getBlockZ() > Z1 && location.getBlockZ() < Z2)
            return true;
        return false;
    }

    public static void setKills(UUID uuid, Integer kills) {
        FileConfiguration config = main.getConfig();

        config.set("Kills."+uuid.toString(),kills);

        main.saveConfig();
    }

    public static Integer getKills(UUID uuid) {
        FileConfiguration config = main.getConfig();

        return config.getInt("Kills."+uuid.toString());
    }

    public static void setDeaths(UUID uuid, Integer deaths) {
        FileConfiguration config = main.getConfig();

        config.set("Deaths."+uuid.toString(),deaths);

        main.saveConfig();
    }

    public static Integer getDeaths(UUID uuid) {
        FileConfiguration config = main.getConfig();

        return config.getInt("Deaths."+uuid.toString());
    }

    public static void setLoc1(Location location) {
        FileConfiguration config = main.getConfig();

        config.set("Locations.Loc1.X",location.getBlockX()+0.5);
        config.set("Locations.Loc1.Y",location.getBlockY()+0.5);
        config.set("Locations.Loc1.Z",location.getBlockZ()+0.5);

        main.saveConfig();
    }

    public static void setLoc2(Location location) {
        FileConfiguration config = main.getConfig();

        config.set("Locations.Loc2.X",location.getBlockX()+0.5);
        config.set("Locations.Loc2.Y",location.getBlockY()+0.5);
        config.set("Locations.Loc2.Z",location.getBlockZ()+0.5);

        main.saveConfig();
    }

    public static Location getLoc1() {
        FileConfiguration config = main.getConfig();

        double x = config.getDouble("Locations.Loc1.X");
        double y = config.getDouble("Locations.Loc1.Y");
        double z = config.getDouble("Locations.Loc1.Z");

        return new Location(getWorld(),x,y,z,0,0);
    }

    public static Location getLoc2() {
        FileConfiguration config = main.getConfig();

        double x = config.getDouble("Locations.Loc2.X");
        double y = config.getDouble("Locations.Loc2.Y");
        double z = config.getDouble("Locations.Loc2.Z");

        return new Location(getWorld(),x,y,z,0,0);
    }

    public static Location getSpawn() {
        FileConfiguration config = main.getConfig();

        double x = config.getDouble("Locations.Spawn.X");
        double y = config.getDouble("Locations.Spawn.Y");
        double z = config.getDouble("Locations.Spawn.Z");

        return new Location(getWorld(),x,y,z,0,0);
    }

    public static void setSpawn(Location location) {
        FileConfiguration config = main.getConfig();

        config.set("World",location.getWorld().getName());
        config.set("Locations.Spawn.X",location.getBlockX()+0.5);
        config.set("Locations.Spawn.Y",location.getBlockY()+0.5);
        config.set("Locations.Spawn.Z",location.getBlockZ()+0.5);

        main.saveConfig();
    }

    public static World getWorld() {
        FileConfiguration config = main.getConfig();

        return Bukkit.getWorld(Objects.requireNonNull(config.getString("World")));
    }
}