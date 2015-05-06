/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.request;

import java.io.DataInputStream;
import race.Race;
import race.RaceManager;
import java.io.IOException;
import net.response.ResponseRaceInit;
import race.RacePlayer;
import util.DataReader;
import util.Log;

/**
 *
 * @author Lowell Milliken
 */
public class RequestRaceInit extends GameRequest {
    private int race_id;
    
    @Override
    public void parse(DataInputStream dataInput) throws IOException {
        race_id = DataReader.readInt(dataInput);
    }

    @Override
    public void process() throws Exception {
        Race race = RaceManager.getInstance().getRace(race_id);
        race.addPlayer(new RacePlayer(client , race_id));
        
        Log.println("Trying to start Race with race ID: " + race_id);
        
        ResponseRaceInit response = new ResponseRaceInit();
        race.sendToAllPlayers(response);
        Log.println("Race created! ");
    }
}