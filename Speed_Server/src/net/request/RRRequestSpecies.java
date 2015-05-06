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
import race.RaceManager;
import util.DataReader;

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
        
        System.out.println("speciec: " + id);

        //The playerID of the oppenet of the player who sent the request
        GameClient opClient = RaceManager.manager.getRaceByClient(client)
                .getOpponent(client).getClient();
        opClient.add(rrResponseSpecies);
    }
}