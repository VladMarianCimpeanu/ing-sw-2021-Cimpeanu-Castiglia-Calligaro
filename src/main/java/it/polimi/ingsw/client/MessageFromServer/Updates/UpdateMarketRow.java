package it.polimi.ingsw.client.MessageFromServer.Updates;

import it.polimi.ingsw.client.MessageFromServer.MessageFromServer;

import java.util.ArrayList;

public class UpdateMarketRow extends MessageFromServer {
    private ArrayList<String> newRow;
    private String newOuterMarble;
    private int row;

    @Override
    public void activateMessage() {
        System.out.println("The market has changed " + row + " row. The new row is \n" + newRow.toString() + "\n The Marble to insert is: " + newOuterMarble);
    }
}
