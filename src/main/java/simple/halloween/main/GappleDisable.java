package simple.halloween.main;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;

import java.io.File;
import java.util.List;

public class GappleDisable implements Listener {
    private Main plugin;
    public GappleDisable(Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler()
    public void eat(PlayerItemConsumeEvent event) {
        File logs_stats = new File(plugin.getDataFolder() + File.separator + "halloween.yml");
        FileConfiguration logs = YamlConfiguration.loadConfiguration(logs_stats);
        List<String> list = logs.getStringList("halloween");
        if (list.isEmpty()) return;
        if (list.get(0).equals("true")) {
            if (event.getPlayer().getGameMode().toString().equals("SURVIVAL")) {
                if (event.getItem().getType() == Material.GOLDEN_APPLE) event.setCancelled(true);
                if (event.getItem().getType() == Material.ENCHANTED_GOLDEN_APPLE) event.setCancelled(true);
            }
        }
    }
}
