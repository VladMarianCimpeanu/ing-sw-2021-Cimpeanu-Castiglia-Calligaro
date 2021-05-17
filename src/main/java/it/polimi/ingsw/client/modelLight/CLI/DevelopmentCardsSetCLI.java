package it.polimi.ingsw.client.modelLight.CLI;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import it.polimi.ingsw.client.Color;
import it.polimi.ingsw.client.Resource;
import it.polimi.ingsw.client.modelLight.DevelopmentCardSetView;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Map;



public class DevelopmentCardsSetCLI extends DevelopmentCardSetView {

    // FIRST INDEX == LEVEL ORDER(1, 2, 3)
    // SECOND INDEX == COLOR ORDER(GREEN, BLUE, YELLOW, PURPLE)
    private static ArrayList<DevelopmentCardCLI> cards;


    public DevelopmentCardsSetCLI(){
        String srcPath = "src/resources/developmentCard.json";
        BufferedReader reader = null;
        Gson converter = new Gson();
        try {
            reader = new BufferedReader(new FileReader(srcPath));
            Type cardsType = new TypeToken<ArrayList<DevelopmentCardCLI>>(){}.getType();
            cards = converter.fromJson(reader, cardsType);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method set the development cards available to be purchased.
     * @param decks specified decks to be set as new cards available.
     */
    public void setDecks(ArrayList<ArrayList<Integer>> decks) {
        this.decks = decks;
        System.out.println("The following cards can be purchased:");
        show();
    }

    /**
     * @param Id specified development card ID
     * @return DevelopmentCardCLI that has the given ID
     */
    public static DevelopmentCardCLI getCard(int Id){
        return cards.get(Id - 1);
    }

    /**
     * Concatenate a specified number of times the given string
     * @param times number of times that the specified string must be repeated.
     * @param string string to be replicated.
     * @return String.
     */
    public static String stringProduct(int times, String string){
        String product = "";
        for(int i = 0; i < times; i ++){
            product = product + string;
        }
        return product;
    }

    /**
     * Create a string containing all the pairs of the Map.
     * @param contentToPrint specified Map to dump.
     * @return a string with all the pairs contained in the map, with the following format:
     * unicode symbol associated to the Resource + quantity of the resource.
     */
    public static String mapContent(Map<Resource, Integer> contentToPrint) {
        String content = "";
        for(Resource resource: contentToPrint.keySet()){
            content = content + " " + resource.escape() + contentToPrint.get(resource);
        }
        //content = content + RESET;
        return content;
    }


    /**
     * Calculate the size of the keys of the specified map containing escape characters.
     * @param contentToPrint specified map of elements to print
     * @return size of all the escape characters contained in the Map.
     */
    public static int countSizeEscapes(Map<Resource, Integer> contentToPrint) {
        int escapes = 0;
        for(Resource resource: contentToPrint.keySet()){
            escapes += resource.escape().length();
            escapes --;
        }
        return escapes;
    }

    /**
     * Print a line containing strings with escape characters.
     * @param contentToDump specific string that contains escapes characters
     * @param color color used to draw card edge
     * @param numberEscapes specific length taken by the escape characters.
     */
    public static void printLine(String contentToDump, Color color, int numberEscapes){
        int cardWidth = 17;
        String edge = color.escape() + "║" + Color.RESET;
        System.out.print(edge);
        System.out.print(contentToDump + stringProduct(numberEscapes + cardWidth - contentToDump.length(), " ") + edge + "  ");
    }

    /**
     * show the developments decks available to purchase.
      */
    @Override
    public void show() {
        for(int levelSet = 3; levelSet > 0; levelSet --){
            for(Color color: Color.values()){
                System.out.print(color.escape() + "╔═════════════════╗  ");
            }
            System.out.print("\n" + Color.RESET);
            for(Color color: Color.values()){   //PRINT LEVEL
                if(getCard(color, levelSet) == 0) System.out.print(color.escape() + "║" +stringProduct(17, " ") + "║  ");
                else printLine("level " + cards.get(getCard(color, levelSet) - 1).getLevel(), color,0);
            }
            System.out.print("\n");
            for(Color color: Color.values()){   //PRINT VICTORY POINTS
                if(getCard(color, levelSet) == 0) System.out.print(color.escape() + "║" +stringProduct(17, " ") + "║  ");
                else printLine("VP " + cards.get(getCard(color, levelSet) - 1).getVictoryPoints(), color, 0);
            }
            System.out.print("\n");
            for(Color color: Color.values()){  //PRINT COST
                if(getCard(color, levelSet) == 0) System.out.print(color.escape() + "║" +stringProduct(17, " ") + "║  ");
                else {
                    String contentToDump = "cost" + mapContent(cards.get(getCard(color, levelSet) - 1).getCost());
                    int numberEscapes = countSizeEscapes(cards.get(getCard(color, levelSet) - 1).getCost());
                    printLine(contentToDump, color, numberEscapes);
                }
            }
            System.out.print("\n");
            for(Color color: Color.values()){  //PRINT INPUT
                if(getCard(color, levelSet) == 0) System.out.print(color.escape() + "║" +stringProduct(17, " ") + "║  ");
                else {
                    String contentToDump = "in" + mapContent(cards.get(getCard(color, levelSet) - 1).getInput());
                    int numberEscapes = countSizeEscapes(cards.get(getCard(color, levelSet) - 1).getInput());
                    printLine(contentToDump, color, numberEscapes);
                }
            }
            System.out.print("\n");
            for(Color color: Color.values()){ //PRINT OUTPUT
                if(getCard(color, levelSet) == 0) System.out.print(color.escape() + "║" +stringProduct(17, " ") + "║  ");
                else {
                    String contentToDump = "out" + mapContent(cards.get(getCard(color, levelSet) - 1).getOutput());
                    int numberEscapes = countSizeEscapes(cards.get(getCard(color, levelSet) - 1).getOutput());
                    printLine(contentToDump, color, numberEscapes);
                }
            }
            System.out.print("\n");
            for(Color color: Color.values()){
                System.out.print(color.escape() + "╚═════════════════╝  ");
            }
            System.out.print("\n" + Color.RESET);
        }
    }


    //TODO: delete this when all the other CLI components are done: this main is used only to test the development
    // card set rendering.
    public static void main(String[] args) {
        DevelopmentCardsSetCLI myCards = new DevelopmentCardsSetCLI();
        ArrayList<ArrayList<Integer>> availableDevelopmentCards;
        availableDevelopmentCards = new ArrayList<>();
        for (int level = 0; level < 3; level++) {
            availableDevelopmentCards.add(new ArrayList<>());
            for(Color color: Color.values())
                availableDevelopmentCards.get(level).add(0);
        }
        ArrayList<DevelopmentCardCLI> tempDeck = myCards.cards;
        for (DevelopmentCardCLI card : tempDeck) {
            availableDevelopmentCards.get(card.getLevel() - 1).set(card.getColor().getIndex(), card.getID());
        }
        availableDevelopmentCards.get(2).set(Color.BLUE.getIndex(), 0);
        myCards.setDecks(availableDevelopmentCards);
        myCards.show();
    }
}
