package it.polimi.ingsw.server.MessageToClient;

public class Error implements MessageToClient{
    private String type;
    private String content;

    public Error(String content){
        this.type = "Error";
        this.content = content;
    }
}
