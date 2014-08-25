package io.github.secondflight.ProgressBar;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

public class MainPlugin extends JavaPlugin implements Listener {
	public final Logger logger = Logger.getLogger("Minecraft");
	public static MainPlugin plugin;
	
	public static Map<Player, Location> locationMap1 = new HashMap<Player, Location>();
	public static Map<Player, Location> locationMap2 = new HashMap<Player, Location>();
	public static Map<Player, Integer> depthMap = new HashMap<Player, Integer>();
	public static Map<Player, Boolean> selectionIsActive = new HashMap<Player, Boolean>();
	
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
	
	@EventHandler
	public void onBlockPlace (BlockPlaceEvent event) {
		if (!(selectionIsActive.get(event.getPlayer()) == null)) {
			event.setCancelled(true);
			if (locationMap1.get(event.getPlayer()) == null && (locationMap2.get(event.getPlayer()) == null)) {
				
			}
		}
	}
	
	private static void resetMaps (Player p) {
		locationMap1.remove(p);
		locationMap2.remove(p);
		depthMap.remove(p);
		selectionIsActive.remove(p);
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
					player.sendMessage("/pb new (name) (depth)");
					player.sendMessage("    -- Creates a new progress bar.");
					player.sendMessage("-------------------------------------------");
				
				} else if (args.length == 1 && args[0].equalsIgnoreCase("new")) {
					player.sendMessage("You must include a name. Type /pb for info.");
				} else if (args.length >= 2 && args[0].equalsIgnoreCase("new")) {
					player.sendMessage("Place a block where you want the upper left corner of your progress bar to be.");
					
					selectionIsActive.put(player, new Boolean (true));
					
					if (args.length == 2) {
						depthMap.put(player, new Integer (1));
					} else {
						depthMap.put(player, new Integer (Integer.parseInt(args[2])));
					}
				}
			}
		} else {
			// this is displayed if the command is called from the console
			logger.warning("You must be a player to use this command.");
		}
		
		
		return false;
		
	}
}
