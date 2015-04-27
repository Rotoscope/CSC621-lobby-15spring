using UnityEngine;
using System;
using System.Collections;

public class CardsOfWildGUI : MonoBehaviour {
	
	private GameObject mainObject;
	// Window Properties
	private float width = 400;
	private float height = 250;

	// Other
	private int window_id;
	private string message = "";
	private Rect windowRect;

	public string logText = "";
	
	void Awake() {
		mainObject = GameObject.Find("MainObject");
		window_id = Constants.GetUniqueID();

		NetworkManager.Listen(
			NetworkCode.PAIR,
			OnPairResult
			);

		NetworkManager.Send(PairProtocol.Prepare());	
	}
	
	// Use this for initialization
	void Start() {
		windowRect = new Rect ((Screen.width - width) / 2, (Screen.height - height) / 2, width, height);
	}
	
	void OnGUI() {
		windowRect = GUILayout.Window(window_id, windowRect, MakeWindow, "Cards of Wild");
	}

	void OnDestroy() {
		NetworkManager.Ignore(
			NetworkCode.PAIR,
			OnPairResult
			);
	}
	
	void MakeWindow(int id) {
		GUILayout.Space(10);

		GUIStyle style = new GUIStyle();
		//style.alignment = TextAnchor.MiddleCenter;
		style.normal.textColor = Color.white;
		
		GUILayout.Label(message, style);

		GUILayout.Label(logText);

		GUILayout.Space(30);
		
		if (GUI.Button(new Rect(windowRect.width / 2 + 80, windowRect.height - 40, 100, 30), "Quit")) {
			OnQuit();
		}

		GUI.BringWindowToFront(window_id);
		GUI.DragWindow();
	}
	
	public void setMessage(string message) {
		this.message = message;
	}
	
	public void OnQuit() {
		//Destroy(this);
	}

	public void OnPairResult(NetworkResponse response) {
		ResponsePair args = response as ResponsePair;

		if (args.status == 0) {
			logText += "Paired successfully, ready to start the game!\n";
			Application.LoadLevel("MiniClient");
		} else {
			logText += "Game room created, waiting for another player...\n";
		}
	}
	
	// Update is called once per frame
	void Update() {
	}
}
