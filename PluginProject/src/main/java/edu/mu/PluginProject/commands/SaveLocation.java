package edu.mu.PluginProject.commands;

import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SaveLocation {

	public SaveLocation() 
	{
		new CommandBase("saveloc", 1, true) {
			@Override
			public boolean onCommand(CommandSender sender, String[] args) {
				Player p = (Player) sender;
				Location loc = p.getLocation();
				
				//TODO: save location coords, + location name to file here
				sender.sendMessage("location: "+args[0]+" at coordinates: "+getLocationXYZText(loc)+"has been saved");
				return true;
			}
			
			@Override
			public String getUsage() {
				return "/saveloc";
			}
		};
	}
	
	public String getLocationXYZText(Location loc)
	{
		int[] coords = {loc.getBlockX(), loc.getBlockY(), loc.getBlockZ()};
		String text = "X: "+coords[0]+" | Y: "+coords[1]+" | Z: "+coords[2];
		return text;
	}
	
	public int[] getLocationXYZ(Location loc)
	{
		int[] coords = {loc.getBlockX(), loc.getBlockY(), loc.getBlockZ()};
		return coords;
	}
}
