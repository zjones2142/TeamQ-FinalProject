package edu.mu.PluginProject;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.*;
import org.bukkit.event.player.*;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.*;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;

public class Plugin extends JavaPlugin implements Listener
{
  private static final Logger LOGGER = Logger.getLogger("PluginProject");
  public PlayerManager playerManager = new PlayerManager();
  private final Map<UUID, Scoreboard> boards = new HashMap<>();
  
  //HELPER METHODS vvvvvvvvvvvvvvvvvvvvvvvvvvvv
  
  //returns a new scoreboard object that contains player home data
  public Scoreboard createCoordinateScoreboard(Player p) 
  {
	    Scoreboard scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
	    Objective objective = scoreboard.registerNewObjective("test", "dummy", "test", RenderType.INTEGER);
	    
	    this.boards.put(p.getUniqueId(), scoreboard);
	    
	    objective.setDisplayName("Home Coords");
	    objective.setDisplaySlot(null);
	    
	    Score x = objective.getScore("X: ");
	    Score y = objective.getScore("Y: ");
	    Score z = objective.getScore("Z: ");
	    
	    x.setScore(0);
	    y.setScore(0);
	    z.setScore(0);
	    
	    return scoreboard;
  }
  
  //returns array of player coordinates in {x,y,z} format
  public int[] getPlayerLocation(Player p) 
  {
	  int[] coords = {p.getLocation().getBlockX(), p.getLocation().getBlockY(), p.getLocation().getBlockZ()};
	  return coords;
  }
  
  public String getPlayerLocationText(Player p)
  {
	  int[] coords = getPlayerLocation(p);
	  String text = "X: "+coords[0]+" | Y: "+coords[1]+" | Z: "+coords[2];
	  return text;
  }
  
  public int getPlayerDistanceFromSurface(Player p)
  {
	  int result = 0;
	  return result;
  }
  
  //updates a players scoreboard object with current coordinates
  public void updatePlayerBoard(Player p)
  {
//	  Scoreboard sb = this.boards.get(p.getUniqueId());
//	  Objective ob = sb.getObjective("test");
//	  int[] coords = getPlayerLocation(p);
//	  
//	  Score x = ob.getScore("X: ");
//	  Score y = ob.getScore("Y: ");
//	  Score z = ob.getScore("Z: ");
//	  
//	  x.setScore(coords[0]);
//	  y.setScore(coords[1]);
//	  z.setScore(coords[2]);
  }
  
  // ENABLE/DISABLE OVERRIDES vvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvv
  @Override
  public void onEnable()
  {
    LOGGER.info("PluginProject enabled");
    getServer().getPluginManager().registerEvents(this, this);
    Bukkit.getPluginManager().registerEvents(this, this);
  }
  
  @Override
  public void onDisable()
  {
    LOGGER.info("PluginProject disabled");
  }
  
  //PLAYER EVENT HANDLERS vvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvv
  @EventHandler
  public void onPlayerJoin(PlayerJoinEvent event)
  {
	  Player p = event.getPlayer();
	  this.playerManager.playerList.add(p);
	  p.setScoreboard(createCoordinateScoreboard(p));
  }
  
  @EventHandler
  public void onPlayerQuit(PlayerQuitEvent event)
  {
	  Player p = event.getPlayer();
	  LOGGER.info(p.getDisplayName()+" has quit");
  }
  
  @EventHandler
  public void onPlayerMove(PlayerMoveEvent event)
  {
	  Player p = event.getPlayer();
	  String text = getPlayerLocationText(p);
	  p.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(text));
  }
  
  @EventHandler
  public void onPlayerHold(PlayerItemHeldEvent event)
  {
	  Player p = event.getPlayer();
	  Material material;
	  try
	  {
		  material = p.getInventory().getItem(event.getNewSlot()).getType();
	  }
	  catch(Exception e)
	  {
		  material = null;
	  }
	  
	  Scoreboard sb = this.boards.get(p.getUniqueId());
	  if (material == Material.COMPASS)
	  {
		  sb.getObjective("test").setDisplaySlot(DisplaySlot.SIDEBAR);
	  }
	  else
	  {
		  sb.getObjective("test").setDisplaySlot(null);
	  }
  }
}
