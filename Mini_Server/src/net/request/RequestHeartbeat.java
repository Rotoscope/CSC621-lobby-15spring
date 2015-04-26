package net.request;

// Java Imports
import java.io.DataInputStream;
import java.io.IOException;

/**
 * The RequestHeartbeat class is mainly used to release all pending responses to
 * the client. Also used to keep the connection alive.
 */
public class RequestHeartbeat extends GameRequest {

    @Override
    public void parse(DataInputStream dataInput) throws IOException {
    }

    @Override
    public void process() throws Exception {
        client.send();
    }
}
