package simple.halloween.main;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
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
                if (coords.getY() < 57) event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, 300, 2));
                if (coords.getY() < 57) event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 300, 1));
                if (coords.getY() < 57) event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.HUNGER, 300, 1));
                if (coords.getY() > 99) event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 300, 2));
                if (coords.getWorld().getBlockAt(coords).getType() == Material.WATER) event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 200, 1));
                if (coords.getWorld().getBlockAt((int) coords.getX(), (int) coords.getY() - 1, (int) coords.getZ()).getType() == Material.WATER) event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 200, 1));

                int walktime = plugin.getConfig().getInt("walk-time");
                int cooldownTime = walktime;
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
                int sx = (int) (player.getLocation().getX() + 10);
                int sz = (int) player.getLocation().getZ();
                int sy = player.getWorld().getHighestBlockYAt(sx, sz);
                Location loc = player.getWorld().getBlockAt(sx, sy, sz).getLocation();
                if (coords.getX() < x + 10) {
                    Random random = new Random();
                    int health = random.ints(20, 100).findFirst().getAsInt();
                    int rand = random.ints(1, 100).findFirst().getAsInt();
                    int spawn = random.ints(1, 100).findFirst().getAsInt();
                    World za_warudo = player.getWorld();

                    if (spawn < 16) {
                        for (int i = 1; 3 > i; i++) {
                            LivingEntity entity = (LivingEntity) za_warudo.spawnEntity(loc, EntityType.SKELETON);
                            entity.setCanPickupItems(true);
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
                        LivingEntity entity = (LivingEntity) za_warudo.spawnEntity(loc, EntityType.SKELETON);
                        entity.setCanPickupItems(true);
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
                    } else {
                        LivingEntity entity = (LivingEntity) za_warudo.spawnEntity(loc, EntityType.ZOMBIE);
                        entity.setCanPickupItems(true);
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

                    if (spawn < 16) {
                        for (int i = 1; 3 > i; i++) {
                            LivingEntity entity = (LivingEntity) za_warudo.spawnEntity(loc, EntityType.SKELETON);
                            entity.setCanPickupItems(true);
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
                        LivingEntity entity = (LivingEntity) za_warudo.spawnEntity(loc, EntityType.SKELETON);
                        entity.setCanPickupItems(true);
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
                    } else {
                        LivingEntity entity = (LivingEntity) za_warudo.spawnEntity(loc, EntityType.ZOMBIE);
                        entity.setCanPickupItems(true);
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

                    if (spawn < 16) {
                        for (int i = 1; 3 > i; i++) {
                            LivingEntity entity = (LivingEntity) za_warudo.spawnEntity(loc, EntityType.SKELETON);
                            entity.setCanPickupItems(true);
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
                        LivingEntity entity = (LivingEntity) za_warudo.spawnEntity(loc, EntityType.SKELETON);
                        entity.setCanPickupItems(true);
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
                    } else {
                        LivingEntity entity = (LivingEntity) za_warudo.spawnEntity(loc, EntityType.ZOMBIE);
                        entity.setCanPickupItems(true);
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

                    if (spawn < 16) {
                        for (int i = 1; 3 > i; i++) {
                            LivingEntity ent = (LivingEntity) za_warudo.spawnEntity(loc, EntityType.SKELETON);
                            ent.getEquipment().setHelmet(plugin.pumpkin());
                            ent.getEquipment().setHelmetDropChance(80.0F);
                            ent.getEquipment().setChestplateDropChance(0.0F);
                            ent.getEquipment().setLeggingsDropChance(0.0F);
                            ent.getEquipment().setBootsDropChance(0.0F);
                            ent.getEquipment().setItemInMainHandDropChance(0.0F);
                            ent.setCanPickupItems(true);
                            rand = random.ints(1, 100).findFirst().getAsInt();
                            int ench = random.ints(0, 4).findFirst().getAsInt();
                            if (rand > 80) {
                                ent.getEquipment().setChestplate(new ItemStack(Material.LEATHER_CHESTPLATE));
                                ent.getEquipment().setChestplate(new ItemStack(Material.LEATHER_LEGGINGS));
                            } else if (rand > 75) {
                                ItemStack aboba1 = new ItemStack(Material.LEATHER_CHESTPLATE);
                                ItemMeta boba = aboba1.getItemMeta();
                                boba.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, ench, false);
                                aboba1.setItemMeta(boba);
                                ent.getEquipment().setChestplate(aboba1);
                                ent.getEquipment().setChestplate(new ItemStack(Material.LEATHER_LEGGINGS));
                                ent.getEquipment().setChestplate(new ItemStack(Material.LEATHER_BOOTS));
                            } else if (rand > 65) {
                                ItemStack aboba1 = new ItemStack(Material.IRON_CHESTPLATE);
                                ItemMeta boba = aboba1.getItemMeta();
                                boba.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, ench, false);
                                aboba1.setItemMeta(boba);
                                ent.getEquipment().setChestplate(aboba1);
                                ent.getEquipment().setChestplate(new ItemStack(Material.IRON_LEGGINGS));
                            } else if (rand > 50) {
                                ItemStack aboba1 = new ItemStack(Material.CHAINMAIL_CHESTPLATE);
                                ItemMeta boba = aboba1.getItemMeta();
                                boba.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, ench, false);
                                aboba1.setItemMeta(boba);
                                ent.getEquipment().setChestplate(aboba1);
                                ent.getEquipment().setChestplate(new ItemStack(Material.CHAINMAIL_LEGGINGS));
                            } else if (rand > 40) {
                                ItemStack aboba1 = new ItemStack(Material.DIAMOND_CHESTPLATE);
                                ItemMeta boba = aboba1.getItemMeta();
                                boba.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, ench, false);
                                aboba1.setItemMeta(boba);
                                ent.getEquipment().setChestplate(aboba1);
                                ent.getEquipment().setChestplate(new ItemStack(Material.DIAMOND_LEGGINGS));
                            } else if (rand > 30) {
                                ItemStack aboba1 = new ItemStack(Material.NETHERITE_CHESTPLATE);
                                ItemMeta boba = aboba1.getItemMeta();
                                boba.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, ench, false);
                                aboba1.setItemMeta(boba);
                                ent.getEquipment().setChestplate(aboba1);
                            } else if (rand > 20) {
                                ItemStack aboba1 = new ItemStack(Material.NETHERITE_CHESTPLATE);
                                ItemMeta boba = aboba1.getItemMeta();
                                boba.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, ench, false);
                                aboba1.setItemMeta(boba);
                                ent.getEquipment().setChestplate(aboba1);
                                ent.getEquipment().setChestplate(new ItemStack(Material.NETHERITE_LEGGINGS));
                            }

                            rand = random.ints(1, 100).findFirst().getAsInt();
                            if (rand > 60) {
                                ent.getEquipment().setItemInMainHand(new ItemStack(Material.STONE_SWORD));
                            } else if (rand > 40) {
                                ent.getEquipment().setItemInMainHand(new ItemStack(Material.STONE_AXE));
                            }

                            rand = random.ints(1, 100).findFirst().getAsInt();
                            if (rand > 50) {
                                ent.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 999999, 1));
                            }
                        }
                    } else if (spawn < 21) {
                        LivingEntity ent = (LivingEntity) za_warudo.spawnEntity(loc, EntityType.SKELETON);
                        ent.getEquipment().setHelmet(plugin.pumpkin());
                        ent.getEquipment().setHelmetDropChance(80.0F);
                        ent.getEquipment().setChestplateDropChance(0.0F);
                        ent.getEquipment().setLeggingsDropChance(0.0F);
                        ent.getEquipment().setBootsDropChance(0.0F);
                        ent.getEquipment().setItemInMainHandDropChance(0.0F);
                        ent.setCanPickupItems(true);
                        rand = random.ints(1, 100).findFirst().getAsInt();
                        int ench = random.ints(0, 4).findFirst().getAsInt();
                        if (rand > 80) {
                            ent.getEquipment().setChestplate(new ItemStack(Material.LEATHER_CHESTPLATE));
                            ent.getEquipment().setChestplate(new ItemStack(Material.LEATHER_LEGGINGS));
                        } else if (rand > 75) {
                            ItemStack aboba1 = new ItemStack(Material.LEATHER_CHESTPLATE);
                            ItemMeta boba = aboba1.getItemMeta();
                            boba.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, ench, false);
                            aboba1.setItemMeta(boba);
                            ent.getEquipment().setChestplate(aboba1);
                            ent.getEquipment().setChestplate(new ItemStack(Material.LEATHER_LEGGINGS));
                            ent.getEquipment().setChestplate(new ItemStack(Material.LEATHER_BOOTS));
                        } else if (rand > 65) {
                            ItemStack aboba1 = new ItemStack(Material.IRON_CHESTPLATE);
                            ItemMeta boba = aboba1.getItemMeta();
                            boba.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, ench, false);
                            aboba1.setItemMeta(boba);
                            ent.getEquipment().setChestplate(aboba1);
                            ent.getEquipment().setChestplate(new ItemStack(Material.IRON_LEGGINGS));
                        } else if (rand > 50) {
                            ItemStack aboba1 = new ItemStack(Material.CHAINMAIL_CHESTPLATE);
                            ItemMeta boba = aboba1.getItemMeta();
                            boba.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, ench, false);
                            aboba1.setItemMeta(boba);
                            ent.getEquipment().setChestplate(aboba1);
                            ent.getEquipment().setChestplate(new ItemStack(Material.CHAINMAIL_LEGGINGS));
                        } else if (rand > 40) {
                            ItemStack aboba1 = new ItemStack(Material.DIAMOND_CHESTPLATE);
                            ItemMeta boba = aboba1.getItemMeta();
                            boba.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, ench, false);
                            aboba1.setItemMeta(boba);
                            ent.getEquipment().setChestplate(aboba1);
                            ent.getEquipment().setChestplate(new ItemStack(Material.DIAMOND_LEGGINGS));
                        } else if (rand > 30) {
                            ItemStack aboba1 = new ItemStack(Material.NETHERITE_CHESTPLATE);
                            ItemMeta boba = aboba1.getItemMeta();
                            boba.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, ench, false);
                            aboba1.setItemMeta(boba);
                            ent.getEquipment().setChestplate(aboba1);
                        } else if (rand > 20) {
                            ItemStack aboba1 = new ItemStack(Material.NETHERITE_CHESTPLATE);
                            ItemMeta boba = aboba1.getItemMeta();
                            boba.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, ench, false);
                            aboba1.setItemMeta(boba);
                            ent.getEquipment().setChestplate(aboba1);
                            ent.getEquipment().setChestplate(new ItemStack(Material.NETHERITE_LEGGINGS));
                        }

                        rand = random.ints(1, 100).findFirst().getAsInt();
                        if (rand > 60) {
                            ent.getEquipment().setItemInMainHand(new ItemStack(Material.STONE_SWORD));
                        } else if (rand > 40) {
                            ent.getEquipment().setItemInMainHand(new ItemStack(Material.STONE_AXE));
                        }

                        rand = random.ints(1, 100).findFirst().getAsInt();
                        if (rand > 50) {
                            ent.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 999999, 1));
                        }
                    } else {
                        LivingEntity ent = (LivingEntity) za_warudo.spawnEntity(loc, EntityType.ZOMBIE);
                        ent.getEquipment().setHelmet(plugin.pumpkin());
                        ent.getEquipment().setHelmetDropChance(80.0F);
                        ent.getEquipment().setChestplateDropChance(0.0F);
                        ent.getEquipment().setLeggingsDropChance(0.0F);
                        ent.getEquipment().setBootsDropChance(0.0F);
                        ent.getEquipment().setItemInMainHandDropChance(0.0F);
                        ent.setCanPickupItems(true);
                        rand = random.ints(1, 100).findFirst().getAsInt();
                        int ench = random.ints(0, 4).findFirst().getAsInt();
                        if (rand > 80) {
                            ent.getEquipment().setChestplate(new ItemStack(Material.LEATHER_CHESTPLATE));
                            ent.getEquipment().setChestplate(new ItemStack(Material.LEATHER_LEGGINGS));
                        } else if (rand > 75) {
                            ItemStack aboba = new ItemStack(Material.LEATHER_CHESTPLATE);
                            ItemMeta boba = aboba.getItemMeta();
                            boba.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, ench, false);
                            aboba.setItemMeta(boba);
                            ent.getEquipment().setChestplate(aboba);
                            ent.getEquipment().setChestplate(new ItemStack(Material.LEATHER_LEGGINGS));
                            ent.getEquipment().setChestplate(new ItemStack(Material.LEATHER_BOOTS));
                        } else if (rand > 65) {
                            ItemStack aboba = new ItemStack(Material.IRON_CHESTPLATE);
                            ItemMeta boba = aboba.getItemMeta();
                            boba.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, ench, false);
                            aboba.setItemMeta(boba);
                            ent.getEquipment().setChestplate(aboba);
                            ent.getEquipment().setChestplate(new ItemStack(Material.IRON_LEGGINGS));
                        } else if (rand > 50) {
                            ItemStack aboba = new ItemStack(Material.CHAINMAIL_CHESTPLATE);
                            ItemMeta boba = aboba.getItemMeta();
                            boba.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, ench, false);
                            aboba.setItemMeta(boba);
                            ent.getEquipment().setChestplate(aboba);
                            ent.getEquipment().setChestplate(new ItemStack(Material.CHAINMAIL_LEGGINGS));
                        } else if (rand > 40) {
                            ItemStack aboba = new ItemStack(Material.DIAMOND_CHESTPLATE);
                            ItemMeta boba = aboba.getItemMeta();
                            boba.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, ench, false);
                            aboba.setItemMeta(boba);
                            ent.getEquipment().setChestplate(aboba);
                            ent.getEquipment().setChestplate(new ItemStack(Material.DIAMOND_LEGGINGS));
                        } else if (rand > 30) {
                            ItemStack aboba = new ItemStack(Material.NETHERITE_CHESTPLATE);
                            ItemMeta boba = aboba.getItemMeta();
                            boba.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, ench, false);
                            aboba.setItemMeta(boba);
                            ent.getEquipment().setChestplate(aboba);
                        } else if (rand > 20) {
                            ItemStack aboba = new ItemStack(Material.NETHERITE_CHESTPLATE);
                            ItemMeta boba = aboba.getItemMeta();
                            boba.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, ench, false);
                            aboba.setItemMeta(boba);
                            ent.getEquipment().setChestplate(aboba);
                            ent.getEquipment().setChestplate(new ItemStack(Material.NETHERITE_LEGGINGS));
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
