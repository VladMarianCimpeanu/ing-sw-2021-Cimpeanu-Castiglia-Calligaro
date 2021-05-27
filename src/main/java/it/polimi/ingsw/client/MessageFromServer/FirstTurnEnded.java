package it.polimi.ingsw.client.MessageFromServer;

import it.polimi.ingsw.client.Client;

public class FirstTurnEnded extends MessageFromServer{
    @Override
    public void activateMessage(Client client) {
        client.getGameView().firstTurnEnded();
    }
}
