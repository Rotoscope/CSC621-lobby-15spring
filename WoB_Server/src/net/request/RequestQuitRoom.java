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
import net.response.ResponseQuitRoom;

/**
 *
 * @author yanxing wang
 */
public class RequestQuitRoom extends GameRequest {
    
    @Override
    public void parse(DataInputStream dataInput) throws IOException {
    }

    @Override
    public void process() throws Exception {
        GameRoom room = GameRoomManager.getInstance().getRoom(client.getID());
        
        ResponseQuitRoom response = new ResponseQuitRoom();     
        response.setStatus((short)1);
        room.sendResponse(response);
        
        GameRoomManager.getInstance().clientQuit(client);        
    }
}
