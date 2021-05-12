package it.polimi.ingsw.client.MessageToServer;

import it.polimi.ingsw.server.controller.Controller;

public class Mode implements MessageToServer {
    private String type;
    private int mode;

    public Mode(int mode) {
        this.type = "Mode";
        this.mode = mode;
    }
}
