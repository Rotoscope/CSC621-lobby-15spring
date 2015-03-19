using System;
using System.Collections.Generic;
using UnityEngine;
using System.Collections;

public class Map : MonoBehaviour {
	
		private Dictionary<int, GameObject> TileList;
		private Dictionary<int, Color> PlayerColorList;
		private Dictionary<int, string> PlayerNameList;

		public GameObject desert_tile;
		public GameObject jungle_tile;
		public GameObject arctic_tile;
		public GameObject grasslands_tile;
		public GameObject player_owned_tile;
		public GameObject opponent_owned_tile;

		private GameObject mainObject;

		GameObject tileGameObject;
		GameObject tileOwnershipGameObject;

		//Holds mouse mode; Mode 0 = normal, Mode 1 = Attack tile selected, Mode 2 = Defender tile selected
		int mode = 0;

		void Awake() {
			mainObject = GameObject.Find("MainObject");		
			TileList = new Dictionary<int, GameObject>();
			mainObject.GetComponent<MessageQueue>().AddCallback(Constants.SMSG_TILE_UPDATE, ResponseTileUpdate);
		}

		void Start(){
		/*
			ConnectionManager cManager = mainObject.GetComponent<ConnectionManager>();
			
			if (cManager) {
				cManager.Send(new ResponseTileUpdate());
			} 
		 */
		}

		public void CreateTileGameObject(int tileid,GameObject tile){
			TileList.Add(tileid, tile);
		}

		/*
		public void CreateTileGameObject(Tile tile)
		{
			if (tile.TerrianType == 1){
				tileGameObject = GameObject.Instantiate(desert_tile) as GameObject;
			}
			else if (tile.TerrianType == 2){
				tileGameObject = GameObject.Instantiate(jungle_tile2) as GameObject;
			}
			else if (tile.TerrianType == 3){
				tileGameObject = GameObject.Instantiate(forest_tile) as GameObject;
			}
			else if (tile.TerrianType == 4){
				tileGameObject = GameObject.Instantiate(arctic_tile) as GameObject;
			}

			tileGameObject.transform.position = coordTo3D(new Vector3(tile.X, tile.Y, tile.Z));
			tileGameObject.transform.parent = GameObject.Find("Map").transform;
			
			tileGameObject.AddComponent("Tile");

			tileGameObject.GetComponent<Tile>() ;
			TileList.Add(tile.TileID, tileGameObject);
		}*/

		public void CreateOwnershipMarkers()
		{
			foreach (var tile in TileList) {
						tileGameObject = tile.Value;
						//Checks if Tile is owned by yourself or unowned
						if (tileGameObject.GetComponent<Tile>().OwnerID == Constants.USER_ID) {
							tileOwnershipGameObject = GameObject.Instantiate(player_owned_tile) as GameObject;
							tileOwnershipGameObject.SetActive(true);
							tileOwnershipGameObject.transform.position = tileGameObject.transform.position;
							tileOwnershipGameObject.transform.parent = tileGameObject.transform;
						}
						//Tile owned by another player
						else if (tileGameObject.GetComponent<Tile>().OwnerID != 0) {
							tileOwnershipGameObject = GameObject.Instantiate(opponent_owned_tile) as GameObject;
							tileOwnershipGameObject.SetActive(true);
							tileOwnershipGameObject.transform.position = tileGameObject.transform.position;
							tileOwnershipGameObject.transform.parent = tileGameObject.transform;
						}
				}
		}
		
		public static Vector3 coordTo3D(Vector3 hc) {
			var x = Globals.Radius * Mathf.Sqrt(3) * (hc.x + hc.z / 2);
			var z = Globals.Radius * 3/2 * hc.z;

			return new Vector3((float)x, 0, (float)z);
		}

		public static int distance(Tile tile1, Tile tile2) {
				return Mathf.Max(
			Mathf.Abs(tile1.X - tile2.X), 
			Mathf.Abs(tile1.Y - tile2.Y), 
			Mathf.Abs(tile1.Z - tile2.Z)
				);
		}

		public void ResponseTileUpdate(ExtendedEventArgs eventArgs) {
			ResponseTileUpdateEventArgs args = eventArgs as ResponseTileUpdateEventArgs;
			//add codes here to update tile list
			if (args.status == 0) {
				Debug.Log("update tile");

				var tile = (GameObject)TileList[args.tile_id];
				tile.GetComponent<Tile>().OwnerID = args.owner_id;
				
				foreach (Transform child in transform) {
					if (child.name.Contains("Outline")) {
						Destroy(child);
					}
				} 
				//Checks if Tile is owned by yourself or unowned
				if (tile.GetComponent<Tile>().OwnerID == Constants.USER_ID) {
					tileOwnershipGameObject = GameObject.Instantiate(player_owned_tile) as GameObject;
					tileOwnershipGameObject.SetActive(true);
					tileOwnershipGameObject.transform.position = tile.transform.position;
					tileOwnershipGameObject.transform.parent = tile.transform;
				}
				//Tile owned by another player
				else if (tile.GetComponent<Tile>().OwnerID != 0) {
					tileOwnershipGameObject = GameObject.Instantiate(opponent_owned_tile) as GameObject;
					tileOwnershipGameObject.SetActive(true);
					tileOwnershipGameObject.transform.position = tile.transform.position;
					tileOwnershipGameObject.transform.parent = tile.transform;
				}
			}
	}
}

