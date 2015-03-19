using UnityEngine;
using System.Collections;

public class MapCamera : MonoBehaviour {
		Ray ray; // just declare the variable
		RaycastHit hit = new RaycastHit();
		public GameObject DefenderCursor;
		public GameObject RoamingCursor;
		public GameObject AttackerCursor;
		public Tile CurrentTile { get; set; }
		public Tile AttackerTile { get; set; }
		public Tile DefenderTile { get; set; }
		public Tile FirstTile { get; set; }
		
		bool dragging = false;
		bool choosingFirstTile = false;
		Vector3 mouseDownPos;
		Vector3 oldCameraPos;
		//Holds mouse mode; Mode 0 = normal, Mode 1 = Attack tile selected, Mode 2 = Defender tile selected, Mode 3 = Selected Home Tile, Mode 4 = possible Home Time clicked
		public int mode { get; set;}
		//Type of battle initiated; 0 = none, 1 = proxy, 2 = direct
		public int battleType { get; private set; }
		
		//Camera Zoom related variables
		private float cameraZoomed = 10f;
		private float cameraNormal = 59.99999f;
		private float cameraSmoothing = 5f;
		private bool isZoomed = false;

		// Use this for initialization
		void Start() {
			reset();
		}
	
		// Update is called once per frame
		void Update() {
			if(Input.GetKeyDown("z")){
				isZoomed = !isZoomed; 
			}
		
			if(isZoomed == true){
				camera.fieldOfView = Mathf.Lerp(camera.fieldOfView,cameraZoomed,Time.deltaTime*cameraSmoothing);
			}
			else{
				camera.fieldOfView = Mathf.Lerp(camera.fieldOfView,cameraNormal,Time.deltaTime*cameraSmoothing);
			}
				if (GUIUtility.hotControl == 0) {
						ray = Camera.main.ScreenPointToRay(Input.mousePosition);

						if (Physics.Raycast(ray, out hit)) {
							CurrentTile = hit.transform.gameObject.GetComponent<Tile>();
							//GameObject.Find("MainObject").GetComponent<TileInfoGUI>().Show();
							if(mode == 0)
								{
									RoamingCursor.transform.position = hit.transform.gameObject.transform.position;
									
									if (Input.GetMouseButtonDown(0) && CurrentTile.OwnerID == Constants.USER_ID) {
										mode = 1;
										AttackerCursor.SetActive(true);
										RoamingCursor.SetActive(false);
										AttackerTile = hit.transform.gameObject.GetComponent<Tile>();
										AttackerCursor.transform.position = hit.transform.gameObject.transform.position;
										GameObject.Find("MainObject").GetComponent<Battle>().isHidden = false;
									}
								}
								else if (mode == 1)
								{
									int distance = Map.distance(CurrentTile, AttackerTile);
									if (distance == 1 && CurrentTile.OwnerID != AttackerTile.OwnerID) {
										DefenderCursor.SetActive(true);
										DefenderCursor.transform.position = hit.transform.gameObject.transform.position;
										if (Input.GetMouseButtonDown(0)) {
											DefenderTile = hit.transform.gameObject.GetComponent<Tile>();	
											DefenderCursor.transform.position = DefenderTile.transform.position;
											mode = 2;
											if (DefenderTile.OwnerID == 0)
											{
												//Attacking empty tile
												battleType = 1;
											}
											else
											{
												//Attacking owned tile
												battleType = 2;
											}
										}
									}
								}
								else if (mode == 3 || mode == 4)
								{
									if (Input.GetMouseButtonDown(0) && CurrentTile.OwnerID == 0) {
										if (!RoamingCursor.activeInHierarchy)
											RoamingCursor.SetActive(true);
										RoamingCursor.transform.position = hit.transform.gameObject.transform.position;
										FirstTile = hit.transform.gameObject.GetComponent<Tile>();
										mode = 4;
									}
								}
						}

						if (Input.GetMouseButtonDown(1)) {
								dragging = true;
								mouseDownPos = Input.mousePosition;
								oldCameraPos = transform.position;
						}
			
						if (Input.GetMouseButtonUp(1))
								dragging = false;
			
						if (dragging)
						{
							if (isZoomed)	
								transform.position = new Vector3(oldCameraPos.x + (mouseDownPos.x - Input.mousePosition.x) * .04f, oldCameraPos.y, oldCameraPos.z + (mouseDownPos.y - Input.mousePosition.y) * .04f);
							else
								transform.position = new Vector3(oldCameraPos.x + (mouseDownPos.x - Input.mousePosition.x) * .25f, oldCameraPos.y, oldCameraPos.z + (mouseDownPos.y - Input.mousePosition.y) * .25f);
						}
				}

		}
	
		public void reset() {
			if (choosingFirstTile) {
						mode = 3;
				}
				else {
						mode = 0;
						RoamingCursor.SetActive(true);
				}
				battleType = 0;
				GameObject.Find("MainObject").GetComponent<Battle>().isHidden = true;
				DefenderCursor.SetActive(false);
				AttackerCursor.SetActive(false);
				AttackerTile = null;
				DefenderTile = null;
				FirstTile = null;
		}

		public void FirstTileProcess(bool ongoing) {
		choosingFirstTile = ongoing;
		}

		public void MakeTileGUI()
		{
		}
}
