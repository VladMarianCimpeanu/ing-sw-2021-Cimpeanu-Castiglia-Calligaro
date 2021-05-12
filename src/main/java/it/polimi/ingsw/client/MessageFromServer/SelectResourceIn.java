package it.polimi.ingsw.client.MessageFromServer;

public class SelectResourceIn extends MessageFromServer {
    @Override
    public void activateMessage() {
        System.out.println("Which resources do you want to you use for base crafting?");
    }
}
