package it.polimi.ingsw.client.MessageFromServer;

public class CrashedPlayer implements MessageToClient {
    private String type;
    private String nickname;

    public CrashedPlayer(String nickname) {
        this.type = "CrashedPlayer";
        this.nickname = nickname;
    }
}
