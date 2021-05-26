package it.polimi.ingsw.client.MessageFromServer;

import it.polimi.ingsw.client.Client;

import java.util.ArrayList;

public class KeepLeadercards extends MessageFromServer {
    private ArrayList<Integer> leaderIds;

    @Override
    public void activateMessage(Client client) {
        if(leaderIds != null) {
            client.getGameView().getPlayer(client.getNickname()).getLeaderCards().keep(leaderIds);
            client.getGameView().dumpMessage("You have received the following leader cards. Choose two of them:");
            client.getGameView().getPlayer(client.getNickname()).getLeaderCards().show();
        }
    }
}
