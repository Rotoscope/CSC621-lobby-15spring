/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package networking.response;

import metadata.Constants;
import utility.GamePacket;

/**
 *
 * @author Ari
 */
public class ResponseTile extends GameResponse {
    
    private short status;
    private int user_id;
    
    public ResponseTile() {
        responseCode = Constants.SMSG_TILE;
    }

    @Override
    public byte[] constructResponseInBytes() {
        GamePacket packet = new GamePacket(responseCode);
        packet.addShort16(status);
        
        if (status == 0) {
            packet.addString("Player's tile request successful.");
        }
        else {
            packet.addString("Player's tile request failed.");
        }
        return packet.getBytes();
    }
    
    public void setStatus(short status) {
        this.status = status;
    }
}
