package it.polimi.ingsw.client.MessageToServer;

import it.polimi.ingsw.server.controller.Controller;
import it.polimi.ingsw.server.controller.states.TurnState;

public class ActivateLeaderCard implements MessageToServer {
    private String type;
    private int id;

    public ActivateLeaderCard(int id) {
        this.type = "ActivateLeaderCard";
        this.id = id;
    }
}
