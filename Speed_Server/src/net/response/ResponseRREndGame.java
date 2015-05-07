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
public class ResponseRREndGame extends GameResponse {

    private boolean gameCompleted;
    private float finalTime;
    private String fastestPlayer;
    private String highestPoint;

    public ResponseRREndGame() {
        response_id = NetworkCode.RRENDGAME + Constants.SMSG_AUTH_BIAS;
    }

    @Override
    public byte[] getBytes() {
        GamePacket packet = new GamePacket(response_id);

        packet.addBoolean(isGameCompleted());
        packet.addFloat(getFinalTime());
        //packet.addString(getFastestPlayer());
        //packet.addString(getHighestPoint());

        return packet.getBytes();
    }

    /**
     * @return the fastestPlayer
     */
    public String getFastestPlayer() {
        return fastestPlayer;
    }

    /**
     * @param fastestPlayer the fastestPlayer to set
     */
    public void setFastestPlayer(String fastestPlayer) {
        this.fastestPlayer = fastestPlayer;
    }

    /**
     * @return the highestPoint
     */
    public String getHighestPoint() {
        return highestPoint;
    }

    /**
     * @param highestPoint the highestPoint to set
     */
    public void setHighestPoint(String highestPoint) {
        this.highestPoint = highestPoint;
    }

    /**
     * @return the gameCompleted
     */
    public boolean isGameCompleted() {
        return gameCompleted;
    }

    /**
     * @param gameCompleted the gameCompleted to set
     */
    public void setGameCompleted(boolean gameCompleted) {
        this.gameCompleted = gameCompleted;
    }

    /**
     * @return the finalTime
     */
    public float getFinalTime() {
        return finalTime;
    }

    /**
     * @param finalTime the finalTime to set
     */
    public void setFinalTime(float finalTime) {
        this.finalTime = finalTime;
    }

}
