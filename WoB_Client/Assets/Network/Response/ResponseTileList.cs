using UnityEngine;
using System;
using System.Collections;
using System.Collections.Generic;

public class ResponseTileListEventArgs : ExtendedEventArgs {
	public short status { get; set; }
	public Dictionary<int, Tile> TileDictionary { get; set; }
	public ResponseTileListEventArgs() {
		event_id = Constants.SMSG_TILE_LIST;
	}
}

public class ResponseTileList : NetworkResponse {
	
	Dictionary<int, Tile> tileDictionary  = new Dictionary<int, Tile>();

	GameObject tileGameObject;
	GameObject tileOwnershipGameObject;

	private short status;
	public ResponseTileList() {
	}
	
	public override void parse() {
		status = DataReader.ReadShort(dataStream);
		Map map = (Map)GameObject.Find("Map").GetComponent<Map>();
		if (status == 0) {
			short size = DataReader.ReadShort(dataStream);
			//Debug.Log("tile list size is: " + size);
				
			for (int i = 0; i < size; i++) {
				String singleTile = DataReader.ReadString(dataStream);
				//Debug.Log (singleTile);
				String[] stringTileArgs = singleTile.Split(',');
				int[] tileArgs = Array.ConvertAll<string, int>(stringTileArgs, int.Parse);
				int tileID = tileArgs[0];
				int ownerID = tileArgs[1];
				int vegetationCapacity = tileArgs[2];
				int terrainType = tileArgs[3];
				int x = tileArgs[4];
				int y = tileArgs[5];
				int z = tileArgs[6];

				if (terrainType == 1){
					tileGameObject = GameObject.Instantiate(map.desert_tile) as GameObject;
				}
				else if (terrainType == 2){
					tileGameObject = GameObject.Instantiate(map.jungle_tile) as GameObject;
				}
				else if (terrainType == 3){
					tileGameObject = GameObject.Instantiate(map.grasslands_tile) as GameObject;
				}
				else if (terrainType == 4){
					tileGameObject = GameObject.Instantiate(map.arctic_tile) as GameObject;
				}
				tileGameObject.transform.position = Map.coordTo3D(new Vector3(x, y, z));
				tileGameObject.transform.parent = GameObject.Find("Map").transform;

				tileGameObject.AddComponent<Tile>();

				Tile tile = tileGameObject.GetComponent<Tile>();
				tile.TileID = tileID;
				tile.OwnerID = ownerID;
				tile.VegetationCapacity = vegetationCapacity;
				tile.TerrianType = terrainType;
				tile.X = x;
				tile.Y = y;
				tile.Z = z;

				//Checks if Tile is owned by yourself or unowned
				//Debug.Log("CONSTANT OWNER: " + Constants.USER_ID);
				/*
				if (tile.OwnerID == Constants.USER_ID)
				{
					tileOwnershipGameObject = GameObject.Instantiate(map.player_owned_tile) as GameObject;
					tileOwnershipGameObject.SetActive(true);
					tileOwnershipGameObject.transform.position = tileGameObject.transform.position;
					tileOwnershipGameObject.transform.parent = tileGameObject.transform;
				}
				//Tile owned by another player
				else if (tile.OwnerID != 0){
					tileOwnershipGameObject = GameObject.Instantiate(map.opponent_owned_tile) as GameObject;
					tileOwnershipGameObject.SetActive(true);
					tileOwnershipGameObject.transform.position = tileGameObject.transform.position;
					tileOwnershipGameObject.transform.parent = tileGameObject.transform;
				}
				*/
				tileDictionary.Add(tileID, tile);
				map.CreateTileGameObject(tile.TileID, tileGameObject);
			}		
		}
	}
	
	public override ExtendedEventArgs process() {
		ResponseTileListEventArgs args = null;
		
		if (status == 0) {
			args = new ResponseTileListEventArgs();
			args.status = status;
			args.TileDictionary = tileDictionary;
		}
		
		return args;
	}
}