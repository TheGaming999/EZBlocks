package me.clip.ezblocks;

import java.util.Set;

public class BlockOptions {
	
	
	private Set<String> enabledWorlds;
	private boolean survivalOnly;
	private boolean useBlocksCommand;
	private String brokenMsg;
	private boolean pickaxeNeverBreaks;
	private boolean onlyBelowY;
	private int belowYCoord;
	
	private boolean usePickCounter;
	private boolean usePickCounterDisplayName;
	private String pickCounterFormat;
	
	private Set<String> blacklistedBlocks;
	
	private Set<String> trackedTools;
	
	private boolean blacklistIsWhitelist;
	
	private boolean giveRewardsOnAddCommand;
	
	public BlockOptions() {
		useBlocksCommand = true;
	}


	public Set<String> getEnabledWorlds() {
		return enabledWorlds;
	}


	public void setEnabledWorlds(Set<String> enabledWorlds) {
		this.enabledWorlds = enabledWorlds;
	}


	public String getBrokenMsg() {
		return brokenMsg;
	}


	public void setBrokenMsg(String brokenMsg) {
		this.brokenMsg = brokenMsg;
	}


	public boolean usePickCounter() {
		return usePickCounter;
	}


	public void setUsePickCounter(boolean usePickCounter) {
		this.usePickCounter = usePickCounter;
	}


	public boolean pickaxeNeverBreaks() {
		return pickaxeNeverBreaks;
	}


	public void setPickaxeNeverBreaks(boolean pickaxeNeverBreaks) {
		this.pickaxeNeverBreaks = pickaxeNeverBreaks;
	}


	public boolean onlyBelowY() {
		return onlyBelowY;
	}


	public void setOnlyBelowY(boolean onlyBelowY) {
		this.onlyBelowY = onlyBelowY;
	}


	public int getBelowYCoord() {
		return belowYCoord;
	}


	public void setBelowYCoord(int belowYCoord) {
		this.belowYCoord = belowYCoord;
	}


	public boolean usePickCounterDisplayName() {
		return usePickCounterDisplayName;
	}


	public void setUsePickCounterDisplayName(boolean usePickCounterDisplayName) {
		this.usePickCounterDisplayName = usePickCounterDisplayName;
	}


	public String getPickCounterFormat() {
		return pickCounterFormat;
	}


	public void setPickCounterFormat(String pickCounterFormat) {
		this.pickCounterFormat = pickCounterFormat;
	}


	public boolean useBlocksCommand() {
		return useBlocksCommand;
	}


	public void setUseBlocksCommand(boolean useBlocksCommand) {
		this.useBlocksCommand = useBlocksCommand;
	}


	public boolean survivalOnly() {
		return survivalOnly;
	}


	public void setSurvivalOnly(boolean survivalOnly) {
		this.survivalOnly = survivalOnly;
	}


	public Set<String> getBlacklistedBlocks() {
		return blacklistedBlocks;
	}


	public void setBlacklistedBlocks(Set<String> blacklistedBlocks) {
		this.blacklistedBlocks = blacklistedBlocks;
	}


	public Set<String> getTrackedTools() {
		return trackedTools;
	}


	public void setTrackedTools(Set<String> trackedTools) {
		this.trackedTools = trackedTools;
	}


	public boolean blacklistIsWhitelist() {
		return blacklistIsWhitelist;
	}


	public void setBlacklistIsWhitelist(boolean blacklistIsWhitelist) {
		this.blacklistIsWhitelist = blacklistIsWhitelist;
	}


	public boolean giveRewardsOnAddCommand() {
		return giveRewardsOnAddCommand;
	}


	public void setGiveRewardsOnAddCommand(boolean giveRewardsOnAddCommand) {
		this.giveRewardsOnAddCommand = giveRewardsOnAddCommand;
	}
	
}
