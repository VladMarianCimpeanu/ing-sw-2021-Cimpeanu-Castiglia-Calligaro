package it.polimi.ingsw.MessageToClient;

public class CrashedPlayer implements MessageToClient{
    private String type;
    private String nickname;

    public CrashedPlayer(String nickname) {
        this.type = "CrashedPlayer";
        this.nickname = nickname;
    }
}
