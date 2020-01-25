package tk.t11e.pewpew.util;
// Created by booky10 in pewPewGame (12:04 25.01.20)


import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

@SuppressWarnings("SameParameterValue")
public enum Weapon {

    RIFLE(Material.STONE_HOE, "§7Rifle", 1, 2D, 5, false),
    PISTOL(Material.WOODEN_HOE, "§7Pistol", 1, 5D, 30, false),
    SHOTGUN(Material.IRON_HOE, "§7Shotgun", 1, 10D, 60, false),
    MINIGUN(Material.DIAMOND_HOE, "§7Minigun", 1, 1.5D, 1, false),
    ROCKET_LAUNCHER(Material.STONE_SWORD, "§7Rocket Launcher", 1, 10D, 40, true);

    private final Material material;
    private final String name;
    private final Integer modelData;
    private final Double damage;
    private final Integer cooldown;
    private final Boolean explosion;

    Weapon(Material material, String name, Integer modelData, Double damage, Integer cooldown,
           Boolean explosion) {
        this.material = material;
        this.name = name;
        this.modelData = modelData;
        this.damage = damage;
        this.cooldown = cooldown;
        this.explosion = explosion;
    }

    public String getName() {
        return name;
    }

    public Material getMaterial() {
        return material;
    }

    public Integer getModelData() {
        return modelData;
    }

    public ItemStack getItem() {
        ItemStack itemStack = new ItemStack(material);
        ItemMeta itemMeta = itemStack.getItemMeta();

        itemMeta.setUnbreakable(true);
        itemMeta.addItemFlags(ItemFlag.values());
        itemMeta.setDisplayName(name);
        itemMeta.setCustomModelData(modelData);

        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public Double getDamage() {
        return damage;
    }

    public Integer getCooldown() {
        return cooldown;
    }

    public Boolean getExplosion() {
        return explosion;
    }
}