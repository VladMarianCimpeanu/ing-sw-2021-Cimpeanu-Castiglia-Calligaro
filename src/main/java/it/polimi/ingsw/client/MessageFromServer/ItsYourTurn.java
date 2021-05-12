package it.polimi.ingsw.client.MessageFromServer;


public class ItsYourTurn extends MessageFromServer {
    private String player;

    @Override
    public void activateMessage() {
        System.out.println("It's " + player + "'s turn!");
    }
}
