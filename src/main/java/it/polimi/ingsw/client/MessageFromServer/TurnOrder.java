package it.polimi.ingsw.client.MessageFromServer;

public class TurnOrder extends MessageFromServer {
    private int position;
    private int numberOfResources;


    @Override
    public void activateMessage() {
        System.out.println("you are in position: " + position + "\nchose " + numberOfResources +  " resources");
    }
}
