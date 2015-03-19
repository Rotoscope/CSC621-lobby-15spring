package model;

import core.ObjectiveManager;

/*
 * author: Ye
 */

public class Battle {
	private String battle_id;
	private ObjectiveManager objectiveManager;
	private Player requestPlayer, targetPlayer;
	private long requestTime;
	private long confirmTime;
	
	public Battle(Player requestPlayer){
		this.setRequestPlayer(requestPlayer);
		
	}
	
	//start battle
	public void startBattle(Player player){
		//battle between battlePlayer and player
	}

	public String getID() {
		return battle_id;
	}
    public void destroy() {
        objectiveManager.stopNextObjectiveTimer();
    }

	public long getRequestTime() {
		return requestTime;
	}

	public void setRequestTime(long requestTime) {
		this.requestTime = requestTime;
	}

	public Player getTargetPlayer() {
		return targetPlayer;
	}

	public void setTargetPlayer(Player targetPlayer) {
		this.targetPlayer = targetPlayer;
	}

	public Player getRequestPlayer() {
		return requestPlayer;
	}

	public void setRequestPlayer(Player requestPlayer) {
		this.requestPlayer = requestPlayer;
	}

	public long getConfirmTime() {
		return confirmTime;
	}

	public void setConfirmTime(long confirmTime) {
		this.confirmTime = confirmTime;
	}

}
