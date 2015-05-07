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
public class ResponseRRPosition extends GameResponse {
    
//    private int x, y;
    private float x, y;
    
    public ResponseRRPosition(){
        response_id = NetworkCode.RRPOSITION + Constants.SMSG_AUTH_BIAS;
    }

    @Override
    public byte[] getBytes() {
        GamePacket packet = new GamePacket(response_id);
        packet.addFloat(x);
        packet.addFloat(y);
        return packet.getBytes();
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }
    
}
