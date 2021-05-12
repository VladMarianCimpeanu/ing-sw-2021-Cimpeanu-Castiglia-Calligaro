package it.polimi.ingsw.server.MessageToClient;

public class NicknameAccepted implements MessageToClient{
    private String type;
    private String nickname;

    public NicknameAccepted(String nickname){
        this.type = "NicknameAccepted";
        this.nickname = nickname;
    }
}
