package me.clip.ezblocks.listeners;

import me.clip.ezblocks.EZBlocks;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import com.vk2gpz.tokenenchant.event.TEBlockExplodeEvent;

public class TEListener implements Listener {
	
	private EZBlocks plugin;
	
	public TEListener(EZBlocks i) {
		plugin = i;
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void onBreak(TEBlockExplodeEvent e) {
		
		if (e.isCancelled()) {
			return;
		}
		
		if (!plugin.getBreakHandler().check(e.getPlayer(), e.getBlock())) {
			return;
		}

		Player p = e.getPlayer();
		
		for (Block b : e.blockList()) {
			if (b != null && b.getType() != Material.AIR) {
				plugin.getBreakHandler().handleBlockBreakEvent(p, b);
			}
		}
	}
}
