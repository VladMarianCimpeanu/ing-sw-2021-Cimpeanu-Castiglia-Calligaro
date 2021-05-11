package it.polimi.ingsw.MessageToClient;

import it.polimi.ingsw.model.market.Marble;

public class MarketGrid implements MessageToClient {
    private String type;
    private String[][] market;
    private String outerMarble;

    public MarketGrid(String[][] market, String outerMarble) {
        this.type = "MarketGrid";
        this.market = market;
        this.outerMarble = outerMarble;
    }
}
