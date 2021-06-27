package it.polimi.ingsw.server.MessageToClient;

import it.polimi.ingsw.server.controller.states.ErrorMessage;

/**
 * Message to client: it notifies the client that an error occurred. It contains a key word representing the error.
 */
public class Error implements MessageToClient{
    private String type;
    private ErrorMessage error;

    public Error(ErrorMessage error){
        this.type = "Error";
        this.error = error;
    }
}
