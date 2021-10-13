package simple.halloween.main;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameRule;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HalloweenCommand implements CommandExecutor, Listener {
    private Main plugin;
    public HalloweenCommand(Main plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player)) {
            return true;
        }

        if (args[0].equals("start")) {
            if (!sender.hasPermission("simple.halloween.halloween.start")) return true;

            File logs_stats = new File(plugin.getDataFolder() + File.separator + "halloween.yml");
            FileConfiguration logs = YamlConfiguration.loadConfiguration(logs_stats);
            List<String> list = logs.getStringList("halloween");
            list = new ArrayList<String>();
            list.add("true");

            logs.set("halloween", list);
            try {
                logs.save(logs_stats);
            } catch (IOException e) {
                e.printStackTrace();
            }

            Bukkit.broadcastMessage(ChatColor.BLACK + "Да начн" + ChatColor.MAGIC + "ётся весе" + ChatColor.BLACK + "лье!");

            Bukkit.getWorld("world").setAmbientSpawnLimit(0);
            Bukkit.getWorld("world").setAnimalSpawnLimit(0);
            Bukkit.getWorld("world").setWaterAmbientSpawnLimit(0);
            Bukkit.getWorld("world").setWaterAnimalSpawnLimit(0);
            Bukkit.getWorld("world").setMonsterSpawnLimit(80);
            Bukkit.getWorld("world").setGameRule(GameRule.DO_DAYLIGHT_CYCLE, false);
            Bukkit.getWorld("world").setTime(20000);
        } else if (args[0].equals("stop")) {
            if (!sender.hasPermission("simple.halloween.halloween.stop")) return true;

            File logs_stats = new File(plugin.getDataFolder() + File.separator + "halloween.yml");
            FileConfiguration logs = YamlConfiguration.loadConfiguration(logs_stats);
            List<String> list = logs.getStringList("halloween");
            list = new ArrayList<String>();
            list.add("false");

            logs.set("halloween", list);
            try {
                logs.save(logs_stats);
            } catch (IOException e) {
                e.printStackTrace();
            }

            Bukkit.broadcastMessage(ChatColor.BLACK + "Упс... походу измерением ошиблись...");

            Bukkit.getWorld("world").setGameRule(GameRule.DO_DAYLIGHT_CYCLE, true);
            Bukkit.getWorld("world").setTime(0);
        } else {
            sender.sendMessage(ChatColor.RED + "Вы не правильно ввели комманду!");
        }

        return true;
    }
}
