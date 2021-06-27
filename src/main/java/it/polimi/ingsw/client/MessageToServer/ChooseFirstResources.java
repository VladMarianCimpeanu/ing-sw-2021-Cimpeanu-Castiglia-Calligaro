package it.polimi.ingsw.client.MessageToServer;

import it.polimi.ingsw.server.controller.Controller;
import it.polimi.ingsw.server.controller.states.TurnState;
import it.polimi.ingsw.client.Resource;

/**
 * Message to server: it contains the resources selected at the beginning of the game and where to place them.
 */
public class ChooseFirstResources implements MessageToServer {
    private String type;
    private Resource res1, res2;
    private int shelf1, shelf2;
    private String nickname;

    public ChooseFirstResources(Resource res1, Resource res2, int shelf1, int shelf2, String nickname) {
        this.type = "ChooseFirstResources";
        this.res1 = res1;
        this.res2 = res2;
        this.shelf1 = shelf1;
        this.shelf2 = shelf2;
        this.nickname = nickname;
    }

    public ChooseFirstResources(Resource res1, int shelf1, String nickname){
        this(res1, null, shelf1, 0, nickname);
    }
}
