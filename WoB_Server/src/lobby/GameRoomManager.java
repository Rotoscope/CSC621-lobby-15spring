
package lobby;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;


import core.GameClient;

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
    
    public GameRoom pairClient(GameClient client) {
        for (GameRoom r : this.mRooms) {
            if (!r.isFull()) {
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
        return room;
    }
    
    public GameRoom getRoom(String id) {
        return mRoomTable.getOrDefault(id, null);
    }
}
