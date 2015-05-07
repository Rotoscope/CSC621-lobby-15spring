/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.response;

import metadata.Constants;
import metadata.NetworkCode;
import util.GamePacket;

/**
 *
 * @author Sbc-ComEx
 */
public class RRResponseSpecies extends GameResponse {
    private int id;

    public RRResponseSpecies() {
         response_id = NetworkCode.RRSPECIES + Constants.SMSG_AUTH_BIAS;
    }

    @Override
    public byte[] getBytes() {
         GamePacket packet = new GamePacket(response_id);
         packet.addInt32(id);
         return packet.getBytes();
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
