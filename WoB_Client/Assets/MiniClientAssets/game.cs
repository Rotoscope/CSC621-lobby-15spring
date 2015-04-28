﻿using UnityEngine;
using UnityEngine.UI;

using System.Collections;
using System.Collections.Generic;

namespace MiniClient {

public class game : MonoBehaviour {

	public Button SubmitBtn = null;
	public Button ChangeNameBtn = null;
	public InputField ChatInputText = null;
	public InputField NameInputText = null;
	public Text ChatText = null;

	private string message = "";

	// Use this for initialization
	void Start () {
		SubmitBtn.onClick.AddListener(() => { OnSubmit(); });
		ChangeNameBtn.onClick.AddListener(() => { OnChangeName(); });

		NameInputText.text = "Your Name";

		PutSystemMessage ("Welcome! Click 'Ready' button and chat with a random stranger.");
	}

	void Awake() {
		NetworkManager.Listen(
			NetworkCode.MESSAGE,
			ProcessMessage
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

	void OnGUI() {
		//if(ChatInputText.isFocused && ChatInputText.text != "" && Input.GetKey(KeyCode.Return)) {
		//	OnSubmit();
		//}
	}
}
}