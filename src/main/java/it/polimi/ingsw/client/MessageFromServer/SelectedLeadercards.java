package it.polimi.ingsw.client.MessageFromServer;

import it.polimi.ingsw.client.Client;

import java.util.ArrayList;

/**
 * Message from server: it contains the IDs of the leader cards selected by the client at the beginning of the game.
 */
public class SelectedLeadercards extends MessageFromServer {
    private int id1;
    private int id2;

    @Override
    public void activateMessage(Client client) {
        if(id1 == 0 || id2 == 0) return;
        ArrayList<Integer> leaderIds = new ArrayList<>();
        leaderIds.add(id1);
        leaderIds.add(id2);
        client.getGameView().getPlayer(client.getNickname()).getLeaderCards().keep(leaderIds);
        client.getGameView().getPlayer(client.getNickname()).getLeaderCards().show();
    }
}
