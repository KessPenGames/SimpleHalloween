package simple.halloween.main;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public final class Main extends JavaPlugin {
    List<String> mob_wl = new ArrayList<String>();

    @Override
    public void onEnable() {
        File config = new File(getDataFolder() + File.separator + "config.yml");
        if(!config.exists()) {
            getConfig().options().copyDefaults(true);
            saveDefaultConfig();
        }

        File halloween = new File(getDataFolder() + File.separator + "halloween.yml");
        if(!halloween.exists()) {
            try {
                halloween.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        File players = new File(getDataFolder() + File.separator + "players.yml");
        if(!players.exists()) {
            try {
                players.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        Bukkit.getPluginManager().registerEvents(new MobsSpawn(this), this);
        Bukkit.getPluginManager().registerEvents(new PlayerMove(this), this);
        Bukkit.getPluginManager().registerEvents(new DisableTotems(this), this);
        Bukkit.getPluginManager().registerEvents(new CreeperExplosion(this), this);
        Bukkit.getPluginManager().registerEvents(new PlayerDeath(this), this);
        Bukkit.getPluginManager().registerEvents(new LeaveCount(this), this);
        Bukkit.getPluginManager().registerEvents(new EnderpearlDisable(this), this);
        Bukkit.getPluginManager().registerEvents(new GappleDisable(this), this);
        Bukkit.getPluginManager().registerEvents(new PardonPlayers(this), this);
        getCommand("halloween").setExecutor(new HalloweenCommand(this));
        getCommand("halloween").setTabCompleter(new HalloweenTab(this));
        getLogger().info("Started up!");
    }

    @Override
    public void onDisable() {
        getLogger().info("Disabled up!");
    }

    public ItemStack pumpkin() {
        return new ItemStack(Material.JACK_O_LANTERN);
    }

    public static Entity[] getNearbyEntities(Location l, int radius) {
        int chunkRadius = radius < 16 ? 1 : (radius - (radius % 16)) / 16;
        HashSet <Entity> radiusEntities = new HashSet< Entity >();

        for (int chX = 0 - chunkRadius; chX <= chunkRadius; chX++) {
            for (int chZ = 0 - chunkRadius; chZ <= chunkRadius; chZ++) {
                int x = (int) l.getX(), y = (int) l.getY(), z = (int) l.getZ();
                for (Entity e: new Location(l.getWorld(), x + (chX * 16), y, z + (chZ * 16)).getChunk().getEntities()) {
                    if (e.getLocation().distance(l) <= radius && e.getLocation().getBlock() != l.getBlock())
                        radiusEntities.add(e);
                }
            }
        }

        return radiusEntities.toArray(new Entity[radiusEntities.size()]);
    }

    public int countmobs(Location loc) {
        int mobcount = 0;
        for (Entity ent : getNearbyEntities(loc, 25)) { // Gets entities within 5 blocks of given location
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
