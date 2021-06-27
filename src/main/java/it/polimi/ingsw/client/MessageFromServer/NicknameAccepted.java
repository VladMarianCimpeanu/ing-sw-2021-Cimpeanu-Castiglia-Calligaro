package it.polimi.ingsw.client.MessageFromServer;

import it.polimi.ingsw.client.Client;

/**
 * Message from server: client that receives this message is notified that the nickname selected by him, has been accepted
 * during the login phase.
 */
public class NicknameAccepted extends MessageFromServer{
    private String nickname;

    @Override
    public void activateMessage(Client client) {
        client.getGameView().dumpMessage("Nickname accepted");
    }

    public String getNickname() {
        return nickname;
    }
}
