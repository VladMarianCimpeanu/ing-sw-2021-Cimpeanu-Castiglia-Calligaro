package it.polimi.ingsw.server.MessageToClient.Updates;

import it.polimi.ingsw.server.MessageToClient.MessageToClient;

public class UpdateStrategyBuffer implements MessageToClient {
    String type = "UpdateStrategyBuffer";
    private int remaining;

    public UpdateStrategyBuffer(int remaining){
        this.remaining = remaining;
    }
}
