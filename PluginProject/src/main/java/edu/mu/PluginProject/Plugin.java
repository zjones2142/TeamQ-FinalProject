package edu.mu.PluginProject;

import java.util.logging.Logger;

import org.bukkit.event.*;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class Plugin extends JavaPlugin implements Listener
{
  private static final Logger LOGGER=Logger.getLogger("PluginProject");

  @Override
  public void onEnable()
  {
    LOGGER.info("PluginProject enabled");
    getServer().getPluginManager().registerEvents(this, this);
  }
  
  @EventHandler
  public void onPlayerJoin(PlayerJoinEvent event)
  {
	  System.out.println("Player has joined");
  }
  
  @Override
  public void onDisable()
  {
    LOGGER.info("PluginProject disabled");
  }
}
