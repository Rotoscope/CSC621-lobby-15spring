using UnityEngine;

using System;

public class RequestTile : NetworkRequest {
	public RequestTile() {
		packet = new GamePacket (request_id = Constants.CMSG_TILE);
	}

	public void Send(int tile_id) {
		packet.addInt32 (tile_id);
		packet.addInt32 (Constants.USER_ID);
	}
}

