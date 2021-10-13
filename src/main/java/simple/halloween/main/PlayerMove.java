package simple.halloween.main;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class PlayerMove implements Listener {
    private Main plugin;
    public PlayerMove(Main plugin) {
        this.plugin = plugin;
    }
    public HashMap<String, Long> cooldowns = new HashMap<String, Long>();
    private int prosto = 0;

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        if (event.getPlayer().getGameMode().toString().equals("SURVIVAL")) {
            File logs_stats = new File(plugin.getDataFolder() + File.separator + "halloween.yml");
            FileConfiguration logs = YamlConfiguration.loadConfiguration(logs_stats);
            List<String> list = logs.getStringList("halloween");
            if (list.isEmpty()) return;
            if (list.get(0).equals("true")) {
                String world_name = event.getPlayer().getWorld().getName();
                if (!world_name.equals("world")) event.getPlayer().setHealth(0.0D);
                Location coords = event.getPlayer().getLocation();
                if (coords.getY() < 57) event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 300, 2));
                if (coords.getY() > 89) event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 300, 2));
                if (coords.getWorld().getBlockAt(coords).getType() == Material.WATER) event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 200, 1));
                if (coords.getWorld().getBlockAt((int) coords.getX(), (int) coords.getY() - 1, (int) coords.getZ()).getType() == Material.WATER) event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 200, 1));

                int cooldownTime = 25;
                if(cooldowns.containsKey(event.getPlayer().getName())) {
                    long secondsLeft = ((cooldowns.get(event.getPlayer().getName())/1000)+cooldownTime) - (System.currentTimeMillis()/1000);
                    if(secondsLeft>0) {
                        return;
                    }
                }
                cooldowns.put(event.getPlayer().getName(), System.currentTimeMillis());

                Player player = event.getPlayer();
                File players = new File(plugin.getDataFolder() + File.separator + "players.yml");
                FileConfiguration p_logs = YamlConfiguration.loadConfiguration(players);
                List<String> p_list = p_logs.getStringList(event.getPlayer().getName());
                if (p_list.isEmpty()) return;
                String coord = p_list.get(1);
                String[] coordi = coord.split("Coords: ");
                coordi = coordi[1].split(" ");
                String[] xx = coordi[0].split("\\.");
                String[] zz = coordi[2].split("\\.");
                int x = Integer.parseInt(xx[0]);
                int z = Integer.parseInt(zz[0]);
                if (coords.getX() < x + 10) {
                    Random random = new Random();
                    int health = random.ints(20, 100).findFirst().getAsInt();
                    int rand = random.ints(1, 100).findFirst().getAsInt();
                    int spawn = random.ints(1, 100).findFirst().getAsInt();
                    World za_warudo = player.getWorld();

                    if (spawn < 6) {
                        LivingEntity entity = (LivingEntity) za_warudo.spawnEntity(player.getLocation(), EntityType.CREEPER);
                        entity.setCanPickupItems(true);
                        entity.setMaxHealth(100.0F);
                        entity.setHealth(health + .0F);
                        entity.getEquipment().setHelmet(plugin.pumpkin());
                        if (rand < 71) {
                            entity.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 9999999, 1));
                        }
                        for (Player other : Bukkit.getOnlinePlayers()) {
                            if (other.getLocation().distance(entity.getLocation()) <= 50) {
                                entity.attack(other);
                            }
                        }
                    } else if (spawn < 16) {
                        for (int i = 1; 3 > i; i++) {
                            LivingEntity entity = (LivingEntity) za_warudo.spawnEntity(player.getLocation(), EntityType.SKELETON);
                            entity.setCanPickupItems(true);
                            entity.setMaxHealth(100.0F);
                            entity.setHealth(health + .0F);
                            entity.getEquipment().setHelmet(plugin.pumpkin());
                            if (rand < 31) {
                                entity.getEquipment().setChestplate(new ItemStack(Material.NETHERITE_CHESTPLATE));
                                entity.getEquipment().setLeggings(new ItemStack(Material.NETHERITE_LEGGINGS));
                                entity.getEquipment().setBoots(new ItemStack(Material.NETHERITE_BOOTS));
                                entity.getEquipment().setItemInMainHand(new ItemStack(Material.NETHERITE_AXE));
                                entity.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 9999999, 2));
                                entity.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 9999999, 6));
                            } else if (rand < 36) {
                                entity.getEquipment().setChestplate(new ItemStack(Material.NETHERITE_CHESTPLATE));
                                entity.getEquipment().setLeggings(new ItemStack(Material.NETHERITE_LEGGINGS));
                                entity.getEquipment().setBoots(new ItemStack(Material.NETHERITE_BOOTS));
                            } else if (rand < 51) {
                                entity.getEquipment().setChestplate(new ItemStack(Material.DIAMOND_CHESTPLATE));
                                entity.getEquipment().setLeggings(new ItemStack(Material.DIAMOND_LEGGINGS));
                                entity.getEquipment().setBoots(new ItemStack(Material.DIAMOND_BOOTS));
                                entity.getEquipment().setItemInMainHand(new ItemStack(Material.DIAMOND_AXE));
                                entity.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 9999999, 1));
                                entity.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 9999999, 6));
                            } else if (rand < 56) {
                                entity.getEquipment().setChestplate(new ItemStack(Material.DIAMOND_CHESTPLATE));
                                entity.getEquipment().setLeggings(new ItemStack(Material.DIAMOND_LEGGINGS));
                                entity.getEquipment().setBoots(new ItemStack(Material.DIAMOND_BOOTS));
                            } else if (rand < 61) {
                                entity.getEquipment().setChestplate(new ItemStack(Material.IRON_CHESTPLATE));
                                entity.getEquipment().setLeggings(new ItemStack(Material.IRON_LEGGINGS));
                                entity.getEquipment().setBoots(new ItemStack(Material.IRON_BOOTS));
                                entity.getEquipment().setItemInMainHand(new ItemStack(Material.IRON_AXE));
                                entity.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 9999999, 1));
                                entity.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 9999999, 5));
                            } else if (rand < 66) {
                                entity.getEquipment().setChestplate(new ItemStack(Material.IRON_CHESTPLATE));
                                entity.getEquipment().setLeggings(new ItemStack(Material.IRON_LEGGINGS));
                                entity.getEquipment().setBoots(new ItemStack(Material.IRON_BOOTS));
                            } else if (rand < 81) {
                                entity.getEquipment().setChestplate(new ItemStack(Material.CHAINMAIL_CHESTPLATE));
                                entity.getEquipment().setLeggings(new ItemStack(Material.CHAINMAIL_LEGGINGS));
                                entity.getEquipment().setBoots(new ItemStack(Material.CHAINMAIL_BOOTS));
                                entity.getEquipment().setItemInMainHand(new ItemStack(Material.GOLDEN_AXE));
                                entity.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 9999999, 1));
                                entity.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 9999999, 5));
                            } else if (rand < 86) {
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
                        }
                    } else if (spawn < 21) {
                        LivingEntity entity = (LivingEntity) za_warudo.spawnEntity(player.getLocation(), EntityType.SKELETON);
                        entity.setCanPickupItems(true);
                        entity.setMaxHealth(100.0F);
                        entity.setHealth(health + .0F);
                        entity.getEquipment().setHelmet(plugin.pumpkin());
                        if (rand < 31) {
                            entity.getEquipment().setChestplate(new ItemStack(Material.NETHERITE_CHESTPLATE));
                            entity.getEquipment().setLeggings(new ItemStack(Material.NETHERITE_LEGGINGS));
                            entity.getEquipment().setBoots(new ItemStack(Material.NETHERITE_BOOTS));
                            entity.getEquipment().setItemInMainHand(new ItemStack(Material.NETHERITE_AXE));
                            entity.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 9999999, 2));
                            entity.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 9999999, 6));
                        } else if (rand < 36) {
                            entity.getEquipment().setChestplate(new ItemStack(Material.NETHERITE_CHESTPLATE));
                            entity.getEquipment().setLeggings(new ItemStack(Material.NETHERITE_LEGGINGS));
                            entity.getEquipment().setBoots(new ItemStack(Material.NETHERITE_BOOTS));
                        } else if (rand < 51) {
                            entity.getEquipment().setChestplate(new ItemStack(Material.DIAMOND_CHESTPLATE));
                            entity.getEquipment().setLeggings(new ItemStack(Material.DIAMOND_LEGGINGS));
                            entity.getEquipment().setBoots(new ItemStack(Material.DIAMOND_BOOTS));
                            entity.getEquipment().setItemInMainHand(new ItemStack(Material.DIAMOND_AXE));
                            entity.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 9999999, 1));
                            entity.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 9999999, 6));
                        } else if (rand < 56) {
                            entity.getEquipment().setChestplate(new ItemStack(Material.DIAMOND_CHESTPLATE));
                            entity.getEquipment().setLeggings(new ItemStack(Material.DIAMOND_LEGGINGS));
                            entity.getEquipment().setBoots(new ItemStack(Material.DIAMOND_BOOTS));
                        } else if (rand < 61) {
                            entity.getEquipment().setChestplate(new ItemStack(Material.IRON_CHESTPLATE));
                            entity.getEquipment().setLeggings(new ItemStack(Material.IRON_LEGGINGS));
                            entity.getEquipment().setBoots(new ItemStack(Material.IRON_BOOTS));
                            entity.getEquipment().setItemInMainHand(new ItemStack(Material.IRON_AXE));
                            entity.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 9999999, 1));
                            entity.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 9999999, 5));
                        } else if (rand < 66) {
                            entity.getEquipment().setChestplate(new ItemStack(Material.IRON_CHESTPLATE));
                            entity.getEquipment().setLeggings(new ItemStack(Material.IRON_LEGGINGS));
                            entity.getEquipment().setBoots(new ItemStack(Material.IRON_BOOTS));
                        } else if (rand < 81) {
                            entity.getEquipment().setChestplate(new ItemStack(Material.CHAINMAIL_CHESTPLATE));
                            entity.getEquipment().setLeggings(new ItemStack(Material.CHAINMAIL_LEGGINGS));
                            entity.getEquipment().setBoots(new ItemStack(Material.CHAINMAIL_BOOTS));
                            entity.getEquipment().setItemInMainHand(new ItemStack(Material.GOLDEN_AXE));
                            entity.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 9999999, 1));
                            entity.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 9999999, 5));
                        } else if (rand < 86) {
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
                    } else if (spawn == 100) {
                        za_warudo.spawnEntity(player.getLocation(), EntityType.CREEPER);
                        za_warudo.spawnEntity(player.getLocation(), EntityType.CREEPER);
                        za_warudo.spawnEntity(player.getLocation(), EntityType.CREEPER);
                        za_warudo.spawnEntity(player.getLocation(), EntityType.CREEPER);
                        za_warudo.spawnEntity(player.getLocation(), EntityType.CREEPER);
                        za_warudo.spawnEntity(player.getLocation(), EntityType.CREEPER);
                        za_warudo.spawnEntity(player.getLocation(), EntityType.CREEPER);
                        za_warudo.spawnEntity(player.getLocation(), EntityType.CREEPER);
                        za_warudo.spawnEntity(player.getLocation(), EntityType.CREEPER);
                        za_warudo.spawnEntity(player.getLocation(), EntityType.CREEPER);
                    } else {
                        LivingEntity entity = (LivingEntity) za_warudo.spawnEntity(player.getLocation(), EntityType.ZOMBIE);
                        entity.setCanPickupItems(true);
                        entity.setMaxHealth(100.0F);
                        entity.setHealth(health + .0F);
                        entity.getEquipment().setHelmet(plugin.pumpkin());
                        if (rand < 51) {
                            entity.getEquipment().setChestplate(new ItemStack(Material.NETHERITE_CHESTPLATE));
                            entity.getEquipment().setLeggings(new ItemStack(Material.NETHERITE_LEGGINGS));
                            entity.getEquipment().setBoots(new ItemStack(Material.NETHERITE_BOOTS));
                            entity.getEquipment().setItemInMainHand(new ItemStack(Material.NETHERITE_AXE));
                            entity.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 9999999, 2));
                            entity.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 9999999, 6));
                        } else if (rand < 56) {
                            entity.getEquipment().setChestplate(new ItemStack(Material.NETHERITE_CHESTPLATE));
                            entity.getEquipment().setLeggings(new ItemStack(Material.NETHERITE_LEGGINGS));
                            entity.getEquipment().setBoots(new ItemStack(Material.NETHERITE_BOOTS));
                        } else if (rand < 61) {
                            entity.getEquipment().setChestplate(new ItemStack(Material.DIAMOND_CHESTPLATE));
                            entity.getEquipment().setLeggings(new ItemStack(Material.DIAMOND_LEGGINGS));
                            entity.getEquipment().setBoots(new ItemStack(Material.DIAMOND_BOOTS));
                            entity.getEquipment().setItemInMainHand(new ItemStack(Material.DIAMOND_SWORD));
                            entity.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 9999999, 2));
                            entity.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 9999999, 5));
                        } else if (rand < 66) {
                            entity.getEquipment().setChestplate(new ItemStack(Material.DIAMOND_CHESTPLATE));
                            entity.getEquipment().setLeggings(new ItemStack(Material.DIAMOND_LEGGINGS));
                            entity.getEquipment().setBoots(new ItemStack(Material.DIAMOND_BOOTS));
                        } else if (rand < 71) {
                            entity.getEquipment().setChestplate(new ItemStack(Material.IRON_CHESTPLATE));
                            entity.getEquipment().setLeggings(new ItemStack(Material.IRON_LEGGINGS));
                            entity.getEquipment().setBoots(new ItemStack(Material.IRON_BOOTS));
                            entity.getEquipment().setItemInMainHand(new ItemStack(Material.IRON_SHOVEL));
                            entity.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 9999999, 4));
                        } else if (rand < 76) {
                            entity.getEquipment().setChestplate(new ItemStack(Material.IRON_CHESTPLATE));
                            entity.getEquipment().setLeggings(new ItemStack(Material.IRON_LEGGINGS));
                            entity.getEquipment().setBoots(new ItemStack(Material.IRON_BOOTS));
                        } else if (rand < 81) {
                            entity.getEquipment().setChestplate(new ItemStack(Material.CHAINMAIL_CHESTPLATE));
                            entity.getEquipment().setLeggings(new ItemStack(Material.CHAINMAIL_LEGGINGS));
                            entity.getEquipment().setBoots(new ItemStack(Material.CHAINMAIL_BOOTS));
                            entity.getEquipment().setItemInMainHand(new ItemStack(Material.GOLDEN_HOE));
                            entity.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 9999999, 3));
                        } else if (rand < 86) {
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
                    }
                    prosto = 1;
                } else if (coords.getZ() < z + 10) {
                    Random random = new Random();
                    int health = random.ints(20, 100).findFirst().getAsInt();
                    int rand = random.ints(1, 100).findFirst().getAsInt();
                    int spawn = random.ints(1, 100).findFirst().getAsInt();
                    World za_warudo = player.getWorld();

                    if (spawn < 6) {
                        LivingEntity entity = (LivingEntity) za_warudo.spawnEntity(player.getLocation(), EntityType.CREEPER);
                        entity.setCanPickupItems(true);
                        entity.setMaxHealth(100.0F);
                        entity.setHealth(health + .0F);
                        entity.getEquipment().setHelmet(plugin.pumpkin());
                        if (rand < 71) {
                            entity.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 9999999, 1));
                        }
                        for (Player other : Bukkit.getOnlinePlayers()) {
                            if (other.getLocation().distance(entity.getLocation()) <= 50) {
                                entity.attack(other);
                            }
                        }
                    } else if (spawn < 16) {
                        for (int i = 1; 3 > i; i++) {
                            LivingEntity entity = (LivingEntity) za_warudo.spawnEntity(player.getLocation(), EntityType.SKELETON);
                            entity.setCanPickupItems(true);
                            entity.setMaxHealth(100.0F);
                            entity.setHealth(health + .0F);
                            entity.getEquipment().setHelmet(plugin.pumpkin());
                            if (rand < 31) {
                                entity.getEquipment().setChestplate(new ItemStack(Material.NETHERITE_CHESTPLATE));
                                entity.getEquipment().setLeggings(new ItemStack(Material.NETHERITE_LEGGINGS));
                                entity.getEquipment().setBoots(new ItemStack(Material.NETHERITE_BOOTS));
                                entity.getEquipment().setItemInMainHand(new ItemStack(Material.NETHERITE_AXE));
                                entity.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 9999999, 2));
                                entity.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 9999999, 6));
                            } else if (rand < 36) {
                                entity.getEquipment().setChestplate(new ItemStack(Material.NETHERITE_CHESTPLATE));
                                entity.getEquipment().setLeggings(new ItemStack(Material.NETHERITE_LEGGINGS));
                                entity.getEquipment().setBoots(new ItemStack(Material.NETHERITE_BOOTS));
                            } else if (rand < 51) {
                                entity.getEquipment().setChestplate(new ItemStack(Material.DIAMOND_CHESTPLATE));
                                entity.getEquipment().setLeggings(new ItemStack(Material.DIAMOND_LEGGINGS));
                                entity.getEquipment().setBoots(new ItemStack(Material.DIAMOND_BOOTS));
                                entity.getEquipment().setItemInMainHand(new ItemStack(Material.DIAMOND_AXE));
                                entity.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 9999999, 1));
                                entity.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 9999999, 6));
                            } else if (rand < 56) {
                                entity.getEquipment().setChestplate(new ItemStack(Material.DIAMOND_CHESTPLATE));
                                entity.getEquipment().setLeggings(new ItemStack(Material.DIAMOND_LEGGINGS));
                                entity.getEquipment().setBoots(new ItemStack(Material.DIAMOND_BOOTS));
                            } else if (rand < 61) {
                                entity.getEquipment().setChestplate(new ItemStack(Material.IRON_CHESTPLATE));
                                entity.getEquipment().setLeggings(new ItemStack(Material.IRON_LEGGINGS));
                                entity.getEquipment().setBoots(new ItemStack(Material.IRON_BOOTS));
                                entity.getEquipment().setItemInMainHand(new ItemStack(Material.IRON_AXE));
                                entity.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 9999999, 1));
                                entity.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 9999999, 5));
                            } else if (rand < 66) {
                                entity.getEquipment().setChestplate(new ItemStack(Material.IRON_CHESTPLATE));
                                entity.getEquipment().setLeggings(new ItemStack(Material.IRON_LEGGINGS));
                                entity.getEquipment().setBoots(new ItemStack(Material.IRON_BOOTS));
                            } else if (rand < 81) {
                                entity.getEquipment().setChestplate(new ItemStack(Material.CHAINMAIL_CHESTPLATE));
                                entity.getEquipment().setLeggings(new ItemStack(Material.CHAINMAIL_LEGGINGS));
                                entity.getEquipment().setBoots(new ItemStack(Material.CHAINMAIL_BOOTS));
                                entity.getEquipment().setItemInMainHand(new ItemStack(Material.GOLDEN_AXE));
                                entity.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 9999999, 1));
                                entity.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 9999999, 5));
                            } else if (rand < 86) {
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
                        }
                    } else if (spawn < 21) {
                        LivingEntity entity = (LivingEntity) za_warudo.spawnEntity(player.getLocation(), EntityType.SKELETON);
                        entity.setCanPickupItems(true);
                        entity.setMaxHealth(100.0F);
                        entity.setHealth(health + .0F);
                        entity.getEquipment().setHelmet(plugin.pumpkin());
                        if (rand < 31) {
                            entity.getEquipment().setChestplate(new ItemStack(Material.NETHERITE_CHESTPLATE));
                            entity.getEquipment().setLeggings(new ItemStack(Material.NETHERITE_LEGGINGS));
                            entity.getEquipment().setBoots(new ItemStack(Material.NETHERITE_BOOTS));
                            entity.getEquipment().setItemInMainHand(new ItemStack(Material.NETHERITE_AXE));
                            entity.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 9999999, 2));
                            entity.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 9999999, 6));
                        } else if (rand < 36) {
                            entity.getEquipment().setChestplate(new ItemStack(Material.NETHERITE_CHESTPLATE));
                            entity.getEquipment().setLeggings(new ItemStack(Material.NETHERITE_LEGGINGS));
                            entity.getEquipment().setBoots(new ItemStack(Material.NETHERITE_BOOTS));
                        } else if (rand < 51) {
                            entity.getEquipment().setChestplate(new ItemStack(Material.DIAMOND_CHESTPLATE));
                            entity.getEquipment().setLeggings(new ItemStack(Material.DIAMOND_LEGGINGS));
                            entity.getEquipment().setBoots(new ItemStack(Material.DIAMOND_BOOTS));
                            entity.getEquipment().setItemInMainHand(new ItemStack(Material.DIAMOND_AXE));
                            entity.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 9999999, 1));
                            entity.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 9999999, 6));
                        } else if (rand < 56) {
                            entity.getEquipment().setChestplate(new ItemStack(Material.DIAMOND_CHESTPLATE));
                            entity.getEquipment().setLeggings(new ItemStack(Material.DIAMOND_LEGGINGS));
                            entity.getEquipment().setBoots(new ItemStack(Material.DIAMOND_BOOTS));
                        } else if (rand < 61) {
                            entity.getEquipment().setChestplate(new ItemStack(Material.IRON_CHESTPLATE));
                            entity.getEquipment().setLeggings(new ItemStack(Material.IRON_LEGGINGS));
                            entity.getEquipment().setBoots(new ItemStack(Material.IRON_BOOTS));
                            entity.getEquipment().setItemInMainHand(new ItemStack(Material.IRON_AXE));
                            entity.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 9999999, 1));
                            entity.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 9999999, 5));
                        } else if (rand < 66) {
                            entity.getEquipment().setChestplate(new ItemStack(Material.IRON_CHESTPLATE));
                            entity.getEquipment().setLeggings(new ItemStack(Material.IRON_LEGGINGS));
                            entity.getEquipment().setBoots(new ItemStack(Material.IRON_BOOTS));
                        } else if (rand < 81) {
                            entity.getEquipment().setChestplate(new ItemStack(Material.CHAINMAIL_CHESTPLATE));
                            entity.getEquipment().setLeggings(new ItemStack(Material.CHAINMAIL_LEGGINGS));
                            entity.getEquipment().setBoots(new ItemStack(Material.CHAINMAIL_BOOTS));
                            entity.getEquipment().setItemInMainHand(new ItemStack(Material.GOLDEN_AXE));
                            entity.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 9999999, 1));
                            entity.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 9999999, 5));
                        } else if (rand < 86) {
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
                    } else if (spawn == 100) {
                        za_warudo.spawnEntity(player.getLocation(), EntityType.CREEPER);
                        za_warudo.spawnEntity(player.getLocation(), EntityType.CREEPER);
                        za_warudo.spawnEntity(player.getLocation(), EntityType.CREEPER);
                        za_warudo.spawnEntity(player.getLocation(), EntityType.CREEPER);
                        za_warudo.spawnEntity(player.getLocation(), EntityType.CREEPER);
                        za_warudo.spawnEntity(player.getLocation(), EntityType.CREEPER);
                        za_warudo.spawnEntity(player.getLocation(), EntityType.CREEPER);
                        za_warudo.spawnEntity(player.getLocation(), EntityType.CREEPER);
                        za_warudo.spawnEntity(player.getLocation(), EntityType.CREEPER);
                        za_warudo.spawnEntity(player.getLocation(), EntityType.CREEPER);
                    } else {
                        LivingEntity entity = (LivingEntity) za_warudo.spawnEntity(player.getLocation(), EntityType.ZOMBIE);
                        entity.setCanPickupItems(true);
                        entity.setMaxHealth(100.0F);
                        entity.setHealth(health + .0F);
                        entity.getEquipment().setHelmet(plugin.pumpkin());
                        if (rand < 51) {
                            entity.getEquipment().setChestplate(new ItemStack(Material.NETHERITE_CHESTPLATE));
                            entity.getEquipment().setLeggings(new ItemStack(Material.NETHERITE_LEGGINGS));
                            entity.getEquipment().setBoots(new ItemStack(Material.NETHERITE_BOOTS));
                            entity.getEquipment().setItemInMainHand(new ItemStack(Material.NETHERITE_AXE));
                            entity.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 9999999, 2));
                            entity.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 9999999, 6));
                        } else if (rand < 56) {
                            entity.getEquipment().setChestplate(new ItemStack(Material.NETHERITE_CHESTPLATE));
                            entity.getEquipment().setLeggings(new ItemStack(Material.NETHERITE_LEGGINGS));
                            entity.getEquipment().setBoots(new ItemStack(Material.NETHERITE_BOOTS));
                        } else if (rand < 61) {
                            entity.getEquipment().setChestplate(new ItemStack(Material.DIAMOND_CHESTPLATE));
                            entity.getEquipment().setLeggings(new ItemStack(Material.DIAMOND_LEGGINGS));
                            entity.getEquipment().setBoots(new ItemStack(Material.DIAMOND_BOOTS));
                            entity.getEquipment().setItemInMainHand(new ItemStack(Material.DIAMOND_SWORD));
                            entity.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 9999999, 2));
                            entity.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 9999999, 5));
                        } else if (rand < 66) {
                            entity.getEquipment().setChestplate(new ItemStack(Material.DIAMOND_CHESTPLATE));
                            entity.getEquipment().setLeggings(new ItemStack(Material.DIAMOND_LEGGINGS));
                            entity.getEquipment().setBoots(new ItemStack(Material.DIAMOND_BOOTS));
                        } else if (rand < 71) {
                            entity.getEquipment().setChestplate(new ItemStack(Material.IRON_CHESTPLATE));
                            entity.getEquipment().setLeggings(new ItemStack(Material.IRON_LEGGINGS));
                            entity.getEquipment().setBoots(new ItemStack(Material.IRON_BOOTS));
                            entity.getEquipment().setItemInMainHand(new ItemStack(Material.IRON_SHOVEL));
                            entity.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 9999999, 4));
                        } else if (rand < 76) {
                            entity.getEquipment().setChestplate(new ItemStack(Material.IRON_CHESTPLATE));
                            entity.getEquipment().setLeggings(new ItemStack(Material.IRON_LEGGINGS));
                            entity.getEquipment().setBoots(new ItemStack(Material.IRON_BOOTS));
                        } else if (rand < 81) {
                            entity.getEquipment().setChestplate(new ItemStack(Material.CHAINMAIL_CHESTPLATE));
                            entity.getEquipment().setLeggings(new ItemStack(Material.CHAINMAIL_LEGGINGS));
                            entity.getEquipment().setBoots(new ItemStack(Material.CHAINMAIL_BOOTS));
                            entity.getEquipment().setItemInMainHand(new ItemStack(Material.GOLDEN_HOE));
                            entity.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 9999999, 3));
                        } else if (rand < 86) {
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
                    }
                    prosto = 1;
                } else if (coords.getX() > x - 10) {
                    if (prosto == 1) {
                        if (p_list.isEmpty()) return;
                        String leave = p_list.get(0);
                        String[] leave1 = leave.split("Leave: ");
                        p_list.clear();
                        p_list = new ArrayList<String>();
                        int aboba = Integer.parseInt(leave1[1]);
                        p_list.add("Leave: " + aboba);
                        p_list.add("Coords: " + player.getLocation().getX() + " " + player.getLocation().getY() + " " + player.getLocation().getZ());

                        p_logs.set(player.getName(), p_list);
                        try {
                            p_logs.save(players);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        return;
                    }
                    Random random = new Random();
                    int health = random.ints(20, 100).findFirst().getAsInt();
                    int rand = random.ints(1, 100).findFirst().getAsInt();
                    int spawn = random.ints(1, 100).findFirst().getAsInt();
                    World za_warudo = player.getWorld();

                    if (spawn < 6) {
                        LivingEntity entity = (LivingEntity) za_warudo.spawnEntity(player.getLocation(), EntityType.CREEPER);
                        entity.setCanPickupItems(true);
                        entity.setMaxHealth(100.0F);
                        entity.setHealth(health + .0F);
                        entity.getEquipment().setHelmet(plugin.pumpkin());
                        if (rand < 71) {
                            entity.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 9999999, 1));
                        }
                        for (Player other : Bukkit.getOnlinePlayers()) {
                            if (other.getLocation().distance(entity.getLocation()) <= 50) {
                                entity.attack(other);
                            }
                        }
                    } else if (spawn < 16) {
                        for (int i = 1; 3 > i; i++) {
                            LivingEntity entity = (LivingEntity) za_warudo.spawnEntity(player.getLocation(), EntityType.SKELETON);
                            entity.setCanPickupItems(true);
                            entity.setMaxHealth(100.0F);
                            entity.setHealth(health + .0F);
                            entity.getEquipment().setHelmet(plugin.pumpkin());
                            if (rand < 31) {
                                entity.getEquipment().setChestplate(new ItemStack(Material.NETHERITE_CHESTPLATE));
                                entity.getEquipment().setLeggings(new ItemStack(Material.NETHERITE_LEGGINGS));
                                entity.getEquipment().setBoots(new ItemStack(Material.NETHERITE_BOOTS));
                                entity.getEquipment().setItemInMainHand(new ItemStack(Material.NETHERITE_AXE));
                                entity.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 9999999, 2));
                                entity.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 9999999, 6));
                            } else if (rand < 36) {
                                entity.getEquipment().setChestplate(new ItemStack(Material.NETHERITE_CHESTPLATE));
                                entity.getEquipment().setLeggings(new ItemStack(Material.NETHERITE_LEGGINGS));
                                entity.getEquipment().setBoots(new ItemStack(Material.NETHERITE_BOOTS));
                            } else if (rand < 51) {
                                entity.getEquipment().setChestplate(new ItemStack(Material.DIAMOND_CHESTPLATE));
                                entity.getEquipment().setLeggings(new ItemStack(Material.DIAMOND_LEGGINGS));
                                entity.getEquipment().setBoots(new ItemStack(Material.DIAMOND_BOOTS));
                                entity.getEquipment().setItemInMainHand(new ItemStack(Material.DIAMOND_AXE));
                                entity.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 9999999, 1));
                                entity.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 9999999, 6));
                            } else if (rand < 56) {
                                entity.getEquipment().setChestplate(new ItemStack(Material.DIAMOND_CHESTPLATE));
                                entity.getEquipment().setLeggings(new ItemStack(Material.DIAMOND_LEGGINGS));
                                entity.getEquipment().setBoots(new ItemStack(Material.DIAMOND_BOOTS));
                            } else if (rand < 61) {
                                entity.getEquipment().setChestplate(new ItemStack(Material.IRON_CHESTPLATE));
                                entity.getEquipment().setLeggings(new ItemStack(Material.IRON_LEGGINGS));
                                entity.getEquipment().setBoots(new ItemStack(Material.IRON_BOOTS));
                                entity.getEquipment().setItemInMainHand(new ItemStack(Material.IRON_AXE));
                                entity.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 9999999, 1));
                                entity.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 9999999, 5));
                            } else if (rand < 66) {
                                entity.getEquipment().setChestplate(new ItemStack(Material.IRON_CHESTPLATE));
                                entity.getEquipment().setLeggings(new ItemStack(Material.IRON_LEGGINGS));
                                entity.getEquipment().setBoots(new ItemStack(Material.IRON_BOOTS));
                            } else if (rand < 81) {
                                entity.getEquipment().setChestplate(new ItemStack(Material.CHAINMAIL_CHESTPLATE));
                                entity.getEquipment().setLeggings(new ItemStack(Material.CHAINMAIL_LEGGINGS));
                                entity.getEquipment().setBoots(new ItemStack(Material.CHAINMAIL_BOOTS));
                                entity.getEquipment().setItemInMainHand(new ItemStack(Material.GOLDEN_AXE));
                                entity.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 9999999, 1));
                                entity.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 9999999, 5));
                            } else if (rand < 86) {
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
                        }
                    } else if (spawn < 21) {
                        LivingEntity entity = (LivingEntity) za_warudo.spawnEntity(player.getLocation(), EntityType.SKELETON);
                        entity.setCanPickupItems(true);
                        entity.setMaxHealth(100.0F);
                        entity.setHealth(health + .0F);
                        entity.getEquipment().setHelmet(plugin.pumpkin());
                        if (rand < 31) {
                            entity.getEquipment().setChestplate(new ItemStack(Material.NETHERITE_CHESTPLATE));
                            entity.getEquipment().setLeggings(new ItemStack(Material.NETHERITE_LEGGINGS));
                            entity.getEquipment().setBoots(new ItemStack(Material.NETHERITE_BOOTS));
                            entity.getEquipment().setItemInMainHand(new ItemStack(Material.NETHERITE_AXE));
                            entity.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 9999999, 2));
                            entity.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 9999999, 6));
                        } else if (rand < 36) {
                            entity.getEquipment().setChestplate(new ItemStack(Material.NETHERITE_CHESTPLATE));
                            entity.getEquipment().setLeggings(new ItemStack(Material.NETHERITE_LEGGINGS));
                            entity.getEquipment().setBoots(new ItemStack(Material.NETHERITE_BOOTS));
                        } else if (rand < 51) {
                            entity.getEquipment().setChestplate(new ItemStack(Material.DIAMOND_CHESTPLATE));
                            entity.getEquipment().setLeggings(new ItemStack(Material.DIAMOND_LEGGINGS));
                            entity.getEquipment().setBoots(new ItemStack(Material.DIAMOND_BOOTS));
                            entity.getEquipment().setItemInMainHand(new ItemStack(Material.DIAMOND_AXE));
                            entity.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 9999999, 1));
                            entity.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 9999999, 6));
                        } else if (rand < 56) {
                            entity.getEquipment().setChestplate(new ItemStack(Material.DIAMOND_CHESTPLATE));
                            entity.getEquipment().setLeggings(new ItemStack(Material.DIAMOND_LEGGINGS));
                            entity.getEquipment().setBoots(new ItemStack(Material.DIAMOND_BOOTS));
                        } else if (rand < 61) {
                            entity.getEquipment().setChestplate(new ItemStack(Material.IRON_CHESTPLATE));
                            entity.getEquipment().setLeggings(new ItemStack(Material.IRON_LEGGINGS));
                            entity.getEquipment().setBoots(new ItemStack(Material.IRON_BOOTS));
                            entity.getEquipment().setItemInMainHand(new ItemStack(Material.IRON_AXE));
                            entity.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 9999999, 1));
                            entity.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 9999999, 5));
                        } else if (rand < 66) {
                            entity.getEquipment().setChestplate(new ItemStack(Material.IRON_CHESTPLATE));
                            entity.getEquipment().setLeggings(new ItemStack(Material.IRON_LEGGINGS));
                            entity.getEquipment().setBoots(new ItemStack(Material.IRON_BOOTS));
                        } else if (rand < 81) {
                            entity.getEquipment().setChestplate(new ItemStack(Material.CHAINMAIL_CHESTPLATE));
                            entity.getEquipment().setLeggings(new ItemStack(Material.CHAINMAIL_LEGGINGS));
                            entity.getEquipment().setBoots(new ItemStack(Material.CHAINMAIL_BOOTS));
                            entity.getEquipment().setItemInMainHand(new ItemStack(Material.GOLDEN_AXE));
                            entity.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 9999999, 1));
                            entity.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 9999999, 5));
                        } else if (rand < 86) {
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
                    } else if (spawn == 100) {
                        za_warudo.spawnEntity(player.getLocation(), EntityType.CREEPER);
                        za_warudo.spawnEntity(player.getLocation(), EntityType.CREEPER);
                        za_warudo.spawnEntity(player.getLocation(), EntityType.CREEPER);
                        za_warudo.spawnEntity(player.getLocation(), EntityType.CREEPER);
                        za_warudo.spawnEntity(player.getLocation(), EntityType.CREEPER);
                        za_warudo.spawnEntity(player.getLocation(), EntityType.CREEPER);
                        za_warudo.spawnEntity(player.getLocation(), EntityType.CREEPER);
                        za_warudo.spawnEntity(player.getLocation(), EntityType.CREEPER);
                        za_warudo.spawnEntity(player.getLocation(), EntityType.CREEPER);
                        za_warudo.spawnEntity(player.getLocation(), EntityType.CREEPER);
                    } else {
                        LivingEntity entity = (LivingEntity) za_warudo.spawnEntity(player.getLocation(), EntityType.ZOMBIE);
                        entity.setCanPickupItems(true);
                        entity.setMaxHealth(100.0F);
                        entity.setHealth(health + .0F);
                        entity.getEquipment().setHelmet(plugin.pumpkin());
                        if (rand < 51) {
                            entity.getEquipment().setChestplate(new ItemStack(Material.NETHERITE_CHESTPLATE));
                            entity.getEquipment().setLeggings(new ItemStack(Material.NETHERITE_LEGGINGS));
                            entity.getEquipment().setBoots(new ItemStack(Material.NETHERITE_BOOTS));
                            entity.getEquipment().setItemInMainHand(new ItemStack(Material.NETHERITE_AXE));
                            entity.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 9999999, 2));
                            entity.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 9999999, 6));
                        } else if (rand < 56) {
                            entity.getEquipment().setChestplate(new ItemStack(Material.NETHERITE_CHESTPLATE));
                            entity.getEquipment().setLeggings(new ItemStack(Material.NETHERITE_LEGGINGS));
                            entity.getEquipment().setBoots(new ItemStack(Material.NETHERITE_BOOTS));
                        } else if (rand < 61) {
                            entity.getEquipment().setChestplate(new ItemStack(Material.DIAMOND_CHESTPLATE));
                            entity.getEquipment().setLeggings(new ItemStack(Material.DIAMOND_LEGGINGS));
                            entity.getEquipment().setBoots(new ItemStack(Material.DIAMOND_BOOTS));
                            entity.getEquipment().setItemInMainHand(new ItemStack(Material.DIAMOND_SWORD));
                            entity.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 9999999, 2));
                            entity.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 9999999, 5));
                        } else if (rand < 66) {
                            entity.getEquipment().setChestplate(new ItemStack(Material.DIAMOND_CHESTPLATE));
                            entity.getEquipment().setLeggings(new ItemStack(Material.DIAMOND_LEGGINGS));
                            entity.getEquipment().setBoots(new ItemStack(Material.DIAMOND_BOOTS));
                        } else if (rand < 71) {
                            entity.getEquipment().setChestplate(new ItemStack(Material.IRON_CHESTPLATE));
                            entity.getEquipment().setLeggings(new ItemStack(Material.IRON_LEGGINGS));
                            entity.getEquipment().setBoots(new ItemStack(Material.IRON_BOOTS));
                            entity.getEquipment().setItemInMainHand(new ItemStack(Material.IRON_SHOVEL));
                            entity.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 9999999, 4));
                        } else if (rand < 76) {
                            entity.getEquipment().setChestplate(new ItemStack(Material.IRON_CHESTPLATE));
                            entity.getEquipment().setLeggings(new ItemStack(Material.IRON_LEGGINGS));
                            entity.getEquipment().setBoots(new ItemStack(Material.IRON_BOOTS));
                        } else if (rand < 81) {
                            entity.getEquipment().setChestplate(new ItemStack(Material.CHAINMAIL_CHESTPLATE));
                            entity.getEquipment().setLeggings(new ItemStack(Material.CHAINMAIL_LEGGINGS));
                            entity.getEquipment().setBoots(new ItemStack(Material.CHAINMAIL_BOOTS));
                            entity.getEquipment().setItemInMainHand(new ItemStack(Material.GOLDEN_HOE));
                            entity.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 9999999, 3));
                        } else if (rand < 86) {
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
                    }
                } else if (coords.getZ() > z - 10) {
                    if (prosto == 1) {
                        if (p_list.isEmpty()) return;
                        String leave = p_list.get(0);
                        String[] leave1 = leave.split("Leave: ");
                        p_list.clear();
                        p_list = new ArrayList<String>();
                        int aboba = Integer.parseInt(leave1[1]);
                        p_list.add("Leave: " + aboba);
                        p_list.add("Coords: " + player.getLocation().getX() + " " + player.getLocation().getY() + " " + player.getLocation().getZ());

                        p_logs.set(player.getName(), p_list);
                        try {
                            p_logs.save(players);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        return;
                    }
                    Random random = new Random();
                    int health = random.ints(20, 100).findFirst().getAsInt();
                    int rand = random.ints(1, 100).findFirst().getAsInt();
                    int spawn = random.ints(1, 100).findFirst().getAsInt();
                    World za_warudo = player.getWorld();

                    if (spawn < 6) {
                        LivingEntity entity = (LivingEntity) za_warudo.spawnEntity(player.getLocation(), EntityType.CREEPER);
                        entity.setCanPickupItems(true);
                        entity.setMaxHealth(100.0F);
                        entity.setHealth(health + .0F);
                        entity.getEquipment().setHelmet(plugin.pumpkin());
                        if (rand < 71) {
                            entity.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 9999999, 1));
                        }
                        for (Player other : Bukkit.getOnlinePlayers()) {
                            if (other.getLocation().distance(entity.getLocation()) <= 50) {
                                entity.attack(other);
                            }
                        }
                    } else if (spawn < 16) {
                        for (int i = 1; 3 > i; i++) {
                            LivingEntity entity = (LivingEntity) za_warudo.spawnEntity(player.getLocation(), EntityType.SKELETON);
                            entity.setCanPickupItems(true);
                            entity.setMaxHealth(100.0F);
                            entity.setHealth(health + .0F);
                            entity.getEquipment().setHelmet(plugin.pumpkin());
                            if (rand < 31) {
                                entity.getEquipment().setChestplate(new ItemStack(Material.NETHERITE_CHESTPLATE));
                                entity.getEquipment().setLeggings(new ItemStack(Material.NETHERITE_LEGGINGS));
                                entity.getEquipment().setBoots(new ItemStack(Material.NETHERITE_BOOTS));
                                entity.getEquipment().setItemInMainHand(new ItemStack(Material.NETHERITE_AXE));
                                entity.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 9999999, 2));
                                entity.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 9999999, 6));
                            } else if (rand < 36) {
                                entity.getEquipment().setChestplate(new ItemStack(Material.NETHERITE_CHESTPLATE));
                                entity.getEquipment().setLeggings(new ItemStack(Material.NETHERITE_LEGGINGS));
                                entity.getEquipment().setBoots(new ItemStack(Material.NETHERITE_BOOTS));
                            } else if (rand < 51) {
                                entity.getEquipment().setChestplate(new ItemStack(Material.DIAMOND_CHESTPLATE));
                                entity.getEquipment().setLeggings(new ItemStack(Material.DIAMOND_LEGGINGS));
                                entity.getEquipment().setBoots(new ItemStack(Material.DIAMOND_BOOTS));
                                entity.getEquipment().setItemInMainHand(new ItemStack(Material.DIAMOND_AXE));
                                entity.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 9999999, 1));
                                entity.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 9999999, 6));
                            } else if (rand < 56) {
                                entity.getEquipment().setChestplate(new ItemStack(Material.DIAMOND_CHESTPLATE));
                                entity.getEquipment().setLeggings(new ItemStack(Material.DIAMOND_LEGGINGS));
                                entity.getEquipment().setBoots(new ItemStack(Material.DIAMOND_BOOTS));
                            } else if (rand < 61) {
                                entity.getEquipment().setChestplate(new ItemStack(Material.IRON_CHESTPLATE));
                                entity.getEquipment().setLeggings(new ItemStack(Material.IRON_LEGGINGS));
                                entity.getEquipment().setBoots(new ItemStack(Material.IRON_BOOTS));
                                entity.getEquipment().setItemInMainHand(new ItemStack(Material.IRON_AXE));
                                entity.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 9999999, 1));
                                entity.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 9999999, 5));
                            } else if (rand < 66) {
                                entity.getEquipment().setChestplate(new ItemStack(Material.IRON_CHESTPLATE));
                                entity.getEquipment().setLeggings(new ItemStack(Material.IRON_LEGGINGS));
                                entity.getEquipment().setBoots(new ItemStack(Material.IRON_BOOTS));
                            } else if (rand < 81) {
                                entity.getEquipment().setChestplate(new ItemStack(Material.CHAINMAIL_CHESTPLATE));
                                entity.getEquipment().setLeggings(new ItemStack(Material.CHAINMAIL_LEGGINGS));
                                entity.getEquipment().setBoots(new ItemStack(Material.CHAINMAIL_BOOTS));
                                entity.getEquipment().setItemInMainHand(new ItemStack(Material.GOLDEN_AXE));
                                entity.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 9999999, 1));
                                entity.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 9999999, 5));
                            } else if (rand < 86) {
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
                        }
                    } else if (spawn < 21) {
                        LivingEntity entity = (LivingEntity) za_warudo.spawnEntity(player.getLocation(), EntityType.SKELETON);
                        entity.setCanPickupItems(true);
                        entity.setMaxHealth(100.0F);
                        entity.setHealth(health + .0F);
                        entity.getEquipment().setHelmet(plugin.pumpkin());
                        if (rand < 31) {
                            entity.getEquipment().setChestplate(new ItemStack(Material.NETHERITE_CHESTPLATE));
                            entity.getEquipment().setLeggings(new ItemStack(Material.NETHERITE_LEGGINGS));
                            entity.getEquipment().setBoots(new ItemStack(Material.NETHERITE_BOOTS));
                            entity.getEquipment().setItemInMainHand(new ItemStack(Material.NETHERITE_AXE));
                            entity.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 9999999, 2));
                            entity.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 9999999, 6));
                        } else if (rand < 36) {
                            entity.getEquipment().setChestplate(new ItemStack(Material.NETHERITE_CHESTPLATE));
                            entity.getEquipment().setLeggings(new ItemStack(Material.NETHERITE_LEGGINGS));
                            entity.getEquipment().setBoots(new ItemStack(Material.NETHERITE_BOOTS));
                        } else if (rand < 51) {
                            entity.getEquipment().setChestplate(new ItemStack(Material.DIAMOND_CHESTPLATE));
                            entity.getEquipment().setLeggings(new ItemStack(Material.DIAMOND_LEGGINGS));
                            entity.getEquipment().setBoots(new ItemStack(Material.DIAMOND_BOOTS));
                            entity.getEquipment().setItemInMainHand(new ItemStack(Material.DIAMOND_AXE));
                            entity.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 9999999, 1));
                            entity.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 9999999, 6));
                        } else if (rand < 56) {
                            entity.getEquipment().setChestplate(new ItemStack(Material.DIAMOND_CHESTPLATE));
                            entity.getEquipment().setLeggings(new ItemStack(Material.DIAMOND_LEGGINGS));
                            entity.getEquipment().setBoots(new ItemStack(Material.DIAMOND_BOOTS));
                        } else if (rand < 61) {
                            entity.getEquipment().setChestplate(new ItemStack(Material.IRON_CHESTPLATE));
                            entity.getEquipment().setLeggings(new ItemStack(Material.IRON_LEGGINGS));
                            entity.getEquipment().setBoots(new ItemStack(Material.IRON_BOOTS));
                            entity.getEquipment().setItemInMainHand(new ItemStack(Material.IRON_AXE));
                            entity.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 9999999, 1));
                            entity.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 9999999, 5));
                        } else if (rand < 66) {
                            entity.getEquipment().setChestplate(new ItemStack(Material.IRON_CHESTPLATE));
                            entity.getEquipment().setLeggings(new ItemStack(Material.IRON_LEGGINGS));
                            entity.getEquipment().setBoots(new ItemStack(Material.IRON_BOOTS));
                        } else if (rand < 81) {
                            entity.getEquipment().setChestplate(new ItemStack(Material.CHAINMAIL_CHESTPLATE));
                            entity.getEquipment().setLeggings(new ItemStack(Material.CHAINMAIL_LEGGINGS));
                            entity.getEquipment().setBoots(new ItemStack(Material.CHAINMAIL_BOOTS));
                            entity.getEquipment().setItemInMainHand(new ItemStack(Material.GOLDEN_AXE));
                            entity.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 9999999, 1));
                            entity.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 9999999, 5));
                        } else if (rand < 86) {
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
                    } else if (spawn == 100) {
                        za_warudo.spawnEntity(player.getLocation(), EntityType.CREEPER);
                        za_warudo.spawnEntity(player.getLocation(), EntityType.CREEPER);
                        za_warudo.spawnEntity(player.getLocation(), EntityType.CREEPER);
                        za_warudo.spawnEntity(player.getLocation(), EntityType.CREEPER);
                        za_warudo.spawnEntity(player.getLocation(), EntityType.CREEPER);
                        za_warudo.spawnEntity(player.getLocation(), EntityType.CREEPER);
                        za_warudo.spawnEntity(player.getLocation(), EntityType.CREEPER);
                        za_warudo.spawnEntity(player.getLocation(), EntityType.CREEPER);
                        za_warudo.spawnEntity(player.getLocation(), EntityType.CREEPER);
                        za_warudo.spawnEntity(player.getLocation(), EntityType.CREEPER);
                    } else {
                        LivingEntity entity = (LivingEntity) za_warudo.spawnEntity(player.getLocation(), EntityType.ZOMBIE);
                        entity.setCanPickupItems(true);
                        entity.setMaxHealth(100.0F);
                        entity.setHealth(health + .0F);
                        entity.getEquipment().setHelmet(plugin.pumpkin());
                        if (rand < 51) {
                            entity.getEquipment().setChestplate(new ItemStack(Material.NETHERITE_CHESTPLATE));
                            entity.getEquipment().setLeggings(new ItemStack(Material.NETHERITE_LEGGINGS));
                            entity.getEquipment().setBoots(new ItemStack(Material.NETHERITE_BOOTS));
                            entity.getEquipment().setItemInMainHand(new ItemStack(Material.NETHERITE_AXE));
                            entity.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 9999999, 2));
                            entity.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 9999999, 6));
                        } else if (rand < 56) {
                            entity.getEquipment().setChestplate(new ItemStack(Material.NETHERITE_CHESTPLATE));
                            entity.getEquipment().setLeggings(new ItemStack(Material.NETHERITE_LEGGINGS));
                            entity.getEquipment().setBoots(new ItemStack(Material.NETHERITE_BOOTS));
                        } else if (rand < 61) {
                            entity.getEquipment().setChestplate(new ItemStack(Material.DIAMOND_CHESTPLATE));
                            entity.getEquipment().setLeggings(new ItemStack(Material.DIAMOND_LEGGINGS));
                            entity.getEquipment().setBoots(new ItemStack(Material.DIAMOND_BOOTS));
                            entity.getEquipment().setItemInMainHand(new ItemStack(Material.DIAMOND_SWORD));
                            entity.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 9999999, 2));
                            entity.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 9999999, 5));
                        } else if (rand < 66) {
                            entity.getEquipment().setChestplate(new ItemStack(Material.DIAMOND_CHESTPLATE));
                            entity.getEquipment().setLeggings(new ItemStack(Material.DIAMOND_LEGGINGS));
                            entity.getEquipment().setBoots(new ItemStack(Material.DIAMOND_BOOTS));
                        } else if (rand < 71) {
                            entity.getEquipment().setChestplate(new ItemStack(Material.IRON_CHESTPLATE));
                            entity.getEquipment().setLeggings(new ItemStack(Material.IRON_LEGGINGS));
                            entity.getEquipment().setBoots(new ItemStack(Material.IRON_BOOTS));
                            entity.getEquipment().setItemInMainHand(new ItemStack(Material.IRON_SHOVEL));
                            entity.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 9999999, 4));
                        } else if (rand < 76) {
                            entity.getEquipment().setChestplate(new ItemStack(Material.IRON_CHESTPLATE));
                            entity.getEquipment().setLeggings(new ItemStack(Material.IRON_LEGGINGS));
                            entity.getEquipment().setBoots(new ItemStack(Material.IRON_BOOTS));
                        } else if (rand < 81) {
                            entity.getEquipment().setChestplate(new ItemStack(Material.CHAINMAIL_CHESTPLATE));
                            entity.getEquipment().setLeggings(new ItemStack(Material.CHAINMAIL_LEGGINGS));
                            entity.getEquipment().setBoots(new ItemStack(Material.CHAINMAIL_BOOTS));
                            entity.getEquipment().setItemInMainHand(new ItemStack(Material.GOLDEN_HOE));
                            entity.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 9999999, 3));
                        } else if (rand < 86) {
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
                    }
                }
                if (p_list.isEmpty()) return;
                String leave = p_list.get(0);
                String[] leave1 = leave.split("Leave: ");
                p_list.clear();
                p_list = new ArrayList<String>();
                int aboba = Integer.parseInt(leave1[1]);
                p_list.add("Leave: " + aboba);
                p_list.add("Coords: " + player.getLocation().getX() + " " + player.getLocation().getY() + " " + player.getLocation().getZ());

                p_logs.set(player.getName(), p_list);
                try {
                    p_logs.save(players);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
