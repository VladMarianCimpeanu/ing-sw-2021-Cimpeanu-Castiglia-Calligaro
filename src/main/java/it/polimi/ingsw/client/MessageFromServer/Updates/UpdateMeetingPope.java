package it.polimi.ingsw.client.MessageFromServer.Updates;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.MessageFromServer.MessageFromServer;

import java.util.ArrayList;
import java.util.Map;

public class UpdateMeetingPope extends MessageFromServer {
    private Map<String, Integer> deltaVictoryPoints;

    @Override
    public void activateMessage(Client client) {
        ArrayList<String> players = new ArrayList<>();
        for(String player: deltaVictoryPoints.keySet())
            if(deltaVictoryPoints.get(player) != 0)
                players.add(player);
        client.getGameView().dumpMessage("The following players has just joined a meeting with the pope:\n"+players.toString());
    }
}
