package game;

import java.util.Map;
import java.util.HashMap;


import core.GameClient;
import util.Log;

/**
 *
 * @author yanxingwang
 */
public class GameRoomManager {
    
    private static GameRoomManager sInstance = null;
    
    private final Map<Integer, GameRoom> mRooms = new HashMap<Integer, GameRoom>();
    
    // key is session_id
    private final Map<String, GameRoom> mRoomTable = new HashMap<String, GameRoom>();
    
    public GameRoomManager() {
        
    }
    
    public static GameRoomManager getInstance() {
        if (sInstance == null) {
            sInstance = new GameRoomManager();
        } 
        return sInstance;
    }
    
    public GameRoom getRoomWithID(int id) {
        if (mRooms.containsKey(id)) {
            return mRooms.get(id);
        } else {
            GameRoom room = new GameRoom();
            room.setID(id);
            mRooms.put(id, room);
            Log.println("Game room allocated with ID = " + Integer.toString(id));
            return room;
        }
    }
    
    public void addClientToRoom(GameClient client, int roomID) {
        GameRoom room = getRoomWithID(roomID);
        room.addClient(client);
        mRoomTable.put(client.getID(), room);
    }

    public GameRoom getRoom(String id) {
        return mRoomTable.get(id);
    }
}
