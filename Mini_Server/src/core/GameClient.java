package core;

// Java Imports
import game.GameRoom;
import game.GameRoomManager;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// Other Imports
import metadata.Constants;
import metadata.GameRequestTable;
import net.request.GameRequest;
import net.response.GameResponse;
import util.DataReader;
import util.GameTimer;
import util.Log;

/**
 * The GameClient class is an extension of the Thread class that represents an
 * individual client. Not only does this class holds the connection between the
 * client and server, it is also in charge of managing the connection to
 * actively receive incoming requests and send outgoing responses. This thread
 * lasts as long as the connection is alive.
 */
public class GameClient {

    // Variables
    private final String session_id;
    private final Socket clientSocket;
    private final DataInputStream inputStream; // For use with incoming requests
    private final OutputStream outputStream; // For use with outgoing responses
    private boolean isAlive = true;
    private short type;
    private String version;
    // Responses
    private final List<GameResponse> responses = Collections.synchronizedList(new ArrayList<GameResponse>()); // Temporarily store responses for client
    // Other Variables
    private final GameTimer saveTimer = new GameTimer();
    private final long lastSave = System.currentTimeMillis(); // Last time saved to the database
    private long lastActivity = System.currentTimeMillis();
    
    // user's name
    private String name = "Your Name"; // default

    /**
     * Initialize the GameClient using the client socket and creating both input
     * and output streams.
     *
     * @param session_id holds the unique identifier of this session
     * @param clientSocket holds reference of the socket being used
     * @throws IOException
     */
    public GameClient(String session_id, Socket clientSocket) throws IOException {
        Log.console("Constructing client object..");
        
        this.session_id = session_id;
        this.clientSocket = clientSocket;

        inputStream = new DataInputStream(clientSocket.getInputStream());
        outputStream = clientSocket.getOutputStream();
    }

    public String getID() {
        return session_id;
    }

    public String getIP() {
        return clientSocket.getInetAddress().getHostAddress();
    }

    /**
     * Holds the main loop that processes incoming requests by first identifying
     * its type, then interpret the following data in each determined request
     * class. Queued up responses created from each request class will be sent
     * after the request is finished processing.
     *
     * The loop exits whenever the isPlaying flag is set to false. One of these
     * occurrences is triggered by a timeout. A timeout occurs whenever no
     * activity is picked up from the client such as being disconnected.
     */
    public void run() {
        try {
            // Extract the size of the package from the data stream
            short size = DataReader.readShort(inputStream);

            if (size > 0) {
                lastActivity = System.currentTimeMillis();
                // Separate the remaining package from the data stream
                byte[] buffer = new byte[size];
		//to allow for network latency, check number of bytes read and continue reading
                //until expected data is received
		int bytesRead = 0;
		int counter = 0;
                do {
                    bytesRead += inputStream.read(buffer, bytesRead, size - bytesRead);
                    counter++;
		}
		while(bytesRead < size);
                DataInputStream dataInput = new DataInputStream(new ByteArrayInputStream(buffer));
                // Extract the request identifier
                short request_id = DataReader.readShort(dataInput);
		if (counter > 1) {
                    Log.printf (
			"Note, network latency issue identified, wait count = %d, protocol ID = %d", 
			counter,
			request_id
                    );
		}
                //output packet to screen for packet level debugging purposes
                //DebugPacket (buffer, (int) request_id, false);
                
                // Determine the type of request
                GameRequest request = GameRequestTable.get(request_id);
                // If the request exists, process like following:
                if (request != null) {
                    request.setGameClient(this);

                    try {
                        // Parse the input stream
                        request.parse(dataInput);
                        // Interpret the data
                        request.process();
                        // Send responses to client, if any
                        send();
                    } catch (Exception ex) {
                        Log.printf_e("Request [%d] Error:\n%s", request_id, ex.getMessage());
                    }
                }
            }
        } catch (IOException ex) {
            Log.println_e(ex.getMessage());
        }

        // If there was no activity for the last moments
        if (isAlive) {
            isAlive = System.currentTimeMillis() - lastActivity < Constants.TIMEOUT_MILLISECONDS;
        }

        if (!isAlive) {
            shutdown();
        }
    }

    private void shutdown() {
    }

    /**
     * Used whenever a player exits from the game. The most recent information
     * stored for the player will be saved into the database and any ties with
     * the server will be removed as well.
     */
    public void logout() {
        saveTimer.finish();
        responses.clear();        
    }

    public void end() {
        isAlive = false;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void add(GameResponse response) {
        synchronized (responses) {
            responses.add(response);
        }
    }

    public void send() {
        synchronized (responses) {
            try {
                while (!responses.isEmpty()) {
                    GameResponse response = responses.get(0);
                    outputStream.write(response.getBytes());
                    //output packet to screen for packet level debugging purposes
                    //DebugPacket (response.getBytes(), response.getID(), true);
                    responses.remove(0);
                }
            } catch (IOException ex) {
                Log.printf_e("Client %s connection lost", session_id);
                isAlive = false;
            }
        }
    }

    public short getType() {
        return type;
    }

    public short setType(short type) {
        return this.type = type;
    }

    public String getVersion() {
        return version;
    }

    public String setVersion(String version) {
        return this.version = version;
    }

    public void updateActiveTime() {
        long current = System.currentTimeMillis();
        long seconds = (current - lastActivity) / 1000;

        //account.setActiveTime(account.getActiveTime() + seconds);
        lastActivity = current;
    }

    public void startSaveTimer() {        
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }
}
