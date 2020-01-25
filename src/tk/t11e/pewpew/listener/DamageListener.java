package tk.t11e.pewpew.listener;
// Created by booky10 in pewPewGame (14:56 25.01.20)

import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import tk.t11e.pewpew.main.Main;
import tk.t11e.pewpew.manager.PewPewManager;
import tk.t11e.pewpew.util.Weapon;

@SuppressWarnings("ConstantConditions")
public class DamageListener implements Listener {

    @EventHandler
    public void onProjectileDamage(EntityDamageByEntityEvent event) {
        if (!event.getDamager().getType().equals(EntityType.ARROW)) return;
        if (!event.getEntityType().equals(EntityType.PLAYER)) return;

        Arrow projectile = (Arrow) event.getDamager();
        Player killer = (Player) projectile.getShooter();
        Player player = (Player) event.getEntity();
        Double damage = Weapon.valueOf(projectile.getCustomName()).getDamage();

        if(PewPewManager.isLocationInSpawn(player.getLocation())){
            event.setCancelled(true);
            return;}

        event.setDamage(damage);

        if (event.getFinalDamage() >= player.getHealth()) {
            event.setCancelled(true);

            PewPewManager.respawn(player);
            PewPewManager.setDeaths(player.getUniqueId(),
                    PewPewManager.getDeaths(player.getUniqueId()) + 1);

            PewPewManager.setKills(killer.getUniqueId(), PewPewManager.getKills(player
                    .getUniqueId()) + 1);
            killer.setHealth(killer.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue());
            killer.sendMessage(Main.PREFIX + "§aYou killed §6\"" + player.getName() + "\"§a!");
        }
    }

    @EventHandler
    public void onAttack(EntityDamageByEntityEvent event) {
        if (!event.getEntity().getWorld().getName().equals(PewPewManager.getWorld().getName())) return;
        if (!event.getEntityType().equals(EntityType.PLAYER)) return;
        if (!event.getDamager().getType().equals(EntityType.PLAYER)) return;

        event.setCancelled(true);
    }

    @EventHandler
    public void onFallDamage(EntityDamageEvent event) {
        if (!event.getEntity().getWorld().getName().equals(PewPewManager.getWorld().getName())) return;
        if (!event.getCause().equals(EntityDamageEvent.DamageCause.FALL)) return;

        event.setCancelled(true);
    }

    @EventHandler
    public void onTNTDamage(EntityDamageByEntityEvent event) {
        if (!event.getEntity().getWorld().getName().equals(PewPewManager.getWorld().getName())) return;
        if (!event.getDamager().getType().equals(EntityType.FIREBALL)) return;
        if (!event.getEntityType().equals(EntityType.PLAYER)) return;
        if (!(event.getCause().equals(EntityDamageEvent.DamageCause.ENTITY_EXPLOSION)
                || event.getCause().equals(EntityDamageEvent.DamageCause.PROJECTILE))) return;

        Fireball projectile = (Fireball) event.getDamager();
        Player killer = (Player) projectile.getShooter();
        Player player = (Player) event.getEntity();
        Double damage = Weapon.valueOf(projectile.getCustomName()).getDamage();

        if(PewPewManager.isLocationInSpawn(player.getLocation())){
            event.setCancelled(true);
        return;}

        if (killer.getName().equals(player.getName())) {
            event.setDamage(0);
            return;
        }else
            event.setDamage(damage);

        if (event.getFinalDamage() >= player.getHealth()) {
            event.setCancelled(true);

            PewPewManager.respawn(player);
            PewPewManager.setDeaths(player.getUniqueId(),
                    PewPewManager.getDeaths(player.getUniqueId()) + 1);

            PewPewManager.setKills(killer.getUniqueId(), PewPewManager.getKills(player
                    .getUniqueId()) + 1);
            killer.setHealth(killer.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue());
            killer.sendMessage(Main.PREFIX + "§aYou killed §6\"" + player.getName() + "\"§a!");
        }
    }

    @EventHandler
    public void onFoodChange(FoodLevelChangeEvent event) {
        if (!event.getEntity().getWorld().getName().equals(PewPewManager.getWorld().getName())) return;

        event.setFoodLevel(20);
    }

    @EventHandler
    public void onExplosion(EntityExplodeEvent event) {
        if (!event.getLocation().getWorld().getName().equals(PewPewManager.getWorld().getName())) return;
        if(!event.getEntityType().equals(EntityType.FIREBALL)) return;

        event.blockList().clear();
    }
}