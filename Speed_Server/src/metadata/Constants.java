package metadata;

/**
 * The Constants class stores important variables as constants for later use.
 */
public class Constants {
    
    public final static int TICK_RATE = 30;
    public final static int TICK_NANOSECOND = 1000000000 / TICK_RATE;
    public final static String CLIENT_VERSION = "1.00";
    public final static int TIMEOUT_MILLISECONDS = 900000000;
    public final static int MAX_CLIENT_THREADS = 10;
    public final static short MAX_NUMBER_OF_PLAYERS = 2;
    
    public final static int SMSG_AUTH_BIAS = 100;
}
