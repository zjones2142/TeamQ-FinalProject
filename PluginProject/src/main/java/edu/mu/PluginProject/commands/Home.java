package edu.mu.PluginProject.commands;

import java.io.IOException;

import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import edu.mu.PluginProject.utils.CommandBase;

public class Home {

	public Home() {
		new CommandBase("home", 0, true) {
			@Override
			public boolean onCommand(CommandSender sender, String [] args) {
				Player p = (Player) sender;
				Location homeLoc = getPlayerHomeLocationBuffer(p);
				if(homeLoc != null)
				{
					p.teleport(homeLoc);
				}
				else
				{
					p.sendMessage("You do not have a home set.");
				}
				return true;
			}
			
			@Override
			public String getUsage() {
				return "/home";
			}
		};
	}
	
	//retrieves player home location data from file
	public static Location getPlayerHomeLocationBuffer(Player p) {
		Location loc;
		try {
			loc = SetHome.getPlayerHomeLocation(p);
		} catch(IOException e) {
			loc = null;
		}
		return loc;
	}
}
