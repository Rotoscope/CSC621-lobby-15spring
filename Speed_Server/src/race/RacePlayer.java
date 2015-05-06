package race;

// Other Imports
import core.GameClient;

/**
 * The Player class holds important information about the player including, most
 * importantly, the account. Such information includes the username, password,
 * email, and the player ID.
 */
public class RacePlayer {

    private GameClient client;
    private String playerID; //session
    private int raceID;
    private int status;
    private RacePlayer opponent;

    // For the database
    private int x;
    private int y;
    private int speed;
    private int boosts; // Number of boosts (positive speed items) a user has.
    
    //Key inputs
    private boolean right;
    private boolean left;
    private boolean jump;
    private boolean boost;
    
    public RacePlayer(GameClient client, int raceID){
        this.client = client;
        this.playerID = client.getID();
        this.raceID = raceID;
    }

    public int getRaceID() {
        return raceID;
    }

    public void setRaceID(int raceID){
        this.raceID = raceID;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
    
    public int setX(int x) {
        return this.x = x;
    }

    public int setY(int y) {
        return this.y = y;
    }
    
    /**
     * @return the speed
     */
    public int getSpeed() {
        return speed;
    }

    /**
     * @param speed the speed to set
     */
    public void setSpeed(int speed) {
        this.speed = speed;
    }

    /**
     * @return the right
     */
    public boolean isRight() {
        return right;
    }

    /**
     * @param right the right to set
     */
    public void setRight(boolean right) {
        this.right = right;
    }

    /**
     * @return the left
     */
    public boolean isLeft() {
        return left;
    }

    /**
     * @param left the left to set
     */
    public void setLeft(boolean left) {
        this.left = left;
    }

    /**
     * @return the jump
     */
    public boolean isJump() {
        return jump;
    }

    /**
     * @param jump the jump to set
     */
    public void setJump(boolean jump) {
        this.jump = jump;
    }

    /**
     * @return the boost
     */
    public boolean isBoost() {
        return boost;
    }

    /**
     * @param boost the boost to set
     */
    public void setBoost(boolean boost) {
        this.boost = boost;
    }

    /**
     * @return the boosts
     */
    public int getBoosts() {
        return boosts;
    }

    /**
     * @param boosts the boosts to set
     */
    public void setBoosts(int boosts) {
        this.boosts = boosts;
    }

    /**
     * @return the opponent
     */
    public RacePlayer getOpponent() {
        return opponent;
    }

    /**
     * @param opponent the opponent to set
     */
    public void setOpponent(RacePlayer opponent) {
        this.opponent = opponent;
    }    

    public GameClient getClient() {
        return client;
    }
    
    public String getID() {
        return playerID;
    }
}
