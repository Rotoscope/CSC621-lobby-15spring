using UnityEngine;
using System.Collections;

namespace RR
{
	public class Running : MonoBehaviour
	{

		public GameObject mainObject;
		public GameObject player1;
		public GameObject player2;
		public float time;
		private bool flag = true;
		private ConnectionManager cManager;
		private MessageQueue messageQueue;
		private short gameState;
	
		// Use this for initialization

		void OnGUI ()
		{
			GUIStyle myStyle = new GUIStyle ();
			myStyle.normal.textColor = Color.blue;
	
			GUI.Label (new Rect (Screen.width - 150, 0, 200, 100), "Running Time:  " + time, myStyle);
	
		}

		void Start ()
		{
			time = 0.0f;
			mainObject = GameObject.Find ("MainObject");

			cManager = mainObject.GetComponent<ConnectionManager> ();
			gameObject.GetComponent<MessageQueue> ().AddCallback (Constants.SMSG_AUTH, ResponseLogin);

		}

		public void RunOnce ()
		{
			player1 = GameObject.Find ("Player_sprite(Clone)");
			player2 = GameObject.Find ("Player_sprite_2(Clone)");
		}

		public void Player2Move (Vector2 newVect)
		{
			player2.transform.position = newVect;
		}

		private void HeartBeat ()
		{
		}

		private IEnumerator Delay ()
		{
			flag = false;
			yield return new WaitForSeconds (4f);

			if (cManager) {
				RequestRRPostion rp = new RequestRRPostion ();
				rp.send ((player1.transform.position.x).ToString (), (player1.transform.position.y).ToString ());
				cManager.Send (rp);
			}
			flag = true;
		}

		public void ResponseLogin (ExtendedEventArgs eventArgs)
		{
			ResponseLoginEventArgs args = eventArgs as ResponseLoginEventArgs;
		
			if (args.status == 0) {
				Constants.USER_ID = args.user_id;
			} else {
				Debug.Log ("Login Failed");
			}
		}

		public void ResponseGameState (ExtendedEventArgs eventArgs)
		{
			ResponseLoginEventArgs args = eventArgs as ResponseLoginEventArgs;
		
			if (args.status == 0) {
				Constants.USER_ID = args.user_id;
			} else {
				Debug.Log ("Response GameState Failed"); // Don't know what comment should be made here
			}
		}
	
		// Update is called once per frame
		void Update ()
		{
			time += Time.deltaTime;

			if (flag) {
				StartCoroutine (Delay ());
			}

			//Debug.Log("this gets called");
			//Debug.Log("outside!!!!!!!!!");
			if (Input.GetKeyDown (KeyCode.LeftArrow)) {
				if (cManager) {
					RequestKeyboard rk = new RequestKeyboard ();
					rk.send (1, -1);
					cManager.Send (rk);
				}

			}
		
			if (Input.GetKeyDown (KeyCode.RightArrow)) {

				if (cManager) {
					RequestKeyboard rk = new RequestKeyboard ();
					rk.send (1, 1);
					cManager.Send (rk);
				}
			}
		
			if (Input.GetKeyDown (KeyCode.Space)) {


				if (cManager) {
					RequestKeyboard rk = new RequestKeyboard ();
					rk.send (2, 1);
					cManager.Send (rk);
				}
			}

			if (Input.GetKeyUp (KeyCode.LeftArrow) && cManager) {
				RequestKeyboard rk = new RequestKeyboard ();
				rk.send (1, 0);
				cManager.Send (rk);
			}
		
			if (Input.GetKeyUp (KeyCode.RightArrow) && cManager) {

				RequestKeyboard rk = new RequestKeyboard ();
				rk.send (1, 0);
				cManager.Send (rk);
			
			}

			if (Input.GetKeyUp (KeyCode.Space) && cManager) {

				RequestKeyboard rk = new RequestKeyboard ();
				rk.send (2, 0);
				cManager.Send (rk);
			
			}
	
		}

		public void SetGameStateOn ()
		{
			gameState = 1;
		}
	}
}
