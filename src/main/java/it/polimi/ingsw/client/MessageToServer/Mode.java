package it.polimi.ingsw.client.MessageToServer;

import it.polimi.ingsw.server.controller.Controller;

/**
 * Message to server: it contains the size of the game to create.
 */
public class Mode implements MessageToServer {
    private String type;
    private int mode;

    public Mode(int mode) {
        this.type = "Mode";
        this.mode = mode;
    }
}
