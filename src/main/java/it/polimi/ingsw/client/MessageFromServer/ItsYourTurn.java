package it.polimi.ingsw.client.MessageFromServer;


public class ItsYourTurn implements MessageToClient {
    private String type;
    private String player;

    public ItsYourTurn(String player) {
        type = "ItsYourTurn";
        this.player = player;
    }
}
