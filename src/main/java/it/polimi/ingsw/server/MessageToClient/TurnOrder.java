package it.polimi.ingsw.server.MessageToClient;

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
