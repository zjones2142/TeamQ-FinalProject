package edu.mu.PluginProject.commands;

import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import edu.mu.PluginProject.utils.CommandBase;

public class GetDistanceToHome {

	public GetDistanceToHome() {
		new CommandBase("homedistance", 0, true) {
			@Override
			public boolean onCommand(CommandSender sender, String[] args) {
				Player p = (Player) sender;
				Location homeLoc = Home.getPlayerHomeLocationBuffer(p);
				Location playerLoc = p.getLocation();
				
				int distance = calculateDistance(playerLoc, homeLoc);
				
				p.sendMessage("Home is "+distance+" blocks away.");
				return true;
			}

			@Override
			public String getUsage() {
				return "/homedistance";
			}
		};
	}
	
	//calculates distance between two locations using 3d coordinates,  returns rounded int indicating number of blocks
	public int calculateDistance(Location loc1, Location loc2)
	  {
	      //get location data
	      int[] l1 = {loc1.getBlockX(), loc1.getBlockY(),loc1.getBlockZ()};
	      int[] l2 = {loc2.getBlockX(), loc2.getBlockY(),loc2.getBlockZ()};
	      //init values for calculation
	      int x1,x2,y1,y2,z1,z2;
	      int d1,d2,d3;
	      //set values with location data
	      x1 = l1[0]; y1 = l1[1]; z1 = l1[2];
	      x2 = l2[0]; y2 = l2[1]; z2 = l2[2];
	      //calculate distances for x,y,z
	      d1 = x2-x1; d2 = y2-y1; d3 = z2-z1;
	      //calculate total distance 
	      int distance = (int) Math.sqrt(Math.pow(d1, 2)+Math.pow(d2, 2)+Math.pow(d3, 2));
	      return distance;
	  }
}
