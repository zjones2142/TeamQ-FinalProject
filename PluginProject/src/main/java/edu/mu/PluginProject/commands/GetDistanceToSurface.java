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
            	Player player = (Player) sender;
                Location currentLocation = player.getLocation();
                int highestBlock = player.getWorld().getHighestBlockAt(currentLocation.getBlockX(), currentLocation.getBlockZ()).getY();
                
                //Material highestBlockType = player.getWorld().getHighestBlockAt(currentLocation.getBlockX(), currentLocation.getBlockZ()).getType();

                //check player's y-level
                int distanceToSurface = highestBlock - currentLocation.getBlockY()-1;

                if(distanceToSurface >= 0 ){
                    player.sendMessage("Distance to the surface: " + distanceToSurface + " blocks.");
                } else {
                    player.sendMessage("You are already at surface level or you are under a tree");
                }
                return true;
            }

            @Override
            public String getUsage(){
                return "/surfacedistance";
            }
        };
    }
}
