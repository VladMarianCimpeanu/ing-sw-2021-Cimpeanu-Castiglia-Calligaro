package it.polimi.ingsw.server.MessageToClient;

/**
 * Message to client: it notifies that the first turn has just ended.
 */
public class FirstTurnEnded implements MessageToClient{
    private String type;

    public FirstTurnEnded(){
        type = "FirstTurnEnded";
    }
}
