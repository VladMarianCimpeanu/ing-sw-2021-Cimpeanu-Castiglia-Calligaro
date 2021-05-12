package it.polimi.ingsw.client.MessageFromServer;

public class SelectResourceOut extends MessageFromServer {
    @Override
    public void activateMessage() {
        System.out.println("Which resource do you want to craft?");
    }
}
