package it.polimi.ingsw.model;

public class Identity {
    private String nickname;
    private boolean online;

    public Identity(String nickname){
        this.nickname = nickname;
        online = true;
    }

    public String getNickname() {
        return nickname;
    }

    public boolean isOnline() {
        return online;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }
}
