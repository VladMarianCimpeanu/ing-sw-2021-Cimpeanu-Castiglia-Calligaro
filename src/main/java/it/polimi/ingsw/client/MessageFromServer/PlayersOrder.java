package it.polimi.ingsw.client.MessageFromServer;

import it.polimi.ingsw.client.Client;

import java.util.ArrayList;

/**
 * Message from server: it contains the a list containing the nicknames of the players that are playing the same game of the
 * client, ordered by the turn order.
 */
public class PlayersOrder extends MessageFromServer {
    private ArrayList<String> nicknames;

    @Override
    public void activateMessage(Client client) {
        client.setNicknames(nicknames);
        String content = nicknames.size() > 1 ? "You are playing against: " : "";
        String order = "\nPlayers order is: ";
        for(String name : nicknames){
            if(!name.equals(client.getNickname()))content += name + " ";
            order += name + " ";
        }
        client.getGameView().dumpMessage(content + order);
    }
}
