package it.polimi.ingsw.client.MessageFromServer;

public class ModeRequest implements MessageToClient {
    private String type;

    public ModeRequest() {
        type = "ModeRequest";
    }
}
