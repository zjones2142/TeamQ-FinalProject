package edu.mu.PluginProject.commands;


import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetHome implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		// TODO Auto-generated method stub
		if (!(sender instanceof Player))
		{
			sender.sendMessage("Only players can use this command.");
			return false;
		}
		
		Player p = (Player) sender;
		Location loc = p.getLocation();
		p.sendMessage("Home Set!");
		p.setBedSpawnLocation(loc);
		return true;
	}
}
