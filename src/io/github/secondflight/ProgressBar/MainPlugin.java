/*
 * Coded by Joshua Wade. https://www.github.com/secondflight/
 * 
 * This plugin can break things. It's good for testing the included
 * class, but its usefulness pretty much ends there.
 */

package io.github.secondflight.ProgressBar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
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
	
	//first corner of first chunk
	public static Map<Player, Block> blockMap1 = new HashMap<Player, Block>();
	
	//second corner of first chunk
	public static Map<Player, Block> blockMap2 = new HashMap<Player, Block>();
	
	//opposite end of bar
	public static Map<Player, Block> blockMap3 = new HashMap<Player, Block>();
	
	//full
	public static Map<Player, Material> materialMap1 = new HashMap<Player, Material>();
	
	//empty
	public static Map<Player, Material> materialMap2 = new HashMap<Player, Material>();
	public static Map<Player, Integer> depthMap = new HashMap<Player, Integer>();
	public static Map<Player, String> nameMap = new HashMap<Player, String>();
	public static Map<Player, Boolean> selectionIsActive = new HashMap<Player, Boolean>();
	
	public List<ProgressBar> barList = new ArrayList<ProgressBar>();
	
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
			Player player = event.getPlayer();
			
			event.setCancelled(true);
			if (blockMap1.get(event.getPlayer()) == null && (blockMap2.get(event.getPlayer()) == null)) {
				blockMap1.put(player, event.getBlock());
				materialMap1.put(player, event.getBlock().getType());
				
				player.sendMessage("Now, place a block on the opposite corner of where you want the first chunk to be. Make sure it shares a plane with the last one.");
			} else if (!(blockMap1.get(event.getPlayer()) == null) && (blockMap2.get(event.getPlayer()) == null)) {
				if (blockMap1.get(player).getX() == event.getBlock().getX()
					|| blockMap1.get(player).getY() == event.getBlock().getY()
					|| blockMap1.get(player).getZ() == event.getBlock().getZ()) {
					blockMap2.put(player, event.getBlock());
					
				
					player.sendMessage("Finally, place a block where you want the opposite end of your progress bar to be.");
				} else {
					player.sendMessage(ChatColor.RED + "The second block must share a plane with the first Block. Use " + ChatColor.WHITE + "/pb exit" + ChatColor.RED + " to cancel selection.");
				}
				
			} else if (!(blockMap1.get(event.getPlayer()) == null) && (!(blockMap2.get(event.getPlayer()) == null))) {
				blockMap3.put(player, event.getBlock());
				materialMap2.put(player, event.getBlock().getType());
				
				// for testing
				player.sendMessage("1");
				
				
				//blockMap1.get(player).setType(Material.IRON_BLOCK);
				//blockMap2.get(player).setType(Material.IRON_BLOCK);
				//try {
				//blockMap3.get(player).setType(Material.IRON_BLOCK);
				//} catch (Exception ex) {
				//	player.sendMessage("aslfdkjalksdjflaksfdjlaksjdf");
				//}
				
				Block b = blockMap3.get(player);
				System.out.println(b);
				
				player.sendMessage("2");
				//Bukkit.getServer().getWorld(player.getWorld().getName()).getBlockAt(blockMap3.get(player)).setType(Material.IRON_BLOCK);
				player.sendMessage("3");
				
				player.sendMessage(blockMap3.get(player).getX() + " " + blockMap3.get(player).getY() + " " + blockMap3.get(player).getZ());
				
				barList.add(new ProgressBar(nameMap.get(player), blockMap1.get(player), blockMap2.get(player), blockMap3.get(player), materialMap1.get(player), materialMap2.get(player)));
				
				player.sendMessage("Progress bar '" + nameMap.get(player) + "' has been successfully created.");
				
				resetMaps(player);
			}
		}
	}
	
	private static void resetMaps (Player p) {
		blockMap1.remove(p);
		blockMap2.remove(p);
		blockMap3.remove(p);
		materialMap1.remove(p);
		materialMap2.remove(p);
		depthMap.remove(p);
		nameMap.remove(p);
		selectionIsActive.remove(p);
	}
	
	// Commands
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args)
	{
		if (sender instanceof Player && (commandLabel.equalsIgnoreCase("pb") || commandLabel.equalsIgnoreCase("progressbar"))) {
			Player player = (Player) sender;
			if (commandLabel.equalsIgnoreCase("progressbar") || commandLabel.equalsIgnoreCase("pb")) {
				
				if (args.length == 0) {
					// Command usage message
					
					player.sendMessage("-------------------------------------------");
					player.sendMessage("Note: This plugin is not designed to be useful. It was created to show off the included progress bar class, the source for which can be found on github: http://www.github.com/SecondFlight/progressbar/");
					player.sendMessage("");
					player.sendMessage("Usage:");
					player.sendMessage("");
					player.sendMessage("/pb new (name) (depth)");
					player.sendMessage("    -- Creates a new progress bar.");
					player.sendMessage("/pb list");
					player.sendMessage("    -- Lists the names of the active progress bars.");
					player.sendMessage("/pb remove (name)");
					player.sendMessage("    -- Removes a progress bar with the given name.");
					player.sendMessage("/pb set (name) (percent)");
					player.sendMessage("    -- Sets the percentage ammount that the progress bar with the given name should display.");
					player.sendMessage("/pb exit");
					player.sendMessage("    -- Exits out of selection mode.");
					player.sendMessage("-------------------------------------------");
				
				} else if (args.length == 1 && args[0].equalsIgnoreCase("new")) {
					player.sendMessage(ChatColor.RED + "You must include a name. Type /pb for info.");
				} else if (args.length >= 2 && args[0].equalsIgnoreCase("new")) {
					player.sendMessage("Place a block on one corner of where you want the first chunk of your progress bar to be.");
					
					selectionIsActive.put(player, new Boolean (true));
					nameMap.put(player, args[1]);
					
					if (args.length == 2) {
						depthMap.put(player, new Integer (1));
					} else {
						depthMap.put(player, new Integer (Integer.parseInt(args[2])));
					}
				} else if (args.length == 1 && args[0].equalsIgnoreCase("list")) {
					if (barList.size() > 0) {
						for (ProgressBar pb:barList) {
							player.sendMessage(pb.name);
						}
					} else {
						player.sendMessage(ChatColor.RED + "There are no active progress bars.");
					}
				} else if (args.length == 2 && args[0].equalsIgnoreCase("remove")) {
					boolean errorMessage = true;
					for (ProgressBar pb:barList) {
						if (pb.name.equals(args[1])) {
							barList.remove(barList.indexOf(pb));
							player.sendMessage("Progress bar '" + pb.name + "' has successfully been removed.");
							errorMessage = false;
							break;
						}
					}
					
					if (errorMessage == true) {
						player.sendMessage(ChatColor.RED + "No progress bar exists with that name. Use " + ChatColor.WHITE + "/pb list" + ChatColor.RED + " to get a list of active progress bars.");
					}
				} else if (args.length == 1 && args[0].equalsIgnoreCase("exit")) {
					if (!(selectionIsActive.get(player) == null)) {
						if (selectionIsActive.get(player).booleanValue() == true) {
							resetMaps(player);
							player.sendMessage("You are no longer in selection mode.");
						} else {
							player.sendMessage(ChatColor.RED + "You are not in selection mode.");
						}
					} else {
						player.sendMessage(ChatColor.RED + "You are not in selection mode.");
					}
					
				} else if (args.length == 3 && args[0].equalsIgnoreCase("set")) {
					boolean displayError = true;
					for (ProgressBar bar : barList) {
						if (bar.name.equals(args[1])) {
							displayError = false;
							if (!(Integer.parseInt(args[2]) < 0 || Integer.parseInt(args[2]) > 100)) {
								player.sendMessage("Setting the value of progress bar '" + bar.name + "' to " + args[2] + ".");
								bar.setPercentage(Integer.parseInt(args[2]));
							} else {
								player.sendMessage(ChatColor.RED + "Must be a number between 0 and 100.");
								break;
							}
						}
					}
					
					if (displayError == true) {
						player.sendMessage(ChatColor.RED + "This progress bar doesn't exist.");
					}
				} else if (args.length == 3 && args[0].equalsIgnoreCase("clearsection")) {
					for (ProgressBar bar : barList) {
						if (bar.name.equals(args[1])) {
							bar.clearSection(Integer.parseInt(args[2]));
						}
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
