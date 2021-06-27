package it.polimi.ingsw.server.MessageToClient.Updates;

import it.polimi.ingsw.server.MessageToClient.MessageToClient;

import java.util.Map;

/**
 * Message to client: contains all the faithpath's positions after the last update.
 * If the game is in single player mode, black cross is indicated with 'blackCross' nickname.
 */
public class UpdateFaithpath implements MessageToClient {
    private String type;
    private Map<String, Integer> newPositions;

    public UpdateFaithpath(Map<String, Integer> newPositions) {
        this.type = "UpdateFaithpath";
        this.newPositions = newPositions;
    }
}
