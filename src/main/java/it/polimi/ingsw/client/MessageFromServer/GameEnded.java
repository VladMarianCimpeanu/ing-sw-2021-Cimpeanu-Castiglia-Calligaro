package it.polimi.ingsw.client.MessageFromServer;

import it.polimi.ingsw.client.Client;

import java.util.Map;

public class GameEnded extends MessageFromServer{
    private Map<String, Integer> ranks;
    @Override
    public void activateMessage(Client client) {

    }
}
