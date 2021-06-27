package it.polimi.ingsw.client.MessageToServer;

import it.polimi.ingsw.server.controller.Controller;

/**
 * Message to server: it contains the nickname with the client tries to log in.
 */
public class Login implements MessageToServer {
    private String type;
    private String nickname;

    public Login(String nickname) {
        this.type = "Login";
        this.nickname = nickname;
    }
}
