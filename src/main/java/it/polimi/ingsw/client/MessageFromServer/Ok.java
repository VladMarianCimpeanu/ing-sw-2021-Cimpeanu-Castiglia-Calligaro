package it.polimi.ingsw.client.MessageFromServer;

public class Ok implements MessageToClient {
    private String type;
    private String message;

    public Ok(String message){
        this.type = "Ok";
        this.message = message;
    }
}
