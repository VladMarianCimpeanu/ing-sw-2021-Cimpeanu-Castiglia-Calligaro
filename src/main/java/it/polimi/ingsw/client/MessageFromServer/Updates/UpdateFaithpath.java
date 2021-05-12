package it.polimi.ingsw.client.MessageFromServer.Updates;

import it.polimi.ingsw.client.MessageFromServer.MessageFromServer;

import java.util.Map;

public class UpdateFaithpath extends MessageFromServer {
    private Map<String, Integer> newPositions;

    @Override
    public void activateMessage() {
        for(String player : newPositions.keySet()) {
            System.out.println(player + " moved to faith path position " + newPositions.get(player));
        }
     }
}
