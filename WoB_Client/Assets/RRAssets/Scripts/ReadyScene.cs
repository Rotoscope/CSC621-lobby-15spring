using UnityEngine;
using System.Collections;

namespace RR
{
	public class ReadyScene : MonoBehaviour
	{
		static public int ROOM_ID = 0;
		private GameObject mainObject;
		
		void Awake() {
			mainObject = GameObject.Find("MainObject");
			mainObject.GetComponent<MessageQueue>().AddCallback(Constants.SMSG_RACE_INIT, ResponseRaceInit);
		}

		void Start() {
			Join ();
		}
				
		// Join makes an attempt to create and or join an online game.
		public void Join() {
			Debug.Log ("Joinning room");
			ConnectionManager cManager = mainObject.GetComponent<ConnectionManager>();
			cManager.Send(RequestRaceInit());
		}
		
		public RequestRaceInit RequestRaceInit() {
			RequestRaceInit request = new RequestRaceInit();
			request.Send(Constants.USER_ID, ROOM_ID);
			return request;
		}
		
		public void ResponseRaceInit(ExtendedEventArgs eventArgs) {
			ResponseRaceInitEventArgs args = eventArgs as ResponseRaceInitEventArgs;
			if (args.status == 0) {
				Debug.Log ("Joined!");
			}
		}
	}
}