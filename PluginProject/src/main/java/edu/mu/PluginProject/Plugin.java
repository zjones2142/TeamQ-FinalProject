package edu.mu.PluginProject;

import java.util.logging.Logger;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.*;
import org.bukkit.event.player.*;
import org.bukkit.plugin.java.JavaPlugin;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;

public class Plugin extends JavaPlugin implements Listener
{
  private static final Logger LOGGER = Logger.getLogger("PluginProject");
  public PlayerManager playerManager = new PlayerManager();
  
  @Override
  public void onEnable()
  {
    LOGGER.info("PluginProject enabled");
    getServer().getPluginManager().registerEvents(this, this);
  }
  
  @EventHandler
  public void onPlayerJoin(PlayerJoinEvent event)
  {
	  Player p = event.getPlayer();
	  this.playerManager.playerList.add(p);
  }
  
  @EventHandler
  public void onPlayerMove(PlayerMoveEvent event)
  {
	  Player p = event.getPlayer();
	  Location pTo = event.getTo();
	  Location pFrom = event.getFrom();
	  
	  int xLoc = p.getLocation().getBlockX();
	  int yLoc = p.getLocation().getBlockY();
	  int zLoc = p.getLocation().getBlockZ();
	  
	  String text = "X: "+xLoc+" | Y: " +yLoc+" | Z: "+zLoc;
	  
	  if ((pTo.getBlockX() != pFrom.getBlockX()) || (pTo.getBlockZ() != pFrom.getBlockZ()) || (pTo.getBlockY() != pFrom.getBlockY())) 
	  {
		  p.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(text));
	  }
  }
  
  @Override
  public void onDisable()
  {
    LOGGER.info("PluginProject disabled");
  }
}
