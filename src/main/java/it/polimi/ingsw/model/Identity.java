package it.polimi.ingsw.model;

/**
 * Identity is created when a player join the waiting room.
 * Contains the nickname of the player and a boolean to represent if the player is online.
 */
public class Identity {
    private String nickname;
    private boolean online;

    public Identity(String nickname) {
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
