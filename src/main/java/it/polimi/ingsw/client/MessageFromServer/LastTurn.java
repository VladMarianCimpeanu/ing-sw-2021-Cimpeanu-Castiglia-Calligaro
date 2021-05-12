package it.polimi.ingsw.client.MessageFromServer;

public class LastTurn implements MessageToClient {
    private String type;
    private String player;
    private String cause;

    public LastTurn(String player, String cause) {
        type = "LastTurn";
        this.player = player;
        this.cause = cause;
    }
}
