/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.request;

import core.GameClient;
import java.io.DataInputStream;
import java.io.IOException;
import net.response.ResponseRRPosition;
import race.RaceManager;
import util.DataReader;

/**
 *
 * @author markfavis
 */
public class RequestRRPosition extends GameRequest {
    
//    private int x, y;
    private float x, y;
    private ResponseRRPosition responseRRPosition;
    
    @Override
    public void parse(DataInputStream dataInput) throws IOException {
        x = Float.parseFloat(DataReader.readString(dataInput));
        y = Float.parseFloat(DataReader.readString(dataInput));
    }

    @Override
    public void process() throws Exception {
        System.out.println("X:  " +  x + "Y :  " + y);
        responseRRPosition = new ResponseRRPosition();
        responseRRPosition.setX(x);
        responseRRPosition.setY(y);
        
        //The playerID of the oppenet of the player who sent the request       
        GameClient opClient = RaceManager.manager.getRaceByClient(client)
                .getOpponent(client).getClient();
        opClient.add(responseRRPosition);
    }
    
}
