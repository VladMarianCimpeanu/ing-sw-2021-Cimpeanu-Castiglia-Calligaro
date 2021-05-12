package it.polimi.ingsw.client.MessageFromServer;

public class ModeRequest extends MessageFromServer {

    @Override
    public void activateMessage() {
        System.out.println("choose number of players");
    }
}
