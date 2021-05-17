package it.polimi.ingsw.client.MessageFromServer;


import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.Marble;


public class MarketGrid extends MessageFromServer {
    private String[][] market;
    private String outerMarble;

    @Override
    public void activateMessage(Client client) {
        Marble[][] marketGrid = new Marble[3][4];
        for(int i = 0; i<3; i++)
            for(int j = 0; j<4; j++)
                marketGrid[i][j] = Marble.valueOf(market[i][j]);
        client.getGameView().getMarket().setUp(marketGrid, Marble.valueOf(outerMarble));
        client.getGameView().getMarket().show();
    }
}
