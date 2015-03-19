package model;

import dataAccessLayer.TileDAO;

/* author: Ye
 * The Tile class holds important information about the tile including, most
 * importantly, the owner. 
 */
public class Tile {
	
	//data fields 
	private int tile_owner;
	private int tile_id;
	private int vegetation_capacity;
	private int terrain_type;
	private int x_position;
	private int y_position;
	private int z_position;
	
	//constructor
	public Tile(int tile_id){
		this.tile_id = tile_id;
	}
	public Tile(int tile_id, int x_position,int y_position, int z_position){
		this.tile_id = tile_id;
		this.x_position = x_position;
		this.y_position = y_position;
		this.z_position = z_position;
		
		this.vegetation_capacity = 0;
		this.terrain_type = 0;
	}
	
	public int getVegetationCapacity() {
		return vegetation_capacity;
	}

	public void setVegetationCapacity(int vegetation_capacity) {
		this.vegetation_capacity = vegetation_capacity;
	}

	public int getXPosition() {
		return x_position;
	}

	public void setXPosition(int x_position) {
		this.x_position = x_position;
	}

	public int getYPosition() {
		return y_position;
	}

	public void setYPosition(int y_position) {
		this.y_position = y_position;
	}

	public int getZPosition() {
		return z_position;
	}

	public void setZPosition(int z_position) {
		this.z_position = z_position;
	}

	public int getOwner() {
		return this.tile_owner;
	}

	public void setOwner(int tile_owner) {
		this.tile_owner = tile_owner;
	}

	public int getTileId() {
		return tile_id;
	}

	public void setTileId(int tile_id) {
		this.tile_id = tile_id;
	}

	public int getTerrainType() {
		return terrain_type;
	}

	public void setTerrainType(int terrain_type) {
		this.terrain_type = terrain_type;
	}
	
	
	
}
