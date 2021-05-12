package it.polimi.ingsw.client.MessageFromServer.Updates;

import it.polimi.ingsw.client.MessageFromServer.MessageToClient;

import java.util.Map;

public class UpdateMeetingPope implements MessageToClient {
    private final String type;
    private Map<String, Integer> deltaVictoryPoints;

    public UpdateMeetingPope(Map<String, Integer> deltaVictoryPoints) {
        type = "UpdateMeetingPope";
        this.deltaVictoryPoints = deltaVictoryPoints;
    }
}
