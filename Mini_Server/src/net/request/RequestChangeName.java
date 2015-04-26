package net.request;

// Java Imports
import java.io.DataInputStream;
import java.io.IOException;

// Other Imports
import util.DataReader;

public class RequestChangeName extends GameRequest {

    private String name;

    @Override
    public void parse(DataInputStream dataInput) throws IOException {
        name = DataReader.readString(dataInput).trim();
    }

    @Override
    public void process() throws Exception {
        client.setName(name);
    }
}
