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
public class ResponseKeyboard extends GameResponse{
    
    private int key,keytype;
    
    
    public ResponseKeyboard(){
        response_id = NetworkCode.KEYBOARD + Constants.SMSG_AUTH_BIAS;
    }
    
    @Override
    public byte[] getBytes() {
        GamePacket packet = new GamePacket(response_id);
        packet.addInt32(keytype);
        packet.addInt32(key);
        return packet.getBytes();
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public int getKeytype() {
        return keytype;
    }

    public void setKeytype(int keytype) {
        this.keytype = keytype;
    }
}
