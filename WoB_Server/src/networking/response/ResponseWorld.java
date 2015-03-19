package networking.response;

/**
 * @author Lobby Team
 */
//Other Imports
import metadata.Constants;
import model.World;
import utility.GamePacket;

public class ResponseWorld extends GameResponse {

 private World world;
 private int world_id;
 private short status;

 public ResponseWorld() {
     responseCode = Constants.SMSG_WORLD;
 }

 @Override
 public byte[] constructResponseInBytes() {
     GamePacket packet = new GamePacket(responseCode);
     packet.addShort16(status);
     if (status == 0) {
    	 packet.addInt32(world.getID());
    	 packet.addString(world.getGameName());
    	 packet.addShort16(world.getGameMode());
    	 packet.addInt32(world.getCredits());
    	 packet.addString(world.getEnvType());
    	 //packet.addShort16((short) world.getMaxPlayers());
    	 packet.addFloat(world.getTimeRate());
    	 packet.addShort16((short) world.getMonth());//Ye: need to change to global time
     }
     return packet.getBytes();
 }

 public void setStatus(short status) {
     this.status = status;
 }

 public void setWorld(World world) {
     this.world = world;
 }
 
 public void setWorldID(int world_id) {
     this.world_id = world_id;
 }
}
