package it.polimi.ingsw.client.MessageFromServer;


import it.polimi.ingsw.client.Client;

public class ItsYourTurn extends MessageFromServer {
    private String player;

    @Override
    public void activateMessage(Client client) {
        client.getGameView().changeTurn(player);
    }
}
