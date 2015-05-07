using UnityEngine;

using System;
using System.Collections.Generic;
using System.Reflection;

namespace RR
{
	public class NetworkResponseTable
	{
		public static Dictionary<short, Type> responseTable { get; set; }
	
		public static void init ()
		{
			responseTable = new Dictionary<short, Type> ();
		
			add (Constants.SMSG_AUTH, "RR.ResponseLogin");
			add (Constants.SMSG_HEARTBEAT, "RR.ResponseHeartbeat");
			add (Constants.SMSG_GAME_STATE, "RR.ResponseGameState");
			add (Constants.SMSG_RACE_INIT, "RR.ResponseRaceInit");
			add (Constants.SMSG_KEYBOARD, "RR.ResponseKeyboard");

			add (Constants.SMSG_RRPOSITION, "RR.ResponseRRPostion");
			add (Constants.SMSG_RRSPECIES, "RR.ResponseRRSpecies");
			add (Constants.SMSG_RRSTARTGAME, "RR.ResponseRRStartGame");
		}
	
		public static void add (short response_id, string name)
		{
			responseTable.Add (response_id, Type.GetType (name));
		}
	
		public static NetworkResponse get (short response_id)
		{
			NetworkResponse response = null;
		
			if (responseTable.ContainsKey (response_id)) {
				response = (NetworkResponse)Activator.CreateInstance (responseTable [response_id]);
				response.response_id = response_id;
			} else {
				Debug.Log ("Response [" + response_id + "] Not Found");
				Debug.Log (response.ToString ());
			}
		
			return response;
		}
	}
}