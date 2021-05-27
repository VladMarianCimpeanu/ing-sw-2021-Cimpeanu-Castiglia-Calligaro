package it.polimi.ingsw.server.MessageToClient;

import java.util.ArrayList;

public class OkRoom implements MessageToClient {
    private String type;
    private String message;
    private int size;
    private ArrayList<String> players;

    public OkRoom(String message, int size, ArrayList<String> players){
        this.type = "OkRoom";
        this.message = message;
        this.size = size;
        this.players = players;
    }
}
