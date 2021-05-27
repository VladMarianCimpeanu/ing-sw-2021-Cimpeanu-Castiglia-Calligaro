package it.polimi.ingsw.server.MessageToClient.Updates;

import it.polimi.ingsw.server.MessageToClient.MessageToClient;

public class UpdateStrategyBuffer implements MessageToClient {
    private String type = "UpdateStrategyBuffer";
    private int remaining;
    private int id;

    public UpdateStrategyBuffer(int remaining, int id){
        this.type = "UpdateStrategyBuffer";
        this.remaining = remaining;
        this.id = id;
    }
}
