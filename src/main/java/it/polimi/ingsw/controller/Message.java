package it.polimi.ingsw.controller;

import java.util.ArrayList;

@Deprecated
public class Message {
    private String command;
    private ArrayList<String> params;

    public Message(String command, ArrayList<String> params){
        this.command = command;
        this.params = params;
    }

    public String getCommand() {
        return command;
    }

    public ArrayList<String> getParams() {
        return params;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public void setParams(ArrayList<String> params) {
        this.params = params;
    }

    @Override
    public String toString() {
        return "command='" + command + '\'' +
                ", params=" + params;
    }
}