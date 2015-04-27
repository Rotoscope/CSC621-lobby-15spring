/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lobby;

import java.io.File;
import java.io.IOException;
import util.Log;

/**
 *
 * @author yanxing wang
 */
public class MiniGame {
    
    private String name = "";
    private boolean singlePlayer = true;
    private boolean available = true;
    private String serverPath = ""; // path to the jar file

    public MiniGame(String name) {
        this.name = name;
    }
    
    public void setAsMultiPlayerGame(String path) {
        this.setSinglePlayer(false);
        this.serverPath = path;
        validate();
    }
    
    public void setAsSinglePlayerGame() {
        this.setSinglePlayer(true);
    }
    
    public void validate() {
        File f = new File(this.serverPath);
        this.available = f.exists() && !f.isDirectory();
    }

    /**
     * @return the singlePlayer
     */
    public boolean isSinglePlayer() {
        return singlePlayer;
    }

    /**
     * @param singlePlayer the singlePlayer to set
     */
    private void setSinglePlayer(boolean singlePlayer) {
        this.singlePlayer = singlePlayer;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @return the available
     */
    public boolean isAvailable() {
        return available;
    }

    void run() throws IOException {
        Log.println("Running server: " + this.name);
        Process ps = Runtime.getRuntime().exec(new String[]{"java", "-jar", this.serverPath});
        /*
        ps.waitFor();
        java.io.InputStream is = ps.getInputStream();
        byte b[] = new byte[is.available()];
        is.read(b,0,b.length);
        System.out.println(new String(b));
        */
    }
}
