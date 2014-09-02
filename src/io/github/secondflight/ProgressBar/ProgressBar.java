package io.github.secondflight.ProgressBar;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;

public class ProgressBar {
	Block cornerOne;
	Block cornerTwo;
	Block end;
	
	Material full;
	Material empty;
	
	//Note: the following must be a number from 0 to 1 -- 0 is empty, 1 is full.
	float percentFilled;
	
	List<List<Block>> blockList = new ArrayList<List<Block>>();
	
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
	
	public ProgressBar (String barName, Block cornerOne, Block cornerTwo, Block end, Material fullMaterial, Material emptyMaterial) {
		this.name = barName;
		
		this.cornerOne = cornerOne;
		this.cornerTwo = cornerTwo;
		this.end = end;
		
		this.full = fullMaterial;
		this.empty = emptyMaterial;
		
		end.setType(Material.IRON_BLOCK);
		
		int xDist = 0;
		int yDist = 0;
		int zDist = 0;
		
		int xMove = 0;
		int yMove = 0;
		int zMove = 0;
		
		int dist = 0;
		
		if (cornerOne.getX() == cornerTwo.getX()) {
			xDist = end.getX() - cornerOne.getX();
			dist = xDist;
			xMove = 1;
		} else if (cornerOne.getY() == cornerTwo.getY()) {
			yDist = end.getY() - cornerOne.getY();
			dist = yDist;
			yMove = 1;
		} else if (cornerOne.getZ() == cornerTwo.getZ()) {
			zDist = end.getZ() - cornerOne.getZ();
			dist = zDist;
			zMove = 1;
		} else {
			throw new IllegalArgumentException("The blocks corrisponding to cornerOne and cornerTwo must share a plane");
		}
		
		Block cornerOneClone = cornerOne;
		Block cornerTwoClone = cornerTwo;
		
		for (int i = 1; i <= dist; i++) {
			List<Block> list = calculateCluster(cornerOneClone, cornerTwoClone);
			blockList.add(list);
			
			percentFilled = 0;
			
			cornerOneClone = cornerOneClone.getRelative(xMove, yMove, zMove);
			cornerTwoClone = cornerTwoClone.getRelative(xMove, yMove, zMove);
		}
		
		List<Block> list = calculateCluster(cornerOneClone, cornerTwoClone);
		blockList.add(list);
		
		update();
		
	}

	private static List<Block> calculateCluster (Block cornerOne, Block cornerTwo) {
		List<Block> blockList = new ArrayList<Block>();
		
		// if they are the same block
		if (cornerOne.equals(cornerTwo)) {
			blockList.add(cornerOne);
			return blockList;
		}
		
		int minX = (int) Math.min(cornerOne.getX(), cornerTwo.getX());
		int minY = (int) Math.min(cornerOne.getY(), cornerTwo.getY());
		int minZ = (int) Math.min(cornerOne.getZ(), cornerTwo.getZ());
		
		int maxX = (int) Math.max(cornerOne.getX(), cornerTwo.getX());
		int maxY = (int) Math.max(cornerOne.getY(), cornerTwo.getY());
		int maxZ = (int) Math.max(cornerOne.getZ(), cornerTwo.getZ());
		
		
		for (int x = minX; x <= maxX; x++) {
			for (int y = minY; y <= maxY; y++) {
				for (int z = minZ; z <= maxZ; z++) {
					Block b = new Location(cornerOne.getWorld(), x, y, z).getBlock();
					if (!(blockList.contains(b))) {
						blockList.add(b);
					}
				}
			}
		}
		
		return blockList;
	}
	
	/**
	 * Sets the bar based on a percentage.
	 * @param percentage
	 * Percent full. Must be between 0 and 100.
	 */
	public void setPercentage (float percentage) {
		percentFilled = (percentage / 100);
		update();
	}
	
	private void update () {
		for (List<Block> list : blockList) {
			//TODO: The following conversion will cause a bit of data loss in the case of long numbers. May be worth fixing.
			int numberFilled = Math.round(percentFilled * blockList.size());
			
			if ((blockList.indexOf(list) + 1) <= numberFilled) {
				for (Block b : list) {
					b.setType(full);
				}
			} else {
				for (Block b : list) {
					b.setType(empty);
				}
			}
		}
	}
	
	private void clear () {
		for (List<Block> list : blockList) {
			for (Block b : list) {
				b.setType(Material.AIR);
			}
		}
	}
	
	public void clearSection (int index) {
		for (Block b : blockList.get(index)) {
			b.setType(Material.AIR);
		}
	}
	
}