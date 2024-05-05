package edu.mu.PluginProject.commands;

import org.bukkit.command.CommandSender;
import edu.mu.PluginProject.utils.CommandBase;
import edu.mu.PluginProject.utils.SurfaceDistance;

public class GetDistanceToSurface {
   public GetDistanceToSurface() 
   {
        new CommandBase("surfacedistance", 0, true){
            @Override
            public boolean onCommand(CommandSender sender, String [] args) {
                new SurfaceDistance();

                return true;
            }

            @Override
            public String getUsage(){
                return "/surfacedistance";
            }
        };
    }
}
