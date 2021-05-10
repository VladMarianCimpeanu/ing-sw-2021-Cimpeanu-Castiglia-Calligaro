package it.polimi.ingsw.MessageToClient;

public class Error {
    private String type;
    private String content;

    public Error(String type, String content){
        this.type = type;
        this.content = content;
    }
}
