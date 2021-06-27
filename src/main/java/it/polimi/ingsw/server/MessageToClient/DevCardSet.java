package it.polimi.ingsw.server.MessageToClient;

import java.util.ArrayList;

/**
 * Message to client: containing all the development cards that can be bought. This message is sent only during the setup
 * game phase.
 */
public class DevCardSet implements MessageToClient {
    private String type;
    private ArrayList<ArrayList<Integer>> set;

    public DevCardSet(ArrayList<ArrayList<Integer>> set) {
        this.type = "DevCardSet";
        this.set = set;
    }
}
