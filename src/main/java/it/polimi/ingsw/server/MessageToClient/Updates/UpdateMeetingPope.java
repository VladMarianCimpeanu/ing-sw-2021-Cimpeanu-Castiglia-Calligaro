package it.polimi.ingsw.server.MessageToClient.Updates;

import it.polimi.ingsw.server.MessageToClient.MessageToClient;

import java.util.Map;

/**
 * Message to client: it is sent when someone reaches a meeting pope position. It contains the points earned by each
 * player during the meeting pope. If someone earns 0 points, it means that he did not join the meeting.
 */
public class UpdateMeetingPope implements MessageToClient {
    private final String type;
    private Map<String, Integer> deltaVictoryPoints;

    public UpdateMeetingPope(Map<String, Integer> deltaVictoryPoints) {
        type = "UpdateMeetingPope";
        this.deltaVictoryPoints = deltaVictoryPoints;
    }
}
