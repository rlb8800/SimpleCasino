package simplecasino.simplecasino;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class InventoryListner implements Listener {

    @EventHandler
    public void onPlayerClickInventory(InventoryClickEvent e){
        if(e.getView().getTitle().equalsIgnoreCase("Gambling Machine")){
            if(e.getCurrentItem().getItemMeta() != null){
                if(e.getCurrentItem().getItemMeta().getDisplayName() != null){
                    if(e.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.RED + "Blunder")){
                        Player p = (Player) e.getWhoClicked();
                        e.setCancelled(true);
                    }
                    if(e.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.GREEN +  "Cash Out")){
                        Player p = (Player) e.getWhoClicked();
                        e.setCancelled(true);
                    }
                }
            }
        }

    }
}

