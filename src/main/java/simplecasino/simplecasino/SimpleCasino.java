package simplecasino.simplecasino;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class SimpleCasino extends JavaPlugin {

    private static Economy econ = null;


    List<Inventory> invs = new ArrayList<Inventory>();
    public static ItemStack[] contents;
    private int itemIndex = 0;



    @Override
    public void onEnable() {
        // Plugin startup logic
        if (!setupEconomy()){
            System.out.println("No Economy plugin detected");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    private boolean setupEconomy(){
        if (getServer().getPluginManager().getPlugin("Vault") == null){
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null){
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }

    public static Economy getEconomy() {return econ;}

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (label.equalsIgnoreCase("gamble")){
            if (!(sender instanceof  Player)){
                return true;
            }
            Player player = (Player) sender;
            spin(player);
        }
        return false;
    }
    //chance
    public void chance(Inventory inv){
        if (contents == null){ //if there is nothing in the item stack array we need to add items to the item stack array
            ItemStack[] items = new ItemStack[10];

            items[0] = new ItemStack(Material.RED_STAINED_GLASS_PANE);
            items[1] = new ItemStack(Material.GREEN_STAINED_GLASS_PANE);
            items[2] = new ItemStack(Material.RED_STAINED_GLASS_PANE);
            items[3] = new ItemStack(Material.GREEN_STAINED_GLASS_PANE);
            items[4] = new ItemStack(Material.RED_STAINED_GLASS_PANE);
            items[5] = new ItemStack(Material.GREEN_STAINED_GLASS_PANE);
            items[6] = new ItemStack(Material.RED_STAINED_GLASS_PANE);
            items[7] = new ItemStack(Material.GREEN_STAINED_GLASS_PANE);
            items[8] = new ItemStack(Material.RED_STAINED_GLASS_PANE);
            items[9] = new ItemStack(Material.GREEN_STAINED_GLASS_PANE);
            contents = items;
        }

        int startingIndex = ThreadLocalRandom.current().nextInt(contents.length); //Randomizes the starting value of the list
        for (int index = 0; index < startingIndex; index++){ //Go through all the items in the index
            for (int itemstacks = 18; itemstacks < 27; itemstacks++) {  //want the gambling row to be in the middle
                inv.setItem(itemstacks, contents[(itemstacks +  itemIndex) % contents.length]);
            }
            itemIndex++;
        }
        //Customize
        ItemStack item = new ItemStack(Material.REDSTONE);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.DARK_GRAY + "!");
        item.setItemMeta(meta);
        inv.setItem(13, item);
    }


    //spinning gui
    public void spin(final Player player){
        Inventory inv = Bukkit.createInventory(null, 45, ChatColor.RED + "" + ChatColor.RED +  "50-50 Machine");
        chance(inv);
        invs.add(inv); //add this inventory to the invs list
        player.openInventory(inv);

        Random r = new Random();
        double seconds = 6.0 + (8.0 - 6.0) * r.nextDouble(); //randomly select time between 6/8 seconds

        new BukkitRunnable(){ //run right when they open the gui
            double delay = 0;
            int ticks = 0;
            boolean done = false;

            public void run(){
                if (done)
                    return;
                ticks++;
                delay += 1/ (20 * seconds);
                if (ticks > delay * 10){
                    ticks = 0;

                    for (int itemstacks = 18; itemstacks < 27; itemstacks ++)
                        inv.setItem(itemstacks, contents[(itemstacks +  itemIndex) % contents.length]);
                    itemIndex++;

                    if (delay >= 0.5){
                        done = true;
                        new BukkitRunnable(){
                            public void run(){
                                ItemStack item = inv.getItem(22);
                                player.getInventory().addItem(item);
                                player.updateInventory();
                                player.closeInventory();
                                cancel();
                            }
                        }.runTaskLater(SimpleCasino.getPlugin(SimpleCasino.class), 50);
                        cancel();
                    }
                }
            }

        }.runTaskTimer(this, 0, 1);


    }
}

