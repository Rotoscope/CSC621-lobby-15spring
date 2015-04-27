/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.request;

import java.io.DataInputStream;
import java.io.IOException;
import lobby.GameRoom;
import lobby.GameRoomManager;
import net.response.ResponsePair;

/**
 *
 * @author yanxing wang
 */
public class RequestPair extends GameRequest {
    
    @Override
    public void parse(DataInputStream dataInput) throws IOException {
    }

    @Override
    public void process() throws Exception {
        // Start pair
        GameRoom room = GameRoomManager.getInstance().pairClient(client);        
        
        ResponsePair response = new ResponsePair();        
        response.setStatus(room.isFull() ? (short)0 : (short)1);
        
        room.sendResponse(response);
    }
}
