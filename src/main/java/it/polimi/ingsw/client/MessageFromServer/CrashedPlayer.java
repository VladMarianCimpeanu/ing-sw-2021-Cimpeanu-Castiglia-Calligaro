package it.polimi.ingsw.client.MessageFromServer;

import it.polimi.ingsw.client.Client;

public class CrashedPlayer extends MessageFromServer {
    private String nickname;

    @Override
    public void activateMessage(Client client) {
        client.getGameView().crashedWaitingPlayer(nickname);
    }
}
