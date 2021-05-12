package it.polimi.ingsw.client.MessageFromServer;

import java.util.ArrayList;

public class SelectedLeadercards extends MessageFromServer {
    private ArrayList<Integer> leaderIds;

    @Override
    public void activateMessage() {
        System.out.println("You selected the following leader cards: " + leaderIds);
    }
}
