package metadata;

// Java Imports
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

// Other Imports
import util.Log;

public class NetworkCode {

    // Request + Response
    public final static short CLIENT = 100;
    public final static short HEARTBEAT = 102;
    public final static short MESSAGE = 107;
    
    public final static short CHANGE_NAME = 153;
    public final static short REQUEST_START = 154;
        
    public final static short KEYBOARD = 108;
    public final static short RACE_INIT = 109;   
    public final static short RRPOSITION = 110;
    public final static short RRSPECIES = 111;
    public final static short RRENDGAME = 112;
    public final static short RRSTARTGAME = 113;
    public final static short RRBOOST = 114;

    /**
     * Check for duplicate values, if any.
     */
    public static void check() {
        NetworkCode nCodes = new NetworkCode();
        Map<Short, String> nCodeMap = new HashMap<Short, String>();

        for (Field field : NetworkCode.class.getDeclaredFields()) {
            try {
                Short value = (Short) field.get(nCodes);

                if (nCodeMap.containsKey(value)) {
                    Log.println_e(field.getName() + " is conflicting with " + nCodeMap.get(value));
                } else {
                    nCodeMap.put(value, field.getName());
                }
            } catch (IllegalArgumentException ex) {
                Log.println_e(ex.getMessage());
            } catch (IllegalAccessException ex) {
                Log.println_e(ex.getMessage());
            }
        }
    }

    @Override
    public String toString() {
        String str = "-----\n";
        str += getClass().getName() + "\n\n";
        for (Field field : getClass().getDeclaredFields()) {
            try {
                str += field.getName() + " - " + field.get(this) + "\n";
            } catch (IllegalArgumentException ex) {
                Log.println_e(ex.getMessage());
            } catch (IllegalAccessException ex) {
                Log.println_e(ex.getMessage());
            }
        }
        return str + "-----";
    }
}
