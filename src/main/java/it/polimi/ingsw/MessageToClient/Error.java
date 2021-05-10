package it.polimi.ingsw.MessageToClient;

public class Error {
    private String type;
    private String content;

    public Error(String content){
        this.type = "Error";
        this.content = content;
    }
}
