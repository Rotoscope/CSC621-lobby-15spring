/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package race;

import core.GameClient;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author markfavis
 */
public class RaceManager {

    // Singleton Instance
    public static RaceManager manager;

    // Regerence Tables
    
    // RaceID -> race
    private final Map<Integer, Race> raceList = new HashMap<Integer, Race>(); 
    private final Map<String, Race> raceClientMap = new HashMap<String, Race>(); 

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
            raceList.put(race_id, race);
        }
        return race;
    }    
    
    public Race getRaceByClient(GameClient client) {
        return raceClientMap.get(client.getID());
    }

    public void addPlayer(Race race, GameClient client) {
        race.addPlayer(new RacePlayer(client , race.getID()));
        raceClientMap.put(client.getID(), race);
    }
}
