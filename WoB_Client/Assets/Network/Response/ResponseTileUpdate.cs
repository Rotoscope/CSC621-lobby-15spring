using UnityEngine;

using System;

public class ResponseTileUpdateEventArgs : ExtendedEventArgs {
	public short status { get; set; }
	public string status_msg { get; set; }
	public int tile_id {get; set; }
	public int owner_id {get; set; }
	
	public ResponseTileUpdateEventArgs() {
		event_id = Constants.SMSG_TILE_UPDATE;
	}
}

public class ResponseTileUpdate : NetworkResponse {
	
	private short status;
	private string status_msg;
	private int tile_id;
	private int owner_id;
	
	public ResponseTileUpdate() {
	}
	
	public override void parse() {
		status = DataReader.ReadShort(dataStream);
		if (status == 0) {
			tile_id = DataReader.ReadInt(dataStream);
			owner_id = DataReader.ReadInt(dataStream);
			status_msg = DataReader.ReadString(dataStream);
		} else {
			status_msg = DataReader.ReadString(dataStream);
		}
	}
	
	public override ExtendedEventArgs process() {
		ResponseTileUpdateEventArgs args = null;
		
		args = new ResponseTileUpdateEventArgs();
		args.status = status;
		args.tile_id = tile_id;
		args.owner_id = owner_id;
		args.status_msg = status_msg;
		
		return args;
	}
}