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
 * @author markfavis
 */
public class ResponseRRBoost extends GameResponse {

    private int boostItemID;

    public ResponseRRBoost() {
        response_id = NetworkCode.RRBOOST + Constants.SMSG_AUTH_BIAS;
        this.boostItemID = 0;
    }

    @Override
    public byte[] getBytes() {
        GamePacket packet = new GamePacket(response_id);

        packet.addInt32(getBoostItemID());
        // nothing to response
        return packet.getBytes();
    }

    /**
     * @return the boostItemID
     */
    public int getBoostItemID() {
        return boostItemID;
    }

    /**
     * @param boostItemID the boostItemID to set
     */
    public void setBoostItemID(int boostItemID) {
        this.boostItemID = boostItemID;
    }

}
