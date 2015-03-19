using UnityEngine;

using System;

public class RequestTileUpdate : NetworkRequest {
	
	public RequestTileUpdate() {
		packet = new GamePacket (request_id = Constants.CMSG_TILE_UPDATE);
	}

	public void Send(int tile_id, int owner_id, int vegetation_capacity, int zone_id, int natural_event) {
		packet.addInt32 (tile_id);
		//packet.addInt32 (Constants.USER_ID);
		packet.addInt32	(owner_id);
		packet.addInt32	(vegetation_capacity);
		packet.addInt32	(zone_id);
		packet.addInt32	(natural_event);
	}
}
