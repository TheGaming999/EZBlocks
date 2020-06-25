package me.clip.ezblocks;

import java.util.ArrayList;
import java.util.List;

public class BlockOptions {
	
	
	private List<String> enabledWorlds = new ArrayList<String>();
	private boolean survivalOnly;
	private boolean useBlocksCommand;
	private String brokenMsg;
	private boolean pickaxeNeverBreaks;
	private boolean onlyBelowY;
	private int belowYCoord;
	
	private boolean usePickCounter;
	private boolean usePickCounterDisplayName;
	private String pickCounterFormat;
	
	private List<String> blacklistedBlocks;
	
	private List<String> trackedTools;
	
	private boolean blacklistIsWhitelist;
	
	private boolean giveRewardsOnAddCommand;
	
	public BlockOptions(){
		useBlocksCommand = true;
	}


	public List<String> getEnabledWorlds() {
		return enabledWorlds;
	}


	public void setEnabledWorlds(List<String> enabledWorlds) {
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


	public List<String> getBlacklistedBlocks() {
		return blacklistedBlocks;
	}


	public void setBlacklistedBlocks(List<String> blacklistedBlocks) {
		this.blacklistedBlocks = blacklistedBlocks;
	}


	public List<String> getTrackedTools() {
		return trackedTools;
	}


	public void setTrackedTools(List<String> trackedTools) {
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
