package networking.request;

// Java Imports
import java.io.IOException;

import core.WorldManager;

// Other Imports
import dataAccessLayer.AvatarDAO;
import dataAccessLayer.PlayerDAO;
import metadata.Constants;
import model.Avatar;
import model.World;
import networking.response.ResponseRegister;
import networking.response.ResponseWorld;
import networking.response.ResponseWorldMenuAction;
import utility.DataReader;

/**
 * The RequestRegister class handles the registration process to create new
 * accounts for users.
 */
public class RequestRegister extends GameRequest {

    // Data
    private String first_name;
    private String last_name;
    private String email;
    private String password;
    private String display_name;
    // Responses
    private ResponseRegister responseRegister;

    public RequestRegister() {
        responses.add(responseRegister = new ResponseRegister());
    }

    @Override
    public void parse() throws IOException {
        first_name = DataReader.readString(dataInput).trim();
        last_name = DataReader.readString(dataInput).trim();
        email = DataReader.readString(dataInput).trim();
        password = DataReader.readString(dataInput).trim();
        display_name = DataReader.readString(dataInput).trim();
    }

    @Override
    public void doBusiness() throws Exception {
        if (!display_name.isEmpty() && !email.isEmpty() && email.split("@").length == 2 && !password.isEmpty()) {
            if (PlayerDAO.containsEmail(email)) {
                responseRegister.setStatus((short) 1);
            } else if (PlayerDAO.containsUsername(display_name)) {
                responseRegister.setStatus((short) 2);
            } else {
                int player_id = PlayerDAO.createAccount(email, password, display_name, first_name, last_name, client.getIP());

                Avatar avatar = new Avatar(-1);
                avatar.setAvatarType(Constants.AVATAR_TYPE_PLANTER);
                avatar.setName(display_name);
                avatar.setCurrency(Constants.INITIAL_COINS);
                avatar.setPlayerID(player_id);

                AvatarDAO.createAvatar(avatar);

                responseRegister.setStatus((short) 0);
                
                //author: Lobby Team
                //create new world for first time player
                World world = new World(-1);
                world.setGameMode(Constants.GAMEMODE);
                //String name = client.getPlayer().getUsername() + "'s World " + System.currentTimeMillis() % 100;
                world.setGameName(display_name + "'s world");

                world.setMaxPlayers(Constants.MAXPLAYERS);
                world.setEnvType(Constants.ENVTYPE);
                world.setAccessType(Constants.ACCESSTYPE);

                if (world.getAccessType() == Constants.PRIVACY_TYPE_PRIVATE) {
                    world.setPassword("");
                }

                world.setCreatorID(player_id);                
                
                world.setCredits(Constants.INITIAL_CREDITS);
                WorldManager.createWorld(world);
                //WorldManager.createEcosystem(world, null);
                
                client.putActiveObject(World.class, world);

                ResponseWorld responseWorld = new ResponseWorld();
                responseWorld.setStatus((short) 0);
                responseWorld.setWorld(world);
                responses.add(responseWorld);
            }
        }
    }
}
