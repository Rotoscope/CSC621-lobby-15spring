package net.response;

// Other Imports
import metadata.NetworkCode;
import util.GamePacket;

/**
 * The ResponseMessage class is used to sent chat messages to the client.
 */
public class ResponseStart extends GameResponse {

    private short status = 0;

    public ResponseStart() {
        response_id = NetworkCode.REQUEST_START;
    }

    @Override
    public byte[] getBytes() {
        GamePacket packet = new GamePacket(response_id);
        packet.addShort16(status);
        return packet.getBytes();
    }

    public void setStatus(short status) {
        this.status = status;
    }
}
