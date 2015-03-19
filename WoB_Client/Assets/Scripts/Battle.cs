//author: Ye, Steven
//battle model

using UnityEngine;
using System.Collections;

public class Battle : MonoBehaviour {
	
	private GameObject mainObject;
	private static bool stopRequest;
	float btnX, btnY, btnW, btnH;
	Rect windowRect;
	public bool isHidden = true;
	int width = 150, height = 130;
	MapCamera mapCamera;

	void Awake() {
		stopRequest = false;
		mainObject = GameObject.Find("MainObject");
		mapCamera = GameObject.Find("MapCamera").GetComponent<MapCamera>();
		mainObject.GetComponent<MessageQueue>().AddCallback(Constants.SMSG_BATTLE_REQ, ResponseBattleInit);
		btnX = Screen.width * 0.8f;
		btnY = Screen.height * 0.8f;
		btnW = Screen.width * 0.2f;
		btnH = Screen.height * 0.1f;
		
	}
	
	// Use this for initialization
	void Start () {
	}
	
	void OnGUI() {
		if (!isHidden) {
			Vector3 coords = Camera.main.WorldToScreenPoint(mapCamera.RoamingCursor.transform.position);
			windowRect = new Rect(coords.x - width/2, Screen.height - coords.y + 64, width, height);
			windowRect = GUILayout.Window((int)Constants.GUI_ID.BattleRequest, windowRect, MakeBattleGUI, "Battle");
		}	
		
	}
	
	void MakeBattleGUI(int id) {
		GUILayout.Label("Click 'Battle' to initiate battle between tiles");
		if (mapCamera.AttackerTile != null 
		    && mapCamera.DefenderTile != null 
		    && GUI.Button(new Rect(8, 64, width - 16, 32), "Battle Request")) {
			if (stopRequest) {
				Debug.Log("battle request is already sent, wait for a battle to begin");
			} else {
				ConnectionManager cManager = mainObject.GetComponent<ConnectionManager>();
				
				if (cManager) {
					//StartCoroutine("RequestBattleInit", Constants.BATTLE_REQUEST_RATE);
					cManager.Send(RequestBattleInit());
					Debug.Log("battle request sent to server");
				}
			}
			} else if (GUI.Button(new Rect(8, 100, width - 16, 32), "Cancel")) {
				Debug.Log("Requested by user: stop sending battle request ");

				//StopCoroutine("RequestBattleInit");
				mapCamera.reset();
			}
		
		/*} else if (Input.GetKeyDown(KeyCode.S)) {
			Debug.Log("Requested by user: stop sending battle request ");

			//StopCoroutine("RequestBattleInit");
			//send request to cancel battle
		} */
	}
	
	// Update is called once per frame
	void Update () {

		/*if (GUI.Button (new Rect (btnX, btnY, btnW, btnH), "Battle Request")) {
			if (stopRequest){
				Debug.Log("battle request is already sent, wait for a battle to begin");
			}
			else {
				ConnectionManager cManager = mainObject.GetComponent<ConnectionManager>();
				
				if (cManager) {
					//StartCoroutine("RequestBattleInit", Constants.BATTLE_REQUEST_RATE);
					cManager.Send(RequestBattleInit());
					Debug.Log("battle request sent to server");
				}
			}
		}

		else if (Input.GetKeyDown(KeyCode.S)) {
						Debug.Log("Requested by user: stop sending battle request ");
						//StopCoroutine("RequestBattleInit");
						//send request to cancel battle
		}*/
		/*else if (stopRequest) {
						Debug.Log("Requested by server: battle quest received, stop sending battle request");
						StopCoroutine("RequestBattleInit");			
				}*/
		
	}
	/*public void stopBattleRequest(){
		if (stopRequest == true) {
			StopCoroutine("RequestBattleInit");
		}
	}*/
	public static void stopSendRequest(){
		stopRequest = true;
	}
	/*public IEnumerator RequestBattleInit(float time) {
		//float time = Constants.BATTLE_REQUEST_RATE;
		while (true) {
			yield return new WaitForSeconds(time);
			
			ConnectionManager cManager = mainObject.GetComponent<ConnectionManager>();
			cManager.Send(new RequestBattleInit());
			//Debug.Log("battle request sent");
			//yield return null;
		}

		//StartCoroutine("RequestBattleInit",time);
	}*/	
	public RequestBattleInit RequestBattleInit() {

		var attackerTileID = mapCamera.AttackerTile.TileID;
		var defenderTileID = mapCamera.DefenderTile.TileID;
		var battleType = mapCamera.battleType;
		Debug.Log("Attacker: " + attackerTileID + ", Defender: " + defenderTileID + "Battle Type: " + battleType);
		//tileHighlight = GameObject.Find("hex_quad");
		//var attacker = (GameObject)Instantiate(tileHighlight);

		//mouse.GetComponent<MapMouse>().enabled = false;
		//phase2 = true;

		//defender = (GameObject)Instantiate(tileHighlight);	
		//Texture2D texture = Resources.Load(Constants.IMAGE_RESOURCES_PATH + "Tile/defender_stroke") as Texture2D;
		//defender.renderer.material.mainTexture = texture;	

		//Texture2D texture = Resources.Load(Constants.IMAGE_RESOURCES_PATH + "Tile/attacker_overlay") as Texture2D;
		//attacker.renderer.material.mainTexture = texture;
		//var attackerTexture = GameObject.Instantiate(Resources.Load (Constants.PREFAB_RESOURCES_PATH + "attacker_quad"), tile_pos, Quaternion.identity);

		RequestBattleInit request = new RequestBattleInit();
		request.Send(attackerTileID,defenderTileID);
		
		return request;
	}
	
	public void ResponseBattleInit(ExtendedEventArgs eventArgs) {
		ResponseBattleInitEventArgs args = eventArgs as ResponseBattleInitEventArgs;
	}
}
