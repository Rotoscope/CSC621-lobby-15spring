/**
 * author: Ye
 */
package networking.response;

// Other Imports
import metadata.Constants;
import utility.GamePacket;

/**
 * The ResponseLogin class contains information about the authentication
 * process.
 */
public class ResponseBattleInit extends GameResponse {

    private short status;


    public ResponseBattleInit() {
        responseCode = Constants.SMSG_BATTLE_INIT;
    }

    @Override
    public byte[] constructResponseInBytes() {
        GamePacket packet = new GamePacket(responseCode);
        packet.addShort16(status);

        if (status == 0) {
            packet.addString("request received battle started");
        }
        else if (status == 1){
        	packet.addString("request received wait for a battle");
        }
        else if (status == 2){
        	packet.addString("want a battle");
        }

        return packet.getBytes();
    }

    public void setStatus(short status) {
        this.status = status;
    }
}
