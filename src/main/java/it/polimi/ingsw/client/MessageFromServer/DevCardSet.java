package it.polimi.ingsw.client.MessageFromServer;

import it.polimi.ingsw.client.Client;

import java.util.ArrayList;

public class DevCardSet extends MessageFromServer {
    private ArrayList<ArrayList<Integer>> set;

    @Override
    public void activateMessage(Client client) {
        System.out.println("Available development cards to buy: " + set);
    }
}
