package it.polimi.ingsw.client.MessageToServer;

public class CheatFaith implements MessageToServer{
    private String type;
    private int steps;

    public CheatFaith(int steps) {
        this.type = "CheatFaith";
        this.steps = steps;
    }
}
