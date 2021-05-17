package it.polimi.ingsw.client.MessageFromServer;

import it.polimi.ingsw.client.Client;

import java.util.ArrayList;

public class PlayersOrder extends MessageFromServer {
    private ArrayList<String> nicknames;

    @Override
    public void activateMessage(Client client) {
        client.setNicknames(nicknames);
    }
}
