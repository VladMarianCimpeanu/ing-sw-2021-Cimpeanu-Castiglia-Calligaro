package it.polimi.ingsw.server.MessageFromClient;

import it.polimi.ingsw.server.controller.Controller;

public class Login extends MessageFromClient {
    private String nickname;

    @Override
    public void activate(Controller controller) {

    }

    public String getNickname() {
        return nickname;
    }
}
