/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package networking.request;

import dataAccessLayer.TileDAO;
import java.io.IOException;
import java.sql.Types;

import core.GameServer;
import core.TileManager;
import model.Player;
import model.Tile;
import networking.response.ResponseTile;
import networking.response.ResponseTileUpdate;
import utility.DataReader;
import utility.Log;

/**
 *
 * @author Ari
 */
public class RequestTile extends GameRequest {
    private int tile_id, user_id;
    private Tile tile;
    private ResponseTile responseTile;
    
    public RequestTile() {
        responses.add(responseTile = new ResponseTile());
    }

    @Override
    public void parse() throws IOException {
        tile_id = DataReader.readInt(dataInput);
        user_id = DataReader.readInt(dataInput);
    }

    @Override
    public void doBusiness() throws Exception {
        try {
        Log.printf("Player %d requests Tile %d ", user_id, tile_id);
        if (TileManager.getInstance().checkTile(tile_id)== true) {
            Log.printf("Tile %d is already owned.", tile_id);
            responseTile.setStatus((short) 1);
        }
        else {
            TileManager.getInstance().ownTile(user_id, tile_id);
            responseTile.setStatus((short) 0);
            Log.printf("Player %d successfully owns tile %d ", user_id, tile_id);
            ResponseTileUpdate responseTileUpdate = new ResponseTileUpdate();
            responseTileUpdate.setStatus((short) 0);
            responseTileUpdate.setTileId(tile_id);
            responseTileUpdate.setTileOwner(user_id);
    		for (Player player: GameServer.getInstance().getActivePlayers()){
    			player.getClient().send(responseTileUpdate);
    			Log.println("Sent response to update tile");
    		}
        }   
        
        } catch (NullPointerException e){
            Log.printf("Error fetching tile %d from database.", tile_id);
        }
    }
    
}
