public class TileData {
	public int tile_id { get; set; }
	public int owner { get; set; }
	public int terrain_type { get; set; }
	public int vegetation_capacity { get; set; }
	public int x_position { get; set; }
	public int y_position { get; set; }
	public int z_position { get; set; }
	
	public TileData(int tile_id) {
		this.tile_id = tile_id;
	}
}
