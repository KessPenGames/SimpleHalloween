package simple.halloween.main;

import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.io.File;
import java.util.List;
import java.util.Random;

public class MobsSpawn implements Listener {
    private Main plugin;
    public MobsSpawn(Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onEntitySpawn(CreatureSpawnEvent event) {
        File logs_stats = new File(plugin.getDataFolder() + File.separator + "halloween.yml");
        FileConfiguration logs = YamlConfiguration.loadConfiguration(logs_stats);
        List<String> list = logs.getStringList("halloween");
        if (list.isEmpty()) return;
        if (list.get(0).equals("true")) {
            if (event.getSpawnReason().toString().equals("NATURAL")) {
                Random random = new Random();
                int health = random.ints(20, 100).findFirst().getAsInt();
                int rand = random.ints(1, 100).findFirst().getAsInt();
                event.setCancelled(true);
                World world = event.getEntity().getWorld();
                int maxmobs = plugin.getConfig().getInt("max-mobs");
                if (countmobs(world) > maxmobs) return;
                Location coords = event.getEntity().getLocation();
                if (coords.getBlock().getType() == Material.WATER) return;
                if (coords.getY() < 39) return;
                if (coords.getY() > 109) return;
                if (!event.getEntity().getWorld().getName().equals("world")) return;
                if (rand > 85) {
                    LivingEntity ent = (LivingEntity) event.getEntity().getWorld().spawnEntity(coords, EntityType.CAVE_SPIDER);
                    rand = random.ints(1, 100).findFirst().getAsInt();
                    if (rand > 70) {
                        ent.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 999999, 1));
                    } else if (rand > 50) {
                        ent.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 999999, 1));
                    } else if (rand > 30) {
                        ent.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 999999, 1));
                        ent.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 999999, 1));
                    }
                } else if (rand > 80) {
                    LivingEntity ent = (LivingEntity) event.getEntity().getWorld().spawnEntity(coords, EntityType.SPIDER);
                    rand = random.ints(1, 100).findFirst().getAsInt();
                    if (rand > 70) {
                        ent.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 999999, 1));
                    } else if (rand > 50) {
                        ent.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 999999, 1));
                    } else if (rand > 30) {
                        ent.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 999999, 1));
                        ent.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 999999, 1));
                    }
                } else if (rand > 75) {
                    LivingEntity ent = (LivingEntity) event.getEntity().getWorld().spawnEntity(coords, EntityType.ZOMBIE);
                    ent.getEquipment().setHelmet(plugin.pumpkin());
                    ent.getEquipment().setHelmetDropChance(80.0F);
                    ent.getEquipment().setChestplateDropChance(50.0F);
                    ent.getEquipment().setLeggingsDropChance(50.0F);
                    ent.getEquipment().setBootsDropChance(50.0F);
                    ent.getEquipment().setItemInMainHandDropChance(50.0F);
                    ent.setCanPickupItems(true);
                    rand = random.ints(1, 100).findFirst().getAsInt();
                    int ench = random.ints(0, 4).findFirst().getAsInt();
                    if (rand > 80) {
                        ent.getEquipment().setChestplate(new ItemStack(Material.LEATHER_CHESTPLATE));
                        ent.getEquipment().setLeggings(new ItemStack(Material.LEATHER_LEGGINGS));
                    } else if (rand > 75) {
                        ItemStack aboba = new ItemStack(Material.LEATHER_CHESTPLATE);
                        ItemMeta boba = aboba.getItemMeta();
                        boba.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, ench, false);
                        aboba.setItemMeta(boba);
                        ent.getEquipment().setChestplate(aboba);
                        ent.getEquipment().setLeggings(new ItemStack(Material.LEATHER_LEGGINGS));
                        ent.getEquipment().setBoots(new ItemStack(Material.LEATHER_BOOTS));
                    } else if (rand > 65) {
                        ItemStack aboba = new ItemStack(Material.IRON_CHESTPLATE);
                        ItemMeta boba = aboba.getItemMeta();
                        boba.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, ench, false);
                        aboba.setItemMeta(boba);
                        ent.getEquipment().setChestplate(aboba);
                        ent.getEquipment().setLeggings(new ItemStack(Material.IRON_LEGGINGS));
                    } else if (rand > 50) {
                        ItemStack aboba = new ItemStack(Material.CHAINMAIL_CHESTPLATE);
                        ItemMeta boba = aboba.getItemMeta();
                        boba.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, ench, false);
                        aboba.setItemMeta(boba);
                        ent.getEquipment().setChestplate(aboba);
                        ent.getEquipment().setLeggings(new ItemStack(Material.CHAINMAIL_LEGGINGS));
                    } else if (rand > 40) {
                        ItemStack aboba = new ItemStack(Material.DIAMOND_CHESTPLATE);
                        ItemMeta boba = aboba.getItemMeta();
                        boba.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, ench, false);
                        aboba.setItemMeta(boba);
                        ent.getEquipment().setChestplate(aboba);
                        ent.getEquipment().setLeggings(new ItemStack(Material.DIAMOND_LEGGINGS));
                    } else if (rand > 30) {
                        ItemStack aboba = new ItemStack(Material.NETHERITE_CHESTPLATE);
                        ItemMeta boba = aboba.getItemMeta();
                        boba.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, ench, false);
                        aboba.setItemMeta(boba);
                        ent.getEquipment().setChestplate(aboba);
                    } else {
                        ItemStack aboba = new ItemStack(Material.NETHERITE_CHESTPLATE);
                        ItemMeta boba = aboba.getItemMeta();
                        boba.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, ench, false);
                        aboba.setItemMeta(boba);
                        ent.getEquipment().setChestplate(aboba);
                        ent.getEquipment().setLeggings(new ItemStack(Material.NETHERITE_LEGGINGS));
                    }

                    rand = random.ints(1, 100).findFirst().getAsInt();
                    if (rand > 80) {
                        ent.getEquipment().setItemInMainHand(new ItemStack(Material.STONE_SWORD));
                    } else if (rand > 75) {
                        ent.getEquipment().setItemInMainHand(new ItemStack(Material.STONE_AXE));
                    } else if (rand > 70) {
                        ent.getEquipment().setItemInMainHand(new ItemStack(Material.IRON_SWORD));
                    } else if (rand > 60) {
                        ent.getEquipment().setItemInMainHand(new ItemStack(Material.IRON_AXE));
                    } else if (rand > 55) {
                        ent.getEquipment().setItemInMainHand(new ItemStack(Material.GOLDEN_SWORD));
                    } else if (rand > 45) {
                        ent.getEquipment().setItemInMainHand(new ItemStack(Material.GOLDEN_AXE));
                    } else if (rand > 30) {
                        ent.getEquipment().setItemInMainHand(new ItemStack(Material.DIAMOND_SWORD));
                    } else if (rand > 25) {
                        ent.getEquipment().setItemInMainHand(new ItemStack(Material.DIAMOND_AXE));
                    } else if (rand > 20) {
                        ent.getEquipment().setItemInMainHand(new ItemStack(Material.NETHERITE_SWORD));
                    } else {
                        ent.getEquipment().setItemInMainHand(new ItemStack(Material.NETHERITE_AXE));
                    }

                    rand = random.ints(1, 100).findFirst().getAsInt();
                    if (rand > 30) {
                        ent.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 999999, 1));
                    }
                } else if (rand > 60) {
                    LivingEntity ent = (LivingEntity) event.getEntity().getWorld().spawnEntity(coords, EntityType.SKELETON);
                    ent.getEquipment().setHelmet(plugin.pumpkin());
                    ent.getEquipment().setHelmetDropChance(80.0F);
                    ent.getEquipment().setChestplateDropChance(50.0F);
                    ent.getEquipment().setLeggingsDropChance(50.0F);
                    ent.getEquipment().setBootsDropChance(50.0F);
                    ent.getEquipment().setItemInMainHandDropChance(50.0F);
                    ent.setCanPickupItems(true);
                    rand = random.ints(1, 100).findFirst().getAsInt();
                    int ench = random.ints(0, 4).findFirst().getAsInt();
                    if (rand > 80) {
                        ent.getEquipment().setChestplate(new ItemStack(Material.LEATHER_CHESTPLATE));
                        ent.getEquipment().setLeggings(new ItemStack(Material.LEATHER_LEGGINGS));
                    } else if (rand > 75) {
                        ItemStack aboba = new ItemStack(Material.LEATHER_CHESTPLATE);
                        ItemMeta boba = aboba.getItemMeta();
                        boba.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, ench, false);
                        aboba.setItemMeta(boba);
                        ent.getEquipment().setChestplate(aboba);
                        ent.getEquipment().setLeggings(new ItemStack(Material.LEATHER_LEGGINGS));
                        ent.getEquipment().setBoots(new ItemStack(Material.LEATHER_BOOTS));
                    } else if (rand > 65) {
                        ItemStack aboba = new ItemStack(Material.IRON_CHESTPLATE);
                        ItemMeta boba = aboba.getItemMeta();
                        boba.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, ench, false);
                        aboba.setItemMeta(boba);
                        ent.getEquipment().setChestplate(aboba);
                        ent.getEquipment().setLeggings(new ItemStack(Material.IRON_LEGGINGS));
                    } else if (rand > 50) {
                        ItemStack aboba = new ItemStack(Material.CHAINMAIL_CHESTPLATE);
                        ItemMeta boba = aboba.getItemMeta();
                        boba.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, ench, false);
                        aboba.setItemMeta(boba);
                        ent.getEquipment().setChestplate(aboba);
                        ent.getEquipment().setLeggings(new ItemStack(Material.CHAINMAIL_LEGGINGS));
                    } else if (rand > 40) {
                        ItemStack aboba = new ItemStack(Material.DIAMOND_CHESTPLATE);
                        ItemMeta boba = aboba.getItemMeta();
                        boba.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, ench, false);
                        aboba.setItemMeta(boba);
                        ent.getEquipment().setChestplate(aboba);
                        ent.getEquipment().setLeggings(new ItemStack(Material.DIAMOND_LEGGINGS));
                    } else if (rand > 30) {
                        ItemStack aboba = new ItemStack(Material.NETHERITE_CHESTPLATE);
                        ItemMeta boba = aboba.getItemMeta();
                        boba.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, ench, false);
                        aboba.setItemMeta(boba);
                        ent.getEquipment().setChestplate(aboba);
                    } else {
                        ItemStack aboba = new ItemStack(Material.NETHERITE_CHESTPLATE);
                        ItemMeta boba = aboba.getItemMeta();
                        boba.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, ench, false);
                        aboba.setItemMeta(boba);
                        ent.getEquipment().setChestplate(aboba);
                        ent.getEquipment().setLeggings(new ItemStack(Material.NETHERITE_LEGGINGS));
                    }

                    rand = random.ints(1, 100).findFirst().getAsInt();
                    if (rand > 60) {
                        ent.getEquipment().setItemInMainHand(new ItemStack(Material.STONE_SWORD));
                    } else if (rand > 40) {
                        ent.getEquipment().setItemInMainHand(new ItemStack(Material.STONE_AXE));
                    }

                    rand = random.ints(1, 100).findFirst().getAsInt();
                    if (rand > 30) {
                        ent.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 999999, 1));
                    }
                } else if (rand > 50) {
                    LivingEntity ent = (LivingEntity) event.getEntity().getWorld().spawnEntity(coords, EntityType.CREEPER);
                    rand = random.ints(1, 100).findFirst().getAsInt();
                    if (rand > 50) {
                        ent.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 999999, 1));
                    }
                } else if (rand > 45) {
                    event.getEntity().getWorld().spawnEntity(coords, EntityType.WITCH);
                } else if (rand > 35) {
                    LivingEntity ent = (LivingEntity) event.getEntity().getWorld().spawnEntity(coords, EntityType.HUSK);

                    rand = random.ints(1, 100).findFirst().getAsInt();
                    if (rand > 30) {
                        ent.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 999999, 1));
                    }
                } else if (rand > 30) {
                    LivingEntity ent = (LivingEntity) event.getEntity().getWorld().spawnEntity(coords, EntityType.STRAY);
                    rand = random.ints(1, 100).findFirst().getAsInt();
                    if (rand > 40) {
                        ent.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 999999, 1));
                    }
                } else if (rand > 20) {
                    event.getEntity().getWorld().spawnEntity(coords, EntityType.EVOKER);
                } else if (rand > 15) {
                    LivingEntity ent = (LivingEntity) event.getEntity().getWorld().spawnEntity(coords, EntityType.WITHER_SKELETON);
                    ent.getEquipment().setHelmet(plugin.pumpkin());
                    ent.getEquipment().setHelmetDropChance(80.0F);
                    ent.getEquipment().setChestplateDropChance(50.0F);
                    ent.getEquipment().setLeggingsDropChance(50.0F);
                    ent.getEquipment().setBootsDropChance(50.0F);
                    ent.getEquipment().setItemInMainHandDropChance(50.0F);
                    ent.setCanPickupItems(true);
                    rand = random.ints(1, 100).findFirst().getAsInt();
                    int ench = random.ints(0, 4).findFirst().getAsInt();
                    if (rand > 80) {
                        ent.getEquipment().setChestplate(new ItemStack(Material.LEATHER_CHESTPLATE));
                        ent.getEquipment().setLeggings(new ItemStack(Material.LEATHER_LEGGINGS));
                    } else if (rand > 75) {
                        ItemStack aboba = new ItemStack(Material.LEATHER_CHESTPLATE);
                        ItemMeta boba = aboba.getItemMeta();
                        boba.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, ench, false);
                        aboba.setItemMeta(boba);
                        ent.getEquipment().setChestplate(aboba);
                        ent.getEquipment().setLeggings(new ItemStack(Material.LEATHER_LEGGINGS));
                        ent.getEquipment().setBoots(new ItemStack(Material.LEATHER_BOOTS));
                    } else if (rand > 65) {
                        ItemStack aboba = new ItemStack(Material.IRON_CHESTPLATE);
                        ItemMeta boba = aboba.getItemMeta();
                        boba.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, ench, false);
                        aboba.setItemMeta(boba);
                        ent.getEquipment().setChestplate(aboba);
                        ent.getEquipment().setLeggings(new ItemStack(Material.IRON_LEGGINGS));
                    } else if (rand > 50) {
                        ItemStack aboba = new ItemStack(Material.CHAINMAIL_CHESTPLATE);
                        ItemMeta boba = aboba.getItemMeta();
                        boba.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, ench, false);
                        aboba.setItemMeta(boba);
                        ent.getEquipment().setChestplate(aboba);
                        ent.getEquipment().setLeggings(new ItemStack(Material.CHAINMAIL_LEGGINGS));
                    } else if (rand > 40) {
                        ItemStack aboba = new ItemStack(Material.DIAMOND_CHESTPLATE);
                        ItemMeta boba = aboba.getItemMeta();
                        boba.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, ench, false);
                        aboba.setItemMeta(boba);
                        ent.getEquipment().setChestplate(aboba);
                        ent.getEquipment().setLeggings(new ItemStack(Material.DIAMOND_LEGGINGS));
                    } else if (rand > 30) {
                        ItemStack aboba = new ItemStack(Material.NETHERITE_CHESTPLATE);
                        ItemMeta boba = aboba.getItemMeta();
                        boba.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, ench, false);
                        aboba.setItemMeta(boba);
                        ent.getEquipment().setChestplate(aboba);
                    } else {
                        ItemStack aboba = new ItemStack(Material.NETHERITE_CHESTPLATE);
                        ItemMeta boba = aboba.getItemMeta();
                        boba.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, ench, false);
                        aboba.setItemMeta(boba);
                        ent.getEquipment().setChestplate(aboba);
                        ent.getEquipment().setLeggings(new ItemStack(Material.NETHERITE_LEGGINGS));
                    }

                    rand = random.ints(1, 100).findFirst().getAsInt();
                    if (rand > 80) {
                        ent.getEquipment().setItemInMainHand(new ItemStack(Material.STONE_SWORD));
                    } else if (rand > 75) {
                        ent.getEquipment().setItemInMainHand(new ItemStack(Material.STONE_AXE));
                    } else if (rand > 70) {
                        ent.getEquipment().setItemInMainHand(new ItemStack(Material.IRON_SWORD));
                    } else if (rand > 60) {
                        ent.getEquipment().setItemInMainHand(new ItemStack(Material.IRON_AXE));
                    } else if (rand > 55) {
                        ent.getEquipment().setItemInMainHand(new ItemStack(Material.GOLDEN_SWORD));
                    } else if (rand > 45) {
                        ent.getEquipment().setItemInMainHand(new ItemStack(Material.GOLDEN_AXE));
                    } else if (rand > 30) {
                        ent.getEquipment().setItemInMainHand(new ItemStack(Material.DIAMOND_SWORD));
                    } else if (rand > 25) {
                        ent.getEquipment().setItemInMainHand(new ItemStack(Material.DIAMOND_AXE));
                    } else if (rand > 20) {
                        ent.getEquipment().setItemInMainHand(new ItemStack(Material.NETHERITE_SWORD));
                    } else {
                        ent.getEquipment().setItemInMainHand(new ItemStack(Material.NETHERITE_AXE));
                    }

                    rand = random.ints(1, 100).findFirst().getAsInt();
                    if (rand > 50) {
                        ent.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 999999, 1));
                    }
                } else if (rand < 2) {
                    LivingEntity ent = (LivingEntity) event.getEntity().getWorld().spawnEntity(coords, EntityType.RAVAGER);
                    ent.setMaxHealth(1000.0F);
                    ent.setHealth(1000.0F);
                }
            } else if (event.getSpawnReason().toString().equals("CUSTOM")) {} else {
                event.setCancelled(true);
            }
        }
    }

    private int countmobs(World world) {
        int mobcount = 0;
        List<LivingEntity> entities = world.getLivingEntities();
        for (LivingEntity ent : entities) {
            if (ent.getType().equals(EntityType.ZOMBIE)) mobcount++;
            else if (ent.getType().equals(EntityType.CREEPER)) mobcount++;
            else if (ent.getType().equals(EntityType.CAVE_SPIDER)) mobcount++;
            else if (ent.getType().equals(EntityType.SPIDER)) mobcount++;
            else if (ent.getType().equals(EntityType.SKELETON)) mobcount++;
            else if (ent.getType().equals(EntityType.WITCH)) mobcount++;
            else if (ent.getType().equals(EntityType.HUSK)) mobcount++;
            else if (ent.getType().equals(EntityType.WITHER_SKELETON)) mobcount++;
            else if (ent.getType().equals(EntityType.STRAY)) mobcount++;
            else if (ent.getType().equals(EntityType.EVOKER)) mobcount++;
            else if (ent.getType().equals(EntityType.RAVAGER)) mobcount++;
        }
        return mobcount;
    }
}
