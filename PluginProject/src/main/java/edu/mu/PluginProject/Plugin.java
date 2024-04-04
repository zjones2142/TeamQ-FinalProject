package edu.mu.PluginProject;

import java.util.logging.Logger;
import org.bukkit.plugin.java.JavaPlugin;

public class Plugin extends JavaPlugin
{
  private static final Logger LOGGER=Logger.getLogger("PluginProject");

  public void onEnable()
  {
    LOGGER.info("PluginProject enabled");
  }

  public void onDisable()
  {
    LOGGER.info("PluginProject disabled");
  }
}
