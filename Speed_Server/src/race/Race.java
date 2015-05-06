package race;

import core.GameClient;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import net.response.ResponseRRStartGame;
import metadata.Constants;
import net.response.GameResponse;
import util.Log;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author markfavis
 */
public class Race {

    private final int raceID;
    private final RacePlayer[] players = new RacePlayer[Constants.MAX_NUMBER_OF_PLAYERS];

    private short playersReadyToStart;

    public Race(int raceID) {
        this.raceID = raceID;
    }
    
    public void addPlayer(RacePlayer p) {
        for (int i = 0; i < Constants.MAX_NUMBER_OF_PLAYERS; ++i) {
            if (this.players[i] == null) {
                this.players[i] = p;
                return;
            }
        }
        Log.printf_e("Can't add player to this race, already full!");
    }

    public int getID() {
        return this.raceID;
    }

    public RacePlayer[] getPlayers() {
        return players;
    }

    public RacePlayer getOpponent(GameClient client) {
        for (int i = 0; i < Constants.MAX_NUMBER_OF_PLAYERS; ++i) {
            if (!this.players[i].getClient().getID().equals(
                    client.getID()
            )) {
                return this.players[i];
            }
        }
        return null; // error
    }

    // USSAGE: Called by RequestRRStartGame.
    // Sends an output to the clients of this race to start the countdown 
    // sequence to the start of a race.
    public void startRace(GameClient client) {

        for (int i = 0; i < Constants.MAX_NUMBER_OF_PLAYERS; ++i) {
            if (this.players[i].getClient().getID().equals(client.getID())) {
                playersReadyToStart++;
            }
        }

        if (playersReadyToStart == Constants.MAX_NUMBER_OF_PLAYERS) {
            ResponseRRStartGame responseStart = new ResponseRRStartGame();
            sendToAllPlayers(responseStart);
        }
    }
    
    public void sendToAllPlayers(GameResponse resp) {
        for (int i = 0; i < Constants.MAX_NUMBER_OF_PLAYERS; ++i) {
            this.players[i].getClient().add(resp);
        }
    }
}
