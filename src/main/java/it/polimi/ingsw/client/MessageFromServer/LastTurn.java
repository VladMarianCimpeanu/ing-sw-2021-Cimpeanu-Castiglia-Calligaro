package it.polimi.ingsw.client.MessageFromServer;

public class LastTurn extends MessageFromServer {
    private String player;
    private String cause;

    @Override
    public void activateMessage() {
        System.out.println("Last turn! " + player + cause);
    }
}
