/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package race;

import core.GameClient;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author markfavis
 */
public class RaceManager {

    // Singleton Instance
    public static RaceManager manager;

    // Regerence Tables
    private Map<Integer, Race> raceList = new HashMap<Integer, Race>(); //RaceID -> race
    private Map<Integer, Race> playerRaceList = new HashMap<Integer, Race>(); //PlayerID -> race
    public Map<Integer, Integer> readyToRace = new HashMap<Integer, Integer>(); //GameID -> number of request

    private List<GameClient> players = new ArrayList<GameClient>(); //used to create a race

    protected short numberOfGamesBeingPlayed;

    public static RaceManager getInstance() {
        if (manager == null) {
            manager = new RaceManager();
        }

        return manager;
    }

    public Race getRace(int race_id) {
        Race race;
        if (raceList.containsKey(race_id)) {
            race = raceList.get(race_id);
        } else {
            race = new Race(race_id);
        }
        return race;
    }
    
    public Race add(Race race) {
        return raceList.put(race.getID(), race);
    }

    public Race getRaceByPlayerID(int playerID) {
        return playerRaceList.get(playerID);
    }
    
    public Race getRaceByClient(GameClient client) {
        return null;
    }
}
