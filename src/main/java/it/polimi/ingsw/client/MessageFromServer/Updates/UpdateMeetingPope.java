package it.polimi.ingsw.client.MessageFromServer.Updates;

import it.polimi.ingsw.client.MessageFromServer.MessageFromServer;

import java.util.Map;

public class UpdateMeetingPope extends MessageFromServer {
    private Map<String, Integer> deltaVictoryPoints;

    @Override
    public void activateMessage() {
        System.out.println("The following players has just joined a meeting with the pope:");
        for (String nickname : deltaVictoryPoints.keySet()) {
            System.out.println("\n" + nickname);
        }
    }
}