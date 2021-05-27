package it.polimi.ingsw.server.MessageToClient;

public class FirstTurnEnded implements MessageToClient{
    private String type;

    public FirstTurnEnded(){
        type = "FirstTurnEnded";
    }
}
