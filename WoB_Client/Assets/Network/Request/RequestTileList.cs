using UnityEngine;

using System;

public class RequestTileList : NetworkRequest {
	
	public RequestTileList() {
		packet = new GamePacket (request_id = Constants.CMSG_TILE_LIST);
	}
	
	public void Send() {
		//packet.addInt32 (user_id);
	}
}
