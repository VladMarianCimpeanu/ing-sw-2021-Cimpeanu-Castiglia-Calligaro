package it.polimi.ingsw.server.MessageToClient;

public class ModeRequest implements MessageToClient{
    private String type;

    public ModeRequest() {
        type = "ModeRequest";
    }
}
