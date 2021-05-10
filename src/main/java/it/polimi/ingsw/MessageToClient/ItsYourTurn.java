package it.polimi.ingsw.MessageToClient;


public class ItsYourTurn {
    private String type;
    private String player;

    public ItsYourTurn(String player) {
        type = "ItsYourTurn";
        this.player = player;
    }
}
