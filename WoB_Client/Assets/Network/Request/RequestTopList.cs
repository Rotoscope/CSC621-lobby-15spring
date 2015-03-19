/**
 * From: Lobby team
 * Author: Jacob
 * Description of Class: request structure for receiving toplist from the server
***/

using UnityEngine;

using System;

public class RequestTopList : NetworkRequest {
	
	public RequestTopList() {
		packet = new GamePacket (request_id = Constants.CMSG_TOPLIST);
	}
	
	public void Send() {

	}
}