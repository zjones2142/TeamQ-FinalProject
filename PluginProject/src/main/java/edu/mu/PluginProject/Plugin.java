package edu.mu.PluginProject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.UUID;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.Location;
//import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.*;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.*;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Criterias;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

import edu.mu.PluginProject.commands.*;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;

import edu.mu.PluginProject.commands.SaveLocation;
import edu.mu.PluginProject.utils.CoordinateUI;

public class Plugin extends JavaPlugin implements Listener
{
  public static Plugin instance;
  private static final Logger LOGGER = Logger.getLogger("PluginProject");
  public final Map<UUID, CoordinateUI> coordUIs = new HashMap<>();
  public static File dataFolder;
  //public Location worldspawn = Bukkit.getWorld("world").getSpawnLocation();
  
  //HELPER METHODS vvvvvvvvvvvvvvvvvvvvvvvvvvvv
  
  //returns array of player coordinates in {x,y,z} format
  public int[] getLocationXYZ(Location loc)
  {
	  int[] coords = {loc.getBlockX(), loc.getBlockY(), loc.getBlockZ()};
	  return coords;
  }
  
  //helper for retrieving coordinate data of location in string format 
  public String getLocationXYZText(Location loc)
  {
	  int[] coords = getLocationXYZ(loc);
	  String text = "X: "+coords[0]+" | Y: "+coords[1]+" | Z: "+coords[2];
	  return text;
  }
  
  //returns player location coordinates in String format
  public String getPlayerLocationText(Player p)
  {
	  int[] coords = getLocationXYZ(p.getLocation());
	  String text = "X: "+coords[0]+" | Y: "+coords[1]+" | Z: "+coords[2];
	  return text;
  }
  
  //creates player data csv if it doesn't exist
  public void createPlayerDataCSV(Player p) throws IOException 
  {
	  String filename = p.getDisplayName()+"-SavedLocations.csv";
	  File csvFile = new File(dataFolder, filename);

	  // Attempt to create the file
	  if (!csvFile.createNewFile()) {
	    getLogger().info("Player data CSV already exists: " + csvFile.getPath());
	  }
  }
  
  // ENABLE/DISABLE OVERRIDES vvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvv
  
  //runs everything in this block when server enables the plugin
  //mainly registers classes and creates data folder if being enabled on new server
  @Override
  public void onEnable()
  {
	  LOGGER.info("PluginProject enabled");
	  getServer().getPluginManager().registerEvents(this, this);
	  Bukkit.getPluginManager().registerEvents(this, this);
	  instance = this;
	  //registering commands vv
	  new SetHome();
	  new Home();
	  new SaveLocation();
	  new RemoveLocation();
	  new CoordinateUI();
	  new GetDistanceToSurface();
	  new GetDistanceToHome();
	  dataFolder = getDataFolder();
	  if(!dataFolder.exists())
	  {
		  getLogger().info("Creating data folder...");
		  dataFolder.mkdirs();
	  }
  }
  
  //runs when server disables plugin
  @Override
  public void onDisable()
  {
	  LOGGER.info("PluginProject disabled");
  }
  
  //PLAYER EVENT HANDLERS vvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvv
  
  //code block executed on player join
  //creates location ui, datafiles, and health display
  @EventHandler
  public void onPlayerJoin(PlayerJoinEvent event)
  {
	  //grab player object
	  Player p = event.getPlayer();
	  //create save location UI and place in hashmap
	  CoordinateUI ui = new CoordinateUI();
	  this.coordUIs.put(p.getUniqueId(), ui);
	  //create player "save location" data csv if it doesn't already exist
	  try {createPlayerDataCSV(p);} catch (IOException e) {}
	  //if player HAS played before, restore previously saved locations from file
	  if(p.hasPlayedBefore())
	  {
		  addPreviousSavedLocations(p);
	  }
	  //create "scoreboard" for displaying player health
	  createPlayerHealthScoreBoard(p);
  }
  
  
  //reads the save location CSV file of the given player and returns a list of the locations stored there
  public List<Map.Entry<String,String>> readLocationFromPlayerFile(Player p) throws FileNotFoundException 
  {
	  List<Map.Entry<String,String>> list = new ArrayList<>();
	  Scanner scanner = null;

	  try {
		  File dataFolder = Plugin.getInstance().getDataFolder();
		  File csvFile = new File(dataFolder, p.getDisplayName() + "-SavedLocations.csv");
		  scanner = new Scanner(new FileReader(csvFile));

		  while (scanner.hasNextLine()) {
			  String line = scanner.nextLine();
			  String[] tokens = line.split(",");

			  if (tokens.length != 4) {
				  // Handle invalid line format (optional)
				  System.out.println("Warning: Invalid line format in CSV file");
				  continue;
			  }

			  String title = tokens[0];
			  String coords = tokens[1]+", "+tokens[2]+", "+tokens[3];

			  list.add(new AbstractMap.SimpleEntry<>(title, coords));
		  }
	   } finally {
		   if (scanner != null) {
			   scanner.close();
		   }
	   }

	   return list;
  }
  
  //if the player has previously saved location(s) within the data file, this adds those locations to given player's location ui
  public void addPreviousSavedLocations(Player p) 
  {
	  List<Map.Entry<String, String>> locList;
	  CoordinateUI ui = this.coordUIs.get(p.getUniqueId());
	  int count = 1;
	  try {
		  locList = readLocationFromPlayerFile(p);
		  for(Map.Entry<String,String> entry : locList) 
		  {
			  ui.addGuiItem(entry.getKey(), count, entry.getValue());
			  count++;
			  if(count > 9) 
			  {
				  //breaks loop if number of locations in file is greater than 9
				  continue;
			  }
		  }
	  } catch (FileNotFoundException e) {
		e.printStackTrace();
	  }
  }

  //creates a scoreboard object for the player to display health stat
  public void createPlayerHealthScoreBoard(Player p)
  {
	  Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();
	  Objective obj = board.registerNewObjective(this+"_health", Criterias.HEALTH, ChatColor.RED+"❤︎");
	  
	  obj.setDisplaySlot(DisplaySlot.BELOW_NAME);
	  
	  p.setScoreboard(board);
  }
  
  //executes when player moves
  //handles displaying of current coordinates (sends them to player's action bar)
  @EventHandler
  public void onPlayerMove(PlayerMoveEvent event)
  {
	  Player p = event.getPlayer();
	  String text = getPlayerLocationText(p);
	  p.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(text));
  }
  
  //executes when player interacts with an object
  //handles the opening of the saved location ui when a player right clicks with a compass in hand
  @EventHandler
  public void onPlayerInteractEvent(PlayerInteractEvent event)
  {
	  Player p = event.getPlayer();
	  Material material;
	  try
	  {
		  material = p.getInventory().getItemInMainHand().getType();
	  }
	  catch(Exception e)
	  {
		  material = null;
	  }
	  CoordinateUI ui = this.coordUIs.get(p.getUniqueId());
	  if (material == Material.COMPASS && (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK))
	  {
		  ui.openInventory(p);
	  }
  }
  
  //executes when a player hits respawn button
  //handles the respawn at player home set with command "/sethome"
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
	  else {
		  p.sendMessage("No home was set with command '/sethome'");
	  }
  }
  
  //helper for grabbing plugin instance (used for non-static file reading methods stored in this class, as well as getting the data folder path)
  public static Plugin getInstance() 
  {
	  return instance;
  }
}
