package edu.mu.PluginProject.commands;

import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetHome {
	
	public SetHome()
	{
		new CommandBase("sethome", 0, true) {
			@Override
			public boolean onCommand(CommandSender sender, String [] args) 
			{
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
			@Override
			public String getUsage() 
			{
				return "/sethome";
			}
		};
	}
	
	public Location getPlayerHomeLocation(Player p) 
	{
		return null;
	}
	
	//helper for retrieving location in block coordinates
	public String getLocationXYZText(Location loc)
	{
		int[] coords = {loc.getBlockX(), loc.getBlockY(), loc.getBlockZ()};
		String text = "X: "+coords[0]+" | Y: "+coords[1]+" | Z: "+coords[2];
		return text;
	}
	
	public void getPlayerHomeLocations() 
	{
		
	}

	public void setPlayerHomeLocation(Player p, Location loc) 
	{
	
	}
}
