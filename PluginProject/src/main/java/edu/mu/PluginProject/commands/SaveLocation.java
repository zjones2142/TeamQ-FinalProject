package edu.mu.PluginProject.commands;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import edu.mu.PluginProject.Plugin;
import edu.mu.PluginProject.CoordinateUI;

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
					sender.sendMessage("location: '"+args[0]+"' at coordinates: ("+getLocationXYZText(loc)+") has been saved");
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
	
	public List<Map.Entry<String,String>> readLocationFromPlayerFile(Player p) throws FileNotFoundException 
	{
		List<Map.Entry<String,String>> list = new ArrayList<>();
	    Scanner scanner = null;

	    try {
	      File dataFolder = Plugin.getInstance().getDataFolder();
	      File csvFile = new File(dataFolder, p.getDisplayName() + "-SavedLocations.csv");
	      scanner = new Scanner(new FileReader(csvFile));

	      // Skip the header row (assuming the format is consistent)
	      scanner.nextLine();

	      while (scanner.hasNextLine()) {
	        String line = scanner.nextLine();
	        String[] tokens = line.split(",");

	        if (tokens.length != 4) {
	          // Handle invalid line format (optional)
	          System.out.println("Warning: Invalid line format in CSV file");
	          continue;
	        }

	        String title = tokens[0];
	        String coords = tokens[1]+", "+tokens[2]+", "+tokens[3];

	        list.add(new AbstractMap.SimpleEntry<>(title, coords));
	      }
	    } finally {
	      if (scanner != null) {
	        scanner.close();
	      }
	    }

	    return list;
	}
	
	public void addLocationToUI(Player p)
	{
		String title = "";
		String coord = "";
		int listLength = 1;
		
		try {
			List<Map.Entry<String,String>> itemList = readLocationFromPlayerFile(p);
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
