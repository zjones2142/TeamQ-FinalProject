package edu.mu.PluginProject.commands;


import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetHome implements CommandExecutor {
	
	private final Map<UUID, Location> playerHomeLocations;
	
	public SetHome()
	{
		this.playerHomeLocations = new HashMap<>();
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) 
	{
		// TODO Auto-generated method stub
		if (!(sender instanceof Player))
		{
			sender.sendMessage("Only players can use this command.");
			return false;
		}
		
		Player p = (Player) sender;
		Location loc = p.getLocation();
		if(getPlayerHomeLocation(p) != loc)
		{
			setPlayerHomeLocation(p, loc);
			Location loc1 = getPlayerHomeLocation(p); 
			p.sendMessage("Home Set at ("+getLocationXYZText(loc1)+")");
			return true;
		}
		else
		{
			p.sendMessage("Home already set at this location.");
			return false;
		}
	}
	
	public Location getPlayerHomeLocation(Player p) 
	{
		return this.playerHomeLocations.get(p.getUniqueId());
	}
	
	public String getLocationXYZText(Location loc)
	{
		int[] coords = {loc.getBlockX(), loc.getBlockY(), loc.getBlockZ()};
		String text = "X: "+coords[0]+" | Y: "+coords[1]+" | Z: "+coords[2];
		return text;
	}
	
	public Map<UUID, Location> getPlayerHomeLocations() 
	{
		return this.playerHomeLocations;
	}

	public void setPlayerHomeLocation(Player p, Location loc) 
	{
		this.playerHomeLocations.put(p.getUniqueId(), loc);
	}
}
