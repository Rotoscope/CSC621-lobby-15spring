/**
 * From: Lobby team
 * Author: Jacob
 * Description of Class: request structure for receiving toplist from the server
***/

package networking.request;

import java.io.IOException;
import networking.response.ResponseTopList;

public class RequestTopList extends GameRequest {

    private ResponseTopList responseTopList;
    
    public RequestTopList() {
        responses.add(responseTopList = new ResponseTopList());
    }

    @Override
    public void parse() throws IOException {
        
    }

    @Override
    public void doBusiness() throws Exception {
        //TODO server team: calculate toplist / send data to responseTopList if needed
        //  might need to add functions to ResponseTopList to recieve data from here?
    }
}