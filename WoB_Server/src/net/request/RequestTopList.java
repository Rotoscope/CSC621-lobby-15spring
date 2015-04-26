package net.request;

// Java Imports
import java.io.DataInputStream;
import java.io.IOException;

// Other Imports
import net.response.ResponseTopList;

public class RequestTopList extends GameRequest {
    @Override
    public void parse(DataInputStream dataInput) throws IOException {
        
    }

    @Override
    public void process() throws Exception {
        ResponseTopList response = new ResponseTopList();
        response.setData("Player1", 1, "Player2", 2, "Player3", 3); //server team - pass actual data here
        client.add(response);
    }
}