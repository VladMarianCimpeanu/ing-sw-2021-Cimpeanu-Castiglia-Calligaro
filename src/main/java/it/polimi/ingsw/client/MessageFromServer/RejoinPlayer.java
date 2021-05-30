package it.polimi.ingsw.client.MessageFromServer;

import it.polimi.ingsw.client.Client;

public class RejoinPlayer extends MessageFromServer {
    private String player;
    @Override
    public void activateMessage(Client client) {
        if(client.getNickname().equals(player)) {
            client.getGameView().dumpMessage("Welcome back " + player + ". We've missed you");
            client.getGameView().startGame();
        }
        else
            client.getGameView().dumpMessage(player+" is back in the game");
    }
}
