
package game;

import core.GameServer;
import core.GameClient;
import net.request.GameRequest;
import net.response.GameResponse;
import util.Log;

/**
 *
 * @author yanxingwang
 */
public class GameRoom {
    
    private final GameClient mClients[];
    private int mNumClients = 0;
    private int mID = 0;
    
    public GameRoom () {
        // only 2
        mClients = new GameClient[2];
    }
    
    public boolean addClient(GameClient player) {
        if(mNumClients == 2) {
            Log.println_e("Game room is full!");
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
        return mNumClients == 2;
    }
    
    public void setID(int id) {
        this.mID = id;
    }
    
    public int getID() {
        return this.mID;
    }
}
