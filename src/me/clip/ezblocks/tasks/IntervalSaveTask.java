package me.clip.ezblocks.tasks;

import java.util.Iterator;
import java.util.Set;

import me.clip.ezblocks.BreakHandler;
import me.clip.ezblocks.EZBlocks;



public class IntervalSaveTask implements Runnable {

	private EZBlocks plugin;
	
	public IntervalSaveTask(EZBlocks instance) {
		plugin = instance;
	}
	
	@Override
	public void run() {
		
		if (BreakHandler.breaks == null || BreakHandler.breaks.isEmpty()) {
			return;
		}
		
		Set<String> save = BreakHandler.breaks.keySet();
		
		Iterator<String> si = save.iterator();
		
		while (si.hasNext()) {
			String uuid = si.next();
			int broken = BreakHandler.breaks.get(uuid);
			if (uuid != null) {
			plugin.playerconfig.savePlayer(uuid, broken);	
			}
		}
		save = null;
	}
}
