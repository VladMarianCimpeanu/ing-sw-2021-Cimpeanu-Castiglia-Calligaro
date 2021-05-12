package it.polimi.ingsw.client.MessageFromServer;

public class Ok extends MessageFromServer {
    private String message;

    @Override
    public void activateMessage() {
        System.out.println("ok: " + message);
    }
}
