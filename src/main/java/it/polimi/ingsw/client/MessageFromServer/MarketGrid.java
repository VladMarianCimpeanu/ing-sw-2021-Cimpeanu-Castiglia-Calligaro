package it.polimi.ingsw.client.MessageFromServer;


import it.polimi.ingsw.client.Marble;


public class MarketGrid extends MessageFromServer {
    private String[][] market;
    private String outerMarble;

    @Override
    public void activateMessage() {
        String marketRow = "+═══+════+═══+═══+";
        System.out.println("Market:" );
        for(int i = 0; i < 3; i++){
            System.out.println(marketRow);
            for(int j = 0; j < 4; j++){
                System.out.print("║ " + Marble.valueOf(market[i][j]).escape() + "◉ " + "\u001B[0m");
            }
            System.out.println("║");
        }
        System.out.println(marketRow);
        System.out.println("Marble to insert: " +Marble.valueOf(outerMarble).escape() + "◉" + "\u001B[0m");
    }
}
