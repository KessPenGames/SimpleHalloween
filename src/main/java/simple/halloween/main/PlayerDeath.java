package simple.halloween.main;

import org.bukkit.*;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.io.File;
import java.io.IOException;
import java.util.*;

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
                ItemStack item = new ItemStack(Material.PLAYER_HEAD, 1, (short) 3);
                SkullMeta sm = (SkullMeta) item.getItemMeta();
                sm.setOwner(event.getEntity().getName());
                sm.addEnchant(Enchantment.BINDING_CURSE, 3, true);
                item.setItemMeta(sm);

                LivingEntity ent = (LivingEntity) event.getEntity().getWorld().spawnEntity(event.getEntity().getLocation(), EntityType.ZOMBIE);
                ent.setCanPickupItems(true);
                ent.setCustomName(event.getEntity().getName());
                ent.setCustomNameVisible(true);
                ent.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 999999, 1));
                ent.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 999999, 2));
                ent.getEquipment().setHelmet(item);
                ent.getEquipment().setHelmetDropChance(100.0F);
                Random random = new Random();
                int rand = random.ints(1, 100).findFirst().getAsInt();
                String death = "разорвана";
                if (2 > rand) death = ChatColor.MAGIC + "выебанна";
                else if (30 > rand) death = "украдена";
                else if (55 > rand) death = "потеряна";
                else if (85 > rand) death = "съедена";
                event.setDeathMessage(ChatColor.RED + "Душа " + event.getEntity().getName() + " была " + death);

                Date date = new GregorianCalendar(2022, Calendar.SEPTEMBER, 20).getTime();
                Bukkit.getBanList(BanList.Type.NAME).addBan(event.getEntity().getName(), ChatColor.RED + "Вас поглотила тьма\n(ждите межсезонья)",
                        date, ChatColor.BLACK + "" + ChatColor.MAGIC + "Шлёпа шлёпский");
                event.getEntity().kickPlayer(ChatColor.RED + "Вас поглотила тьма\n(ждите межсезонья)");
            }
        }
    }
}
