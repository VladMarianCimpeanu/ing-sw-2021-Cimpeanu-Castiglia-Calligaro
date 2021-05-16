package it.polimi.ingsw.client.MessageFromServer;

import java.util.ArrayList;

public class DevCardSet extends MessageFromServer {
    private ArrayList<ArrayList<Integer>> set;

    @Override
    public void activateMessage() {
        System.out.println("Available development cards to buy: " + set);
    }
}
