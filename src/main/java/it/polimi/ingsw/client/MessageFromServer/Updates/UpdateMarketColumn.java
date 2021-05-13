package it.polimi.ingsw.client.MessageFromServer.Updates;

import it.polimi.ingsw.client.MessageFromServer.MessageFromServer;

import java.util.ArrayList;

public class UpdateMarketColumn extends MessageFromServer {
    private ArrayList<String> newColumn;
    private String newOuterMarble;
    private int col;

    @Override
    public void activateMessage() {
        System.out.println("The market has changed  column " + col + ". The new column is \n" + newColumn.toString() + "\n The Marble to insert is: " + newOuterMarble);
    }
}
