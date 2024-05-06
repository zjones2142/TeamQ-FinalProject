package edu.mu.PluginProject.commands;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import edu.mu.PluginProject.Plugin;
import edu.mu.PluginProject.utils.CommandBase;

public class SetHome {
	
	/* Constructor implements the “/sethome” command. Which sets If the present location 
	is different than the previous recorded location then it overwrites the file with present 
	location and sends the set location to the player “Home set at {location}”. If the location 
	is the same it messages the player “Home already set at this location.” */
	public SetHome()
	{
		new CommandBase("sethome", 0, true) {
			@Override
			public boolean onCommand(CommandSender sender, String [] args) 
			{
				Player p = (Player) sender;
				Location loc = p.getLocation();
				try {
					if(getPlayerHomeLocation(p) != loc)
					{
						setPlayerHomeLocation(p, loc);
						p.sendMessage("Home Set at ("+Plugin.getInstance().getLocationXYZText(loc)+")");
						return true;
					}
					else
					{
						p.sendMessage("Home already set at this location.");
						return false;
					}
				} catch (IOException e) {
					e.printStackTrace();
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
	

	//writes the desired location to player home data file
	public void setPlayerHomeLocation(Player p, Location loc) throws IOException 
	{
		File dataFolder = Plugin.getInstance().getDataFolder();
		String filename = p.getDisplayName()+"-Home.yaml";
		File file = new File(dataFolder, filename);

		// Create a new YAML configuration object
		YamlConfiguration config = new YamlConfiguration();
	  
		// Set location data using Location's built-in methods
		config.set("world", p.getLocation().getWorld().getName());
		config.set("x", p.getLocation().getX());
		config.set("y", p.getLocation().getY());
		config.set("z", p.getLocation().getZ());
		config.set("yaw", p.getLocation().getYaw());
		config.set("pitch", p.getLocation().getPitch());
	  
		// Save the configuration to the file
		config.save(file);
	}
	
	//reads the player home data file and returns a location object
	public static Location getPlayerHomeLocation(Player p) throws IOException
	{
		File dataFolder = Plugin.getInstance().getDataFolder();
		String filename = p.getDisplayName()+"-Home.yaml";
		File file = new File(dataFolder, filename);
		// Load the YAML configuration from the file
		YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
		  
		// Get location data and create a Location object
		World world = Bukkit.getWorld(config.getString("world"));
		double x = config.getDouble("x");
		double y = config.getDouble("y");
		double z = config.getDouble("z");
		float yaw = (float) config.getDouble("yaw"); // YAML stores doubles for floats
		float pitch = (float) config.getDouble("pitch");
		
		return new Location(world, x, y, z, yaw, pitch);
	}
}
