package simple.halloween.main;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public final class Main extends JavaPlugin {
    List<String> mob_wl = new ArrayList<String>();

    @Override
    public void onEnable() {
        mob_wl.add("ZOMBIE");
        mob_wl.add("SKELETON");
        mob_wl.add("CREEPER");
        mob_wl.add("SPIDER");

        Bukkit.getPluginManager().registerEvents(new MobsSpawn(this), this);
        Bukkit.getPluginManager().registerEvents(new PlayerMove(this), this);
        Bukkit.getPluginManager().registerEvents(new DisableTotems(this), this);
        Bukkit.getPluginManager().registerEvents(new CreeperExplosion(this), this);
        Bukkit.getPluginManager().registerEvents(new PlayerDeath(this), this);
        Bukkit.getPluginManager().registerEvents(new LeaveCount(this), this);
        getCommand("halloween").setExecutor(new HalloweenCommand(this));
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
