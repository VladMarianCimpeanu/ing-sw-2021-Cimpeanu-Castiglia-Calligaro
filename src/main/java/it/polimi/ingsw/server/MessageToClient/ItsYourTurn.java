package it.polimi.ingsw.server.MessageToClient;


public class ItsYourTurn implements MessageToClient{
    private String type;
    private String player;

    public ItsYourTurn(String player) {
        type = "ItsYourTurn";
        this.player = player;
    }
}