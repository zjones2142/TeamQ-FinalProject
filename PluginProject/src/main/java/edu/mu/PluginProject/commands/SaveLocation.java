package edu.mu.PluginProject.commands;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import edu.mu.PluginProject.Plugin;

public class SaveLocation {

	public SaveLocation() 
	{
		new CommandBase("saveloc", 1, true) {
			@Override
			public boolean onCommand(CommandSender sender, String[] args) {
				Player p = (Player) sender;
				Location loc = p.getLocation();
				try {
					writeLocationToPlayerFile(args[0],loc,p);
					sender.sendMessage("location: '"+args[0]+"' at coordinates: ("+getLocationXYZText(loc)+") has been saved");
				} catch (IOException e) {
					sender.sendMessage("Problem during location save.");
					e.printStackTrace();
				}
				return true;
			}
			
			@Override
			public String getUsage() {
				return "/saveloc";
			}
		};
	}
	
	public void writeLocationToPlayerFile(String title, Location loc, Player p) throws IOException
	{
		int x = loc.getBlockX();
		int y = loc.getBlockY();
		int z = loc.getBlockZ();
		
		File dataFolder = Plugin.getInstance().getDataFolder();
		File csvFile = new File(dataFolder, p.getDisplayName()+"-SavedLocations.csv");
		String dataRow = String.format("%s,%d,%d,%d\n", title, x, y, z);
		
		FileWriter writer = new FileWriter(csvFile, true);
		BufferedWriter bufferedWriter = new BufferedWriter(writer);
		
		try {
			// Write the data row to the file
		    bufferedWriter.write(dataRow);
		} finally {
		    // Close the writer resources
		    bufferedWriter.close();
		    writer.close();
		}
	}
	
	public void addLocationToUI(Player p)
	{
		
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
