package it.polimi.ingsw.client.modelLight.CLI;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import it.polimi.ingsw.client.Color;
import it.polimi.ingsw.client.Resource;
import it.polimi.ingsw.client.modelLight.DevelopmentCardSetView;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Map;



public class DevelopmentCardsSetCLI extends DevelopmentCardSetView {

    // FIRST INDEX == LEVEL ORDER(1, 2, 3)
    // SECOND INDEX == COLOR ORDER(GREEN, BLUE, YELLOW, PURPLE)
    private static ArrayList<DevelopmentCardCLI> cards;


    public DevelopmentCardsSetCLI(){
        String srcPath = "/developmentCard.json";
        Reader reader = new InputStreamReader(this.getClass().getResourceAsStream(srcPath), StandardCharsets.UTF_8);
        Gson converter = new Gson();
        Type cardsType = new TypeToken<ArrayList<DevelopmentCardCLI>>(){}.getType();
        cards = converter.fromJson(reader, cardsType);
    }

    /**
     * This method set the development cards available to be purchased.
     * @param decks specified decks to be set as new cards available.
     */
    public void setDecks(ArrayList<ArrayList<Integer>> decks) {
        this.decks = decks;
        show();
    }

    /**
     * updates the cards available replacing the current card in the specified place with the new one.
     * @param color color of the new card to place.
     * @param level level of the new card to place.
     * @param ID id of the new card to place.
     */
    @Override
    public void updateSet(Color color, int level, int ID){
        System.out.println("A " + color + " development card of level " + level + " has just drown.");
        if (ID == 0) System.out.println("There are no more cards.");
        decks.get(level - 1).set(color.getIndex(), ID);
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
     * print an empty line of the standard length of a card with colored edges
     * @param color specified color of the edges of the card.
     */
    public static void printEmptyLine(Color color){
        System.out.print(color.escape() + "║" +stringProduct(17, " ") + "║  ");
    }

    /**
     * show the developments decks available to purchase.
      */
    @Override
    public void show() {
        System.out.println("You can purchase the following cards:");
        for(int levelSet = 3; levelSet > 0; levelSet --){
            for(Color color: Color.values()){
                System.out.print(color.escape() + "╔═════════════════╗  ");
            }
            System.out.print("\n" + Color.RESET);
            for(Color color: Color.values()){   //PRINT LEVEL
                if(getCard(color, levelSet) == 0) printEmptyLine(color);
                else printLine("level " + cards.get(getCard(color, levelSet) - 1).getLevel(), color,0);
            }
            System.out.print("\n");
            for(Color color: Color.values()){   //PRINT VICTORY POINTS
                if(getCard(color, levelSet) == 0) printEmptyLine(color);
                else printLine("VP " + cards.get(getCard(color, levelSet) - 1).getVictoryPoints(), color, 0);
            }
            System.out.print("\n");
            for(Color color: Color.values()){  //PRINT COST
                if(getCard(color, levelSet) == 0) printEmptyLine(color);
                else {
                    String contentToDump = "cost" + mapContent(cards.get(getCard(color, levelSet) - 1).getCost());
                    int numberEscapes = countSizeEscapes(cards.get(getCard(color, levelSet) - 1).getCost());
                    printLine(contentToDump, color, numberEscapes);
                }
            }
            System.out.print("\n");
            for(Color color: Color.values()){  //PRINT INPUT
                if(getCard(color, levelSet) == 0) printEmptyLine(color);
                else {
                    String contentToDump = "in" + mapContent(cards.get(getCard(color, levelSet) - 1).getInput());
                    int numberEscapes = countSizeEscapes(cards.get(getCard(color, levelSet) - 1).getInput());
                    printLine(contentToDump, color, numberEscapes);
                }
            }
            System.out.print("\n");
            for(Color color: Color.values()){ //PRINT OUTPUT
                if(getCard(color, levelSet) == 0) printEmptyLine(color);
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
}
