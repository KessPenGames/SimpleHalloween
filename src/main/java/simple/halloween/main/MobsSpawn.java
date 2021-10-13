package simple.halloween.main;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.inventory.ItemStack;
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
                LivingEntity entity = event.getEntity();
                if (event.getEntityType().toString().equals(plugin.mob_wl.get(0))) {
                    entity.setCanPickupItems(true);
                    entity.setMaxHealth(100.0F);
                    entity.setHealth(health + .0F);
                    entity.getEquipment().setHelmet(plugin.pumpkin());
                    if (rand < 11) {
                        entity.getEquipment().setChestplate(new ItemStack(Material.NETHERITE_CHESTPLATE));
                        entity.getEquipment().setLeggings(new ItemStack(Material.NETHERITE_LEGGINGS));
                        entity.getEquipment().setBoots(new ItemStack(Material.NETHERITE_BOOTS));
                        entity.getEquipment().setItemInMainHand(new ItemStack(Material.NETHERITE_AXE));
                        entity.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 9999999, 2));
                        entity.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 9999999, 6));
                    } else if (rand < 16) {
                        entity.getEquipment().setChestplate(new ItemStack(Material.NETHERITE_CHESTPLATE));
                        entity.getEquipment().setLeggings(new ItemStack(Material.NETHERITE_LEGGINGS));
                        entity.getEquipment().setBoots(new ItemStack(Material.NETHERITE_BOOTS));
                    } else if (rand < 31) {
                        entity.getEquipment().setChestplate(new ItemStack(Material.DIAMOND_CHESTPLATE));
                        entity.getEquipment().setLeggings(new ItemStack(Material.DIAMOND_LEGGINGS));
                        entity.getEquipment().setBoots(new ItemStack(Material.DIAMOND_BOOTS));
                        entity.getEquipment().setItemInMainHand(new ItemStack(Material.DIAMOND_SWORD));
                        entity.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 9999999, 2));
                        entity.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 9999999, 5));
                    } else if (rand < 36) {
                        entity.getEquipment().setChestplate(new ItemStack(Material.DIAMOND_CHESTPLATE));
                        entity.getEquipment().setLeggings(new ItemStack(Material.DIAMOND_LEGGINGS));
                        entity.getEquipment().setBoots(new ItemStack(Material.DIAMOND_BOOTS));
                    } else if (rand < 51) {
                        entity.getEquipment().setChestplate(new ItemStack(Material.IRON_CHESTPLATE));
                        entity.getEquipment().setLeggings(new ItemStack(Material.IRON_LEGGINGS));
                        entity.getEquipment().setBoots(new ItemStack(Material.IRON_BOOTS));
                        entity.getEquipment().setItemInMainHand(new ItemStack(Material.IRON_SHOVEL));
                        entity.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 9999999, 4));
                    } else if (rand < 56) {
                        entity.getEquipment().setChestplate(new ItemStack(Material.IRON_CHESTPLATE));
                        entity.getEquipment().setLeggings(new ItemStack(Material.IRON_LEGGINGS));
                        entity.getEquipment().setBoots(new ItemStack(Material.IRON_BOOTS));
                    } else if (rand < 71) {
                        entity.getEquipment().setChestplate(new ItemStack(Material.CHAINMAIL_CHESTPLATE));
                        entity.getEquipment().setLeggings(new ItemStack(Material.CHAINMAIL_LEGGINGS));
                        entity.getEquipment().setBoots(new ItemStack(Material.CHAINMAIL_BOOTS));
                        entity.getEquipment().setItemInMainHand(new ItemStack(Material.GOLDEN_HOE));
                        entity.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 9999999, 3));
                    } else if (rand < 76) {
                        entity.getEquipment().setChestplate(new ItemStack(Material.CHAINMAIL_CHESTPLATE));
                        entity.getEquipment().setLeggings(new ItemStack(Material.CHAINMAIL_LEGGINGS));
                        entity.getEquipment().setBoots(new ItemStack(Material.CHAINMAIL_BOOTS));
                    }
                    entity.getEquipment().setHelmetDropChance(90.0F);
                    entity.getEquipment().setChestplateDropChance(0.0F);
                    entity.getEquipment().setLeggingsDropChance(0.0F);
                    entity.getEquipment().setBootsDropChance(0.0F);
                    entity.getEquipment().setItemInMainHandDropChance(0.0F);
                    for (Player other : Bukkit.getOnlinePlayers()) {
                        if (other.getLocation().distance(entity.getLocation()) <= 50) {
                            entity.attack(other);
                        }
                    }
                } else if (event.getEntityType().toString().equals(plugin.mob_wl.get(1))) {
                    entity.setCanPickupItems(true);
                    entity.setMaxHealth(100.0F);
                    entity.setHealth(health + .0F);
                    entity.getEquipment().setHelmet(plugin.pumpkin());
                    if (rand < 11) {
                        entity.getEquipment().setChestplate(new ItemStack(Material.NETHERITE_CHESTPLATE));
                        entity.getEquipment().setLeggings(new ItemStack(Material.NETHERITE_LEGGINGS));
                        entity.getEquipment().setBoots(new ItemStack(Material.NETHERITE_BOOTS));
                        entity.getEquipment().setItemInMainHand(new ItemStack(Material.NETHERITE_AXE));
                        entity.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 9999999, 2));
                        entity.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 9999999, 6));
                    } else if (rand < 16) {
                        entity.getEquipment().setChestplate(new ItemStack(Material.NETHERITE_CHESTPLATE));
                        entity.getEquipment().setLeggings(new ItemStack(Material.NETHERITE_LEGGINGS));
                        entity.getEquipment().setBoots(new ItemStack(Material.NETHERITE_BOOTS));
                    } else if (rand < 31) {
                        entity.getEquipment().setChestplate(new ItemStack(Material.DIAMOND_CHESTPLATE));
                        entity.getEquipment().setLeggings(new ItemStack(Material.DIAMOND_LEGGINGS));
                        entity.getEquipment().setBoots(new ItemStack(Material.DIAMOND_BOOTS));
                        entity.getEquipment().setItemInMainHand(new ItemStack(Material.DIAMOND_AXE));
                        entity.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 9999999, 1));
                        entity.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 9999999, 6));
                    } else if (rand < 36) {
                        entity.getEquipment().setChestplate(new ItemStack(Material.DIAMOND_CHESTPLATE));
                        entity.getEquipment().setLeggings(new ItemStack(Material.DIAMOND_LEGGINGS));
                        entity.getEquipment().setBoots(new ItemStack(Material.DIAMOND_BOOTS));
                    } else if (rand < 51) {
                        entity.getEquipment().setChestplate(new ItemStack(Material.IRON_CHESTPLATE));
                        entity.getEquipment().setLeggings(new ItemStack(Material.IRON_LEGGINGS));
                        entity.getEquipment().setBoots(new ItemStack(Material.IRON_BOOTS));
                        entity.getEquipment().setItemInMainHand(new ItemStack(Material.IRON_AXE));
                        entity.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 9999999, 1));
                        entity.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 9999999, 5));
                    } else if (rand < 56) {
                        entity.getEquipment().setChestplate(new ItemStack(Material.IRON_CHESTPLATE));
                        entity.getEquipment().setLeggings(new ItemStack(Material.IRON_LEGGINGS));
                        entity.getEquipment().setBoots(new ItemStack(Material.IRON_BOOTS));
                    } else if (rand < 71) {
                        entity.getEquipment().setChestplate(new ItemStack(Material.CHAINMAIL_CHESTPLATE));
                        entity.getEquipment().setLeggings(new ItemStack(Material.CHAINMAIL_LEGGINGS));
                        entity.getEquipment().setBoots(new ItemStack(Material.CHAINMAIL_BOOTS));
                        entity.getEquipment().setItemInMainHand(new ItemStack(Material.GOLDEN_AXE));
                        entity.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 9999999, 1));
                        entity.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 9999999, 5));
                    } else if (rand < 76) {
                        entity.getEquipment().setChestplate(new ItemStack(Material.CHAINMAIL_CHESTPLATE));
                        entity.getEquipment().setLeggings(new ItemStack(Material.CHAINMAIL_LEGGINGS));
                        entity.getEquipment().setBoots(new ItemStack(Material.CHAINMAIL_BOOTS));
                    }
                    entity.getEquipment().setHelmetDropChance(90.0F);
                    entity.getEquipment().setChestplateDropChance(0.0F);
                    entity.getEquipment().setLeggingsDropChance(0.0F);
                    entity.getEquipment().setBootsDropChance(0.0F);
                    entity.getEquipment().setItemInMainHandDropChance(0.0F);
                    for (Player other : Bukkit.getOnlinePlayers()) {
                        if (other.getLocation().distance(entity.getLocation()) <= 50) {
                            entity.attack(other);
                        }
                    }
                } else if (event.getEntityType().toString().equals(plugin.mob_wl.get(2))) {
                    entity.setCanPickupItems(true);
                    entity.setMaxHealth(100.0F);
                    entity.setHealth(health + .0F);
                    entity.getEquipment().setHelmet(plugin.pumpkin());
                    if (rand < 51) {
                        entity.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 9999999, 1));
                    }
                    for (Player other : Bukkit.getOnlinePlayers()) {
                        if (other.getLocation().distance(entity.getLocation()) <= 50) {
                            entity.attack(other);
                        }
                    }
                } else if (event.getEntityType().toString().equals(plugin.mob_wl.get(3))) {
                    entity.setCanPickupItems(true);
                    entity.setMaxHealth(100.0F);
                    entity.setHealth(health + .0F);
                    entity.getEquipment().setHelmet(plugin.pumpkin());
                    if (rand < 11) {
                        entity.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 9999999, 4));
                        entity.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 9999999, 1));
                        entity.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, 9999999, 1));
                        entity.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 9999999, 8));
                    } else if (rand < 21) {
                        entity.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 9999999, 3));
                        entity.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 9999999, 1));
                        entity.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 9999999, 8));
                    } else if (rand < 31) {
                        entity.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 9999999, 3));
                        entity.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 9999999, 1));
                        entity.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 9999999, 6));
                    } else if (rand < 51) {
                        entity.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 9999999, 2));
                        entity.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 9999999, 1));
                        entity.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 9999999, 6));
                    } else if (rand < 71) {
                        entity.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 9999999, 2));
                        entity.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 9999999, 1));
                        entity.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 9999999, 4));
                    } else if (rand < 81) {
                        entity.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 9999999, 1));
                        entity.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 9999999, 4));
                    }

                    for (Player other : Bukkit.getOnlinePlayers()) {
                        if (other.getLocation().distance(entity.getLocation()) <= 10) {
                            entity.attack(other);
                        }
                    }
                } else {
                    event.setCancelled(true);
                }
            } else if (event.getSpawnReason().toString().equals("CUSTOM")) {} else {
                Random random = new Random();
                int health = random.ints(20, 100).findFirst().getAsInt();
                int rand = random.ints(1, 100).findFirst().getAsInt();
                LivingEntity entity = event.getEntity();
                if (event.getEntityType().toString().equals(plugin.mob_wl.get(0))) {
                    entity.setCanPickupItems(true);
                    entity.setMaxHealth(100.0F);
                    entity.setHealth(health + .0F);
                    entity.getEquipment().setHelmet(plugin.pumpkin());
                    if (rand < 11) {
                        entity.getEquipment().setChestplate(new ItemStack(Material.NETHERITE_CHESTPLATE));
                        entity.getEquipment().setLeggings(new ItemStack(Material.NETHERITE_LEGGINGS));
                        entity.getEquipment().setBoots(new ItemStack(Material.NETHERITE_BOOTS));
                        entity.getEquipment().setItemInMainHand(new ItemStack(Material.NETHERITE_AXE));
                        entity.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 9999999, 2));
                        entity.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 9999999, 6));
                    } else if (rand < 16) {
                        entity.getEquipment().setChestplate(new ItemStack(Material.NETHERITE_CHESTPLATE));
                        entity.getEquipment().setLeggings(new ItemStack(Material.NETHERITE_LEGGINGS));
                        entity.getEquipment().setBoots(new ItemStack(Material.NETHERITE_BOOTS));
                    } else if (rand < 31) {
                        entity.getEquipment().setChestplate(new ItemStack(Material.DIAMOND_CHESTPLATE));
                        entity.getEquipment().setLeggings(new ItemStack(Material.DIAMOND_LEGGINGS));
                        entity.getEquipment().setBoots(new ItemStack(Material.DIAMOND_BOOTS));
                        entity.getEquipment().setItemInMainHand(new ItemStack(Material.DIAMOND_SWORD));
                        entity.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 9999999, 2));
                        entity.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 9999999, 5));
                    } else if (rand < 36) {
                        entity.getEquipment().setChestplate(new ItemStack(Material.DIAMOND_CHESTPLATE));
                        entity.getEquipment().setLeggings(new ItemStack(Material.DIAMOND_LEGGINGS));
                        entity.getEquipment().setBoots(new ItemStack(Material.DIAMOND_BOOTS));
                    } else if (rand < 51) {
                        entity.getEquipment().setChestplate(new ItemStack(Material.IRON_CHESTPLATE));
                        entity.getEquipment().setLeggings(new ItemStack(Material.IRON_LEGGINGS));
                        entity.getEquipment().setBoots(new ItemStack(Material.IRON_BOOTS));
                        entity.getEquipment().setItemInMainHand(new ItemStack(Material.IRON_SHOVEL));
                        entity.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 9999999, 4));
                    } else if (rand < 56) {
                        entity.getEquipment().setChestplate(new ItemStack(Material.IRON_CHESTPLATE));
                        entity.getEquipment().setLeggings(new ItemStack(Material.IRON_LEGGINGS));
                        entity.getEquipment().setBoots(new ItemStack(Material.IRON_BOOTS));
                    } else if (rand < 71) {
                        entity.getEquipment().setChestplate(new ItemStack(Material.CHAINMAIL_CHESTPLATE));
                        entity.getEquipment().setLeggings(new ItemStack(Material.CHAINMAIL_LEGGINGS));
                        entity.getEquipment().setBoots(new ItemStack(Material.CHAINMAIL_BOOTS));
                        entity.getEquipment().setItemInMainHand(new ItemStack(Material.GOLDEN_HOE));
                        entity.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 9999999, 3));
                    } else if (rand < 76) {
                        entity.getEquipment().setChestplate(new ItemStack(Material.CHAINMAIL_CHESTPLATE));
                        entity.getEquipment().setLeggings(new ItemStack(Material.CHAINMAIL_LEGGINGS));
                        entity.getEquipment().setBoots(new ItemStack(Material.CHAINMAIL_BOOTS));
                    }
                    entity.getEquipment().setHelmetDropChance(90.0F);
                    entity.getEquipment().setChestplateDropChance(0.0F);
                    entity.getEquipment().setLeggingsDropChance(0.0F);
                    entity.getEquipment().setBootsDropChance(0.0F);
                    entity.getEquipment().setItemInMainHandDropChance(0.0F);
                    for (Player other : Bukkit.getOnlinePlayers()) {
                        if (other.getLocation().distance(entity.getLocation()) <= 50) {
                            entity.attack(other);
                        }
                    }
                } else if (event.getEntityType().toString().equals(plugin.mob_wl.get(1))) {
                    entity.setCanPickupItems(true);
                    entity.setMaxHealth(100.0F);
                    entity.setHealth(health + .0F);
                    entity.getEquipment().setHelmet(plugin.pumpkin());
                    if (rand < 11) {
                        entity.getEquipment().setChestplate(new ItemStack(Material.NETHERITE_CHESTPLATE));
                        entity.getEquipment().setLeggings(new ItemStack(Material.NETHERITE_LEGGINGS));
                        entity.getEquipment().setBoots(new ItemStack(Material.NETHERITE_BOOTS));
                        entity.getEquipment().setItemInMainHand(new ItemStack(Material.NETHERITE_AXE));
                        entity.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 9999999, 2));
                        entity.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 9999999, 6));
                    } else if (rand < 16) {
                        entity.getEquipment().setChestplate(new ItemStack(Material.NETHERITE_CHESTPLATE));
                        entity.getEquipment().setLeggings(new ItemStack(Material.NETHERITE_LEGGINGS));
                        entity.getEquipment().setBoots(new ItemStack(Material.NETHERITE_BOOTS));
                    } else if (rand < 31) {
                        entity.getEquipment().setChestplate(new ItemStack(Material.DIAMOND_CHESTPLATE));
                        entity.getEquipment().setLeggings(new ItemStack(Material.DIAMOND_LEGGINGS));
                        entity.getEquipment().setBoots(new ItemStack(Material.DIAMOND_BOOTS));
                        entity.getEquipment().setItemInMainHand(new ItemStack(Material.DIAMOND_AXE));
                        entity.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 9999999, 1));
                        entity.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 9999999, 6));
                    } else if (rand < 36) {
                        entity.getEquipment().setChestplate(new ItemStack(Material.DIAMOND_CHESTPLATE));
                        entity.getEquipment().setLeggings(new ItemStack(Material.DIAMOND_LEGGINGS));
                        entity.getEquipment().setBoots(new ItemStack(Material.DIAMOND_BOOTS));
                    } else if (rand < 51) {
                        entity.getEquipment().setChestplate(new ItemStack(Material.IRON_CHESTPLATE));
                        entity.getEquipment().setLeggings(new ItemStack(Material.IRON_LEGGINGS));
                        entity.getEquipment().setBoots(new ItemStack(Material.IRON_BOOTS));
                        entity.getEquipment().setItemInMainHand(new ItemStack(Material.IRON_AXE));
                        entity.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 9999999, 1));
                        entity.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 9999999, 5));
                    } else if (rand < 56) {
                        entity.getEquipment().setChestplate(new ItemStack(Material.IRON_CHESTPLATE));
                        entity.getEquipment().setLeggings(new ItemStack(Material.IRON_LEGGINGS));
                        entity.getEquipment().setBoots(new ItemStack(Material.IRON_BOOTS));
                    } else if (rand < 71) {
                        entity.getEquipment().setChestplate(new ItemStack(Material.CHAINMAIL_CHESTPLATE));
                        entity.getEquipment().setLeggings(new ItemStack(Material.CHAINMAIL_LEGGINGS));
                        entity.getEquipment().setBoots(new ItemStack(Material.CHAINMAIL_BOOTS));
                        entity.getEquipment().setItemInMainHand(new ItemStack(Material.GOLDEN_AXE));
                        entity.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 9999999, 1));
                        entity.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 9999999, 5));
                    } else if (rand < 76) {
                        entity.getEquipment().setChestplate(new ItemStack(Material.CHAINMAIL_CHESTPLATE));
                        entity.getEquipment().setLeggings(new ItemStack(Material.CHAINMAIL_LEGGINGS));
                        entity.getEquipment().setBoots(new ItemStack(Material.CHAINMAIL_BOOTS));
                    }
                    entity.getEquipment().setHelmetDropChance(90.0F);
                    entity.getEquipment().setChestplateDropChance(0.0F);
                    entity.getEquipment().setLeggingsDropChance(0.0F);
                    entity.getEquipment().setBootsDropChance(0.0F);
                    entity.getEquipment().setItemInMainHandDropChance(0.0F);
                    for (Player other : Bukkit.getOnlinePlayers()) {
                        if (other.getLocation().distance(entity.getLocation()) <= 50) {
                            entity.attack(other);
                        }
                    }
                } else if (event.getEntityType().toString().equals(plugin.mob_wl.get(2))) {
                    entity.setCanPickupItems(true);
                    entity.setMaxHealth(100.0F);
                    entity.setHealth(health + .0F);
                    entity.getEquipment().setHelmet(plugin.pumpkin());
                    if (rand < 51) {
                        entity.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 9999999, 1));
                    }
                    for (Player other : Bukkit.getOnlinePlayers()) {
                        if (other.getLocation().distance(entity.getLocation()) <= 50) {
                            entity.attack(other);
                        }
                    }
                } else if (event.getEntityType().toString().equals(plugin.mob_wl.get(3))) {
                    entity.setCanPickupItems(true);
                    entity.setMaxHealth(100.0F);
                    entity.setHealth(health + .0F);
                    entity.getEquipment().setHelmet(plugin.pumpkin());
                    if (rand < 11) {
                        entity.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 9999999, 4));
                        entity.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 9999999, 1));
                        entity.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, 9999999, 1));
                        entity.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 9999999, 8));
                    } else if (rand < 21) {
                        entity.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 9999999, 3));
                        entity.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 9999999, 1));
                        entity.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 9999999, 8));
                    } else if (rand < 31) {
                        entity.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 9999999, 3));
                        entity.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 9999999, 1));
                        entity.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 9999999, 6));
                    } else if (rand < 51) {
                        entity.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 9999999, 2));
                        entity.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 9999999, 1));
                        entity.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 9999999, 6));
                    } else if (rand < 71) {
                        entity.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 9999999, 2));
                        entity.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 9999999, 1));
                        entity.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 9999999, 4));
                    } else if (rand < 81) {
                        entity.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 9999999, 1));
                        entity.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 9999999, 4));
                    }

                    for (Player other : Bukkit.getOnlinePlayers()) {
                        if (other.getLocation().distance(entity.getLocation()) <= 10) {
                            entity.attack(other);
                        }
                    }
                } else {
                    event.setCancelled(true);
                }
            }
        }
    }
}
