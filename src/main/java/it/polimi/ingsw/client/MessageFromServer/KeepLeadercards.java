package it.polimi.ingsw.client.MessageFromServer;

import it.polimi.ingsw.client.Client;

import java.util.ArrayList;

public class KeepLeadercards extends MessageFromServer {
    private ArrayList<Integer> leaderIds;

    @Override
    public void activateMessage(Client client) {
        System.out.println("choose 2 leader cards between :" + leaderIds.toString());
    }
}
