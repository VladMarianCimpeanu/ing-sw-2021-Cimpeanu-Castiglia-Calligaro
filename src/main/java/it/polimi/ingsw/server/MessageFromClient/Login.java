package it.polimi.ingsw.server.MessageFromClient;

import it.polimi.ingsw.server.controller.Controller;

/**
 * Message from client: it contains the nickname with the client tries to log in.
 */
public class Login extends MessageFromClient {
    private String nickname;

    @Override
    public void activate(Controller controller) {

    }

    public String getNickname() {
        return nickname;
    }
}
