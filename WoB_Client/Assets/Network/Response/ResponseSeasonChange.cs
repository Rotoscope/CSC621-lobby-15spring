//author: Lobby Team

using UnityEngine;

using System;

public class ResponseSeasonChangeEventArgs : ExtendedEventArgs {
	public short status { get; set; }
	public int env_id { get; set; }
	
	public ResponseSeasonChangeEventArgs() {
		event_id = Constants.SMSG_SEASON_CHANGE;
	}
}

public class ResponseSeasonChange: NetworkResponse {
	
	private int event_id;
	private short status;
	
	public ResponseSeasonChange() {
	}
	
	public override void parse() {
		status = DataReader.ReadShort(dataStream);
		event_id = DataReader.ReadInt(dataStream);
	}
	
	public override ExtendedEventArgs process() {
		ResponseSeasonChangeEventArgs args = new ResponseSeasonChangeEventArgs();
		args.env_id = event_id;
		args.status = status;

		return args;
	}
}

