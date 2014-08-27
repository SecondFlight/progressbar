package io.github.secondflight.ProgressBar;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;

public class ProgressBar {
	Location cornerOne;
	Location cornerTwo;
	Location end;
	Material full;
	Material empty;
	public String name;
	
	public ProgressBar (String barName, Location cornerOne, Location cornerTwo, Location end, Material fullMaterial, Material emptyMaterial) {
		this.name = barName;
		
		this.cornerOne = cornerOne;
		this.cornerTwo = cornerTwo;
		this.end = end;
		
		cornerOne.getBlock().setType(Material.DIAMOND_BLOCK);
		cornerTwo.getBlock().setType(Material.GOLD_BLOCK);
		end.getBlock().setType(Material.REDSTONE_BLOCK);
		
		List<Location> list = calculateCluster(cornerOne, cornerTwo);
		
		for (Location l : list) {
			l.getBlock().setType(full);
		}
	}

	private static List<Location> calculateCluster (Location cornerOne, Location cornerTwo) {
		List<Location> locList = new ArrayList<Location>();
		
		// if they are the same block
		if (cornerOne.getBlock().equals(cornerTwo.getBlock())) {
			locList.add(cornerOne);
			return locList;
		}
		
		int minX = Math.min(cornerOne.getBlockX(), cornerTwo.getBlockX());
		int minY = Math.min(cornerOne.getBlockY(), cornerTwo.getBlockY());
		int minZ = Math.min(cornerOne.getBlockZ(), cornerTwo.getBlockZ());
		
		int maxX = Math.max(cornerOne.getBlockX(), cornerTwo.getBlockX());
		int maxY = Math.max(cornerOne.getBlockY(), cornerTwo.getBlockY());
		int maxZ = Math.max(cornerOne.getBlockZ(), cornerTwo.getBlockZ());
		
		System.out.println("laksjdf");
		
		for (int x = minX; x <= maxX; x++) {
			for (int y = minX; y <= maxX; y++) {
				for (int z = minX; z <= maxX; z++) {
					new Location(cornerOne.getWorld(), x, y, z).getBlock().setType(Material.DIAMOND_BLOCK);
					locList.add(new Location(cornerOne.getWorld(), x, y, z));
				}
			}
		}
		
		return locList;
	}
	
}