using UnityEngine;

using System;
using System.Collections;
using System.IO;
using System.Net.Sockets;

public class Login : MonoBehaviour {
	
	public Camera mainCamera;
	public Camera loginCamera;
	private GameObject mainObject;
	// Window Properties
	private float width = 280;
	private float height = 100;
	// Other
	//public Texture background;
	private string user_id = "";
	private string password = "";
	private Rect windowRect;
	private bool isHidden;
	private Map map;

	void Awake() {
		mainObject = GameObject.Find("MainObject");
		map = (Map)GameObject.Find("Map").GetComponent<Map>();
		mainObject.AddComponent<GameState>();		//mainObject.AddComponent<Shop>();
		mainObject.GetComponent<MessageQueue>().AddCallback(Constants.SMSG_AUTH, ResponseLogin);
		mainObject.GetComponent<MessageQueue>().AddCallback(Constants.SMSG_SPECIES_LIST, ResponseSpeciesList);
		mainObject.GetComponent<MessageQueue>().AddCallback(Constants.SMSG_TILE_LIST, ResponseTileList);
		mainObject.GetComponent<MessageQueue>().AddCallback(Constants.SMSG_SEASON_CHANGE, ResponseSeasonChange);
		mainObject.GetComponent<MessageQueue>().AddCallback(Constants.SMSG_AVATAR_LIST, ResponseAvatarList);
		mainObject.GetComponent<MessageQueue>().AddCallback(Constants.SMSG_WORLD, ResponseWorld);

	}
	
	// Use this for initialization
	void Start() {
		ConnectionManager cManager = mainObject.GetComponent<ConnectionManager>();
		
		if (cManager) {
			cManager.Send(new RequestSpeciesList());
			cManager.Send(new RequestTileList());
		}
	}
	
	void OnGUI() {
		// Background
		//GUI.DrawTexture(new Rect(0, 0, Screen.width, Screen.height), background);
		
		// Client Version Label
		GUI.Label(new Rect(Screen.width - 75, Screen.height - 30, 65, 20), "v" + Constants.CLIENT_VERSION + " Beta");
		
		// Login Interface
		if (!isHidden) {
			windowRect = new Rect((Screen.width - width) / 2, Screen.height / 2 - height, width, height);
			windowRect = GUILayout.Window((int) Constants.GUI_ID.Login, windowRect, MakeWindow, "Login");
		
			if (Event.current.type == EventType.KeyUp && Event.current.keyCode == KeyCode.Return) {
				Submit();
			}
		}
	}
	
	void MakeWindow(int id) {
		GUILayout.Label("User ID (Display Name or Email)");
		GUI.SetNextControlName("username_field");
		user_id = GUI.TextField(new Rect(10, 45, windowRect.width - 20, 25), user_id, 25);

		GUILayout.Space(30);
		
		GUILayout.Label("Password");
		GUI.SetNextControlName("password_field");
		password = GUI.PasswordField(new Rect(10, 100, windowRect.width - 20, 25), password, "*"[0], 25);
		
		GUILayout.Space(75);
		if (GUI.Button(new Rect(windowRect.width / 2 - 110, 135, 100, 30), "Log In")) Submit();
		if (GUI.Button(new Rect(windowRect.width / 2 + 10, 135, 100, 30), "Register")) SwitchToRegister();
	}
	
	public void Submit() {
		user_id = user_id.Trim();
		password = password.Trim();

		if (user_id.Length == 0) {
			mainObject.GetComponent<Main>().CreateMessageBox("User ID Required");
			GUI.FocusControl("username_field");
		} else if (password.Length == 0) {
			mainObject.GetComponent<Main>().CreateMessageBox("Password Required");
			GUI.FocusControl("password_field");
		} else {
			ConnectionManager cManager = mainObject.GetComponent<ConnectionManager>();
			
			if (cManager) {
				cManager.Send(RequestLogin(user_id, password));
			}
		}
	}
	//SpeedController speedController = go.GetComponent <SpeedController> ();
	//float courrentSpeed = speedController.speed;
	public RequestLogin RequestLogin(string username, string password) {
		RequestLogin request = new RequestLogin();
		request.Send(username, password);
		
		return request;
	}
	
	public void ResponseLogin(ExtendedEventArgs eventArgs) {
		ResponseLoginEventArgs args = eventArgs as ResponseLoginEventArgs;
		//world.name = args.name;
		//world.credits = args.credits;
		if (args.status == 0) {
			Player player = (Player) mainObject.AddComponent<Player>();
			Constants.USER_ID = args.user_id;
			player.username = args.username;
			SwitchToTileSelect(args.tile_num);
			map.CreateOwnershipMarkers();
		} else {
			mainObject.GetComponent<Main>().CreateMessageBox("Login Failed");
		}
	}
	
	public void ResponseSpeciesList(ExtendedEventArgs eventArgs) {
		ResponseSpeciesListEventArgs args = eventArgs as ResponseSpeciesListEventArgs;
//		SpeciesTable.Update(args.speciesList);
	}
	public void ResponseTileList(ExtendedEventArgs eventArgs) {
		ResponseTileListEventArgs args = eventArgs as ResponseTileListEventArgs;
	}	
	public void ResponseSeasonChange(ExtendedEventArgs eventArgs) {
		ResponseSeasonChangeEventArgs args = eventArgs as ResponseSeasonChangeEventArgs;
	}	
	public void ResponseAvatarList(ExtendedEventArgs eventArgs) {
		ResponseAvatarListEventArgs args = eventArgs as ResponseAvatarListEventArgs;
	}
	public void SwitchToRegister() {
		isHidden = true;
		gameObject.AddComponent<Register>();
	}

	public void SwitchToTileSelect(int numTilesOwned) {
				isHidden = true;
				Debug.Log("Testing SwitchtoTileSelect");
				mainCamera.enabled = true;
				mainCamera.active = true;
				loginCamera.active = false;
				mainObject.AddComponent<Battle>();
				//mainObject.AddComponent<Chat>();
				//mainObject.AddComponent("TileInfoGUI");

				//If player owns no tiles, they will need to pick a new home tile
				if (numTilesOwned == 0)
				{
						GameObject.Find("MapCamera").GetComponent<MapCamera>().FirstTileProcess(true);
						mainObject.AddComponent<TileSelectGUI>();
				}
				GameObject.Find("Cube").GetComponent<Shop>().Show();
				GameObject.Find("Cube").GetComponent<Shop>().enabled = true;
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
	public void ResponseWorld(ExtendedEventArgs eventArgs) {
		ResponseWorldEventArgs args = eventArgs as ResponseWorldEventArgs;
	}
}
