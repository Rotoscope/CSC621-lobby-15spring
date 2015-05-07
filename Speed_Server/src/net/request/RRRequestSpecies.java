/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.request;

import core.GameClient;
import java.io.DataInputStream;
import java.io.IOException;
import net.response.RRResponseSpecies;
import race.Race;
import race.RaceManager;
import race.RacePlayer;
import util.DataReader;
import util.Log;

/**
 *
 * @author Sbc-ComEx
 */
public class RRRequestSpecies extends GameRequest {

    private int id;
    private RRResponseSpecies rrResponseSpecies;

    @Override
    public void parse(DataInputStream dataInput) throws IOException {
        id = DataReader.readInt(dataInput);
    }

    @Override
    public void process() throws Exception {

        rrResponseSpecies = new RRResponseSpecies();
        rrResponseSpecies.setId(id);
        
        System.out.println("Speciec: " + id);

        //The playerID of the oppenet of the player who sent the request
        Race r = RaceManager.manager.getRaceByClient(client);
        if (r == null) {
            Log.printf_e("Null Race!");
            return;
        }
        RacePlayer opponent = r.getOpponent(client);
        if (opponent == null) {
            Log.printf_e("No opponent!");
        } else {
            opponent.getClient().add(rrResponseSpecies);
        }
    }
}