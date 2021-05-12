package it.polimi.ingsw.client.MessageFromServer;

public class FirstTurn extends MessageFromServer {
    private int position;
    private int numberOfResources;


    @Override
    public void activateMessage() {
        System.out.println("you are in position: " + position + "\n chose " + numberOfResources +  "resources");
    }
}
