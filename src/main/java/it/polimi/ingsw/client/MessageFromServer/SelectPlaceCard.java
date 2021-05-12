package it.polimi.ingsw.client.MessageFromServer;

public class SelectPlaceCard extends MessageFromServer {

    @Override
    public void activateMessage() {
        System.out.println("Where do you want to place the development card?");
    }
}
