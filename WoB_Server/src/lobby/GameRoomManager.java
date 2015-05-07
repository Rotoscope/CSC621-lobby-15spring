
package lobby;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

import core.GameClient;
import java.util.Iterator;
import util.Log;

/**
 *
 * @author yanxingwang
 */
public class GameRoomManager {
    
    private static GameRoomManager sInstance = null;
    
    private final List<GameRoom> mRooms = new ArrayList<GameRoom>();
    
    // key is session_id
    private final Map<String, GameRoom> mRoomTable = new HashMap<String, GameRoom>();
    
    private int mRoomIDCount = 0;
    
    public GameRoomManager() {
    }
    
    public static GameRoomManager getInstance() {
        if (sInstance == null) {
            sInstance = new GameRoomManager();
        } 
        return sInstance;
    }
    
    public Iterator<GameRoom> getRoomsIter() {
        return mRooms.iterator();
    }
    
    public int getNumRooms() {
        return mRooms.size();
    }
    
    public GameRoom pairClient(GameClient client) {
        for (GameRoom r : this.mRooms) {
            if (!r.isFull()) {
                Log.println("Paired the client to room: " + Integer.toString(r.getID()));
                r.addClient(client);
                mRoomTable.put(client.getID(), r);
                return r;
            }
        }
        
        GameRoom room = new GameRoom();
        room.setID(mRoomIDCount++);
        room.addClient(client);
        mRooms.add(room);
        mRoomTable.put(client.getID(), room);
        
        Log.println("New room created with ID: " + Integer.toString(room.getID()));
        Log.println("Number of rooms: " + Integer.toString(mRooms.size()));
        return room;
    }
    
    public void clientQuit(GameClient client) {
        if (mRoomTable.containsKey(client.getID())) {
            GameRoom room = mRoomTable.get(client.getID());
            room.removeClient(client);
            Log.println("Removed a client in room " + Integer.toString(room.getID()));
            mRoomTable.remove(client.getID());
            
            if (room.isEmpty()) {
                mRooms.remove(room);
                Log.println("This room is now empty, remove it!");
            }
        }
        Log.println("Number of rooms: " + Integer.toString(mRooms.size()));
    }
    
    public GameRoom getRoom(String id) {
        return mRoomTable.getOrDefault(id, null);
    }
}
