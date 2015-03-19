/**
 * author: Ye
 */

package networking.request;

// Java Imports
import java.io.IOException;
import java.sql.Types;

import core.BattleManager;
import core.GameServer;
import dataAccessLayer.TileDAO;
import model.Tile;
import model.Battle;
import model.Player;
import networking.response.ResponseBattleInit;
import utility.DataReader;
import utility.Log;

/**
 * The RequestBattleInit class responses to the battle request from client.
 */
public class RequestBattleInit extends GameRequest {

    // Responses
    private ResponseBattleInit responseBattleInit;
    private int tile_id, target_tile_id,user_id;
    private Tile tile;


    public RequestBattleInit() {
        responses.add(responseBattleInit = new ResponseBattleInit());
    }

    @Override
    public void parse() throws IOException {
        user_id = DataReader.readInt(dataInput);
    	tile_id = DataReader.readInt(dataInput);
    	target_tile_id = DataReader.readInt(dataInput);
    }

    @Override
    public void doBusiness() throws Exception {
    	int owner_id;
    	boolean isOwned;
    	boolean isOline;
    	Player tile_owner;
    	owner_id = TileDAO.getTileOwner(target_tile_id); //connect database to get tile information
    	
    	//dummy tile
    	//tile = new Tile (tile_id);
    	//tile.setOwner(2);
    	//
        //int owner_id = tile.getOwner();
        //Player tile_owner = GameServer.getInstance().getActivePlayer(owner_id);
        Log.println_e("battle request received for tile #" + target_tile_id);
    	
    	isOwned = (TileDAO.getTileOwner(owner_id)!=Types.NULL);//connect database to check if the tile (tile_id) is owned
        //if (owner_id != 0)
            //isOwned = true;
    	boolean isOnline = GameServer.getInstance().isActive(owner_id);//add call to check if the tile owner is online
    	Player requestPlayer = client.getPlayer();
    	if (isOwned && isOnline) {
    		//send response to the owner, if owner accepts battle, start direct battle.
    		//if the owner does not accept within one minute, start proxy battle.
    		tile_owner = GameServer.getInstance().getThreadByPlayerID(owner_id).getPlayer();
    		BattleManager.getInstance().directBattleInit(tile_owner,requestPlayer, responseBattleInit);
    	}
    	else {
    		BattleManager.getInstance().proxyBattleInit(requestPlayer, responseBattleInit);
    	}
    }
}

