package edu.mu.PluginProject.commands;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import edu.mu.PluginProject.Plugin;
import edu.mu.PluginProject.utils.CommandBase;
import edu.mu.PluginProject.utils.CoordinateUI;

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
					addLocationToUI(p);
					sender.sendMessage("Location: '"+args[0]+"' at coordinates: ("+Plugin.getInstance().getLocationXYZText(loc)+") has been saved");
				} catch (IOException e) {
					sender.sendMessage("Problem during location save.");
					e.printStackTrace();
				}
				return true;
			}
			
			@Override
			public String getUsage() {
				return "/saveloc [location name]";
			}
		};
	}
	
	//writes the desired location to players csv data file
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
	
	//adds most recently stored location in csv file to the specified player's location ui
	public void addLocationToUI(Player p)
	{
		String title = "";
		String coord = "";
		int listLength = 1;
		
		try {
			List<Map.Entry<String,String>> itemList = Plugin.getInstance().readLocationFromPlayerFile(p);
			Map.Entry<String,String> item = itemList.getLast();
			title = item.getKey();
			coord = item.getValue();
			listLength = itemList.size();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		CoordinateUI ui = Plugin.getInstance().coordUIs.get(p.getUniqueId());
		ui.addGuiItem(title, listLength, coord);
		ui.openInventory(p);
	}
}
