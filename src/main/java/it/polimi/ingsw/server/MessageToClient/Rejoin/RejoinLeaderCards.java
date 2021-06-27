package it.polimi.ingsw.server.MessageToClient.Rejoin;

import it.polimi.ingsw.server.MessageToClient.MessageToClient;

import java.util.ArrayList;
import java.util.Map;

/**
 * Message containing all the leader cards owned by a specified player in his depot.
 * If the player is not the same that is rejoining, he will receive only the already activated cards.
 * This message is sent to the client when the player tries to join an already started game.
 */

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
