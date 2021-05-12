package it.polimi.ingsw.client.MessageFromServer;

public class GameStarted extends MessageFromServer {

    @Override
    public void activateMessage() {
        System.out.println("game is started!");
    }
}
