package simple.halloween.main;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityResurrectEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import java.io.File;
import java.util.List;

public class DisableTotems implements Listener {
    private Main plugin;
    public DisableTotems(Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void useTotem(EntityResurrectEvent event) {
        File logs_stats = new File(plugin.getDataFolder() + File.separator + "halloween.yml");
        FileConfiguration logs = YamlConfiguration.loadConfiguration(logs_stats);
        List<String> list = logs.getStringList("halloween");
        if (list.isEmpty()) return;
        if (list.get(0).equals("true")) {
            event.setCancelled(true);
        }
    }
}
