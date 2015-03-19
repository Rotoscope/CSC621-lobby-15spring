package core;


/*
 * author: Ye
 */

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import networking.response.ResponseBattleInit;
import utility.Log;
import model.Battle;
import model.Player;

public class BattleManager {

	// Singleton Instance
	public static BattleManager manager;
	// battle list
	private ArrayList<Battle> battleList = new ArrayList<Battle>();
	private Player battlePlayer;
	
	public BattleManager() {
	}
	
	public static BattleManager getInstance() {
	    if (manager == null) {
	        manager = new BattleManager();
	    }	
	    return manager;
	}
    public void addBattle(Battle battle) {
        battleList.add(battle);
    }
    public Battle getWaitBattle(){
    	if (battleList.size()== 0){
    		return null;
    	}
    	return battleList.get(battleList.size()-1);
    }
    public Player getWaitPlayer(){
    	if (getWaitBattle()== null) {
    		return null;
    	}
    	return getWaitBattle().getRequestPlayer();
    }
    public void proxyBattleInit(Player requestPlayer,ResponseBattleInit responseBattleInit) throws IOException{
    	//check battle queue, if it is null, then add player to the queue
    	//if it is not null, then pass player pair to the battle manager
    	Player waitPlayer = getWaitPlayer();
    	if (waitPlayer != null){
    			getWaitBattle().setTargetPlayer(requestPlayer);
    			//getWaitBattle().setConfirmTime(System.currentTimeMillis());
    			Log.println_e("start a battle between " + requestPlayer.getID() + " and "+waitPlayer.getID());
    			responseBattleInit.setStatus((short) 0);    
    			requestPlayer.getClient().send(responseBattleInit);
    			//pass information to battle manager
    			//notify player in queue 
    			//clear battle queue
    			ResponseBattleInit temp = new ResponseBattleInit();
    			temp.setStatus((short) 0);
    			try {
					waitPlayer.getClient().send(temp);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
    			BattleManager.getInstance().clearBattlePlayer();    		
    	}
    	else {
    		//add player to battle queue
    		Battle battle = new Battle(requestPlayer);
    		addBattle(battle);
    		Log.println_e(requestPlayer.getID()+ " is added to battle queue");
    		//tell client to wait
    		responseBattleInit.setStatus((short) 1);
    	}
    }
    
    public void directBattleInit(Player targetPlayer,Player requestPlayer,ResponseBattleInit responseBattleInit){
    	//add a new battle to battle manager
    	Battle battle = new Battle(requestPlayer);
    	battle.setTargetPlayer(targetPlayer);
    	battle.setRequestTime(System.currentTimeMillis());
    	addBattle(battle);
    	//send request
    	ResponseBattleInit temp = new ResponseBattleInit();
		temp.setStatus((short) 2);
		try {
			targetPlayer.getClient().send(temp);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    }
	
	public Player getBattlePlayer() {
		return battlePlayer;
	}

	public void setBattlePlayer(Player battlePlayer) {
		synchronized (this) {
			this.battlePlayer = battlePlayer;
		}
	}
	public void clearBattlePlayer() {
		synchronized (this) {
			this.battlePlayer = null;
		}
	} 
	

	
}