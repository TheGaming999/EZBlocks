package me.clip.ezblocks.listeners;

import me.clip.autosell.events.AutoSellEvent;
import me.clip.autosell.events.DropsToInventoryEvent;
import me.clip.ezblocks.EZBlocks;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

public class AutoSellListener implements Listener {
	
	private EZBlocks plugin;
	
	public AutoSellListener(EZBlocks i) {
		plugin = i;
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}

	@EventHandler(priority = EventPriority.HIGH)
	public void onBreak(AutoSellEvent e) {
		if (e.isCancelled()) {
			return;
		}
		
		plugin.getBreakHandler().handleBlockBreakEvent(e.getPlayer(), e.getBlock());
	}
	
	@EventHandler(priority = EventPriority.HIGH)
	public void onBreak(DropsToInventoryEvent e) {
		if (e.isCancelled()) {
			return;
		}
		
		plugin.getBreakHandler().handleBlockBreakEvent(e.getPlayer(), e.getBlock());
	}
}
