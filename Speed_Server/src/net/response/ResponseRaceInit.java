/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.response;

import core.GameClient;
import metadata.Constants;
import metadata.NetworkCode;
import util.GamePacket;

/**
 *
 * @author Lowell Milliken
 */
public class ResponseRaceInit extends GameResponse {
 
    private short status;
    private GameClient player;
    
    public ResponseRaceInit(){
        response_id = NetworkCode.RACE_INIT + Constants.SMSG_AUTH_BIAS;
        status = 0;
    }
    @Override
    public byte[] getBytes() {        
        GamePacket packet = new GamePacket(response_id);
                
        packet.addShort16(status);        
        
        return packet.getBytes();
    }

    public void setStatus(short status) {
        this.status = status;
    }    

    public void setPlayer(GameClient player) {
        this.player = player;
    }
}

