package metadata;

// Java Imports
import java.util.HashMap;
import java.util.Map;

// Other Imports
import net.request.GameRequest;
import util.Log;

/**
 * The GameRequestTable class stores a mapping of unique request code numbers
 * with its corresponding request class.
 */
public class GameRequestTable {

    private static final Map<Short, Class> requestTable = new HashMap<>(); // Request Code -> Class

    /**
     * Initialize the hash map by populating it with request codes and classes.
     */
    public static void init() {
        Log.console("Loading Requests...");

        NetworkCode.check();
        
        // Populate the table using request codes and class names
        add(NetworkCode.CLIENT, "RequestClient");
        add(NetworkCode.HEARTBEAT, "RequestHeartbeat");
        add(NetworkCode.MESSAGE, "RequestMessage");
        add(NetworkCode.CHANGE_NAME, "RequestChangeName");
        add(NetworkCode.REQUEST_START, "RequestStart");

        Log.println("Done!");
    }

    /**
     * Map the request code number with its corresponding request class, derived
     * from its class name using reflection, by inserting the pair into the
     * table.
     *
     * @param request_id a value that uniquely identifies the request type
     * @param name a string value that holds the name of the request class
     */
    public static void add(short request_id, String name) {
        try {
            if (!requestTable.containsKey(request_id)) {
                requestTable.put(request_id, Class.forName("net.request." + name));
            } else {
                Log.printf_e("Request ID [%d] already exists! Ignored '%s'\n", request_id, name);
            }
        } catch (ClassNotFoundException ex) {
            Log.printf_e("%s not found", ex.getMessage());
        }
    }

    /**
     * Get the instance of the request class by the given request code.
     *
     * @param request_id a value that uniquely identifies the request type
     * @return the instance of the request class
     */
    public static GameRequest get(short request_id) {
        GameRequest request = null;
        try {
            Class name = requestTable.get(request_id);

            if (name != null) {
                request = (GameRequest) name.getDeclaredConstructor().newInstance();
            } else {
                Log.printf_e("Request ID [%d] does not exist!\n", request_id);
            }
        } catch (Exception ex) {
            Log.println_e(ex.getMessage());
        }

        return request;
    }
}
