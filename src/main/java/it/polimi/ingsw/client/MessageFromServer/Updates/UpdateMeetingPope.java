package it.polimi.ingsw.client.MessageFromServer.Updates;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.MessageFromServer.MessageFromServer;

import java.util.ArrayList;
import java.util.Map;


/**
 * Message from server: it is sent when someone reaches a meeting pope position. It contains the points earned by each
 * player during the meeting pope. If someone earns 0 points, it means that he did not join the meeting.
 */
public class UpdateMeetingPope extends MessageFromServer {
    private Map<String, Integer> deltaVictoryPoints;

    @Override
    public void activateMessage(Client client) {
        ArrayList<String> players = new ArrayList<>();
        for(String player: deltaVictoryPoints.keySet())
            if(deltaVictoryPoints.get(player) != 0) {
                players.add(player);
                client.getGameView().getPlayer(player).attendPopeMeeting(deltaVictoryPoints.get(player));
            }
        client.getGameView().dumpMessage("The following players has just joined a meeting with the pope:\n"+players.toString());
    }
}
