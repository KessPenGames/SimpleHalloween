package simple.halloween.main;

import org.bukkit.*;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
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

            File players = new File(plugin.getDataFolder() + File.separator + "players.yml");
            FileConfiguration p_logs = YamlConfiguration.loadConfiguration(players);
            List<String> p_list = p_logs.getStringList(player.getName());
            if (p_list.isEmpty()) return;
            if (player.getGameMode().toString().equals("SURVIVAL")) {
                String leave = p_list.get(0);
                String[] leave1 = leave.split("Leave: ");
                int aboba1 = Integer.parseInt(leave1[1]);
                int leavecount = plugin.getConfig().getInt("leave-count");
                if (aboba1 > leavecount) {
                    Random random = new Random();
                    int rand = random.ints(1, 100).findFirst().getAsInt();
                    int spawn = random.ints(1, 100).findFirst().getAsInt();
                    World za_warudo = player.getWorld();

                    int sx = (int) (player.getLocation().getX() + 10);
                    int sz = (int) player.getLocation().getZ();
                    int sy = player.getWorld().getHighestBlockYAt(sx, sz);
                    Location coords = za_warudo.getBlockAt(sx, sy + 1, sz).getLocation();
                    if (plugin.countmobs(player.getLocation()) > 59) return;

                    if (spawn > 85) {
                        for (int i = 1; 6 > i; i++) {
                            LivingEntity ent = (LivingEntity) za_warudo.spawnEntity(coords, EntityType.CAVE_SPIDER);
                            rand = random.ints(1, 100).findFirst().getAsInt();
                            if (rand > 70) {
                                ent.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 999999, 1));
                            } else if (rand > 50) {
                                ent.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 999999, 1));
                            } else if (rand > 30) {
                                ent.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 999999, 1));
                                ent.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 999999, 1));
                            }
                        }
                    } else if (spawn > 80) {
                        for (int i = 1; 6 > i; i++) {
                            LivingEntity ent = (LivingEntity) za_warudo.spawnEntity(coords, EntityType.SPIDER);
                            rand = random.ints(1, 100).findFirst().getAsInt();
                            if (rand > 70) {
                                ent.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 999999, 1));
                            } else if (rand > 50) {
                                ent.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 999999, 1));
                            } else if (rand > 30) {
                                ent.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 999999, 1));
                                ent.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 999999, 1));
                            }
                        }
                    } else if (spawn > 75) {
                        for (int i = 1; 6 > i; i++) {
                            LivingEntity ent = (LivingEntity) za_warudo.spawnEntity(coords, EntityType.ZOMBIE);
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
                        }
                    } else if (spawn > 60) {
                        for (int i = 1; 6 > i; i++) {
                            LivingEntity ent = (LivingEntity) za_warudo.spawnEntity(coords, EntityType.SKELETON);
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
                        }
                    } else if (spawn > 50) {
                        za_warudo.spawnEntity(coords, EntityType.WITCH);
                        za_warudo.spawnEntity(coords, EntityType.WITCH);
                        za_warudo.spawnEntity(coords, EntityType.WITCH);
                        za_warudo.spawnEntity(coords, EntityType.WITCH);
                        za_warudo.spawnEntity(coords, EntityType.WITCH);
                    } else if (spawn > 45) {
                        for (int i = 1; 16 > i; i++) {
                            LivingEntity ent = (LivingEntity) za_warudo.spawnEntity(coords, EntityType.HUSK);

                            rand = random.ints(1, 100).findFirst().getAsInt();
                            if (rand > 30) {
                                ent.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 999999, 1));
                            }
                        }
                    } else if (spawn > 40) {
                        for (int i = 1; 9 > i; i++) {
                            LivingEntity ent = (LivingEntity) za_warudo.spawnEntity(coords, EntityType.STRAY);
                            rand = random.ints(1, 100).findFirst().getAsInt();
                            if (rand > 40) {
                                ent.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 999999, 1));
                            }
                        }
                    } else if (spawn > 30) {
                        za_warudo.spawnEntity(coords, EntityType.EVOKER);
                    } else if (spawn > 25) {
                        for (int i = 1; 4 > i; i++) {
                            LivingEntity ent = (LivingEntity) za_warudo.spawnEntity(coords, EntityType.WITHER_SKELETON);
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
                        }
                    } else if (spawn < 2) {
                        LivingEntity ent = (LivingEntity) za_warudo.spawnEntity(coords, EntityType.RAVAGER);
                        ent.setMaxHealth(1000.0F);
                        ent.setHealth(1000.0F);
                    } else {
                        for (int i = 1; 11 > i; i++) {
                            LivingEntity ent = (LivingEntity) za_warudo.spawnEntity(coords, EntityType.ZOMBIE);
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
                        }
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
