//author: Ye

using UnityEngine;

using System;

public class RequestBattleInit : NetworkRequest {
	
	public RequestBattleInit() {
		packet = new GamePacket(request_id = Constants.CMSG_BATTLE_REQ);
	}
	
	public void Send(int tile_id, int target_tile_id) {
		packet.addInt32(Constants.USER_ID);
		packet.addInt32 (tile_id);
		packet.addInt32 (target_tile_id);
		packet.addInt32(3);
	}
}