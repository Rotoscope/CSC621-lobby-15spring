using UnityEngine;

using System;

public class ResponseTileEventArgs : ExtendedEventArgs {
	public short status { get; set; }
	public string status_msg { get; set; }
	
	public ResponseTileEventArgs() {
		event_id = Constants.SMSG_TILE;
	}
}

public class ResponseTile : NetworkResponse {
	
	private short status;
	private string status_msg;
	
	public ResponseTile() {
	}
	
	public override void parse() {
		status = DataReader.ReadShort(dataStream);
		status_msg = DataReader.ReadString (dataStream);
	}
	
	public override ExtendedEventArgs process() {
		ResponseTileEventArgs args = null;
		
		args = new ResponseTileEventArgs();
		args.status = status;
		args.status_msg = status_msg;
		
		return args;
	}
}