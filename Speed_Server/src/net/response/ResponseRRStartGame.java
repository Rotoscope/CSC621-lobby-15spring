/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.response;

import util.GamePacket;
import util.Log;
import metadata.NetworkCode;

/**
 *
 * @author markfavis
 */
public class ResponseRRStartGame extends GameResponse {
    
    private final short status;

    //  Ignore comment below, 0 by convention is success.
    /*
    status:
        0 = opponent not ready
        1 = opponent ready
        2 = opponent quit
    */
    
    public ResponseRRStartGame(){
        response_id = NetworkCode.RRSTARTGAME;
        status = 0;
        Log.println("A ResponseRRStartGame has been sent out.");
    }

    @Override
    public byte[] getBytes() {
        GamePacket packet = new GamePacket(response_id);
        packet.addShort16(status);
        return packet.getBytes();
    }
}
