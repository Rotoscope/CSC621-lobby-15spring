package networking.request;

// Java Imports
import java.io.IOException;

import utility.DataReader;

// Other Imports
import dataAccessLayer.ShopDAO;
import networking.response.ResponseShop;

public class RequestShop extends GameRequest {
	short type;

    public RequestShop() {
    }

    @Override
    public void parse() throws IOException {
    	type = DataReader.readShort(dataInput);
    }

    @Override
    public void doBusiness() throws Exception {
        ResponseShop responseShop = new ResponseShop();
        responseShop.setShopList(ShopDAO.getItems("level:0,99"));
        responses.add(responseShop);
    }
}
