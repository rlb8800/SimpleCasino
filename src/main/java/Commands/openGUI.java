package Commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import simplecasino.simplecasino.SimpleCasino;

public class openGUI implements CommandExecutor {


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player){
            Player p = (Player) sender;
            Inventory inv = Bukkit.createInventory(null, 45, "Gambling Machine");
            ItemStack redpane = new ItemStack(Material.RED_STAINED_GLASS_PANE);
            ItemStack greenpane = new ItemStack(Material.GREEN_STAINED_GLASS_PANE);
            ItemMeta meta1 = redpane.getItemMeta();
            ItemMeta meta2 = greenpane.getItemMeta();
            meta1.setDisplayName((ChatColor.RED + "Blunder"));
            meta2.setDisplayName((ChatColor.GREEN + "Cash Out"));
            redpane.setItemMeta(meta1);
            greenpane.setItemMeta(meta2);
            inv.setItem(18, redpane);
            inv.setItem(20, redpane);
            inv.setItem(22, redpane);
            inv.setItem(24, redpane);
            inv.setItem(26, redpane);
            inv.setItem(19, greenpane);
            inv.setItem(21, greenpane);
            inv.setItem(23, greenpane);
            inv.setItem(25, greenpane);
            p.openInventory(inv);
            return true;
        }
        return false;
    }
}