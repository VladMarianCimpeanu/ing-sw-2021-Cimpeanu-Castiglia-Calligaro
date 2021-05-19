package it.polimi.ingsw.client.MessageFromServer.Updates;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.MessageFromServer.MessageFromServer;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class UpdateActiveLeaderCard extends MessageFromServer {
    private String player;
    private int id;

    @Override
    public void activateMessage(Client client) {
        client.getGameView().getPlayer(client.getNickname()).dumpPlayer(player, "LeaderCards");
        client.getGameView().getPlayer(player).getLeaderCards().dumpMessage("Activated a LeaderCard.");

        if(client.getNickname().equals(player)) {
            client.getGameView().getPlayer(player).getLeaderCards().activate(id);
            client.getGameView().getPlayer(player).getLeaderCards().show();
        }
        else{
            ArrayList<Integer> idS = new ArrayList<>();
            idS.add(id);
            client.getGameView().getPlayer(player).getLeaderCards().update(idS);
            client.getGameView().getPlayer(player).getLeaderCards().activate(id);
        }
    }
}
