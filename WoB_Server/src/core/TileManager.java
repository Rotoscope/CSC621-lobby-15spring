/**
 * Ye
 */
package core;

import java.sql.SQLException;
import java.sql.Types;
import java.util.HashMap;
import java.util.Map;

import utility.Log;
import worldManager.gameEngine.Zone;

import dataAccessLayer.EnvironmentDAO;
import dataAccessLayer.TileDAO;
import dataAccessLayer.WorldDAO;

import metadata.Constants;
import model.Environment;
import model.Tile;
import model.World;

public class TileManager {
	// Singleton Instance
	public static TileManager manager;
	private static Map<Integer, Tile> tileList;
	//private List<Tile> tileList;
	public TileManager() {
	}
	
	public static TileManager getInstance() {
	    if (manager == null) {
	        manager = new TileManager();
	        tileList = new HashMap<Integer, Tile>();
	    }	
	    return manager;
	}
	
	public void makeTile(){
		int tile_id = 1;
		int temp = (int) (Math.sqrt(Constants.TOTAL_TILE_NUM)/2);
		for (int x = 1-temp; x < temp; x++){
			for (int y =1-temp; y < temp; y++){
				//int z =  0 - (x + y);
				tileList.put(tile_id,new Tile(tile_id, x,y,0-(x+y)));
				tile_id++;
				//Log.println( tile_id +"Tile made");
			}
		}
		Log.println("Tile made");
		storeTile();
		Log.println("Tile Saved");
	}
	public void storeTile(){
		try {
			TileDAO.setTileMap(tileList);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * check if the tile table is there or not
	 */
	public void checkTileMap(){
	}
	
	/**
	 * check if the tile is owned or not
	 * @throws SQLException 
	 */
	public boolean checkTile(int tile_id) throws SQLException{
		if (TileDAO.getTileOwner(tile_id) == Types.NULL){
			return false;
		}
		else {
			return true;
		}
	}
	
	/**
	 * own a tile, call TileDAO to save the player id to the tile table
	 * @throws SQLException 
	 */
	public void ownTile(int user_id, int tile_id) throws SQLException{
		TileDAO.updateTileOwner(user_id, tile_id);
		World world= WorldDAO.getPlayerWorlds(user_id).get(0);
        Environment env = EnvironmentDAO.getEnvironmentByWorldID(world.getID());
		Zone zone = env.getZones().get(0);
		int zone_id = zone.getID();
		Log.println(zone_id + "");
		TileDAO.updateZone(tile_id, zone_id);
		
	}
	
	/*public static void main(String[] args){
		TileManager.getInstance().makeTile();
		Map<Integer, Tile> temp =  TileManager.getInstance().tileList;
		for (int i = 0; i < temp.size(); i++){
			System.out.println(temp.get(i).getXPosition());
			System.out.println(temp.get(i).getYPosition());
			System.out.println(temp.get(i).getZPosition());
		}
		
	}*/
}
