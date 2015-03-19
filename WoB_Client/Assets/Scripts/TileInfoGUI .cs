using UnityEngine;

using System;
using System.Collections;

public class TileInfoGUI : MonoBehaviour {

	private MapCamera mapCamera;
	// Window Properties
	private float width = 200;
	private float height = 40;
	// Other
	public Texture background;
	private Rect windowRect;
	private bool isHidden = false;
	
	void Awake() {
		mapCamera = GameObject.Find("MapCamera").GetComponent<MapCamera>();
	}
	
	// Use this for initialization
	void Start() {
	}
	
	void OnGUI() {

		if (!isHidden) {
			Vector3 coords = Camera.main.WorldToScreenPoint(mapCamera.RoamingCursor.transform.position);
			windowRect = new Rect(coords.x - width/2 + 180, Screen.height - coords.y - 50, width, height);
			windowRect = GUILayout.Window((int) Constants.GUI_ID.Login, windowRect, MakeWindow, "Tile Information");
		}
	}
	
	void MakeWindow(int id) {
		GUILayout.Label("Owner: " + mapCamera.CurrentTile.OwnerID);
		GUILayout.Label("Terrian Type: " + mapCamera.CurrentTile.TerrianType);
		GUILayout.Label("Vegetation Capacity: " + mapCamera.CurrentTile.VegetationCapacity);
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
}
