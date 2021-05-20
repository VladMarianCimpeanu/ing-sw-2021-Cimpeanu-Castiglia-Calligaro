package it.polimi.ingsw.client.modelLight.CLI;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import it.polimi.ingsw.client.modelLight.LeaderCardSetView;
import it.polimi.ingsw.client.modelLight.LeaderCardView;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class LeaderCardSetCLI extends LeaderCardSetView {
    protected ArrayList<LeaderCardCLI> cards;

    public LeaderCardSetCLI() {
        cards = new ArrayList<>();
        String srcPath = "src/resources/leaderCards.json";
        BufferedReader reader = null;
        Gson converter = new Gson();
        try {
            reader = new BufferedReader(new FileReader(srcPath));
            Type cardsType = new TypeToken<ArrayList<LeaderCardCLI>>(){}.getType();
            cards = converter.fromJson(reader, cardsType);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void show() {
        if(cards.size() == 0){
            System.out.println("You don't have any LeaderCard");
            return;
        }
        System.out.println("These are your leader cards:");
        String[][] leadercards = new String[4][7];
        String[] leaderCardSet = new String[8];
        int i = 0;
        for(LeaderCardCLI card : cards){
            leadercards[i] = card.generateAscii();
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
    public void update(ArrayList<Integer> idS){
        cards = cards
                .stream()
                .filter(
                        card -> idS.contains(card.getID())
                ).collect(Collectors.toCollection(ArrayList::new));
    };

    public int getIDfromIndex(int index){
        return cards.get(index-1).getID();
    }

    @Override
    public void remove(int id) {
        cards = cards
                .stream()
                .filter(
                        card -> id != card.getID()
                ).collect(Collectors.toCollection(ArrayList::new));
    }

    @Override
    public void activate(int id) {
        for(LeaderCardCLI l: cards){
            if(l.getID() == id){
                l.activate();
                return;
            }
        }
    }

    @Override
    public void dumpMessage(String message) {
        System.out.println(message);
    }
}
