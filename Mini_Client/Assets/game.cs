using UnityEngine;
using UnityEngine.UI;

using System.Collections;
using System.Collections.Generic;

public class game : MonoBehaviour {

	public Button SubmitBtn = null;
	public Button ChangeNameBtn = null;
	public Button ReadyBtn = null;
	public InputField ChatInputText = null;
	public InputField NameInputText = null;
	public Text ChatText = null;

	private string message = "";

	// Use this for initialization
	void Start () {
		SubmitBtn.onClick.AddListener(() => { OnSubmit(); });
		ChangeNameBtn.onClick.AddListener(() => { OnChangeName(); });
		ReadyBtn.onClick.AddListener(() => { OnReady(); });

		NameInputText.text = "Your Name";

		PutSystemMessage ("Welcome! Click 'Ready' button and chat with a random stranger.");
	}

	void Awake() {
		NetworkManager.Listen(
			NetworkCode.MESSAGE,
			ProcessMessage
		);

		NetworkManager.Listen(
			NetworkCode.PAIR,
			ProcessReadyReply
		);
	}

	void OnDestroy() {
		NetworkManager.Ignore(
			NetworkCode.MESSAGE,
			ProcessMessage
		);
	}

	public void ProcessMessage(NetworkResponse response) {
		ResponseMessage args = response as ResponseMessage;
		
		if (args.status == 0) {
			string message = "";
			
			if (args.type == 0) {
				message += "<b>" + args.username + "</b>: ";
			}
			
			message += args.message;
			PutMessange(message);
		}
	}

	public void SendMessage() {
		if (message != "") {
			NetworkManager.Send(
				MessageProtocol.Prepare(0, message)
				);
			message = "";
		}
	}

	void Update () {
	}

	void PutSystemMessage(string message) {
		ChatText.text += "<color=navy>" + message + "</color>\n";
	}

	void PutMessange(string message) {
		ChatText.text += message + "\n";

		// Clear input field
		ChatInputText.text = "";
		ChatInputText.Select();
		ChatInputText.ActivateInputField();
	}

	void OnSubmit() {
		if (ChatInputText.text != "") {
			NetworkManager.Send(
				MessageProtocol.Prepare(0, ChatInputText.text)
			);
			message = "";
		}
	}

	void OnChangeName() {
		var name = NameInputText.text;
		NetworkManager.Send(
			ChangeNameProtocol.Prepare(name)
		);
	}

	void OnReady() {
		NetworkManager.Send(
			PairProtocol.Prepare()
		);
	}

	public void ProcessReadyReply(NetworkResponse response) {
		ResponsePair args = response as ResponsePair;
		if (args.status == 0) { // new room, waiting for other client
			print ("");
		} else { // paired sucessfully
		}
	}

	void OnGUI() {
		//if(ChatInputText.isFocused && ChatInputText.text != "" && Input.GetKey(KeyCode.Return)) {
		//	OnSubmit();
		//}
	}
}
