/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.request;

import game.GameRoom;
import game.GameRoomManager;
import java.io.DataInputStream;
import java.io.IOException;
import net.response.ResponseStart;
import util.DataReader;
import util.Log;

/**
 *
 * @author yanxingwang
 */
public class RequestStart extends GameRequest {

    private int roomID;

    @Override
    public void parse(DataInputStream dataInput) throws IOException {
        roomID = DataReader.readInt(dataInput);        
    }

    @Override
    public void process() throws Exception {
        Log.println("A client is requesting for starting...");
        
        // Allocate room
        GameRoomManager.getInstance().addClientToRoom(client, roomID);
        
        ResponseStart response = new ResponseStart();
        response.setStatus((short)0);
        client.add(response);        
    }
}
