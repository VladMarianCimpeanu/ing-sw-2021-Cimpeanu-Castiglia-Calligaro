package it.polimi.ingsw.client.MessageFromServer;

import java.util.ArrayList;

public class KeepLeadercards extends MessageFromServer {
    private ArrayList<Integer> leaderIds;

    @Override
    public void activateMessage() {
        System.out.println("choose 2 leader cards between :" + leaderIds.toString());
    }
}
