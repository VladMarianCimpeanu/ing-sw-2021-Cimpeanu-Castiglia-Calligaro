package it.polimi.ingsw.server.MessageToClient;

/**
 * Message to client: it contains a matrix with all the marbles in the market and which marble is out.
 */
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
