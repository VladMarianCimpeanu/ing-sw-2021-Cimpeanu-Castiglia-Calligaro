package it.polimi.ingsw.client.MessageFromServer;

public class MarketGrid extends MessageFromServer {
    private String[][] market;
    private String outerMarble;


    @Override
    public void activateMessage() {
        System.out.println("Market:" );
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 4; j++){
                System.out.print(market[i][j] + " ");
            }
            System.out.println("");
        }
        System.out.println("Marble to insert: " + outerMarble);
    }
}
