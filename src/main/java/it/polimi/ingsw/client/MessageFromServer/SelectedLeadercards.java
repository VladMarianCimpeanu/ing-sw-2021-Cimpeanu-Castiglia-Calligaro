package it.polimi.ingsw.client.MessageFromServer;

import java.util.ArrayList;

public class SelectedLeadercards extends MessageFromServer {
    private int id1;
    private int id2;

    @Override
    public void activateMessage() {
        System.out.println("You selected the following leader cards: " + id1 + ", " + id2);
    }
}
