package it.polimi.ingsw.server.MessageToClient.Rejoin;

import it.polimi.ingsw.server.MessageToClient.MessageToClient;

import java.util.ArrayList;
import java.util.Map;

public class RejoinLeaderCards implements MessageToClient {
    private String type;
    private String nickname;
    private Map<Integer, Boolean> active;

    public RejoinLeaderCards(String nickname, Map<Integer, Boolean> active) {
        this.type = "RejoinLeaderCards";
        this.nickname = nickname;
        this.active = active;
    }
}
