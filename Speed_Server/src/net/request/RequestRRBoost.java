/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.request;

import core.GameClient;
import core.GameServer;
import java.io.DataInputStream;
import java.io.IOException;
import net.response.ResponseRRBoost;
import race.RaceManager;
import util.DataReader;

/**
 *
 * @author markfavis
 */
public class RequestRRBoost extends GameRequest {

    private int boostItemID;
    private int p_id;
    private ResponseRRBoost responseRRBoost;

    @Override
    public void parse(DataInputStream dataInput) throws IOException {
        this.boostItemID = DataReader.readInt(dataInput);
    }

    @Override
    public void process() throws Exception {
        responseRRBoost = new ResponseRRBoost();
        responseRRBoost.setBoostItemID(boostItemID);

        GameClient opClient = RaceManager.manager.getRaceByClient(client)
                .getOpponent(client).getClient();
        opClient.add(responseRRBoost);
    }

}
