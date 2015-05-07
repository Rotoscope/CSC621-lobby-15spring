using UnityEngine;

using System;
using System.Collections.Generic;
using System.Reflection;

namespace RR
{
	public class NetworkRequestTable
	{

		public static Dictionary<short, Type> requestTable { get; set; }
	
		public static void init ()
		{
			requestTable = new Dictionary<short, Type> ();
	
			add (Constants.CMSG_AUTH, "RR.RequestLogin");
			add (Constants.CMSG_HEARTBEAT, "RR.RequestHeartbeat");
			add (Constants.CMSG_IN_GAME_HEARTBEAT, "RR.InGameHeartBeat");
			add (Constants.CMSG_LOOKING_FOR_OPPONENT, "RR.LookingForOpponent");
			add (Constants.CMSG_GAMEOVER, "RR.GameOver");
			add (Constants.CMSG_GAME_STATE, "RR.RequestGameState");
			add (Constants.CMSG_RACE_INIT, "RR.RequestRaceInit");
			add (Constants.CMSG_RRSTARTGAME, "RR.RequestRRStartGame");
		}
	
		public static void add (short request_id, string name)
		{
			requestTable.Add (request_id, Type.GetType (name));
		}
	
		public static NetworkRequest get (short request_id)
		{
			NetworkRequest request = null;
		
			if (requestTable.ContainsKey (request_id)) {
				request = (NetworkRequest)Activator.CreateInstance (requestTable [request_id]);
				request.request_id = request_id;
			} else {
				Debug.Log ("Request [" + request_id + "] Not Found");
			}
		
			return request;
		}
	}
}