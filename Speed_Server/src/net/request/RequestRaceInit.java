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
import util.DataReader;
import util.Log;

/**
 *
 * @author Lowell Milliken
 */
public class RequestRaceInit extends GameRequest {
    private int player_id;
    private int room_id;

    @Override
    public void parse(DataInputStream dataInput) throws IOException {
        player_id = DataReader.readInt(dataInput);
        room_id = DataReader.readInt(dataInput);
    }

    @Override
    public void process() throws Exception {
        Race race = RaceManager.getInstance().getRace(room_id);
        RaceManager.getInstance().addPlayer(race, client);
        
        Log.println("Trying to start Race with race ID: " + room_id);
        
        ResponseRaceInit response = new ResponseRaceInit();
        race.sendToAllPlayers(response);
        Log.println("Race created! ");
    }
}