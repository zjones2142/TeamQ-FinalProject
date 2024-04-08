package edu.mu.PluginProject;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.*;
import org.bukkit.event.player.*;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.*;

public class Plugin extends JavaPlugin implements Listener
{
  private static final Logger LOGGER = Logger.getLogger("PluginProject");
  public PlayerManager playerManager = new PlayerManager();
  private final Map<UUID, Scoreboard> boards = new HashMap<>();
  
  public Scoreboard createScoreboard(Player p) 
  {
	    Scoreboard scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
	    Objective objective = scoreboard.registerNewObjective("test", "dummy", "test", RenderType.INTEGER);
	    
	    this.boards.put(p.getUniqueId(), scoreboard);
	    
	    objective.setDisplayName("Coordinates");
	    objective.setDisplaySlot(DisplaySlot.SIDEBAR);

	    Score x = objective.getScore("X: ");
	    Score y = objective.getScore("Y: ");
	    Score z = objective.getScore("Z: ");
	    
	    int[] coords = getPlayerLocation(p);

	    x.setScore(coords[0]);
	    y.setScore(coords[1]);
	    z.setScore(coords[2]);

	    return scoreboard;
  }
  
  public int[] getPlayerLocation(Player p) 
  {
	  int[] text = {p.getLocation().getBlockX(), p.getLocation().getBlockY(), p.getLocation().getBlockZ()};
	  return text;
  }
  
  public void updatePlayerBoard(Player p)
  {
	  Scoreboard sb = this.boards.get(p.getUniqueId());
	  Objective ob = sb.getObjective("test");
	  int[] coords = getPlayerLocation(p);
	  
	  Score x = ob.getScore("X: ");
	  Score y = ob.getScore("Y: ");
	  Score z = ob.getScore("Z: ");
	  
	  x.setScore(coords[0]);
	  y.setScore(coords[1]);
	  z.setScore(coords[2]);
  }
  
  @Override
  public void onEnable()
  {
    LOGGER.info("PluginProject enabled");
    getServer().getPluginManager().registerEvents(this, this);
    Bukkit.getPluginManager().registerEvents(this, this);
  }
  
  @EventHandler
  public void onPlayerJoin(PlayerJoinEvent event)
  {
	  Player p = event.getPlayer();
	  this.playerManager.playerList.add(p);
	  p.setScoreboard(createScoreboard(p));
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
	  updatePlayerBoard(p);
  }
  
  @Override
  public void onDisable()
  {
    LOGGER.info("PluginProject disabled");
  }
}
