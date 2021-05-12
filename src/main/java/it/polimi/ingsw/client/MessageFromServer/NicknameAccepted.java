package it.polimi.ingsw.client.MessageFromServer;

public class NicknameAccepted extends MessageFromServer{
    private String nickname;

    @Override
    public void activateMessage() {
        System.out.println("nickname accepted");
    }

    public String getNickname() {
        return nickname;
    }
}
