package edu.mu.PluginProject;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class SurfaceDistance extends JavaPlugin implements Listener{
    
    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(this, this);
        getLogger().info("SurfaceDistancePlugin enabled");
    }

    @Override
    public void onDisable(){
        getLogger().info("SurfaceDistancePlugin disabled");
    }    

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event){ //calculates and updates player distance to surface based off player movement
        Player player = event.getPlayer();
        Location currentLocation = player.getLocation();
        Location highestBlock = player.getWorld().getHighestBlockAt(currentLocation.getBlockX(), currentLocation.getBlockZ()).getLocation();
        
        Material highestBlockType = player.getWorld().getHighestBlockAt(currentLocation.getBlockX(), currentLocation.getBlockZ()).getType();

        //check player's y-level
        int distanceToSurface = highestBlock.getBlockY() - currentLocation.getBlockY();

        if(highestBlockType == Material.AIR && distanceToSurface >=0 ){
            player.sendMessage("Distance to the surface: " + distanceToSurface + " blocks.");
        } else {
            player.sendMessage("You are already at surface level or you are under a tree");
        }
    }
}
