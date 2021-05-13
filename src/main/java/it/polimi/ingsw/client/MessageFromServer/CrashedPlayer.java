package it.polimi.ingsw.client.MessageFromServer;

public class CrashedPlayer extends MessageFromServer {
    private String nickname;

    @Override
    public void activateMessage() {
        System.out.println(nickname + " crashed");
    }
}
