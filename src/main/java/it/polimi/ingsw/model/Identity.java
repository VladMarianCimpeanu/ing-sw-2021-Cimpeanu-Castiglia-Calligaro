package it.polimi.ingsw.model;

public class Identity {
    private String Nickname;
    private boolean online;

    public Identity(String nickname){

    }

    public String getNickname() {
        return Nickname;
    }

    public boolean isOnline() {
        return online;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }
}
