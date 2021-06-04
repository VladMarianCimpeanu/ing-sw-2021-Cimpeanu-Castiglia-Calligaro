package it.polimi.ingsw.client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Generates a colored FaithPath using Ascii Art
 * randomly assign a colored cross to each player
 */
public class AsciiFaithPath {
    private Map<String, Integer> positions;
    private Map<String, String> playerColors;
    private static final int length = 134;
    private static final int heigth = 9;
    private String yellow = "\u001B[33m";
    private String blue = "\u001B[34m";
    private String reset = "\u001B[0m";
    private String[][] path;

    public AsciiFaithPath() {
        playerColors = new HashMap<>();
        positions = new HashMap<>();

        path = new String[heigth][length];
        for (int i = 0; i < heigth; i++) {
            for (int j = 0; j < length; j++) {
                path[i][j] = new String(" ");
            }
        }
        drawPath(0, 0);
        color(3, 2 * 7, yellow);
        color(0, 4 * 7, yellow);
        color(0, 7 * 7, yellow);
        color(2 * 3, 8 * 7, yellow);
        color(2 * 3, 11 * 7, yellow);
        color(0, 12 * 7, yellow);
        color(0, 15 * 7, yellow);
        color(0, 6 * 7, blue);
        color(2 * 3, 12 * 7, blue);
        color(0, 18 * 7, blue);
        putNumbers(yellow);

//        for(String s: players){
//            positions.put(s, 0);
//        }
//        assignColor();
//        move(null, 0);
    }

    private void drawSquare(int y, int x){
        path[y][x] = "╔";
        for(int t = 1; t < 6; t++){
            path[y][x+t] = "═";
        }
        path[y][x+6] = "╗";
        path[y+1][x] = "║";
        path[y+1][x+6] = "║";
        path[y+2][x] = "╚";
        path[y+2][x+6] = "╝";
        for(int t = 1; t < 6; t++){
            path[y+2][x+t] = "═";
        }
    }

    private void drawPath(int y, int x){
        drawSquare(y + 6, x);
        drawSquare(y + 6,7 + x);
        drawSquare(y + 6,14 + x);
        drawSquare(y + 3,14 + x);
        drawSquare(y,14 + x);
        drawSquare(y,21 + x);
        drawSquare(y,28 + x);
        drawSquare(y,35 + x);
        drawSquare(y,42 + x);
        drawSquare(y,49 + x);
        drawSquare(y + 3,49 + x);
        drawSquare(y + 6,49 + x);
        drawSquare(y + 6,56 + x);
        drawSquare(y + 6,63 + x);
        drawSquare(y + 6,70 + x);
        drawSquare(y + 6,77 + x);
        drawSquare(y + 6,84 + x);
        drawSquare(y + 3,84 + x);
        drawSquare(y,84 + x);
        drawSquare(y,91 + x);
        drawSquare(y,98 + x);
        drawSquare(y,105 + x);
        drawSquare(y,112 + x);
        drawSquare(y,119 + x);
        drawSquare(y,126 + x);
    }

    private void color(int y, int x, String color){
        for(int t = 0; t < 3; t++){
            path[y + t][x] = (color + path[y + t][x]);
        }
        for(int t = 0; t < 3; t++){
            path[y + t][x + 6] = (path[y + t][x + 6] + reset);
        }
        path[y + 1][x] = (path[y + 1][x] + reset);
        path[y + 1][x + 6] =(color + path[y + 1][x + 6]);
    }

    private void putNumbers(String color){
        path[4][14] = (color + "1" + reset);
        path[1][28] = (color + "2" + reset);
        path[1][49] = (color + "4" + reset);
        path[7][56] = (color + "6" + reset);
        path[7][77] = (color + "9" + reset);
        path[1][83] = (color + "1");
        path[1][84] = ("2" + reset);
        path[1][104] = (color + "1");
        path[1][105] = ("6" + reset);
        path[1][125] = (blue + "2");
        path[1][126] = ("0" + reset);
    }

    private void move(String nick, int newPos){
        if(newPos > 24) return;
        int xC, yC;
        if(nick != null){
            int oldPos = positions.get(nick);
            positions.replace(nick, newPos);
            ArrayList<String> oldPosPlayers = new ArrayList<>();
            for(String s: positions.keySet()){
                if(positions.get(s) == oldPos) oldPosPlayers.add(s);
            }
            xC = getCenterX(oldPos);
            yC = getCenterY(oldPos);
            clearInside(yC, xC);
            if(oldPosPlayers.size() == 1){
                String p = oldPosPlayers.remove(0);
                path[yC][xC] = (playerColors.get(p) + "┼" + reset);
            }else if(oldPosPlayers.size() == 2){
                String p = oldPosPlayers.remove(0);
                path[yC][xC - 1] = (playerColors.get(p) + "┼" + reset);
                p = oldPosPlayers.remove(0);
                path[yC][xC + 1] = (playerColors.get(p) + "┼" + reset);
            }else if(oldPosPlayers.size() == 3){
                String p = oldPosPlayers.remove(0);
                path[yC][xC - 2] = (playerColors.get(p) + "┼" + reset);
                p = oldPosPlayers.remove(0);
                path[yC][xC] = (playerColors.get(p) + "┼" + reset);
                p = oldPosPlayers.remove(0);
                path[yC][xC + 2] = (playerColors.get(p) + "┼" + reset);
            }
        }

        ArrayList<String> newPosPlayers = new ArrayList<>();
        for(String s: positions.keySet()){
            if(positions.get(s) == newPos) newPosPlayers.add(s);
        }
        xC = getCenterX(newPos);
        yC = getCenterY(newPos);
        clearInside(yC, xC);
        if(newPosPlayers.size() == 1){
            String p = newPosPlayers.remove(0);
            path[yC][xC] = (playerColors.get(p) + "┼" + reset);
        }else if(newPosPlayers.size() == 2){
            String p = newPosPlayers.remove(0);
            path[yC][xC - 1] = (playerColors.get(p) + "┼" + reset);
            p = newPosPlayers.remove(0);
            path[yC][xC + 1] = (playerColors.get(p) + "┼" + reset);
        }else if(newPosPlayers.size() == 3){
            String p = newPosPlayers.remove(0);
            path[yC][xC - 2] = (playerColors.get(p) + "┼" + reset);
            p = newPosPlayers.remove(0);
            path[yC][xC] = (playerColors.get(p) + "┼" + reset);
            p = newPosPlayers.remove(0);
            path[yC][xC + 2] = (playerColors.get(p) + "┼" + reset);
        }else if(newPosPlayers.size() == 4){
            String p = newPosPlayers.remove(0);
            path[yC][xC - 2] = (playerColors.get(p) + "┼" + reset);
            p = newPosPlayers.remove(0);
            path[yC][xC - 1] = (playerColors.get(p) + "┼" + reset);
            p = newPosPlayers.remove(0);
            path[yC][xC + 1] = (playerColors.get(p) + "┼" + reset);
            p = newPosPlayers.remove(0);
            path[yC][xC + 2] = (playerColors.get(p) + "┼" + reset);
        }
    }

    private void clearInside(int y, int x){
        for(int t = - 2; t < 3; t++)
        path[y][x + t] = " ";
    }

    private int getCenterX(int pos){
        if(pos <= 2){
            return (3 + pos * 7);
        }
        if(pos == 3) return 17;
        if(pos >= 4 && pos <= 9){
            return (3 + (pos - 2 ) * 7);
        }
        if(pos == 10) return 52;
        if(pos >= 11 && pos <= 16){
            return (3 + (pos - 4 ) * 7);
        }
        if(pos == 17) return 87;
        if(pos >= 18){
            return (3 + (pos - 6 ) * 7);
        }
        return 0;
    }

    private int getCenterY(int pos){
        if(pos <= 2 || (11 <= pos && pos <= 16)){
            return 7;
        }
        if(pos == 3 || pos == 10 || pos == 17){
            return 4;
        }
        if((4 <= pos && pos <= 9) || (18 <= pos && pos <= 24)){
            return 1;
        }
        return 0;
    }

    private void assignColor(){
        ArrayList<String> colors = new ArrayList<>();
        colors.add("\u001B[32m");
        colors.add("\u001B[34m");
        colors.add("\u001B[33m");
        colors.add("\u001B[95m");
        for(String nickname: positions.keySet()){
            playerColors.put(nickname, colors.remove(0));
        }
    }

    private void addLegend(){
        String legendRow;
        int i = 4;
        for(String player: playerColors.keySet()){
            legendRow = (playerColors.get(player) + "┼ " + player + Color.RESET);
            path[i][length - 1] = legendRow;
            i++;
        }
    }

    /**
     * prints the faithPath
     */
    public void print(){
        for(int i = 0; i < heigth; i++){
            for(int j = 0; j < length; j++){
                System.out.print(path[i][j]);
            }
            System.out.println();
        }
    }

    /**
     * updates the player positions on the faithPath
     * @param newPositions for each player: nickname and position
     */
    public void update(Map<String, Integer> newPositions){
        if(positions.size() == 0){
            positions = new HashMap<>(newPositions);
            assignColor();
            addLegend();
            move(null, 0);
        }
        for(String s: positions.keySet()){
            if(!positions.get(s).equals(newPositions.get(s)))
                move(s, newPositions.get(s));
        }
        positions = new HashMap<>(newPositions);
    }
}
