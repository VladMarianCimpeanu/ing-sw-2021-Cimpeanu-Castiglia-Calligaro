package it.polimi.ingsw.MessageToClient;

public class ModeRequest implements MessageToClient{
    private String type;

    public ModeRequest() {
        type = "ModeRequest";
    }
}
