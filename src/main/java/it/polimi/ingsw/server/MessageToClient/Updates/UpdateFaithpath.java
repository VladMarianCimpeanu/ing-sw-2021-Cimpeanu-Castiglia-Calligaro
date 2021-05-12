package it.polimi.ingsw.server.MessageToClient.Updates;

import it.polimi.ingsw.server.MessageToClient.MessageToClient;

import java.util.Map;

public class UpdateFaithpath implements MessageToClient {
    private String type;
    private Map<String, Integer> newPositions;

    public UpdateFaithpath(Map<String, Integer> newPositions) {
        this.type = "UpdateFaithpath";
        this.newPositions = newPositions;
    }
}
