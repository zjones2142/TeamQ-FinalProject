package edu.mu.PluginProject.commands;

import java.io.IOException;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class RemoveLocation {

	public RemoveLocation() {
		new CommandBase("removeloc", 1, true) {
			@Override
			public boolean onCommand(CommandSender sender, String[] args) {
				Player p = (Player) sender;
				try {
					removeLocationFromPlayerFile(args[0],p);
					removeLocationFromUI(p);
					sender.sendMessage("removed location: '"+args[0]+"' from list");
				} catch (IOException e) {
					sender.sendMessage("Problem during location removal.");
					e.printStackTrace();
				}
				return true;
			}
			
			@Override
			public String getUsage() {
				return "/removeloc [location name]";
			}
		};
	}

	public void removeLocationFromPlayerFile(String string, Player p) throws IOException
	{
		
	}
	
	public void removeLocationFromUI(Player p)
	{
		
	}
}
