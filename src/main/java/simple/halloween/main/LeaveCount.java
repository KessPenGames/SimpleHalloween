package simple.halloween.main;

import org.bukkit.*;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class LeaveCount implements Listener {
    private Main plugin;
    public LeaveCount(Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event) {
        File logs_stats = new File(plugin.getDataFolder() + File.separator + "halloween.yml");
        FileConfiguration logs = YamlConfiguration.loadConfiguration(logs_stats);
        List<String> list = logs.getStringList("halloween");
        if (list.isEmpty()) return;
        if (list.get(0).equals("true")) {
            Player player = event.getPlayer();
            File players = new File(plugin.getDataFolder() + File.separator + "players.yml");
            FileConfiguration p_logs = YamlConfiguration.loadConfiguration(players);
            List<String> p_list = p_logs.getStringList(event.getPlayer().getName());
            if (player.getGameMode().toString().equals("SURVIVAL")) {
                if (p_list.isEmpty()) {
                    p_list = new ArrayList<String>();
                    p_list.add("Leave: 1");
                    p_list.add("Coords: " + player.getLocation().getX() + " " + player.getLocation().getY() + " " + player.getLocation().getZ());

                    p_logs.set(player.getName(), p_list);
                    try {
                        p_logs.save(players);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    String leave = p_list.get(0);
                    String[] leave1 = leave.split("Leave: ");
                    p_list.clear();
                    p_list = new ArrayList<String>();
                    int aboba = Integer.parseInt(leave1[1]) + 1;
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

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        File logs_stats = new File(plugin.getDataFolder() + File.separator + "halloween.yml");
        FileConfiguration logs = YamlConfiguration.loadConfiguration(logs_stats);
        List<String> list = logs.getStringList("halloween");
        if (list.isEmpty()) return;
        if (list.get(0).equals("true")) {
            Player player = event.getPlayer();

            File death_list = new File(plugin.getDataFolder() + File.separator + "death.yml");
            FileConfiguration death_logs = YamlConfiguration.loadConfiguration(death_list);
            List<String> listik = death_logs.getStringList(player.getName());
            if (!listik.isEmpty()) player.kickPlayer(ChatColor.RED + "Вас поглотила тьма\n(ждите межсезонья)");

            File players = new File(plugin.getDataFolder() + File.separator + "players.yml");
            FileConfiguration p_logs = YamlConfiguration.loadConfiguration(players);
            List<String> p_list = p_logs.getStringList(player.getName());
            if (p_list.isEmpty()) return;
            if (player.getGameMode().toString().equals("SURVIVAL")) {
                String leave = p_list.get(0);
                String[] leave1 = leave.split("Leave: ");
                int aboba = Integer.parseInt(leave1[1]);
                int leavecount = plugin.getConfig().getInt("leave-count");
                if (aboba > leavecount) {
                    Random random = new Random();
                    int health = random.ints(20, 100).findFirst().getAsInt();
                    int rand = random.ints(1, 100).findFirst().getAsInt();
                    int spawn = random.ints(1, 100).findFirst().getAsInt();
                    World za_warudo = player.getWorld();

                    if (spawn < 61) {
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
                    } else if (spawn < 71) {
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
                    } else if (spawn < 81) {
                        for (int i = 1; 5 > i; i++) {
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
                    } else {
                        za_warudo.spawnEntity(player.getLocation(), EntityType.ZOMBIE);
                        za_warudo.spawnEntity(player.getLocation(), EntityType.ZOMBIE);
                        za_warudo.spawnEntity(player.getLocation(), EntityType.ZOMBIE);
                        za_warudo.spawnEntity(player.getLocation(), EntityType.ZOMBIE);
                        za_warudo.spawnEntity(player.getLocation(), EntityType.ZOMBIE);
                        za_warudo.spawnEntity(player.getLocation(), EntityType.ZOMBIE);
                        za_warudo.spawnEntity(player.getLocation(), EntityType.ZOMBIE);
                        za_warudo.spawnEntity(player.getLocation(), EntityType.ZOMBIE);
                        za_warudo.spawnEntity(player.getLocation(), EntityType.ZOMBIE);
                        za_warudo.spawnEntity(player.getLocation(), EntityType.ZOMBIE);
                    }

                    p_list.clear();
                    p_list = new ArrayList<String>();
                    p_list.add("Leave: 1");
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
}
