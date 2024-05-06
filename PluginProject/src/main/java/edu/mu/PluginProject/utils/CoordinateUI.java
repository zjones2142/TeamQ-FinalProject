package edu.mu.PluginProject.utils;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class CoordinateUI implements Listener{
	private final Inventory inv;

	//constructor (creates new minecraft chest inventory)
	public CoordinateUI() 
	{
		this.inv = Bukkit.createInventory(null, 9, "Saved Locations:");
		//initInv();
	}
	
	//adds new ItemStack to next available inventory slot, item(s) will have specified text via name and lore parameters
	public void addGuiItem(final String name, final int num, final String... lore)
	{
		this.inv.addItem(createGuiItem(Material.MAP, name, num, lore));
	}
	
	//opens the inventory on specified players screen
	public void openInventory(final HumanEntity ent) 
	{
        ent.openInventory(this.inv);
    }
	
	//creates a gui element using the provided opjects from spigot libraries, used for creating new in-game objects/entities
	protected ItemStack createGuiItem(final Material material, final String name, final int num, final String... lore)
	{
        final ItemStack item = new ItemStack(material, num);
        final ItemMeta meta = item.getItemMeta();

        // Set the name of the item
        meta.setDisplayName(name);

        // Set the lore of the item
        meta.setLore(Arrays.asList(lore));

        item.setItemMeta(meta);

        return item;
    }
	
	//returns the number of empty slots in an inventory object
	public int getNumInvSlotsRemaining()
	{
		int invSize = this.inv.getSize();
		int remainingSlots = this.inv.getStorageContents().length;
		return invSize - remainingSlots;
	}
	
	//executes when a player clicks inside of the inventory
	//handles preventing the player from removing/adding objects to the inventory specified for display only
	@EventHandler
	public void onInventoryClick(final InventoryClickEvent e)
	{
        if (!e.getInventory().equals(inv)) return;

        e.setCancelled(true);

        final ItemStack clickedItem = e.getCurrentItem();

        // verify current item is not null
        if (clickedItem == null || clickedItem.getType().isAir()) return;

        final Player p = (Player) e.getWhoClicked();

        // Using slots click is a best option for your inventory click's
        p.sendMessage("You clicked at slot " + e.getRawSlot());
    }
	
	//executes at the same time as previous event handler
	//handles same event as previous, but for drag rather than click
	@EventHandler
    public void onInventoryClick(final InventoryDragEvent e) 
	{
        if (e.getInventory().equals(inv)) 
        {
          e.setCancelled(true);
        }
    }
}
