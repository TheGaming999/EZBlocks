package me.clip.ezblocks;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.bukkit.configuration.file.FileConfiguration;

public class EZBlocksConfig {

	private EZBlocks plugin;

	public EZBlocksConfig(EZBlocks i) {
		plugin = i;

	}

	protected void loadConfigurationFile() {
		FileConfiguration c = plugin.getConfig();
		c.options().header("EZBlocks version " + plugin.getDescription().getVersion()
						+ " Main configuration file." +
						"\ndatabase: true/false" +
						"\nIf you choose to use MySQL, set this to true and" +
						"\nenter your MySQL credentials" +
						"\nsave_interval: how often (in minutes) should EZBlocks save to file/MySQL" +
						"\nenabled_worlds: what worlds should EZBlocks be enabled in, use 'all' for all worlds" +
						"\npickaxe_never_breaks: should EZBlocks prevent pickaxes from breaking" +
						"\nonly_track_below_y: should we only track blockbreaks below a certain y coordinate" +
						"\npickaxe_counter:" +
						"\nThis section lets you choose how to display the pickaxe specific blockbreak counter" +
						"\nIf useDisplayName: true, Do not add Blocks broken: to the format" +
						"\nthe format specified will be added on any pickaxe with a displayname present" +
						"\nif useDisplayName: false, the format will be added in the lore of the pickaxe" +
						"\n" +
						"\nglobal_rewards:" +
						"\nthis is where you can give rewards when players reach x amount of global blockbreaks" +
						"\nthis does not reward depending on the pickaxe counter, only global blockbreaks" +
						"\n" +
						"\ninterval_rewards:" +
						"\nthis is where you can give rewards every x blocks mined" +
						"\nthis does not reward depending on the pickaxe counter, only global blockbreaks");
		/** Config changes database @author Maximvdw */
		c.addDefault("database.enabled", false);
		c.addDefault("database.hostname", "localhost");
		c.addDefault("database.port", 3306);
		c.addDefault("database.database", "ezblocks");
		c.addDefault("database.prefix", "");
		c.addDefault("database.username", "root");
		c.addDefault("database.password", "");
		/** End of database config */
		if (c.contains("integration")) {
			c.set("integration", null);
		}
		c.addDefault("hooks.autosell.use_autosell_events", true);
		c.addDefault("hooks.tokenenchant.count_exploded_blocks", true);
		
		c.addDefault("save_interval", 5);
		c.addDefault("blockbreakevent_priority", "HIGHEST");
		c.addDefault("enabled_worlds", Arrays.asList(new String[] { "world", "world_nether", "all" }));
		c.addDefault("survival_mode_only", true);
		c.addDefault("pickaxe_never_breaks", true);
		c.addDefault("only_track_below_y.enabled", false);
		c.addDefault("only_track_below_y.coord", 50);
		c.addDefault("pickaxe_counter.enabled", false);
		c.addDefault("pickaxe_counter.useDisplayName", true);
		c.addDefault("pickaxe_counter.format", "&8[&c%blocks%&8]");	
		c.addDefault("command_options.add.give_rewards_on_add", true);	
		c.addDefault("global_rewards.default.blocks_needed", 100);
		c.addDefault("global_rewards.default.reward_commands",
				Arrays.asList(new String[] { "eco give %player% 100",
						"ezmsg &bCongrats on your first 100 blocks!" }));
		c.addDefault("interval_rewards.default.every", 100);
		c.addDefault("interval_rewards.default.reward_commands",
				Arrays.asList(new String[] { "eco give %player% 1",
						"ezmsg You mined 100 blocks!" }));
		c.addDefault("pickaxe_global_rewards.default.blocks_needed", 100);
		c.addDefault("pickaxe_global_rewards.default.reward_commands",
				Arrays.asList(new String[] { "eco give %player% 1",
						"ezmsg &bCongrats You mined 100 blocks with this pickaxe!" }));
		c.addDefault("pickaxe_interval_rewards.default.every", 100);
		c.addDefault("pickaxe_interval_rewards.default.reward_commands",
				Arrays.asList(new String[] { "eco give %player% 1",
						"ezmsg You mined 100 blocks!" }));
		c.addDefault("blocks_broken_command_enabled", true);
		c.addDefault("blocks_broken_message", "&bYou have broken &e%blocksbroken%&b blocks!");
		c.addDefault("material_blacklist",
				Arrays.asList(new String[] { "DIRT",
						"GRASS" }));
		c.addDefault("blacklist_is_whitelist", false);
		c.addDefault("tracked_tools",
				Arrays.asList(new String[] { "WOOD_PICKAXE", "STONE_PICKAXE", "IRON_PICKAXE", "GOLD_PICKAXE",
						"DIAMOND_PICKAXE" }));
		c.options().copyDefaults(true);
		plugin.saveConfig();
		plugin.reloadConfig();
	}
	
	public boolean giveRewardsOnAddCommand() {
		return plugin.getConfig().getBoolean("command_options.add.give_rewards_on_add", true);	
	}
	
	public boolean blacklistIsWhitelist() {
		return plugin.getConfig().getBoolean("blacklist_is_whitelist");
	}
	
	public boolean hookTokenEnchant() {
		return plugin.getConfig().getBoolean("hooks.tokenenchant.count_exploded_blocks");
	}
	
	public boolean useAutoSellEvents() {
		return plugin.getConfig().getBoolean("hooks.autosell.use_autosell_events");
	}
	
	public String getListenerPriority() {
		return plugin.getConfig().getString("blockbreakevent_priority");
	}
	
	public List<String> trackedTools() {
		return plugin.getConfig().getStringList("tracked_tools");
	}
	
	public boolean pickCounterEnabled() {
		return plugin.getConfig().getBoolean("pickaxe_counter.enabled");
	}
	
	public boolean pickCounterInDisplay() {
		return plugin.getConfig().getBoolean("pickaxe_counter.useDisplayName");
	}
	
	public String pickCounterFormat() {
		return plugin.getConfig().getString("pickaxe_counter.format");
	}

	protected int loadGlobalRewards() {
		FileConfiguration c = plugin.getConfig();
		RewardHandler.globalRewards = new HashMap<Integer, Reward>();
		if (c.isConfigurationSection("global_rewards")) {

			Set<String> keys = c.getConfigurationSection("global_rewards")
					.getKeys(false);

			if (keys == null || keys.isEmpty()) {
				return 0;
			}

			for (String id : keys) {
				Reward r = new Reward(id);
				int needed = c.getInt("global_rewards."+id+".blocks_needed");
				if (needed > 0) {
					r.setBlocksNeeded(needed);
					r.setCommands(c.getStringList("global_rewards." + id
							+ ".reward_commands"));
					plugin.rewards.setReward(r.getBlocksNeeded(), r);
				}
				
			}
			return RewardHandler.globalRewards.size();
		}
		return 0;
	}
	
	protected int loadPickaxeGlobalRewards() {
		FileConfiguration c = plugin.getConfig();
		RewardHandler.pickaxeGlobalRewards = new HashMap<Integer, Reward>();
		if (c.isConfigurationSection("pickaxe_global_rewards")) {

			Set<String> keys = c.getConfigurationSection("pickaxe_global_rewards")
					.getKeys(false);

			if (keys == null || keys.isEmpty()) {
				return 0;
			}

			for (String id : keys) {
				Reward r = new Reward(id);
				int needed = c.getInt("pickaxe_global_rewards."+id+".blocks_needed");
				if (needed > 0) {
					r.setBlocksNeeded(needed);
					r.setCommands(c.getStringList("pickaxe_global_rewards." + id
							+ ".reward_commands"));
					plugin.rewards.setPickaxeReward(r.getBlocksNeeded(), r);
				}
				
			}
			return RewardHandler.pickaxeGlobalRewards.size();
		}
		return 0;
	}
	
	protected int loadIntervalRewards() {
		FileConfiguration c = plugin.getConfig();
		RewardHandler.intervalRewards = new HashMap<Integer, Reward>();
		if (c.isConfigurationSection("interval_rewards")) {

			Set<String> keys = c.getConfigurationSection("interval_rewards")
					.getKeys(false);

			if (keys == null || keys.isEmpty()) {
				return 0;
			}

			for (String id : keys) {
				Reward r = new Reward(id);
				int every = c.getInt("interval_rewards."+id+".every");
				if (every > 0) {
				r.setBlocksNeeded(every);
				r.setCommands(c.getStringList("interval_rewards." + id
						+ ".reward_commands"));
				plugin.rewards.setIntervalReward(r.getBlocksNeeded(), r);
				}
			}
			return RewardHandler.intervalRewards.size();
		}
		return 0;

	}
	
	protected int loadPickaxeIntervalRewards() {
		FileConfiguration c = plugin.getConfig();
		RewardHandler.pickaxeIntervalRewards = new HashMap<Integer, Reward>();
		if (c.isConfigurationSection("pickaxe_interval_rewards")) {

			Set<String> keys = c.getConfigurationSection("pickaxe_interval_rewards")
					.getKeys(false);

			if (keys == null || keys.isEmpty()) {
				return 0;
			}

			for (String id : keys) {
				Reward r = new Reward(id);
				int every = c.getInt("pickaxe_interval_rewards."+id+".every");
				if (every > 0) {
				r.setBlocksNeeded(every);
				r.setCommands(c.getStringList("pickaxe_interval_rewards." + id
						+ ".reward_commands"));
				plugin.rewards.setPickaxeIntervalReward(r.getBlocksNeeded(), r);
				}
			}
			return RewardHandler.pickaxeIntervalRewards.size();
		}
		return 0;
	}

}
