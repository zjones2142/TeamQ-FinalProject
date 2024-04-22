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
		p.sendMessage("Home Set!");
		if(getPlayerHomeLocation(p) != loc)
		{
			setPlayerHomeLocation(p, loc);
		}
		return true;
	}
	
	public Location getPlayerHomeLocation(Player p) 
	{
		return this.playerHomeLocations.get(p.getUniqueId());
	}

	public void setPlayerHomeLocation(Player p, Location loc) 
	{
		if(this.playerHomeLocations.containsKey(p.getUniqueId()))
		{
			this.playerHomeLocations.remove(p.getUniqueId());
			this.playerHomeLocations.put(p.getUniqueId(), loc);
		}
		else
		{
			this.playerHomeLocations.put(p.getUniqueId(), loc);
		}
	}
}
