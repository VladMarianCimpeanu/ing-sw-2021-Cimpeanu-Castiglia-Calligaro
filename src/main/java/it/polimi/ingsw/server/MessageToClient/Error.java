package it.polimi.ingsw.server.MessageToClient;

import it.polimi.ingsw.server.controller.states.ErrorMessage;

public class Error implements MessageToClient{
    private String type;
    private ErrorMessage error;

    public Error(ErrorMessage error){
        this.type = "Error";
        this.error = error;
    }
}
