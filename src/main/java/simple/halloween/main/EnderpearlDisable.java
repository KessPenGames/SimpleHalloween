package simple.halloween.main;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;

import java.io.File;
import java.util.List;

public class EnderpearlDisable implements Listener {
    private Main plugin;
    public EnderpearlDisable(Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler()
    public void enderPearlThrown(PlayerTeleportEvent event) {
        File logs_stats = new File(plugin.getDataFolder() + File.separator + "halloween.yml");
        FileConfiguration logs = YamlConfiguration.loadConfiguration(logs_stats);
        List<String> list = logs.getStringList("halloween");
        if (list.isEmpty()) return;
        if (list.get(0).equals("true")) {
            if (event.getPlayer().getGameMode().toString().equals("SURVIVAL")) {
                if (event.getCause() != PlayerTeleportEvent.TeleportCause.ENDER_PEARL) return;
                event.setCancelled(true);
                double health = event.getPlayer().getHealth();
                event.getPlayer().setHealth(health - 5);
            }
        }
    }
}
