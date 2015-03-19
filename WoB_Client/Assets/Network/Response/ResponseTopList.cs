/**
 * From: Lobby team
 * Author: Jacob
 * Description of Class: response structure for receiving toplist from the server
***/


using UnityEngine;

using System;
using System.Collections.Generic;

public class ResponseTopListEventArgs : ExtendedEventArgs {
	public string name1 { get; set; }
	public int score1 { get; set; }
	public string name2 { get; set; }
	public int score2 { get; set; }
	public string name3 { get; set; }
	public int score3 { get; set; }

	public ResponseTopListEventArgs() {
		event_id = Constants.SMSG_TOPLIST;
	}
}

public class ResponseTopList : NetworkResponse {
	
	public string name1;
	public int score1;
	public string name2;
	public int score2;
	public string name3;
	public int score3;
	
	public ResponseTopList() {
	}
	
	public override void parse() {
		name1 = DataReader.ReadString(dataStream);
		score1 = DataReader.ReadInt(dataStream);
		name2 = DataReader.ReadString(dataStream);
		score2 = DataReader.ReadInt(dataStream);
		name3 = DataReader.ReadString(dataStream);
		score3 = DataReader.ReadInt(dataStream);
	}
	
	public override ExtendedEventArgs process() {
		ResponseTopListEventArgs args = new ResponseTopListEventArgs();
		
		args.name1 = name1;
		args.score1 = score1;
		args.name2 = name2;
		args.score2 = score2;
		args.name3 = name3;
		args.score3 = score3;
		
		return args;
	}
}