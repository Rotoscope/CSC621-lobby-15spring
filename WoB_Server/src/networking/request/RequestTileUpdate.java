package networking.request;


import core.TileManager;
import dataAccessLayer.TileDAO;
import java.io.IOException;
import model.Tile;
import networking.response.ResponseTileUpdate;
import utility.DataReader;
import utility.Log;

public class RequestTileUpdate extends GameRequest {
	private int tile_id, owner_id, vegetation_capacity, zone_id, natural_event;
	private ResponseTileUpdate responseTileUpdate;
        private Tile tile;

	public RequestTileUpdate() {
		responses.add(responseTileUpdate = new ResponseTileUpdate());
	}
	
	@Override
	public void parse() throws IOException {
		tile_id = DataReader.readInt(dataInput);
		owner_id = DataReader.readInt(dataInput);
		vegetation_capacity = DataReader.readInt(dataInput);
		zone_id = DataReader.readInt(dataInput);
		natural_event = DataReader.readInt(dataInput);
	}

	@Override
	public void doBusiness() throws Exception {
		try {
			Log.printf("Updating tile %d", tile_id);
			TileDAO.updateTileOwner(owner_id, tile_id);
			TileDAO.updateVegetationCapacity(vegetation_capacity, tile_id);
			TileDAO.updateZone(tile_id, zone_id);
			TileDAO.updateNaturalEvent(natural_event, tile_id);
			responseTileUpdate.setStatus((short) 0);
                        tile = TileDAO.getTile(tile_id);
                        //responseTileUpdate.setTile(tile);
			Log.printf("Updated tile %d successfully %d", tile_id);
		} catch (Exception e) {
			responseTileUpdate.setStatus((short) 1);
			Log.printf("tile %d update failed", tile_id);
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
