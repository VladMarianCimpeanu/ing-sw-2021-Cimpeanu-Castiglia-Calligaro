package it.polimi.ingsw.client.MessageFromServer;

import java.util.ArrayList;

public class SelectedMarbles extends MessageFromServer{
    private ArrayList<String> marbles;

    @Override
    public void activateMessage() {
        System.out.println("You have received:\n" + marbles);
    }
}
