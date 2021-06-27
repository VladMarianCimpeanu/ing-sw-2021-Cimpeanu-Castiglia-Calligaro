package it.polimi.ingsw.server.MessageToClient.Updates;

import it.polimi.ingsw.server.MessageToClient.MessageToClient;

/**
 * Message to client: it contains the IDs of the leader cards selected as strategies for the market. It contains the
 * number of white marbles that remain to be converted.
 */
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
