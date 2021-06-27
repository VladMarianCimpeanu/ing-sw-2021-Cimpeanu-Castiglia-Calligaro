package it.polimi.ingsw.client.MessageToServer;

import it.polimi.ingsw.server.controller.Controller;
import it.polimi.ingsw.server.controller.states.TurnState;

/**
 * Message to server: it contains the ids of the leader cards selected by the specified player at the beginning of the game.
 */
public class KeepLeaderCard implements MessageToServer {
    private String type;
    private int id1, id2;
    private String nickname;

    public KeepLeaderCard(int id1, int id2, String nickname) {
        this.type = "KeepLeaderCard";
        this.id1 = id1;
        this.id2 = id2;
        this.nickname = nickname;
    }
}
