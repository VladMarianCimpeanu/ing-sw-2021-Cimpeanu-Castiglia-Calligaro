package it.polimi.ingsw.server.MessageToClient;

public class CrashedPlayer implements MessageToClient{
    private String type;
    private String nickname;

    public CrashedPlayer(String nickname) {
        this.type = "CrashedPlayer";
        this.nickname = nickname;
    }
}
