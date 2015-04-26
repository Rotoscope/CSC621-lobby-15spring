package net.request;

// Java Imports
import game.GameRoom;
import game.GameRoomManager;
import java.io.DataInputStream;
import java.io.IOException;

// Other Imports
import net.response.ResponseMessage;
import util.DataReader;
import util.Log;

/**
 * The RequestMessage class handles all incoming chat messages and redirect those
 * messages, if needed, to other users.
 */
public class RequestMessage extends GameRequest {

    private short type;
    private String message;

    @Override
    public void parse(DataInputStream dataInput) throws IOException {
        type = DataReader.readShort(dataInput);
        message = DataReader.readString(dataInput).trim();

        if (message.isEmpty()) {
            throw new IOException();
        }
    }

    @Override
    public void process() throws Exception {
        ResponseMessage response = new ResponseMessage();
        response.setMessage(message);
        response.setName(client.getName());
        response.setType(type);
        
        GameRoom room = GameRoomManager.getInstance().getRoom(client.getID());
        if (room != null) {
            room.sendResponse(response);
        } else {
            Log.printf_e("Can't find the game room!");
        }
    }
}
