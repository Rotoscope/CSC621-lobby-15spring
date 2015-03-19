/**
 * @author Lobby Team
 */
package core;

import java.io.IOException;

import model.Player;
import networking.response.ResponseSeasonChange;

import utility.Log;

/**
 * Season Manager is a thread that handles season change and natural events, 
 * such as fire, snow etc.
 *
 */

public class SeasonManager implements Runnable{
	// Singleton Instance
	public static SeasonManager manager;
    private boolean isDone;
    
	public SeasonManager() {
	}
	
	public static SeasonManager getInstance() {
	    if (manager == null) {
	        manager = new SeasonManager();
	    }	
	    return manager;
	}
	
	@Override
	public void run() {
		Log.println("Season Manager starts...");
		// TODO Auto-generated method stub
		//test
        while (!isDone) {
            try {
            		//send request season change
                	ResponseSeasonChange response = new ResponseSeasonChange();
            		response.setStatus((short) 0);
            		response.setEventCode(0);
            		for (Player player: GameServer.getInstance().getActivePlayers()){
            			player.getClient().send(response);
            			Log.println("Sent response to change season");
            			isDone = true;
            		}
            		
            	
            }
            catch (Exception ex) {
            Log.printf_e("SeasonManager [%d] Error:");
            Log.println_e(ex.getMessage());
            Log.println_e("---");
            ex.printStackTrace();
            }
        }
	}

}
