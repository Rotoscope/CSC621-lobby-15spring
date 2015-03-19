//author: Ye
using UnityEngine;

using System;

public class ResponseBattleInitEventArgs : ExtendedEventArgs {
	public short status { get; set; }
	
	public ResponseBattleInitEventArgs() {
		event_id = Constants.SMSG_BATTLE_REQ;
	}
}

public class ResponseBattleInit : NetworkResponse {
	
	private short status;//start a battle: 0; wait for a battle: 1
	
	public ResponseBattleInit() {
	}
	
	public override void parse() {
		status = DataReader.ReadShort(dataStream);
		Battle.stopSendRequest();
		if (status == 0) {
			Debug.Log("battle stated");
			//change to battle scene
			//when the battle is ended, change stopSendRequest to true;
		}
		else if (status ==1) {
			Debug.Log("request received by server, wait for a battle");
		}
	}
	
	public override ExtendedEventArgs process() {
		ResponseBattleInitEventArgs args = null;
		
		args = new ResponseBattleInitEventArgs();
		args.status = status;
		
		if (status == 0) {
			//battle start, stop sending battle request
				} else if (status == 1) {
			//battle not start, continue sending battle request
				
				}
		return args;
	}
}