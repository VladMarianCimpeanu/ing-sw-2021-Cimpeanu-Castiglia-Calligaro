package it.polimi.ingsw.MessageToClient;

public class FirstTurn {
    private String type;
    private int position;
    private int numberOfResources;

    public FirstTurn(int position, int numberOfResources) {
        type = "FirstTurn";
        this.position = position;
        this.numberOfResources = numberOfResources;
    }
}
