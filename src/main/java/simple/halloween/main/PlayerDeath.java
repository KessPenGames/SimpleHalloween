package simple.halloween.main;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.io.File;
import java.util.List;

public class PlayerDeath implements Listener {
    private Main plugin;
    public PlayerDeath(Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        File logs_stats = new File(plugin.getDataFolder() + File.separator + "halloween.yml");
        FileConfiguration logs = YamlConfiguration.loadConfiguration(logs_stats);
        List<String> list = logs.getStringList("halloween");
        if (list.isEmpty()) return;
        if (list.get(0).equals("true")) {
            if (event.getEntity().getGameMode().toString().equals("SURVIVAL")) {
                LivingEntity ent = (LivingEntity) event.getEntity().getWorld().spawnEntity(event.getEntity().getLocation(), EntityType.ZOMBIE);
                ent.setCanPickupItems(true);
                ent.setCustomName(event.getEntity().getName());
                ent.setCustomNameVisible(true);
                ent.setMaxHealth(100.0F);
                ent.setHealth(40.0F);
                ent.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 999999, 1));
                ent.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 999999, 2));
                event.getEntity().setGameMode(GameMode.SPECTATOR);
                event.setDeathMessage(ChatColor.BLACK + "Игрока " + event.getEntity().getName() + " поглотила тьма...");
            }
        }
    }
}
