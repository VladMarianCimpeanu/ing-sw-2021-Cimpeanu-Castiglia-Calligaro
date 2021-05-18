package it.polimi.ingsw.client.modelLight.CLI;

import it.polimi.ingsw.client.Marble;
import it.polimi.ingsw.client.modelLight.MarketView;

public class MarketCLI extends MarketView {

    @Override
    public void show() {
        String marketRow = "╠═══╬═══╬═══╬═══╣";

        System.out.println("Market:" );
        System.out.println("╔═══╦═══╦═══╦═══╗");
        for(int i = 0; i < 3; i++){
            if(i != 0)
                System.out.println(marketRow);
            for(int j = 0; j < 4; j++){
                System.out.print("║ " + marketGrid[i][j].escape() + "\u001B[0m" + " ");
            }
            System.out.println("║");
        }
        //System.out.println(marketRow);
        System.out.println("╚═══╩═══╩═══╩═══╝");
        System.out.println("Marble to insert: " +outerMarble.escape() + "\u001B[0m");
    }

    @Override
    public void showColUpdate() {
        //TODO
    }

    @Override
    public void showRowUpdate() {
        //TODO
    }
}
