package simple.halloween.main;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Creeper;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;

import java.io.File;
import java.util.List;

public class CreeperExplosion implements Listener {
    private Main plugin;
    public CreeperExplosion(Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onEntityExplode(EntityExplodeEvent event) {
        File logs_stats = new File(plugin.getDataFolder() + File.separator + "halloween.yml");
        FileConfiguration logs = YamlConfiguration.loadConfiguration(logs_stats);
        List<String> list = logs.getStringList("halloween");
        if (list.isEmpty()) return;
        if (list.get(0).equals("true")) {
            if (event.getEntity() instanceof Creeper) {
                event.setCancelled(true);
                event.getLocation().getWorld().createExplosion(event.getLocation(), 13);
            }
        }
    }
}
