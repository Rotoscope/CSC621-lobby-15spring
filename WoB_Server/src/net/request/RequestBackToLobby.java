/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.request;

import java.io.DataInputStream;
import java.io.IOException;
import lobby.GameRoomManager;

/**
 *
 * @author yanxing wang
 */
public class RequestBackToLobby extends GameRequest {

    @Override
    public void parse(DataInputStream dataInput) throws IOException {
    }

    @Override
    public void process() throws Exception {
        GameRoomManager.getInstance().onClientQuit(client);
    }   
}
