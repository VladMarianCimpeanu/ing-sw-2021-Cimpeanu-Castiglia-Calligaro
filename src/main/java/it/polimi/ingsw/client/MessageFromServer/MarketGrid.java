package it.polimi.ingsw.client.MessageFromServer;

public class MarketGrid extends MessageFromServer {
    private String[][] market;
    private String outerMarble;


    @Override
    public void activateMessage() {
        System.out.println("Updated market: \n" + market.toString() + "\nMarble to insert: " + outerMarble);
    }
}
