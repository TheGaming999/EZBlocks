package me.clip.ezblocks.listeners;

import org.bukkit.event.Event;
import org.bukkit.event.EventException;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.plugin.EventExecutor;

import me.clip.ezblocks.EZBlocks;

public class BreakListener implements EventExecutor, Listener {

	private EZBlocks plugin;
	
	public BreakListener(EZBlocks plugin, EventPriority priority) {
		this.plugin = plugin;
		this.plugin.getServer().getPluginManager().registerEvent(BlockBreakEvent.class,
				this, priority, this, plugin, true);
	}
	
	@Override
	public void execute(Listener listener, Event event) throws EventException {
		this.onBreak((BlockBreakEvent) event);
	}
	
	@EventHandler
	private void onBreak(BlockBreakEvent e)
    {
		if (e.isCancelled()) return;
		
		if (plugin.getBreakHandler().check(e.getPlayer(), e.getBlock())) {
			plugin.getBreakHandler().handleBlockBreakEvent(e.getPlayer(), e.getBlock());
		}
    }

}
