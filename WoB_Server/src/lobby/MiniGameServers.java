/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lobby;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import util.Log;

/**
 *
 * @author yanxingwang
 */
public class MiniGameServers {
    
    private static MiniGameServers sServers;
    private final Map<String, MiniGame> miniGames = new HashMap<String, MiniGame>();
    
    public static MiniGameServers getInstance() {
        if (sServers == null) {
            sServers = new MiniGameServers();
        }
        return sServers;
    }
    
    public MiniGameServers() {
        initMiniGames();
        
        int totalAvailables = 0;
        for (MiniGame mg : miniGames.values()) {
            if ( mg.isAvailable() ) {
                ++totalAvailables;
            }
        }
        Log.printf("All mini games initialized: %d/%d avaiable", totalAvailables, miniGames.size());
    }
    
    public void runServers() {
        try {
            for (MiniGame g : this.miniGames.values()) {
                g.run();
            }
        } catch (IOException ex) {
            Logger.getLogger(MiniGameServers.class.getName()).log(Level.SEVERE, null, ex);
        }            
    }
    
    private void initMiniGames() {
        MiniGame game;

        // test
        game = new MiniGame("Cards of Wild");
        game.setAsMultiPlayerGame("../Mini_Server/dist/Mini_Server.jar", 9245);
        miniGames.put(game.getName(), game);
    }
}
