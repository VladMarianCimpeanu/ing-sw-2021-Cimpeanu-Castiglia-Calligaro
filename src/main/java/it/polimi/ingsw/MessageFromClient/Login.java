package it.polimi.ingsw.MessageFromClient;

import it.polimi.ingsw.controller.Controller;

public class Login extends MessageFromClient {
    private String nickname;

    @Override
    public void activate(Controller controller) {

    }

    public String getNickname() {
        return nickname;
    }
}
