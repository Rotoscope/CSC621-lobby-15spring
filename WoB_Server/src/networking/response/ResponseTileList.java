/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package networking.response;

import java.sql.Types;
import java.util.List;
import metadata.Constants;
import model.Tile;
import utility.GamePacket;
import utility.Log;

/**
 *
 * @author Ari
 */
public class ResponseTileList extends GameResponse {
    private List<Tile> tileList;
    private short status;
    private String singleTile;
    private int start, end;
    
    public ResponseTileList() {
        responseCode = Constants.SMSG_TILE_LIST;
    }
    
    @Override
    public byte[] constructResponseInBytes() {
        GamePacket packet = new GamePacket(responseCode);
        packet.addShort16(status);
        
        if (status == 0) {
            packet.addShort16((short) (end-start));
            //Log.println("tile size is " + (end-start));
             for (int i = start; i < end; i++) {
                Tile tile = tileList.get(i);
                singleTile = "";
                singleTile += tile.getTileId();
                if (tile.getOwner()== Types.NULL){
                	singleTile += "," + Constants.TILE_NO_OWNER;
                }
                else {
                	singleTile += "," + tile.getOwner();
                }
                singleTile += "," + tile.getVegetationCapacity() + "," + tile.getTerrainType()
                        + "," + tile.getXPosition() + "," + tile.getYPosition()
                        + "," + tile.getZPosition();
                packet.addString(singleTile);
            }
        }
        return packet.getBytes();
    }

    public void setTileList(List<Tile> tileList) {
        this.tileList = tileList;
    }
    
    public void setStatus(short status) {
        this.status = status;
    }
    
    public void setStart(int start) {
    	this.start = start;
    }
    
    public void setEnd(int end) {
    	this.end = end;
    }
}
