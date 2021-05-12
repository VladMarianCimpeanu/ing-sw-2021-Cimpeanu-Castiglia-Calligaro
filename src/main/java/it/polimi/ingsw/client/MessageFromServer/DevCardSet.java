package it.polimi.ingsw.client.MessageFromServer;

import java.util.ArrayList;

public class DevCardSet implements MessageToClient {
    private String type;
    private ArrayList<ArrayList<Integer>> set;

    public DevCardSet(ArrayList<ArrayList<Integer>> set) {
        this.type = "DevCardSet";
        this.set = set;
    }
}
