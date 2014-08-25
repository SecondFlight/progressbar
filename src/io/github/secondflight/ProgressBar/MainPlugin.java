package io.github.secondflight.ProgressBar;

import java.util.logging.Logger;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
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
		
		
		
		return false;
		
	}
}
