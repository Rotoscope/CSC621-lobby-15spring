/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.request;

import java.io.DataInputStream;
import java.io.IOException;
import race.Race;
import race.RaceManager;
import util.Log;

/**
 *
 * @author markfavis
 */
public class RequestRRStartGame extends GameRequest {

    @Override
    public void parse(DataInputStream dataInput) throws IOException {
    }

    @Override
    public void process() throws Exception {
        Log.println("request start game from user: '" + client.getID() + "' recieved");
        Race r = RaceManager.getInstance().getRaceByClient(client);        
        Log.println("The race the user belongs to is '" +  r.getID() + "'");
        r.startRace(client);
    }
}
