
package lobby;

import core.GameClient;
import net.response.GameResponse;

/**
 *
 * @author yanxing wang
 */
public class GameRoom {
    
    private final GameClient mClients[];
    private final int CAPACITY = 2;
    
    private int mNumClients = 0;
    private int mID = 0;
    
    public GameRoom () {
        mClients = new GameClient[CAPACITY];
    }
    
    public boolean addClient(GameClient player) {
        if(mNumClients == CAPACITY) {
            return false;
        }
        mClients[mNumClients++] = player;
        return true;
    }
    
    public void sendResponse(GameResponse resp) {
        for(int i = 0; i < mNumClients; ++i) {
            mClients[i].add(resp);
        }
    }
    
    public boolean isFull() {
        return mNumClients == CAPACITY;
    }
    
    public void setID(int id) {
        this.mID = id;
    }
    
    public int getID() {
        return this.mID;
    }
}