package simple.halloween.main;

import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.io.File;
import java.util.List;
import java.util.Set;

public class PardonPlayers implements Listener {
    private Main plugin;
    public PardonPlayers(Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler()
    public void ritual(BlockPlaceEvent event) {
        File logs_stats = new File(plugin.getDataFolder() + File.separator + "halloween.yml");
        FileConfiguration logs = YamlConfiguration.loadConfiguration(logs_stats);
        List<String> list = logs.getStringList("halloween");
        if (list.isEmpty()) return;
        if (!list.get(0).equals("true")) return;

        if (event.getItemInHand().getType() == Material.PLAYER_HEAD) {
            Block b = event.getBlock();
            ItemStack item = new ItemStack(b.getType(), 1, (short) 3);
            item.setItemMeta(event.getItemInHand().getItemMeta());
            SkullMeta sm = (SkullMeta) item.getItemMeta();
            boolean ban = Bukkit.getBanList(BanList.Type.NAME).isBanned(sm.getOwningPlayer().getName());
            if (ban) {
                Location getnether = new Location(b.getWorld(), b.getX(), b.getY() - 1, b.getZ());
                if (getnether.getBlock().getType() == Material.NETHERITE_BLOCK) {
                    getnether.getBlock().setType(Material.AIR);
                    b.setType(Material.AIR);
                    Bukkit.getBanList(BanList.Type.NAME).pardon(sm.getOwningPlayer().getName());
                    Bukkit.broadcastMessage(ChatColor.WHITE + "Душа " + sm.getOwningPlayer().getName() + " была освобождена!");
                }
            }
        }
    }

    @EventHandler
    public void onSignUse(PlayerInteractEvent event) {
        File logs_stats = new File(plugin.getDataFolder() + File.separator + "halloween.yml");
        FileConfiguration logs = YamlConfiguration.loadConfiguration(logs_stats);
        List<String> list = logs.getStringList("halloween");
        if (list.isEmpty()) return;
        if (!list.get(0).equals("true")) return;

        if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (event.getClickedBlock().getType() == Material.WARPED_WALL_SIGN) {
                Sign s = (Sign) event.getClickedBlock().getState();
                boolean ban = Bukkit.getBanList(BanList.Type.NAME).isBanned(s.getLine(0));
                if (!ban) return;
                Material coord1 = new Location(s.getWorld(), s.getX() - 1, s.getY(), s.getZ()).getBlock().getType();
                Material coord2 = new Location(s.getWorld(), s.getX() + 1, s.getY(), s.getZ()).getBlock().getType();
                Material coord3 = new Location(s.getWorld(), s.getX(), s.getY(), s.getZ() - 1).getBlock().getType();
                Material coord4 = new Location(s.getWorld(), s.getX(), s.getY(), s.getZ() + 1).getBlock().getType();
                if (coord1 == Material.NETHERITE_BLOCK || coord2 == Material.NETHERITE_BLOCK ||
                        coord3 == Material.NETHERITE_BLOCK || coord4 == Material.NETHERITE_BLOCK) {
                    Material block1 = new Location(s.getWorld(), s.getX() - 1, s.getY() + 1, s.getZ()).getBlock().getType();
                    Material block2 = new Location(s.getWorld(), s.getX() + 1, s.getY() + 1, s.getZ()).getBlock().getType();
                    Material block3 = new Location(s.getWorld(), s.getX(), s.getY() + 1, s.getZ() - 1).getBlock().getType();
                    Material block4 = new Location(s.getWorld(), s.getX(), s.getY() + 1, s.getZ() + 1).getBlock().getType();
                    if (block1 == Material.NETHERITE_BLOCK) {
                        new Location(s.getWorld(), s.getX() - 1, s.getY() + 1, s.getZ()).getBlock().setType(Material.AIR);
                        new Location(s.getWorld(), s.getX() - 1, s.getY(), s.getZ()).getBlock().setType(Material.AIR);
                        s.getBlock().setType(Material.AIR);

                        Bukkit.getBanList(BanList.Type.NAME).pardon(s.getLine(0));
                        Bukkit.broadcastMessage(ChatColor.WHITE + "Душа " + s.getLine(0) + " была освобождена!");
                    } else if (block2 == Material.NETHERITE_BLOCK) {
                        new Location(s.getWorld(), s.getX() + 1, s.getY() + 1, s.getZ()).getBlock().setType(Material.AIR);
                        new Location(s.getWorld(), s.getX() + 1, s.getY(), s.getZ()).getBlock().setType(Material.AIR);
                        s.getBlock().setType(Material.AIR);

                        Bukkit.getBanList(BanList.Type.NAME).pardon(s.getLine(0));
                        Bukkit.broadcastMessage(ChatColor.WHITE + "Душа " + s.getLine(0) + " была освобождена!");
                    } else if (block3 == Material.NETHERITE_BLOCK) {
                        new Location(s.getWorld(), s.getX(), s.getY() + 1, s.getZ() - 1).getBlock().setType(Material.AIR);
                        new Location(s.getWorld(), s.getX(), s.getY(), s.getZ() - 1).getBlock().setType(Material.AIR);
                        s.getBlock().setType(Material.AIR);

                        Bukkit.getBanList(BanList.Type.NAME).pardon(s.getLine(0));
                        Bukkit.broadcastMessage(ChatColor.WHITE + "Душа " + s.getLine(0) + " была освобождена!");
                    } else if (block4 == Material.NETHERITE_BLOCK) {
                        new Location(s.getWorld(), s.getX(), s.getY() + 1, s.getZ() + 1).getBlock().setType(Material.AIR);
                        new Location(s.getWorld(), s.getX(), s.getY(), s.getZ() + 1).getBlock().setType(Material.AIR);
                        s.getBlock().setType(Material.AIR);

                        Bukkit.getBanList(BanList.Type.NAME).pardon(s.getLine(0));
                        Bukkit.broadcastMessage(ChatColor.WHITE + "Душа " + s.getLine(0) + " была освобождена!");
                    }
                }
            }
        }
    }
}
