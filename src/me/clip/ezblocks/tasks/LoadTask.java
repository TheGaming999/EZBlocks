package me.clip.ezblocks.tasks;

import me.clip.ezblocks.BreakHandler;
import me.clip.ezblocks.EZBlocks;

public class LoadTask implements Runnable {

	private EZBlocks plugin;
	private String uuid;

	public LoadTask(EZBlocks instance, String uuid) {
		plugin = instance;
		this.uuid = uuid;
	}

	@Override
	public void run() {
		if (BreakHandler.breaks != null) {
			if (!BreakHandler.breaks.containsKey(uuid)) {
				if (plugin.playerconfig.hasData(uuid)) {
					BreakHandler.breaks.put(uuid, plugin.playerconfig.getBlocksBroken(uuid));
				} else {
					BreakHandler.breaks.put(uuid, 0);
				}
			}
		}
	}
}