package edu.mu.PluginProject.commands;

import java.io.IOException;

import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

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
	
	public Location getPlayerHomeLocationBuffer(Player p) {
		Location loc;
		try {
			loc = SetHome.getPlayerHomeLocation(p);
		} catch(IOException e) {
			loc = null;
		}
		return loc;
	}
}
