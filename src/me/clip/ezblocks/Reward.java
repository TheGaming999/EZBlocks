package me.clip.ezblocks;

import java.util.List;

public class Reward {
	
	private String rewardName;
	
	private int blocksNeeded;
	
	private List<String> commands;
	
	public Reward(String rewardName) {
		this.setRewardName(rewardName);
	}

	public String getRewardName() {
		return rewardName;
	}

	public void setRewardName(String rewardName) {
		this.rewardName = rewardName;
	}

	public int getBlocksNeeded() {
		return blocksNeeded;
	}

	public void setBlocksNeeded(int blocksNeeded) {
		this.blocksNeeded = blocksNeeded;
	}

	public List<String> getCommands() {
		return commands;
	}

	public void setCommands(List<String> commands) {
		this.commands = commands;
	}
	
	
	

}
