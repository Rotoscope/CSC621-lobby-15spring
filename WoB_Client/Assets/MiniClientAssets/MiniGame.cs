using UnityEngine;
using UnityEngine.UI;

using System.Collections;
using System.Collections.Generic;

namespace MiniClient
{
	public class MiniGame : MonoBehaviour
	{

		public Button SubmitBtn = null;
		public Button ChangeNameBtn = null;
		public Button QuitBtn = null;
		public InputField ChatInputText = null;
		public InputField NameInputText = null;
		public Text ChatText = null;
		private string message = "";
		public static int reservedRoomID = 0;

		// Use this for initialization
		void Start ()
		{
			SubmitBtn.onClick.AddListener (() => {
				OnSubmit (); });
			ChangeNameBtn.onClick.AddListener (() => {
				OnChangeName (); });
			QuitBtn.onClick.AddListener (() => {
				OnQuit (); });

			NameInputText.text = "Your Name";
		}

		void Awake ()
		{
			NetworkManager.Listen (NetworkCode.REQUEST_START, ProcessStartReply);
			NetworkManager.Listen (NetworkCode.MESSAGE, ProcessMessage);

			NetworkManager.Send (RequestStartProtocol.Prepare (reservedRoomID));
		}

		void OnDestroy ()
		{
			NetworkManager.Ignore (NetworkCode.MESSAGE, ProcessMessage);
			NetworkManager.Ignore (NetworkCode.REQUEST_START, ProcessStartReply);
		}

		public void ProcessStartReply (NetworkResponse response)
		{
			RequestStartResponse args = response as RequestStartResponse;
			if (args.status == 0) {
				PutSystemMessage ("Joined a game room, id = " + reservedRoomID);
				PutSystemMessage ("Let's chat!");
			} else {
				PutSystemMessage ("Failed to join a game room!");
			}
		}

		public void ProcessMessage (NetworkResponse response)
		{
			ResponseMessage args = response as ResponseMessage;
		
			if (args.status == 0) {
				string message = "";
			
				if (args.type == 0) {
					message += "<b>" + args.username + "</b>: ";
				}
			
				message += args.message;
				PutMessange (message);
			}
		}

		public void SendMessage ()
		{
			if (message != "") {
				NetworkManager.Send (
				MessageProtocol.Prepare (0, message)
				);
				message = "";
			}
		}

		void Update ()
		{
		}

		void PutSystemMessage (string message)
		{
			ChatText.text += "<color=navy>" + message + "</color>\n";
		}

		void PutMessange (string message)
		{
			ChatText.text += message + "\n";

			// Clear input field
			ChatInputText.text = "";
			ChatInputText.Select ();
			ChatInputText.ActivateInputField ();
		}

		void OnSubmit ()
		{
			if (ChatInputText.text != "") {
				NetworkManager.Send (
				MessageProtocol.Prepare (0, ChatInputText.text)
				);
				message = "";
			}
		}

		void OnChangeName ()
		{
			var name = NameInputText.text;
			NetworkManager.Send (
			ChangeNameProtocol.Prepare (name)
			);
		}

		void OnQuit ()
		{
			Game.SwitchScene ("World");
		}

		void OnGUI ()
		{
			//if(ChatInputText.isFocused && ChatInputText.text != "" && Input.GetKey(KeyCode.Return)) {
			//	OnSubmit();
			//}
		}
	}
}