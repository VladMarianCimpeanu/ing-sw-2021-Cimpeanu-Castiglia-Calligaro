package it.polimi.ingsw.client.MessageFromServer;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.GUI;

/**
 * Message from server: it contains the nickname of the player that has just rejoined the game.
 */
public class RejoinPlayer extends MessageFromServer {
    private String player;
    @Override
    public void activateMessage(Client client) {
        if(client.getNickname().equals(player)) {
            client.getGameView().dumpMessage("Welcome back " + player + ". We've missed you");
            client.getGameView().rejoinGame();
        }
        else
            client.getGameView().dumpMessage(player+" is back in the game");
    }
}
