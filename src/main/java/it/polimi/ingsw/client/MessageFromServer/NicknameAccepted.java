package it.polimi.ingsw.client.MessageFromServer;

import it.polimi.ingsw.client.Client;

public class NicknameAccepted extends MessageFromServer{
    private String nickname;

    @Override
    public void activateMessage(Client client) {
        System.out.println("nickname accepted");
    }

    public String getNickname() {
        return nickname;
    }
}
