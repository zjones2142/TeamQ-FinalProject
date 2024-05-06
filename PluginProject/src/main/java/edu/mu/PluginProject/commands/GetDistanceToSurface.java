package edu.mu.PluginProject.commands;

import org.bukkit.Location;
//import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import edu.mu.PluginProject.utils.CommandBase;

public class GetDistanceToSurface {
   public GetDistanceToSurface() 
   {
        new CommandBase("surfacedistance", 0, true) {
            @Override
            public boolean onCommand(CommandSender sender, String [] args) {
            	Player p = (Player) sender;
            	int distanceToSurface = calculateDistanceToSurface(p);

                if(distanceToSurface >= 0 ){
                    p.sendMessage("You are " + distanceToSurface + " blocks from the surface (or there is no block above you)");
                } else {
                    p.sendMessage("You are already at surface level.");
                }
                return true;
            }

            @Override
            public String getUsage() {
                return "/surfacedistance";
            }
        };
   }
   
   //calculates distance to surface using the player location and the highest block at that X,Z column in the world
   public int calculateDistanceToSurface(Player p)
   {
	   Location currentLocation = p.getLocation();
       int highestBlock = p.getWorld().getHighestBlockAt(currentLocation.getBlockX(), currentLocation.getBlockZ()).getY();
       int result = highestBlock - currentLocation.getBlockY()-1;
	   return result;
   }
}
