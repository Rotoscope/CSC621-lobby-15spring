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
import net.response.ResponseRREndGame;
import race.RaceManager;
import util.DataReader;

/**
 *
 * @author markfavis
 */
public class RequestRREndGame extends GameRequest {
    
    private boolean gameCompleted;
    private float finalTime;
    private int p_id;
    private ResponseRREndGame responseRREndGame;
    
    public RequestRREndGame(){
        gameCompleted = false;
        finalTime = 0;
    }

    @Override
    public void parse(DataInputStream dataInput) throws IOException {
        gameCompleted = DataReader.readBoolean(dataInput);
        finalTime = DataReader.readFloat(dataInput);
    }

    @Override
    public void process() throws Exception {
        responseRREndGame = new ResponseRREndGame();
        
        responseRREndGame.setFinalTime(finalTime);
        responseRREndGame.setGameCompleted(gameCompleted);
        
        // GET FASTEST and HIGHEST POINT PLAYER NAMES FROM DATABASE
        // THEN SET RESPONSERRENDGAME VALUES TO IT
        
        GameClient opClient = RaceManager.manager.getRaceByClient(client)
                .getOpponent(client).getClient();
        opClient.add(responseRREndGame);
    }
    
}
