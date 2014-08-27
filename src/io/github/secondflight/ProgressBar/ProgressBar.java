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
		
		this.full = fullMaterial;
		this.empty = emptyMaterial;
		
		end.getBlock().setType(Material.IRON_BLOCK);
		
		//try {
			List<Location> list = calculateCluster(cornerOne, cornerTwo);
		
			for (Location l : list) {
				l.getBlock().setType(full);
			}
		//} catch (Throwable ex){
		//	ex.printStackTrace();
		//}
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
		
		
		for (int x = minX; x <= maxX; x++) {
			for (int y = minY; y <= maxY; y++) {
				for (int z = minZ; z <= maxZ; z++) {
					Location l = new Location(cornerOne.getWorld(), x, y, z);
					locList.add(l);
				}
			}
		}
		
		return locList;
	}
	
}