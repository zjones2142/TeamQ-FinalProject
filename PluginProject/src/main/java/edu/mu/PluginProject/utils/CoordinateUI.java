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

	public CoordinateUI() 
	{
		this.inv = Bukkit.createInventory(null, 9, "Saved Locations:");
		//initInv();
	}
	
	public void initInv()
	{
		this.inv.addItem(createGuiItem(Material.COMPASS, "PlaceHolder", 1, "0, 0, 0"));
	}
	
	public void addGuiItem(final String name, final int num, final String... lore)
	{
		this.inv.addItem(createGuiItem(Material.MAP, name, num, lore));
	}
	
	public void openInventory(final HumanEntity ent) 
	{
        ent.openInventory(this.inv);
    }
	
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
	
	public int getNumInvSlotsRemaining()
	{
		int invSize = this.inv.getSize();
		int remainingSlots = this.inv.getStorageContents().length;
		return invSize - remainingSlots;
	}
	
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
	
	@EventHandler
    public void onInventoryClick(final InventoryDragEvent e) 
	{
        if (e.getInventory().equals(inv)) 
        {
          e.setCancelled(true);
        }
    }
}
