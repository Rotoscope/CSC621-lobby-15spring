package core.match;

import core.GameClient;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import util.Log;
import core.GameServer;
import model.Player;

/*
 * @author Nathanael Aff
 */
public class MatchManager {

    // Singleton Instance
    public static MatchManager manager;
    int matchID = 100;
    // Active battles: <matchID, Match> 
    private final Map<Integer, Match> matchList = new HashMap<Integer, Match>();
    // <playerID, matchID>
    private final Map<Integer, Integer> matchIDList = new HashMap<Integer, Integer>();
    
    private final Map<Integer, String> idSessionList = new HashMap<Integer, String>();

    public MatchManager() {
    }

    public static MatchManager getInstance() {
        if (manager == null) {
            manager = new MatchManager();
        }
        return manager;
    }
    
    public static GameClient toClient(int playerid) {
        String session = MatchManager.getInstance().idSessionList.get(playerid);
        return GameServer.getInstance().getActiveClient(session);
    }
    
    public static String toSession(int playerid) {
        String session = MatchManager.getInstance().idSessionList.get(playerid);
        return session;
    }

    // This constructor adds sessionID, otherwise its the same
    public Match createMatch(int playerID1, int playerID2, String sessionID) {
        List<GameClient> players = new ArrayList<GameClient>();
        Match match = null;

        match = getMatchByPlayer(playerID1);
        // if match not initialized, initialize match
        if (match == null) {
            Log.printf("Manager creating new match");
            matchID = makeMatchID();
            System.out.println("Creating match" + matchID);
            // get players
            players.add(GameServer.getInstance().getActiveClient(this.idSessionList.get(playerID1)));
            players.add(GameServer.getInstance().getActiveClient(this.idSessionList.get(playerID2)));
            match = new Match(players, matchID, sessionID);
            //TODO: update when Lobby has design for passing data to games
            matchList.put(matchID, match);
            matchIDList.put(playerID1, matchID);
            matchIDList.put(playerID2, matchID);
        }
        return match;
    }
    
    public Match getOrCreateMatch(int matchID) {
        Match match = matchList.getOrDefault(matchID, null);
        
        if (match == null) {
            Log.printf("Manager creating new match");
            System.out.println("Creating match: " + matchID);
            
            match = new Match(null, matchID);
            matchList.put(matchID, match);
        }
        
        return match;
    }
    
    public Match matchPlayerTo(int matchID, int playerID, GameClient client) {
        Log.printf("Matching player[%d] to match[%d]", playerID, matchID);
        this.idSessionList.put(playerID, client.getID());
        
        Match match = getOrCreateMatch(matchID);
        if (match.addPlayer(client)) {
            matchIDList.put(playerID, matchID);
            Log.println("Player added");
        } else {
            Log.println("Player can't be added");
        }
        return match;
    }

    public Match createMatch(int playerID1, int playerID2) {
        List<GameClient> players = new ArrayList<GameClient>();
        Match match;

        match = getMatchByPlayer(playerID1);
        // if match not initialized, initialize match
        if (match == null) {
            Log.printf("Manager creating new match");
            matchID = makeMatchID();
            System.out.println("Creating match" + matchID);
            // get players
            players.add(GameServer.getInstance().getActiveClient(this.idSessionList.get(playerID1)));
            players.add(GameServer.getInstance().getActiveClient(this.idSessionList.get(playerID2)));
            match = new Match(players, matchID);
            //TODO: update when Lobby has design for passing data to games
            matchList.put(matchID, match);
            matchIDList.put(playerID1, matchID);
            matchIDList.put(playerID2, matchID);
        }
        return match;
    }

    int makeMatchID() {
        return ++matchID;
    }

    public Match getMatch(Integer matchID) {
        return matchList.get(matchID);
    }

    public Match getMatchByPlayer(Integer playerID) {
        return matchList.get(matchIDList.get(playerID));
    }

    public Integer getMatchIDByPlayer(Integer playerID) {
        return matchIDList.get(playerID);
    }

}
