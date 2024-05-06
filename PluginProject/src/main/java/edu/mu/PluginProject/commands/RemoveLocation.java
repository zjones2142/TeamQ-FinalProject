package edu.mu.PluginProject.commands;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import edu.mu.PluginProject.Plugin;
import edu.mu.PluginProject.utils.CommandBase;

public class RemoveLocation {

	public RemoveLocation() {
		new CommandBase("removeloc", 1, true) {
			@Override
			public boolean onCommand(CommandSender sender, String[] args) {
				Player p = (Player) sender;
				try {
					if(removeLocationFromPlayerFile(args[0],p)) {
						sender.sendMessage("Removed location: '"+args[0]+"' from list");
						sender.sendMessage("Re-log in order to refresh locations list");
					} else {
						sender.sendMessage("No Saved Location found with that title.");
					}
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
	//removes location with specified name from player data file
	public Boolean removeLocationFromPlayerFile(String string, Player p) throws IOException
	{
		File dataFolder = Plugin.getInstance().getDataFolder();
	    String inputFile = Paths.get(dataFolder.getAbsolutePath(), p.getDisplayName()+"-SavedLocations.csv").toString();
	    Boolean stringFound = false;

	    List<String> lines = new ArrayList<>();

	    // Read the CSV file line by line
	    try (BufferedReader reader = new BufferedReader(new FileReader(inputFile))) {
	      String line;
	      while ((line = reader.readLine()) != null) {
	        if (!line.startsWith(string)) {
	          lines.add(line);
	        }
	        else {
	        	stringFound = true;
	        }
	      }
	    }

	    // Create a temporary file
	    Path tempFile = Files.createTempFile("modified_csv", ".csv");

	    // Write the filtered lines to the temporary file
	    try (FileWriter writer = new FileWriter(tempFile.toFile())) {
	      for (String line : lines) {
	        writer.write(line + "\n");
	      }
	    }

	    // Rename the temporary file to the original file name (atomic operation)
	    Files.move(tempFile, Paths.get(inputFile), StandardCopyOption.REPLACE_EXISTING);
	    return stringFound;
	 }
}
