/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.request;

import core.GameClient;
import core.GameServer;
import java.io.DataInputStream;
import java.io.IOException;
import net.response.ResponseKeyboard;
import race.RaceManager;
import util.DataReader;

/**
 *
 * @author Sbc-ComEx
 */
public class RequestKeyboard extends  GameRequest{
    
    private int keytype,key;
    private  ResponseKeyboard responsekeyboard;
       
    @Override
    public void parse(DataInputStream dataInput) throws IOException {
        keytype = DataReader.readInt(dataInput);
        key = DataReader.readInt(dataInput);
    }
    
    @Override
    public void process() throws Exception {
        responsekeyboard = new ResponseKeyboard();
        responsekeyboard.setKeytype(keytype);
        responsekeyboard.setKey(key);
        
        GameClient opClient = RaceManager.manager.getRaceByClient(client)
                .getOpponent(client).getClient();
        opClient.add(responsekeyboard);        
    }
}
