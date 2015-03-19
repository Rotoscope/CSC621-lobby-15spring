using UnityEngine;

using System;
using System.Collections;
using System.IO;
using System.Net.Sockets;

public class TileSelectGUI : MonoBehaviour {
	
	private GameObject mainObject;
	private MapCamera mapCamera;
	// Window Properties
	private float width = 280;
	private float height = 100;
	// Other
	public Texture background;
	private string user_id = "";
	private string password = "";
	private Rect windowRect;
	private bool isHidden = false;
	
	void Awake() {
		mainObject = GameObject.Find("MainObject");
		mapCamera = GameObject.Find("MapCamera").GetComponent<MapCamera>();
		mainObject.GetComponent<MessageQueue>().AddCallback(Constants.SMSG_TILE, ResponseTile);

	}
	
	// Use this for initialization
	void Start() {
	}
	
	void OnGUI() {
		// Background
		//GUI.DrawTexture(new Rect(0, 0, Screen.width, Screen.height), background);
		
		// Client Version Label
		GUI.Label(new Rect(Screen.width - 75, Screen.height - 30, 65, 20), "v" + Constants.CLIENT_VERSION + " Beta");
		
		// Login Interface
		if (!isHidden) {
			windowRect = new Rect((Screen.width - width) / 2, Screen.height - height - 10f, width, height);
			windowRect = GUILayout.Window((int) Constants.GUI_ID.Login, windowRect, MakeWindow, "Select Your First Tile ...");
			
			if (Event.current.type == EventType.KeyUp && Event.current.keyCode == KeyCode.Return) {
				Submit();
			}
		}
	}
	
	void MakeWindow(int id) {
		GUILayout.Label("Click on a tile to select it wth the cursor:");
		GUI.SetNextControlName("username_field");
		if (mapCamera.mode == 4)
		{
			if (GUI.Button(new Rect(10, 50, width - 20, 30), "Select Tile")) Submit();
		}
	}
	
	public void Submit() {
		int tile_id = mapCamera.FirstTile.TileID;
		mapCamera.FirstTileProcess(false);
		mapCamera.mode = 0;

		Hide();
		//gameObject.AddComponent<MainGUI>();
		//gameObject.AddComponent<Chart>();
		//gameObject.AddComponent<Battle>();
		gameObject.AddComponent<Season>();
		ConnectionManager cManager = mainObject.GetComponent<ConnectionManager>();
		if (cManager) cManager.Send(RequestTile(tile_id));


	}
	public RequestTile RequestTile(int tile_id) {
		RequestTile request = new RequestTile();
		request.Send(tile_id);
		
		return request;
	}


	public void Show() {
		isHidden = false;
	}
	
	public void Hide() {
		isHidden = true;
	}
	
	// Update is called once per frame
	void Update() {
	}
	public void ResponseTile(ExtendedEventArgs eventArgs) {
		ResponseTileEventArgs args = eventArgs as ResponseTileEventArgs;
	}

}
