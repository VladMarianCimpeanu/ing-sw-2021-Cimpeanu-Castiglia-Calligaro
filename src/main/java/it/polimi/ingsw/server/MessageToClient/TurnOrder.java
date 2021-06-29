package it.polimi.ingsw.server.MessageToClient;

/**
 * Message to client: notify the client how many resources can redeem at beginning of the game.
 */
public class TurnOrder implements MessageToClient{
    private String type;
    private int position;
    private int numberOfResources;

    public TurnOrder(int position, int numberOfResources) {
        type = "TurnOrder";
        this.position = position;
        this.numberOfResources = numberOfResources;
    }
}
