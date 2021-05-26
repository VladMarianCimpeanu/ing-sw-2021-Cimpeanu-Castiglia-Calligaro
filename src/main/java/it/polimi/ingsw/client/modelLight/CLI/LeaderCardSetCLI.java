package it.polimi.ingsw.client.modelLight.CLI;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import it.polimi.ingsw.client.modelLight.LeaderCardSetView;
import it.polimi.ingsw.server.model.leaderCards.LeaderCard;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class LeaderCardSetCLI extends LeaderCardSetView {
    private Map<Integer,LeaderCardCLI> cards;

    public LeaderCardSetCLI() {
        playerCards = new ArrayList<>();
        cards = new HashMap<>();
        ArrayList<LeaderCardCLI> read = new ArrayList<>();
        String srcPath = "/leaderCards.json";
        Reader reader = new InputStreamReader(this.getClass().getResourceAsStream(srcPath), StandardCharsets.UTF_8);
        Gson converter = new Gson();
        Type cardsType = new TypeToken<ArrayList<LeaderCardCLI>>(){}.getType();
        read = converter.fromJson(reader, cardsType);
        for(LeaderCardCLI lc: read){
            cards.put(lc.getID(), lc);
        }
    }

    @Override
    public void show() {
        if(playerCards.size() == 0){
            System.out.println("You don't have any LeaderCard");
            return;
        }
        System.out.println("These are your leader cards:");
        String[][] leadercards = new String[4][7];
        String[] leaderCardSet = new String[8];
        int i = 0;
        for(int id: playerCards){
            leadercards[i] = cards.get(id).generateAscii();
            i++;
        }
        for(int row = 0; row < 7; row++) {
            leaderCardSet[row] = "";
            for (int j = 0; j < i; j++) {
                leaderCardSet[row] += leadercards[j][row];
            }
        }
        leaderCardSet[7] = "";
        for (int j = 0; j < i; j++) {
            leaderCardSet[7] += "        (" + (j+1) + ")          ";
        }
        for(int row = 0; row < 8; row ++){
            System.out.println(leaderCardSet[row]);
        }
    }

    @Override
    public void activate(int id) {
        cards.get(id).activate();
    }

    @Override
    public void dumpMessage(String message) {
        System.out.println(message);
    }
}
