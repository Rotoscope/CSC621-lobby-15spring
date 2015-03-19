/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package networking.request;

import dataAccessLayer.TileDAO;
import java.io.IOException;
import java.util.List;
import model.Tile;
import networking.response.ResponseTileList;
import utility.DataReader;

/**
 *
 * @author Ari
 */
public class RequestTileList extends GameRequest {
    
    private ResponseTileList responseTileList1, responseTileList2;
    private int user_id;
    
    
    public RequestTileList () {
        responses.add(responseTileList1 = new ResponseTileList());
        responses.add(responseTileList2 = new ResponseTileList());
    }

    @Override
    public void parse() throws IOException {
        //user_id = DataReader.readInt(dataInput);
    }

    @Override
    public void doBusiness() throws Exception {
        List<Tile> tileList = TileDAO.getTileList();
        if (tileList.size() > 800) {
        	responseTileList1.setStart(0);
        	responseTileList1.setStatus((short) 0);
        	responseTileList1.setEnd(tileList.size() / 2);
        	responseTileList1.setTileList(tileList);
        	responseTileList2.setStart(tileList.size() / 2);
        	responseTileList2.setStatus((short) 0);
        	responseTileList2.setTileList(tileList);
        	responseTileList2.setEnd(tileList.size());
        	
        } else {
            responseTileList1.setTileList(tileList);
            responseTileList1.setStatus((short) 0);
        }
    }
    
}
