package io.github.secondflight.ProgressBar;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;

public class ProgressBar {
	Location cornerOne;
	Location cornerTwo;
	Location end;
	Material full;
	Material empty;
	public String name;
	
	/**
	 * 
	 * Constructor for a progress bar. I'm bad at writing descriptions.
	 * 
	 * @param barName
	 * Name of the progress bar. Use null if you don't need a name.
	 * 
	 * @param cornerOne
	 * One corner of the first chunk.
	 * 
	 * @param cornerTwo
	 * Opposite corner of the first chunk. Must share a plane with the first corner.
	 * 
	 * @param end
	 * Location somewhere on the end chunk of the progress bar.
	 * 
	 * @param fullMaterial
	 * Material that will be used as the filled texture.
	 * 
	 * @param emptyMaterial
	 * Material that will be used as the empty texture.
	 */
	
	public ProgressBar (String barName, Location cornerOne, Location cornerTwo, Location end, Material fullMaterial, Material emptyMaterial) {
		this.name = barName;
		
		List<List<Location>> locationList = new ArrayList<List<Location>>();
		
		this.cornerOne = cornerOne;
		this.cornerTwo = cornerTwo;
		this.end = end;
		
		this.full = fullMaterial;
		this.empty = emptyMaterial;
		
		end.getBlock().setType(Material.IRON_BLOCK);
		
		int xDist = 0;
		int yDist = 0;
		int zDist = 0;
		
		int xMove = 0;
		int yMove = 0;
		int zMove = 0;
		
		int dist = 0;
		
		if (cornerOne.getBlockX() == cornerTwo.getBlockX()) {
			xDist = end.getBlockX() - cornerOne.getBlockX();
			dist = xDist;
			xMove = 1;
		} else if (cornerOne.getBlockY() == cornerTwo.getBlockY()) {
			yDist = end.getBlockY() - cornerOne.getBlockY();
			dist = yDist;
			yMove = 1;
		} else if (cornerOne.getBlockZ() == cornerTwo.getBlockZ()) {
			zDist = end.getBlockZ() - cornerOne.getBlockZ();
			dist = zDist;
			zMove = 1;
		} else {
			throw new IllegalArgumentException("The blocks corrisponding to cornerOne and cornerTwo must share a plane");
		}
		
		for (int i = 1; i <= dist; i++) {
			List<Location> list = calculateCluster(cornerOne.add((double) xMove, (double) yMove, (double) zMove), cornerTwo.add((double) xMove, (double) yMove, (double) zMove));
			locationList.add(list);
			for (Location l : list) {
				l.getBlock().setType(full);
			}
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
		
		
		for (int x = minX; x <= maxX; x++) {
			for (int y = minY; y <= maxY; y++) {
				for (int z = minZ; z <= maxZ; z++) {
					Location l = new Location(cornerOne.getWorld(), x, y, z);
					if (!(locList.contains(l))) {
						locList.add(l);
					}
				}
			}
		}
		
		return locList;
	}
	
}