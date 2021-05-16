package it.polimi.ingsw.client;

import it.polimi.ingsw.server.MultiEchoServer;
import it.polimi.ingsw.server.model.Color;
import it.polimi.ingsw.server.model.benefit.Resource;
import it.polimi.ingsw.server.model.market.Yellow;

import java.sql.PreparedStatement;
import java.util.HashMap;
import java.util.Map;

import static it.polimi.ingsw.client.ColorANSI.*;

public class Welcome {
    private static String title1 = "█▀▄▀█ ██      ▄▄▄▄▄      ▄▄▄▄▀ ▄███▄   █▄▄▄▄   ▄▄▄▄▄       ████▄ ▄████      █▄▄▄▄ ▄███▄      ▄   ██   ▄█    ▄▄▄▄▄    ▄▄▄▄▄   ██      ▄   ▄█▄    ▄███▄   ";
    private static String title2 = "█ █ █ █ █    █     ▀▄ ▀▀▀ █    █▀   ▀  █  ▄▀  █     ▀▄     █   █ █▀   ▀     █  ▄▀ █▀   ▀      █  █ █  ██   █     ▀▄ █     ▀▄ █ █      █  █▀ ▀▄  █▀   ▀  ";
    private static String title3 = "█ ▄ █ █▄▄█ ▄  ▀▀▀▀▄       █    ██▄▄    █▀▀▌ ▄  ▀▀▀▀▄       █   █ █▀▀        █▀▀▌  ██▄▄    ██   █ █▄▄█ ██ ▄  ▀▀▀▀▄ ▄  ▀▀▀▀▄   █▄▄█ ██   █ █   ▀  ██▄▄    ";
    private static String title4 = "█   █ █  █  ▀▄▄▄▄▀       █     █▄   ▄▀ █  █  ▀▄▄▄▄▀        ▀████ █          █  █  █▄   ▄▀ █ █  █ █  █ ▐█  ▀▄▄▄▄▀   ▀▄▄▄▄▀    █  █ █ █  █ █▄  ▄▀ █▄   ▄▀ ";
    private static String title5 = "   █     █              ▀      ▀███▀     █                        █           █   ▀███▀   █  █ █    █  ▐                        █ █  █ █ ▀███▀  ▀███▀   ";
    private static String title6 = "  ▀     █                               ▀                          ▀         ▀            █   ██   █                           █  █   ██                ";
    private static String title7 = "       ▀                                                                                          ▀                           ▀           ";

    public static void dump() {
        System.out.println(BLUE.escape() + title1);
        System.out.println(title2);
        System.out.println(title3);
        System.out.println(title4);
        System.out.println(title5);
        System.out.println(title6);
        System.out.println(title7 + RESET);
    }

    public static String stringProduct(int times, String string){
        String product = "";
        for(int i = 0; i < times; i ++){
            product = product + string;
        }
        return product;
    }

    public static String mapContent(Map<ColorANSI, Integer> contentToPrint) {
        String content = "";
        for(ColorANSI color: contentToPrint.keySet()){
            content = content + color.escape() + " ■" + contentToPrint.get(color);
        }
        content = content + RESET;
        return content;
    }

    public static int countSizeEscapes(Map<ColorANSI, Integer> contentToPrint) {
        int sizeEscape = "\u001B[31m".length();
        int escapes = 1;
        for(ColorANSI color: contentToPrint.keySet()) escapes ++;
        return escapes * sizeEscape;
    }

    public static void main(String[] args) {
        int level = 1;
        int victoryPoints = 10;
        Map<ColorANSI, Integer> resources = new HashMap<>();
        resources.put(YELLOW, 10);
        resources.put(RED, 10);
        resources.put(BLUE, 10);
        int cardWidth = 17;
        for(int levelSet = 0; levelSet < 3; levelSet ++){
            for(ColorANSI color: ColorANSI.values()){
                System.out.print(color.escape() + "╔═════════════════╗  ");
            }
            System.out.print("\n" + RESET);
            for(ColorANSI color: ColorANSI.values()){   //PRINT LEVEL
                String edge = color.escape() + "║" + RESET;
                System.out.print(edge);
                String contentToDump = "level " + Integer.toString(level);
                System.out.print(contentToDump + stringProduct(cardWidth - contentToDump.length(), " ") + edge + "  ");
            }
            System.out.print("\n" + RESET);
            for(ColorANSI color: ColorANSI.values()){   //PRINT VICTORYPOINTS
                String edge = color.escape() + "║" + RESET;
                System.out.print(edge);
                String contentToDump = "VP " + Integer.toString(victoryPoints);
                System.out.print(contentToDump + stringProduct(cardWidth - contentToDump.length(), " ") + edge + "  ");
            }
            System.out.print("\n" + RESET);
            for(ColorANSI color: ColorANSI.values()){  //PRINT COST
                String edge = color.escape() + "║" + RESET;
                System.out.print(edge);
                String contentToDump = "cost" + mapContent(resources);
                int numberEscapes = countSizeEscapes(resources);
                System.out.print(contentToDump + stringProduct(numberEscapes + cardWidth - contentToDump.length() - 1, " ") + edge + "  ");
            }
            System.out.print("\n" + RESET);
            for(ColorANSI color: ColorANSI.values()){  //PRINT INPUT
                String edge = color.escape() + "║" + RESET;
                System.out.print(edge);
                String contentToDump = "in" + mapContent(resources);
                int numberEscapes = countSizeEscapes(resources);
                System.out.print(contentToDump + stringProduct(numberEscapes + cardWidth - contentToDump.length() - 1, " ") + edge + "  ");
            }
            System.out.print("\n" + RESET);
            for(ColorANSI color: ColorANSI.values()){
                String edge = color.escape() + "║" + RESET;
                System.out.print(edge);
                String contentToDump = "out" + mapContent(resources);
                int numberEscapes = countSizeEscapes(resources);
                System.out.print(contentToDump + stringProduct(numberEscapes + cardWidth - contentToDump.length() - 1, " ") + edge + "  ");
            }
            System.out.print("\n" + RESET);
            for(ColorANSI color: ColorANSI.values()){
                System.out.print(color.escape() + "╚═════════════════╝  ");
            }
            System.out.print("\n" + RESET);
        }
    }


}

 enum ColorANSI {
    RED("\u001B[31m"),
    GREY("\u001B[90m"),
    YELLOW("\u001B[33m"),
    BLUE("\u001B[34m");
    static final String RESET = "\u001B[0m";

    private String escape;

    ColorANSI(String escape) {
        this.escape = escape;
    }
    public String escape(){
        return escape;
    }

}
