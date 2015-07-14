package me.clip.ezblocks;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;


public class RewardHandler {
	
	EZBlocks plugin;
	
	protected static HashMap<Integer, Reward> globalRewards;

	protected static HashMap<Integer, Reward> intervalRewards;
	
	protected static HashMap<Integer, Reward> pickaxeGlobalRewards;
	
	protected static HashMap<Integer, Reward> pickaxeIntervalRewards;
	
	public RewardHandler(EZBlocks i) {
		globalRewards = new HashMap<Integer, Reward>();
		plugin = i;
	}
	
	public void setReward(int breaks, Reward r) {
		if (globalRewards == null) {
			globalRewards = new HashMap<Integer, Reward>();
		}
		globalRewards.put(breaks, r);
	}
	
	public void setIntervalReward(int breaks, Reward r) {
		if (intervalRewards == null) {
			intervalRewards = new HashMap<Integer, Reward>();
		}
		intervalRewards.put(breaks, r);
	}
	
	public void setPickaxeReward(int breaks, Reward r) {
		if (pickaxeGlobalRewards == null) {
			pickaxeGlobalRewards = new HashMap<Integer, Reward>();
		}
		pickaxeGlobalRewards.put(breaks, r);
	}
	
	public void setPickaxeIntervalReward(int breaks, Reward r) {
		if (pickaxeIntervalRewards == null) {
			pickaxeIntervalRewards = new HashMap<Integer, Reward>();
		}
		pickaxeIntervalRewards.put(breaks, r);
	}
	
	public void giveReward(Player p, int breaks) {
		
		if (globalRewards == null 
				|| !globalRewards.containsKey(breaks) 
				|| globalRewards.get(breaks) == null) {
			return;
		}
		
		Reward r = globalRewards.get(breaks);
		
		if (r.getCommands() == null || r.getCommands().isEmpty()) {
			return;
		}
		
		for (String cmd : r.getCommands()) {
			
			String f = cmd.replace("%player%", p.getName()).replace("%blocksbroken%", breaks+"");
			
			if (cmd.startsWith("ezmsg")) {
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', f.replace("ezmsg ", "")));
			}
			else if (cmd.startsWith("ezbroadcast")) {
				Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', f.replace("ezbroadcast ", "")));
			}
			else {
				Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), f);
			}
			
		}
				
	}
	
	public void givePickaxeReward(Player p, int breaks) {
		
		if (pickaxeGlobalRewards == null 
				|| !pickaxeGlobalRewards.containsKey(breaks) 
				|| pickaxeGlobalRewards.get(breaks) == null) {
			return;
		}
		
		Reward r = globalRewards.get(breaks);
		
		if (r == null) {
			return;
		}
		
		if (r.getCommands() == null || r.getCommands().isEmpty()) {
			return;
		}
		
		for (String cmd : r.getCommands()) {
			
			String f = cmd.replace("%player%", p.getName()).replace("%blocksbroken%", breaks+"");
			
			if (cmd.startsWith("ezmsg")) {
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', f.replace("ezmsg ", "")));
			}
			else if (cmd.startsWith("ezbroadcast")) {
				Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', f.replace("ezbroadcast ", "")));
			}
			else {
				Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), f);
			}
			
		}
				
	}
	
	
	public void giveIntervalReward(Player p, int breaks) {
		
		if (intervalRewards == null || intervalRewards.isEmpty()) {
			return;
		}
		
		for (Reward r : intervalRewards.values()) {
			if (breaks % r.getBlocksNeeded() == 0) {
							
				if (r.getCommands() == null || r.getCommands().isEmpty()) {
					return;
				}
				
				for (String cmd : r.getCommands()) {
					
					String f = cmd.replace("%player%", p.getName()).replace("%blocksbroken%", breaks+"");
					
					if (cmd.startsWith("ezmsg ")) {
						p.sendMessage(ChatColor.translateAlternateColorCodes('&', f.replace("ezmsg ", "")));
					}
					else if (cmd.startsWith("ezbroadcast ")) {
						Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', f.replace("ezbroadcast ", "")));
					}
					else {
						Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), f);
					}
					
				}
			}
		}		
	}
	
	public void givePickaxeIntervalReward(Player p, int breaks) {

		if (pickaxeIntervalRewards == null || pickaxeIntervalRewards.isEmpty()) {
			return;
		}

		for (Reward r : pickaxeIntervalRewards.values()) {
			if (breaks % r.getBlocksNeeded() == 0) {

				if (r.getCommands() == null || r.getCommands().isEmpty()) {
					return;
				}

				for (String cmd : r.getCommands()) {

					String f = cmd.replace("%player%", p.getName()).replace(
							"%blocksbroken%", breaks + "");

					if (cmd.startsWith("ezmsg ")) {
						p.sendMessage(ChatColor.translateAlternateColorCodes(
								'&', f.replace("ezmsg ", "")));
					} else if (cmd.startsWith("ezbroadcast ")) {
						Bukkit.broadcastMessage(ChatColor
								.translateAlternateColorCodes('&',
										f.replace("ezbroadcast ", "")));
					} else {
						Bukkit.getServer().dispatchCommand(
								Bukkit.getConsoleSender(), f);
					}

				}
			}
		}
	}

}
