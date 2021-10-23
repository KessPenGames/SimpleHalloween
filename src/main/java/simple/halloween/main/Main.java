package simple.halloween.main;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
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

        File death = new File(getDataFolder() + File.separator + "death.yml");
        if(!death.exists()) {
            try {
                death.createNewFile();
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
}
