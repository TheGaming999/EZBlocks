package me.clip.ezblocks;

import java.util.Iterator;
import java.util.Set;

import me.clip.ezblocks.database.Database;
import me.clip.ezblocks.database.MySQL;
import me.clip.ezblocks.listeners.AutoSellListener;
import me.clip.ezblocks.listeners.BreakListenerHigh;
import me.clip.ezblocks.listeners.BreakListenerHighest;
import me.clip.ezblocks.listeners.BreakListenerLow;
import me.clip.ezblocks.listeners.BreakListenerLowest;
import me.clip.ezblocks.listeners.BreakListenerMonitor;
import me.clip.ezblocks.listeners.BreakListenerNormal;
import me.clip.ezblocks.listeners.TEListener;
import me.clip.ezblocks.tasks.IntervalSaveTask;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

public class EZBlocks extends JavaPlugin {

	public PlayerConfig playerconfig = new PlayerConfig(this);
	protected EZBlocksConfig config = new EZBlocksConfig(this);
	protected BreakHandler breakHandler = new BreakHandler(this);
	protected RewardHandler rewards = new RewardHandler(this);
	protected EZBlocksCommands commands = new EZBlocksCommands(this);

	protected static BlockOptions options;

	protected static int saveInterval;
	
	protected static BukkitTask savetask;

	private static EZBlocks ezblocks;

	public static Database database = null;

	@Override
	public void onEnable() {

		ezblocks = this;

		config.loadConfigurationFile();
		
		loadOptions();

		initDb();
		
		breakHandler = new BreakHandler(this);

		Bukkit.getServer().getPluginManager().registerEvents(breakHandler, this);
		
		registerBlockBreakListener();
		
		startSaveTask();
		
		getCommand("blocks").setExecutor(commands);
		
		getLogger().info(config.loadGlobalRewards() + " global rewards loaded!");
		
		getLogger().info(config.loadIntervalRewards() + " interval rewards loaded!");
		
		getLogger().info(config.loadPickaxeGlobalRewards() + " global pickaxe rewards loaded!");
		
		getLogger().info(config.loadPickaxeIntervalRewards() + " interval pickaxe rewards loaded!");
		
		if (Bukkit.getPluginManager().isPluginEnabled("TokenEnchant") && config.hookTokenEnchant()) {
			new TEListener(this);
			getLogger().info("Hooked into TokenEnchant for TEBlockExplodeEvent listener");
		}
	}
	
	private void initDb() {
		if (!getConfig().getBoolean("database.enabled")) {
			playerconfig.reload();
			playerconfig.save();
			getLogger().info("Saving/loading via flatfile!");
		} else {
			// Make connection to the database
			try {
				getLogger().info("Creating MySQL connection ...");
				database = new MySQL(getConfig().getString("database.prefix"),
						getConfig().getString("database.hostname"), getConfig()
								.getInt("database.port") + "", getConfig()
								.getString("database.database"), getConfig()
								.getString("database.username"), getConfig()
								.getString("database.password"));
				database.open();
				// Check if table exists
				if (!database.checkTable("playerblocks")) {
					// Create table
					getLogger().info("Creating MySQL table ...");
					
					database.createTable("CREATE TABLE IF NOT EXISTS `"
							+ database.getTablePrefix() + "data` ("
							+ "  `uuid` varchar(50) NOT NULL,"
							+ "  `blocks` integer NOT NULL,"
							+ "  PRIMARY KEY (`uuid`)"
							+ ") ENGINE=InnoDB DEFAULT CHARSET=latin1;");
				}
			} catch (Exception ex) {
				ex.printStackTrace();
				getLogger().severe("Falling back to flatfiles ...");
				database = null;
				playerconfig.reload();
				playerconfig.save();
			}
		}
	}

	private void loadOptions() {
		saveInterval = getConfig().getInt("save_interval");
		options = new BlockOptions();
		options.setUseBlocksCommand(getConfig().getBoolean("blocks_broken_command_enabled"));
		options.setBrokenMsg(getConfig().getString("blocks_broken_message"));
		options.setEnabledWorlds(getConfig().getStringList("enabled_worlds"));
		options.setUsePickCounter(config.pickCounterEnabled());
		options.setUsePickCounterDisplayName(config.pickCounterInDisplay());
		options.setPickCounterFormat(config.pickCounterFormat());
		options.setPickaxeNeverBreaks(getConfig().getBoolean("pickaxe_never_breaks"));
		options.setOnlyBelowY(getConfig().getBoolean("only_track_below_y.enabled"));
		options.setBelowYCoord(getConfig().getInt("only_track_below_y.coord"));
		options.setSurvivalOnly(getConfig().getBoolean("survival_mode_only"));
		options.setBlacklistedBlocks(getConfig().getStringList("material_blacklist"));
		options.setTrackedTools(config.trackedTools());
		options.setBlacklistIsWhitelist(config.blacklistIsWhitelist());
		options.setGiveRewardsOnAddCommand(config.giveRewardsOnAddCommand());
	}

	protected void reload() {
		stopSaveTask();
		getServer().getScheduler().runTask(this, new IntervalSaveTask(this));
		reloadConfig();
		saveConfig();
		loadOptions();
		startSaveTask();
		getLogger().info(config.loadGlobalRewards() + " global rewards loaded!");
		getLogger().info(config.loadIntervalRewards() + " interval rewards loaded!");
		getLogger().info(config.loadPickaxeGlobalRewards() + " global pickaxe rewards loaded!");
		getLogger().info(config.loadPickaxeIntervalRewards() + " interval pickaxe rewards loaded!");
	}

	@Override
	public void onDisable() {
		stopSaveTask();
		if (BreakHandler.breaks != null) {
			Set<String> save = BreakHandler.breaks.keySet();
		
			Iterator<String> si = save.iterator();
		
			while (si.hasNext()) {
				
				String uuid = si.next();
				
				int broken = BreakHandler.breaks.get(uuid);
				
				playerconfig.savePlayer(uuid, broken);	
				
			}
		
			System.out.println("[EZBlocks] "+save.size()+" players saved!");
			save = null;
		}
		
		RewardHandler.globalRewards = null;
		ezblocks = null;
	}

	protected void registerBlockBreakListener() {
		
		if (config.useAutoSellEvents() && Bukkit.getPluginManager().getPlugin("AutoSell") != null) {
			getLogger().info("Using AutoSell events for block break and sell detection...");
			new AutoSellListener(this);
			return;
		} else {
			getLogger().info("Failed to detect AutoSell. Defaulting to bukkit event listeners...");
		}
		//register break listener
		String priority = config.getListenerPriority();
		
		if (priority.equalsIgnoreCase("lowest")) {
			getLogger().info("BlockBreakEvent listener registered on LOWEST");
			new BreakListenerLowest(this); 
		} else if (priority.equalsIgnoreCase("low")) {
			getLogger().info("BlockBreakEvent listener registered on LOW");
			new BreakListenerLow(this);
		} else if (priority.equalsIgnoreCase("normal")) {
			getLogger().info("BlockBreakEvent listener registered on NORMAL");
			new BreakListenerNormal(this);
		} else if (priority.equalsIgnoreCase("high")) {
			getLogger().info("BlockBreakEvent listener registered on HIGH");
			new BreakListenerHigh(this);
		} else if (priority.equalsIgnoreCase("highest")) {
			getLogger().info("BlockBreakEvent listener registered on HIGHEST");
			new BreakListenerHighest(this);
		} else if (priority.equalsIgnoreCase("monitor")) {
			getLogger().info("BlockBreakEvent listener registered on MONITOR");
			new BreakListenerMonitor(this);
		} else {
			getLogger().info("BlockBreakEvent listener registered on HIGHEST");
			new BreakListenerHighest(this);
		}
	}

	private void startSaveTask() {
		if (savetask == null) {
			getLogger().info("Saving all players every " + saveInterval + " minutes");
			savetask = getServer().getScheduler().runTaskTimerAsynchronously(
					this, new IntervalSaveTask(this), 1L,
					((20L * 60L) * saveInterval));
		} else {
			savetask.cancel();
			savetask = null;
			getLogger().info(
					"Saving all players every " + saveInterval + " minutes");
			savetask = getServer().getScheduler().runTaskTimerAsynchronously(
					this, new IntervalSaveTask(this), 1L,
					((20L * 60L) * saveInterval));
		}

	}

	private void stopSaveTask() {
		if (savetask != null) {
			savetask.cancel();
			savetask = null;
		}
	}

	public int getBlocksBroken(Player p) {
		if (BreakHandler.breaks != null
				&& BreakHandler.breaks.containsKey(p.getUniqueId().toString())) {
			return BreakHandler.breaks.get(p.getUniqueId().toString());
		} else {
			return 0;
		}

	}

	public static EZBlocks getEZBlocks() {
		return ezblocks;
	}
	
	public BreakHandler getBreakHandler() {
		return breakHandler;
	}
}
