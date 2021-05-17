package it.polimi.ingsw.client.modelLight.CLI;

import it.polimi.ingsw.client.Color;
import it.polimi.ingsw.client.modelLight.DevelopmentCardDecksView;

import static it.polimi.ingsw.client.modelLight.CLI.DevelopmentCardsSetCLI.*;

public final class DevelopmentCardsDecksCLI extends DevelopmentCardDecksView {

    /**
     * given a development cards it returns its color.
     * @param cardID ID of the specified development card: note that this method do not verify if the ID is 0
     * (if ID is 0 it will be thrown a runtime Exception OutOfBoundException)
     * @return return the color of the specified card.
     */
    private Color chooseColor(int cardID){
        return DevelopmentCardsSetCLI.getCard(cardID).getColor();
    }

    /**
     * it prints an empty line with length 17 between two white edges followed by two spaces.
     */
    private void printEmptyLine(){
        System.out.print(Color.RESET + "║" +stringProduct(17, " ") + "║  ");
    }

    /**
     * show the development cards owned by the player.
     */
    @Override
    public void show() {
        System.out.println("You have the following development cards:");
        String color;
        for(int card: playerCards){
            color = card == 0 ? Color.RESET : chooseColor(card).escape();
            System.out.print(color + "╔═════════════════╗  ");
        }
        System.out.print("\n" + Color.RESET);
        for(int card: playerCards){   //PRINT LEVEL
            if(card == 0) printEmptyLine();
            else printLine("level " + DevelopmentCardsSetCLI.getCard(card).getLevel(), chooseColor(card),0);
        }
        System.out.print("\n");
        for(int card: playerCards){   //PRINT VICTORY POINTS
            if(card == 0) printEmptyLine();
            else printLine("VP " + DevelopmentCardsSetCLI.getCard(card).getVictoryPoints(), chooseColor(card),0);
        }
        System.out.print("\n");
        for(int card: playerCards){  //PRINT COST
            if(card == 0) printEmptyLine();
            else {
                String contentToDump = "cost" + mapContent(DevelopmentCardsSetCLI.getCard(card).getCost());
                int numberEscapes = countSizeEscapes(DevelopmentCardsSetCLI.getCard(card).getCost());
                printLine(contentToDump, chooseColor(card), numberEscapes);
            }
        }
        System.out.print("\n");
        for(int card: playerCards){  //PRINT INPUT
            if(card == 0) printEmptyLine();
            else {
                String contentToDump = "in" + mapContent(DevelopmentCardsSetCLI.getCard(card).getInput());
                int numberEscapes = countSizeEscapes(DevelopmentCardsSetCLI.getCard(card).getInput());
                printLine(contentToDump, chooseColor(card), numberEscapes);
            }
        }
        System.out.print("\n");
        for(int card: playerCards){ //PRINT OUTPUT
            if(card == 0) printEmptyLine();
            else {
                String contentToDump = "out" + mapContent(DevelopmentCardsSetCLI.getCard(card).getOutput());
                int numberEscapes = countSizeEscapes(DevelopmentCardsSetCLI.getCard(card).getOutput());
                printLine(contentToDump, chooseColor(card), numberEscapes);
            }
        }
        System.out.print("\n");
        for(int card: playerCards){
            color = card == 0 ? Color.RESET : chooseColor(card).escape();
            System.out.print(color + "╚═════════════════╝  ");
        }
        System.out.print("\n" + Color.RESET);
        System.out.println("DECKS :" +
                "(1)" + stringProduct(19 ," ") + "(2)" + stringProduct(17 ," ") + "(3)" );
    }

    //TODO: delete this when all the other CLI components are done: this main is used only to test the development
    // card deck rendering.
    public static void main(String[] args) {
        DevelopmentCardsSetCLI set = new DevelopmentCardsSetCLI();
        DevelopmentCardsDecksCLI decks = new DevelopmentCardsDecksCLI();
        decks.addCard(1,1);
        decks.addCard(3, 48);
        decks.addCard(2, 18);
        decks.show();
    }
}
