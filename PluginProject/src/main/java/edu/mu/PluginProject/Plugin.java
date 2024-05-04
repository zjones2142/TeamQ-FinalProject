package edu.mu.PluginProject;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.Location;
//import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.*;
import org.bukkit.event.player.*;
import org.bukkit.plugin.java.JavaPlugin;

import edu.mu.PluginProject.commands.*;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;

public class Plugin extends JavaPlugin implements Listener
{
  public static Plugin instance;
  private static final Logger LOGGER = Logger.getLogger("PluginProject");
  public PlayerManager playerManager = new PlayerManager();
  public final Map<UUID, CoordinateUI> coordUIs = new HashMap<>();
  public static File dataFolder;
  //public Location worldspawn = Bukkit.getWorld("world").getSpawnLocation();
  
  //HELPER METHODS vvvvvvvvvvvvvvvvvvvvvvvvvvvv
  
  //returns array of player coordinates in {x,y,z} format
  public int[] getPlayerLocation(Player p) 
  {
	  int[] coords = {p.getLocation().getBlockX(), p.getLocation().getBlockY(), p.getLocation().getBlockZ()};
	  return coords;
  }
  
  //returns player location coordinates in String format
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
  
  public void createPlayerDataCSV(Player p) throws IOException {
	  String filename = p.getDisplayName()+"-SavedLocations.csv";
	  File csvFile = new File(dataFolder, filename);

	  // Attempt to create the file
	  if (!csvFile.createNewFile()) {
	    getLogger().info("Player data CSV already exists: " + csvFile.getPath());
	  }
	}
  
  // ENABLE/DISABLE OVERRIDES vvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvv
  @Override
  public void onEnable()
  {
    LOGGER.info("PluginProject enabled");
    getServer().getPluginManager().registerEvents(this, this);
    Bukkit.getPluginManager().registerEvents(this, this);
    instance = this;
    new SetHome();
    new SaveLocation();
    new Home();
    dataFolder = getDataFolder();
    if(!dataFolder.exists())
    {
    	getLogger().info("Creating data folder...");
        dataFolder.mkdirs();
    }
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
	  CoordinateUI ui = new CoordinateUI();
	  this.coordUIs.put(p.getUniqueId(), ui);
	  try {createPlayerDataCSV(p);} catch (IOException e) {}
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
	  CoordinateUI ui = this.coordUIs.get(p.getUniqueId());
	  if (material == Material.COMPASS)
	  {
		  ui.openInventory(p);
	  }
  }
  
  @EventHandler
  public void onPlayerRespawn(PlayerRespawnEvent event) throws IOException
  {
	  Player p = event.getPlayer();
	  Location loc;
	  try {
		  loc = SetHome.getPlayerHomeLocation(p);
	  } catch (IOException e) {
		  loc = null;
		  LOGGER.info("Respawning "+p.getDisplayName()+" at player home failed.");
	  }
		  
	  if(loc != null)
	  {
		  event.setRespawnLocation(loc);
	  }
  }
  
  public static Plugin getInstance() 
  {
	  return instance;
  }
}
