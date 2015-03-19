//author: Lobby Team

using UnityEngine;

using System;

public class ResponseWorldEventArgs : ExtendedEventArgs {
	public short status { get; set; }
	public int world_id { get; set; }
	public string name { get; set; }
	public short game_mode { get; set; }
	public int credits { get; set; }
	public string env_type { get; set; }
	//public short max_players { get; set; }
	public float time_rate { get; set; }
	public short month { get; set; }
	
	public ResponseWorldEventArgs() {
		event_id = Constants.SMSG_WORLD;
	}
}

public class ResponseWorld : NetworkResponse {

	private short status;
	private int world_id;
	private string name;
	private short game_mode;
	private int credits;
	private string env_type;
	//private short max_players;
	private float time_rate;
	private short month;
	
	public ResponseWorld() {
	}
	
	public override void parse() {
			status = DataReader.ReadShort(dataStream);
			
			if (status == 0) {
				world_id = DataReader.ReadInt(dataStream);
				name = DataReader.ReadString(dataStream);
				game_mode = DataReader.ReadShort(dataStream);
				credits = DataReader.ReadInt(dataStream);
				env_type = DataReader.ReadString(dataStream);
				//max_players = DataReader.ReadShort(dataStream);
				time_rate = DataReader.ReadFloat(dataStream);
				month = DataReader.ReadShort(dataStream);
			}
	}
	
	public override ExtendedEventArgs process() {
		ResponseWorldEventArgs args = null;
		
		if (status == 0) {
			args = new ResponseWorldEventArgs();
			//args.action = action;
			args.status = status;
			args.world_id = world_id;
			args.name = name;
			args.game_mode = game_mode;
			args.credits = credits;
			args.env_type = env_type;
			//args.max_players = max_players;
			args.time_rate = time_rate;
			args.month = month;
		}
		
		return args;
	}
}


