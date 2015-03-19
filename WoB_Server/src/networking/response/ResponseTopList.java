/**
 * From: Lobby team
 * Author: Jacob
 * Description of Class: response structure for receiving toplist from the server
***/

package networking.response;

import metadata.Constants;
import utility.GamePacket;

public class ResponseTopList extends GameResponse {

    private String name1;
    private int score1;
    private String name2;
    private int score2;
    private String name3;
    private int score3;

    public ResponseTopList() {
        responseCode = Constants.SMSG_TOPLIST;
    }

    @Override
    public byte[] constructResponseInBytes() {
        GamePacket packet = new GamePacket(responseCode);
        
        //TODO server team: construct packet w/ toplist info

        return packet.getBytes();
    }
}
