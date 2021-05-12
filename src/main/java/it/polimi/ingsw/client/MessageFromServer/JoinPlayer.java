package it.polimi.ingsw.client.MessageFromServer;

public class JoinPlayer extends MessageFromServer {
    private String player;

    @Override
    public void activateMessage() {
        System.out.println(player + "joined the game");
    }
}
