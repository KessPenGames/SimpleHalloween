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
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
                ent.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 999999, 1));
                ent.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 999999, 2));
                Random random = new Random();
                int rand = random.ints(1, 100).findFirst().getAsInt();
                String death = "разорвана";
                if (20 > rand) death = ChatColor.MAGIC + "выебанна";
                else if (30 > rand) death = "украдена";
                else if (55 > rand) death = "потеряна";
                else if (85 > rand) death = "съедена";
                event.setDeathMessage(ChatColor.RED + "Душа " + event.getEntity().getName() + " была " + death);

                File death_list = new File(plugin.getDataFolder() + File.separator + "death.yml");
                FileConfiguration death_logs = YamlConfiguration.loadConfiguration(death_list);
                List<String> listik = new ArrayList<String>();
                listik.add("true");

                death_logs.set(event.getEntity().getName(), listik);
                try {
                    death_logs.save(death_list);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                event.getEntity().kickPlayer(ChatColor.RED + "Вас поглотила тьма\n (ждите межсезонья)");
            }
        }
    }
}
