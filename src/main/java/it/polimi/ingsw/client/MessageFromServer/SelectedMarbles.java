package it.polimi.ingsw.client.MessageFromServer;

import it.polimi.ingsw.client.Client;

import java.util.ArrayList;

public class SelectedMarbles extends MessageFromServer{
    private ArrayList<String> marbles;

    @Override
    public void activateMessage(Client client) {
        System.out.println("You have received:\n" + marbles);
    }
}
