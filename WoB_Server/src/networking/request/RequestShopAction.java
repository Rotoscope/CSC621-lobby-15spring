package networking.request;

// Java Imports
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import core.WorldManager;

import dataAccessLayer.EnvironmentDAO;
import dataAccessLayer.WorldDAO;
import dataAccessLayer.ZoneDAO;

import model.Environment;
import model.Player;
// Other Imports
import model.World;
import networking.response.ResponseShopAction;
import utility.DataReader;
import worldManager.gameEngine.Zone;

public class RequestShopAction extends GameRequest {

    private short action;
    private Map<Integer, Integer> itemList;

    public RequestShopAction() {
        itemList = new HashMap<Integer, Integer>();
    }

    @Override
    public void parse() throws IOException {
        action = DataReader.readShort(dataInput);

        int size = DataReader.readShort(dataInput);

        for (int i = 0; i < size; i++) {
            int item_id = DataReader.readInt(dataInput);
            int amount = DataReader.readInt(dataInput);

            itemList.put(item_id, amount);
        }
    }

    @Override
    public void doBusiness() throws Exception {
        int totalSpent = 0;

        World world = client.getActiveObject(World.class);
        
        //lobby team
        //for first time purchase
        //initialize ecosystem files 
        Environment env = EnvironmentDAO.getEnvironmentByWorldID(world.getID());
        Zone zone = ZoneDAO.getZoneByEnvironmentId(env.getID()).get(0);
        if (zone.getManipulationID().length()==0){
        	WorldManager.createEcosystem(world, itemList);
        	//update credits
            if (world != null) {
                ResponseShopAction responseShopAction = new ResponseShopAction();
                totalSpent = world.createShopOrder2(itemList, client.getPlayer());

                if (totalSpent > -1) {
                    responseShopAction.setStatus((short) 0);
                    responseShopAction.setTotalSpent(totalSpent);
                } else {
                    responseShopAction.setStatus((short) 1);
                }

                responses.add(responseShopAction);
            }
        }
        //other purchase
        else {     
        	if (world != null) {
	            ResponseShopAction responseShopAction = new ResponseShopAction();
	            totalSpent = world.createShopOrder(itemList, client.getPlayer());
	
	            if (totalSpent > -1) {
	                responseShopAction.setStatus((short) 0);
	                responseShopAction.setTotalSpent(totalSpent);
	            } else {
	                responseShopAction.setStatus((short) 1);
	            }
	
	            responses.add(responseShopAction);
	        }
        }
    }
}
