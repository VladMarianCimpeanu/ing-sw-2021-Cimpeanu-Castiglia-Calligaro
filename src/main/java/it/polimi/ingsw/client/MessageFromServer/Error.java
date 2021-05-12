package it.polimi.ingsw.client.MessageFromServer;

public class Error extends MessageFromServer {
    private String content;


    @Override
    public void activateMessage() {
        System.out.println("error :" + content);
    }
}
