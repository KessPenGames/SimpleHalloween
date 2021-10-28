package simple.halloween.main;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
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
                if (coords.getY() < 60) event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 300, 1));
                if (coords.getY() < 60) event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.HUNGER, 300, 1));
                if (coords.getY() < 55) event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, 300, 1));
                if (coords.getY() < 55) event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 300, 1));
                if (coords.getY() < 55) event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.HUNGER, 300, 2));
                if (coords.getY() < 50) event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, 300, 2));
                if (coords.getY() < 50) event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 300, 2));
                if (coords.getY() < 50) event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.HUNGER, 300, 2));
                if (coords.getY() < 45) event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 200, 2));
                if (coords.getY() > 79) event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 300, 1));
                if (coords.getY() > 79) event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.HUNGER, 300, 1));
                if (coords.getY() > 89) event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 300, 1));
                if (coords.getY() > 89) event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.HUNGER, 300, 2));
                if (coords.getY() > 89) event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, 300, 1));
                if (coords.getY() > 99) event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 300, 2));
                if (coords.getY() > 99) event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.HUNGER, 300, 2));
                if (coords.getY() > 99) event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, 300, 2));
                if (coords.getY() > 99) event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 300, 1));
                if (coords.getY() > 109) event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 300, 2));
                if (coords.getY() > 109) event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.HUNGER, 300, 3));
                if (coords.getY() > 109) event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, 300, 3));
                if (coords.getY() > 109) event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 300, 2));
                if (coords.getY() > 119) event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 300, 2));
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
                if (p_list.isEmpty()) {
                    p_list = new ArrayList<String>();
                    p_list.add("Leave: 0");
                    p_list.add("Coords: " + player.getLocation().getX() + " " + player.getLocation().getY() + " " + player.getLocation().getZ());

                    p_logs.set(player.getName(), p_list);
                    try {
                        p_logs.save(players);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return;
                }
                String coord = p_list.get(1);
                String[] coordi = coord.split("Coords: ");
                coordi = coordi[1].split(" ");
                String[] xx = coordi[0].split("\\.");
                String[] zz = coordi[2].split("\\.");
                int x = Integer.parseInt(xx[0]);
                int z = Integer.parseInt(zz[0]);
                int sx = (int) (player.getLocation().getX() + 20);
                int sz = (int) player.getLocation().getZ();
                int sy = player.getWorld().getHighestBlockYAt(sx, sz);
                Location loc = player.getWorld().getBlockAt(sx, sy + 1, sz).getLocation();
                if (plugin.countmobs(player.getLocation()) > 59) return;
                if (coords.getX() < x + 10) {
                    Random random = new Random();
                    int health = random.ints(20, 100).findFirst().getAsInt();
                    int rand = random.ints(1, 100).findFirst().getAsInt();
                    int spawn = random.ints(1, 100).findFirst().getAsInt();
                    World za_warudo = player.getWorld();

                    if (spawn > 85) {
                        for (int i = 1; 6 > i; i++) {
                            LivingEntity ent = (LivingEntity) za_warudo.spawnEntity(loc, EntityType.CAVE_SPIDER);
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
                            LivingEntity ent = (LivingEntity) za_warudo.spawnEntity(loc, EntityType.SPIDER);
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
                            LivingEntity ent = (LivingEntity) za_warudo.spawnEntity(loc, EntityType.ZOMBIE);
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
                            LivingEntity ent = (LivingEntity) za_warudo.spawnEntity(loc, EntityType.SKELETON);
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
                        LivingEntity ent = (LivingEntity) za_warudo.spawnEntity(loc, EntityType.CREEPER);
                        rand = random.ints(1, 100).findFirst().getAsInt();
                    } else if (spawn > 45) {
                        za_warudo.spawnEntity(loc, EntityType.WITCH);
                        za_warudo.spawnEntity(loc, EntityType.WITCH);
                        za_warudo.spawnEntity(loc, EntityType.WITCH);
                        za_warudo.spawnEntity(loc, EntityType.WITCH);
                        za_warudo.spawnEntity(loc, EntityType.WITCH);
                    } else if (spawn > 35) {
                        for (int i = 1; 16 > i; i++) {
                            LivingEntity ent = (LivingEntity) za_warudo.spawnEntity(loc, EntityType.HUSK);

                            rand = random.ints(1, 100).findFirst().getAsInt();
                            if (rand > 30) {
                                ent.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 999999, 1));
                            }
                        }
                    } else if (spawn > 30) {
                        for (int i = 1; 9 > i; i++) {
                            LivingEntity ent = (LivingEntity) za_warudo.spawnEntity(loc, EntityType.STRAY);
                            rand = random.ints(1, 100).findFirst().getAsInt();
                            if (rand > 40) {
                                ent.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 999999, 1));
                            }
                        }
                    } else if (spawn > 20) {
                        za_warudo.spawnEntity(loc, EntityType.EVOKER);
                    } else if (spawn > 15) {
                        for (int i = 1; 4 > i; i++) {
                            LivingEntity ent = (LivingEntity) za_warudo.spawnEntity(loc, EntityType.WITHER_SKELETON);
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
                    } else if (spawn < 5) {
                        LivingEntity ent = (LivingEntity) za_warudo.spawnEntity(loc, EntityType.RAVAGER);
                        ent.setMaxHealth(1000.0F);
                        ent.setHealth(1000.0F);
                    } else {
                        for (int i = 1; 11 > i; i++) {
                            LivingEntity ent = (LivingEntity) za_warudo.spawnEntity(loc, EntityType.ZOMBIE);
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
                    prosto = 1;
                } else if (coords.getZ() < z + 10) {
                    Random random = new Random();
                    int health = random.ints(20, 100).findFirst().getAsInt();
                    int rand = random.ints(1, 100).findFirst().getAsInt();
                    int spawn = random.ints(1, 100).findFirst().getAsInt();
                    World za_warudo = player.getWorld();

                    if (spawn > 85) {
                        for (int i = 1; 6 > i; i++) {
                            LivingEntity ent = (LivingEntity) za_warudo.spawnEntity(loc, EntityType.CAVE_SPIDER);
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
                            LivingEntity ent = (LivingEntity) za_warudo.spawnEntity(loc, EntityType.SPIDER);
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
                            LivingEntity ent = (LivingEntity) za_warudo.spawnEntity(loc, EntityType.ZOMBIE);
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
                            LivingEntity ent = (LivingEntity) za_warudo.spawnEntity(loc, EntityType.SKELETON);
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
                        LivingEntity ent = (LivingEntity) za_warudo.spawnEntity(loc, EntityType.CREEPER);
                        rand = random.ints(1, 100).findFirst().getAsInt();
                    } else if (spawn > 45) {
                        za_warudo.spawnEntity(loc, EntityType.WITCH);
                        za_warudo.spawnEntity(loc, EntityType.WITCH);
                        za_warudo.spawnEntity(loc, EntityType.WITCH);
                        za_warudo.spawnEntity(loc, EntityType.WITCH);
                        za_warudo.spawnEntity(loc, EntityType.WITCH);
                    } else if (spawn > 35) {
                        for (int i = 1; 16 > i; i++) {
                            LivingEntity ent = (LivingEntity) za_warudo.spawnEntity(loc, EntityType.HUSK);

                            rand = random.ints(1, 100).findFirst().getAsInt();
                            if (rand > 30) {
                                ent.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 999999, 1));
                            }
                        }
                    } else if (spawn > 30) {
                        for (int i = 1; 9 > i; i++) {
                            LivingEntity ent = (LivingEntity) za_warudo.spawnEntity(loc, EntityType.STRAY);
                            rand = random.ints(1, 100).findFirst().getAsInt();
                            if (rand > 40) {
                                ent.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 999999, 1));
                            }
                        }
                    } else if (spawn > 20) {
                        za_warudo.spawnEntity(loc, EntityType.EVOKER);
                    } else if (spawn > 15) {
                        for (int i = 1; 4 > i; i++) {
                            LivingEntity ent = (LivingEntity) za_warudo.spawnEntity(loc, EntityType.WITHER_SKELETON);
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
                    } else if (spawn < 5) {
                        LivingEntity ent = (LivingEntity) za_warudo.spawnEntity(loc, EntityType.RAVAGER);
                        ent.setMaxHealth(1000.0F);
                        ent.setHealth(1000.0F);
                    } else {
                        for (int i = 1; 11 > i; i++) {
                            LivingEntity ent = (LivingEntity) za_warudo.spawnEntity(loc, EntityType.ZOMBIE);
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

                    if (spawn > 85) {
                        for (int i = 1; 6 > i; i++) {
                            LivingEntity ent = (LivingEntity) za_warudo.spawnEntity(loc, EntityType.CAVE_SPIDER);
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
                            LivingEntity ent = (LivingEntity) za_warudo.spawnEntity(loc, EntityType.SPIDER);
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
                            LivingEntity ent = (LivingEntity) za_warudo.spawnEntity(loc, EntityType.ZOMBIE);
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
                            LivingEntity ent = (LivingEntity) za_warudo.spawnEntity(loc, EntityType.SKELETON);
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
                        LivingEntity ent = (LivingEntity) za_warudo.spawnEntity(loc, EntityType.CREEPER);
                        rand = random.ints(1, 100).findFirst().getAsInt();
                    } else if (spawn > 45) {
                        za_warudo.spawnEntity(loc, EntityType.WITCH);
                        za_warudo.spawnEntity(loc, EntityType.WITCH);
                        za_warudo.spawnEntity(loc, EntityType.WITCH);
                        za_warudo.spawnEntity(loc, EntityType.WITCH);
                        za_warudo.spawnEntity(loc, EntityType.WITCH);
                    } else if (spawn > 35) {
                        for (int i = 1; 16 > i; i++) {
                            LivingEntity ent = (LivingEntity) za_warudo.spawnEntity(loc, EntityType.HUSK);

                            rand = random.ints(1, 100).findFirst().getAsInt();
                            if (rand > 30) {
                                ent.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 999999, 1));
                            }
                        }
                    } else if (spawn > 30) {
                        for (int i = 1; 9 > i; i++) {
                            LivingEntity ent = (LivingEntity) za_warudo.spawnEntity(loc, EntityType.STRAY);
                            rand = random.ints(1, 100).findFirst().getAsInt();
                            if (rand > 40) {
                                ent.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 999999, 1));
                            }
                        }
                    } else if (spawn > 20) {
                        za_warudo.spawnEntity(loc, EntityType.EVOKER);
                    } else if (spawn > 15) {
                        for (int i = 1; 4 > i; i++) {
                            LivingEntity ent = (LivingEntity) za_warudo.spawnEntity(loc, EntityType.WITHER_SKELETON);
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
                    } else if (spawn < 5) {
                        LivingEntity ent = (LivingEntity) za_warudo.spawnEntity(loc, EntityType.RAVAGER);
                        ent.setMaxHealth(1000.0F);
                        ent.setHealth(1000.0F);
                    } else {
                        for (int i = 1; 11 > i; i++) {
                            LivingEntity ent = (LivingEntity) za_warudo.spawnEntity(loc, EntityType.ZOMBIE);
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

                    if (spawn > 85) {
                        for (int i = 1; 6 > i; i++) {
                            LivingEntity ent = (LivingEntity) za_warudo.spawnEntity(loc, EntityType.CAVE_SPIDER);
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
                            LivingEntity ent = (LivingEntity) za_warudo.spawnEntity(loc, EntityType.SPIDER);
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
                            LivingEntity ent = (LivingEntity) za_warudo.spawnEntity(loc, EntityType.ZOMBIE);
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
                            LivingEntity ent = (LivingEntity) za_warudo.spawnEntity(loc, EntityType.SKELETON);
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
                        LivingEntity ent = (LivingEntity) za_warudo.spawnEntity(loc, EntityType.CREEPER);
                        rand = random.ints(1, 100).findFirst().getAsInt();
                    } else if (spawn > 45) {
                        za_warudo.spawnEntity(loc, EntityType.WITCH);
                        za_warudo.spawnEntity(loc, EntityType.WITCH);
                        za_warudo.spawnEntity(loc, EntityType.WITCH);
                        za_warudo.spawnEntity(loc, EntityType.WITCH);
                        za_warudo.spawnEntity(loc, EntityType.WITCH);
                    } else if (spawn > 35) {
                        for (int i = 1; 16 > i; i++) {
                            LivingEntity ent = (LivingEntity) za_warudo.spawnEntity(loc, EntityType.HUSK);

                            rand = random.ints(1, 100).findFirst().getAsInt();
                            if (rand > 30) {
                                ent.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 999999, 1));
                            }
                        }
                    } else if (spawn > 30) {
                        for (int i = 1; 9 > i; i++) {
                            LivingEntity ent = (LivingEntity) za_warudo.spawnEntity(loc, EntityType.STRAY);
                            rand = random.ints(1, 100).findFirst().getAsInt();
                            if (rand > 40) {
                                ent.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 999999, 1));
                            }
                        }
                    } else if (spawn > 20) {
                        za_warudo.spawnEntity(loc, EntityType.EVOKER);
                    } else if (spawn > 15) {
                        for (int i = 1; 4 > i; i++) {
                            LivingEntity ent = (LivingEntity) za_warudo.spawnEntity(loc, EntityType.WITHER_SKELETON);
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
                    } else if (spawn < 5) {
                        LivingEntity ent = (LivingEntity) za_warudo.spawnEntity(loc, EntityType.RAVAGER);
                        ent.setMaxHealth(1000.0F);
                        ent.setHealth(1000.0F);
                    } else {
                        for (int i = 1; 11 > i; i++) {
                            LivingEntity ent = (LivingEntity) za_warudo.spawnEntity(loc, EntityType.ZOMBIE);
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
