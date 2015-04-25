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

    /**
     * Check for duplicate values, if any.
     */
    public static void check() {
        NetworkCode nCodes = new NetworkCode();
        Map<Short, String> nCodeMap = new HashMap<>();

        for (Field field : NetworkCode.class.getDeclaredFields()) {
            try {
                Short value = (Short) field.get(nCodes);

                if (nCodeMap.containsKey(value)) {
                    Log.println_e(field.getName() + " is conflicting with " + nCodeMap.get(value));
                } else {
                    nCodeMap.put(value, field.getName());
                }
            } catch (IllegalArgumentException | IllegalAccessException ex) {
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
            } catch (IllegalArgumentException | IllegalAccessException ex) {
                Log.println_e(ex.getMessage());
            }
        }
        return str + "-----";
    }
}
