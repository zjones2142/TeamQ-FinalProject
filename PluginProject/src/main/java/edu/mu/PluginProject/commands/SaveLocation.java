package edu.mu.PluginProject.commands;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

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
	
	public String[] readLocationFromPlayerFile(Player p) throws FileNotFoundException 
	{
	    Scanner scanner = null;
	    String[] finalItemTitle;
	    
	    try {
	      File dataFolder = Plugin.getInstance().getDataFolder();
	      File csvFile = new File(dataFolder, p.getDisplayName() + "-SavedLocations.csv");
	      scanner = new Scanner(new FileReader(csvFile));
	      
	      String[] itemTitle;
	      String[] titles;
	      String[] coords;
	      
	      String title;
	      String x;
	      String y;
	      String z;
	      
	      int counter = 0;

	      while (scanner.hasNextLine()) {
	        String line = scanner.nextLine();
	        String[] tokens = line.split(",");

	        if (tokens.length != 4) {
	          // Handle invalid line format (optional)
	          System.out.println("Warning: Invalid line format in CSV file");
	          continue;
	        }

	        title = tokens[0];
	        x = tokens[1];
	        y = tokens[2];
	        z = tokens[3];
	        
	        String xyz = x+", "+y+", "+z;
	        titles[counter] = title;
	        coords[counter] = xyz;
	        
	        counter++;
	      }
	      
	      itemTitle[0] = titles[counter];
	      itemTitle[1] = coords[counter];
	      
	      if (scanner != null) 
	      {
	        scanner.close();
	      }
	      finalItemTitle = itemTitle;
	    } catch(IOException e) {
	    	finalItemTitle = null;
	    	e.printStackTrace();
	    }

	    return finalItemTitle;
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
