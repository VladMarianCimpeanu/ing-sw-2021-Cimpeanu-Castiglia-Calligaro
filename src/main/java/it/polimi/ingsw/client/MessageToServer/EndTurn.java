package it.polimi.ingsw.client.MessageToServer;

import it.polimi.ingsw.server.controller.Controller;
import it.polimi.ingsw.server.controller.states.TurnState;

/**
 * Message to server: the client tries to end his turn.
 */
public class EndTurn implements MessageToServer {
    private String type;

    public EndTurn() {
        this.type = "EndTurn";
    }
}
