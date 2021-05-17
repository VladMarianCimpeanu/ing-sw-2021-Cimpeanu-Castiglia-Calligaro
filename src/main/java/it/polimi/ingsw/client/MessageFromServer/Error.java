package it.polimi.ingsw.client.MessageFromServer;

import it.polimi.ingsw.client.Client;

public class Error extends MessageFromServer {
    private String content;


    @Override
    public void activateMessage(Client client) {
        System.out.println("error :" + content);
    }
}
