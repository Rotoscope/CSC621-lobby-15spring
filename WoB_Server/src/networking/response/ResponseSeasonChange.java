/**
 * @author Lobby Team
 */
package networking.response;

// Other Imports
import metadata.Constants;
import utility.GamePacket;

/**
 * The ResponseLogin class contains information about the authentication
 * process.
 */
public class ResponseSeasonChange extends GameResponse {

    private short status;
    private int seasonCode;
    private int eventCode;


    public ResponseSeasonChange() {
        responseCode = Constants.SMSG_SEASON_CHANGE;
    }

    @Override
    public byte[] constructResponseInBytes() {
        GamePacket packet = new GamePacket(responseCode);
        packet.addShort16(status);

        if (status == 0) { //season change, call season
            packet.addInt32(seasonCode);            
        }
        else if (status == 1){ //natural event change, call natural event
        	packet.addInt32(eventCode); 
        }
        else if (status == 2){
        	//packet.addString();
        }

        return packet.getBytes();
    }

    public void setStatus(short status) {
        this.status = status;
    }
    
    public void setSeasonCode(int seasonCode) {
        this.seasonCode = seasonCode;
    }
    
    public void setEventCode(int eventCode) {
        this.eventCode = eventCode;
    }
}

