package it.polimi.ingsw.server.MessageToClient;

/**
 * Message to client: client that receives this message is notified that the nickname selected by him, has been accepted
 * during the login phase.
 */
public class NicknameAccepted implements MessageToClient{
    private String type;
    private String nickname;

    public NicknameAccepted(String nickname){
        this.type = "NicknameAccepted";
        this.nickname = nickname;
    }
}
