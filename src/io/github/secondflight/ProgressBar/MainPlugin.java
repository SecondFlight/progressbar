package io.github.secondflight.ProgressBar;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

public class MainPlugin extends JavaPlugin implements Listener {
	public final Logger logger = Logger.getLogger("Minecraft");
	public static MainPlugin plugin;
	
	@Override
	public void onDisable() {
		PluginDescriptionFile pdfFile = this.getDescription();
		this.logger.info(pdfFile.getName() + " has been disabled.");
	}
	
	@Override
	public void onEnable() {
		plugin = this;
		
		PluginDescriptionFile pdfFile = this.getDescription();
		this.logger.info(pdfFile.getName() + " has been Enabled.");
		
		getServer().getPluginManager().registerEvents(this, this);
	
	}
	
	
	// Commands
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args)
	{
		if (sender instanceof Player) {
			Player player = (Player) sender;
			if (commandLabel.equalsIgnoreCase("progressbar") || commandLabel.equalsIgnoreCase("pb")) {
				
				if (args.length == 0) {
					// Command usage message
					
					player.sendMessage("-------------------------------------------");
					player.sendMessage("Usage:");
					player.sendMessage("");
					player.sendMessage("/pb new");
					player.sendMessage("    -- creates a new progress bar");
					player.sendMessage("-------------------------------------------");
					
				}
			}
		} else {
			// this is displayed if the command is called from the console
			logger.warning("You must be a player to use this command.");
		}
		
		
		return false;
		
	}
}
