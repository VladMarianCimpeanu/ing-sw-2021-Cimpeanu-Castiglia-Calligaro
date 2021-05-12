package it.polimi.ingsw.client.MessageFromServer;

public class Ping extends MessageFromServer {

    @Override
    public void activateMessage() {
        System.out.println("ping from server.");
    }
}
