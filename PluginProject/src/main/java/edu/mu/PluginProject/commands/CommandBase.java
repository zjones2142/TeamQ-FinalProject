package edu.mu.PluginProject.commands;

import edu.mu.PluginProject.Plugin;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;
import org.bukkit.plugin.SimplePluginManager;

public abstract class CommandBase extends BukkitCommand implements CommandExecutor {
	
	private List<String> delayedPlayers = null;
	private int delay = 0;
	private final int minArgs;
	private final int maxArgs;
	private final boolean playerOnly;

	public CommandBase(String command) {
		this(command, 0);
	}
	
	public CommandBase(String command, boolean playerOnly) {
		this(command, 0, playerOnly);
	}
	
	public CommandBase(String command, int requiredArgs) {
		this(command, requiredArgs, requiredArgs);
	}
	
	public CommandBase(String command, int minArgs, int maxArgs) {
		this(command, minArgs, maxArgs, false);
	}
	
	public CommandBase(String command, int requiredArgs, boolean playerOnly) {
		this(command, requiredArgs, requiredArgs, playerOnly);
	}
	
	public CommandBase(String command, int minArgs, int maxArgs, boolean playerOnly) {
		super(command);
		
		this.minArgs = minArgs;
		this.maxArgs = maxArgs;
		this.playerOnly = playerOnly;
		
		CommandMap commandMap = getCommandMap();
		if(commandMap != null) {
			commandMap.register(command, this);
		}
	}
	
	public CommandMap getCommandMap() {
		try {
			if(Bukkit.getPluginManager() instanceof SimplePluginManager) {
				Field field = SimplePluginManager.class.getDeclaredField("commandMap");
				field.setAccessible(true);
				
				return (CommandMap) field.get(Bukkit.getPluginManager());
			}
		}
		catch (NoSuchFieldException | IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public CommandBase enableDelay(int delay) {
		this.delay = delay;
		this.delayedPlayers = new ArrayList<>();
		return this;
	}
	
	public void removeDelay(Player p) {
		this.delayedPlayers.remove(p.getName());
	}
	
	public void sendUsage(CommandSender sender) {
		sender.sendMessage(getUsage());
	}
	
	public boolean execute(CommandSender sender, String alias, String [] args) {
		if(args.length < minArgs || (args.length > maxArgs && maxArgs != -1)) {
			sendUsage(sender);
			return true;
		}
		
		if(playerOnly && !(sender instanceof Player)) {
			sender.sendMessage("Only players can use this command.");
		}
		
		if(getPermission() != null && !sender.hasPermission(getPermission())) {
			sender.sendMessage("You do not have permission to use this command.");
			return true;
		}
		
		if(delayedPlayers != null && sender instanceof Player) {
			final Player p = (Player) sender;
			if (delayedPlayers.contains(p.getName())) {
				sender.sendMessage("You must wait to use this command again.");
				return true;
			}
			delayedPlayers.add(p.getName());
			Bukkit.getScheduler().scheduleSyncDelayedTask(Plugin.getInstance(), new Runnable() {
				@Override
				public void run() {
					delayedPlayers.remove(p.getName());
				}
			}, 20L*delay);
		}
		
		if(!onCommand(sender, args)) {
			sendUsage(sender);
		}
		
		return true;
	}
	
	public boolean onCommand(CommandSender sender, Command command, String alias, String [] args) {
		return this.onCommand(sender, args);
	}
	
	public abstract boolean onCommand(CommandSender sender, String [] args);
	
	public abstract String getUsage();
}
