package it.polimi.ingsw.client.modelLight.GUI;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import it.polimi.ingsw.client.GUI;
import it.polimi.ingsw.client.Shape;
import it.polimi.ingsw.client.modelLight.LeaderCardSetView;

import java.awt.*;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class LeaderCardSetGUI extends LeaderCardSetView {
    private ArrayList<LeaderCardGUI> cards;
    private final static int startX = 20;
    private final static int startY = 450;
    private final static int cardWidth = 133;
    private final static int cardHeight = 200;
    private final static int margin = 17;

    public LeaderCardSetGUI() {
        cards = new ArrayList<>();
        String srcPath = "/leaderCards.json";
        Reader reader = new InputStreamReader(this.getClass().getResourceAsStream(srcPath), StandardCharsets.UTF_8);
        Gson converter = new Gson();
        Type cardsType = new TypeToken<ArrayList<LeaderCardGUI>>(){}.getType();
        cards = converter.fromJson(reader, cardsType);
    }

    @Override
    public void show() {

    }

    @Override
    public void update(ArrayList<Integer> idS){
        ArrayList<LeaderCardGUI> newCards;
        newCards = cards
                .stream()
                .filter(
                        card -> idS.contains(card.getID())
                ).collect(Collectors.toCollection(ArrayList::new));
        for(LeaderCardGUI c : cards){
            if(!newCards.contains(c))
                GUI.getGamePanel().removeGameboard(c);
        }
        cards = newCards;
        int x = 0;
        for(LeaderCardGUI card: cards){
            card.setShape(new Shape((startX + x), startY, cardWidth, cardHeight));
            x += (cardWidth + margin);
            GUI.getGamePanel().addGameboard(card);
        }
        GUI.getGamePanel().repaint();
    };

    @Override
    public void remove(int id) {
        cards = cards
                .stream()
                .filter(
                        card -> id != card.getID()
                ).collect(Collectors.toCollection(ArrayList::new));
        int x = 0;
        for(LeaderCardGUI card: cards){
            card.setShape(new Shape((startX + x), startY, cardWidth, cardHeight));
            x += (cardWidth + margin);
            GUI.getGamePanel().addGameboard(card);
        }
        GUI.getGamePanel().repaint();
    }

    @Override
    public int getIDfromIndex(int index){
        return cards.get(index-1).getID();
    }

    @Override
    public void activate(int id) {

    }

    @Override
    public void dumpMessage(String message) {

    }

    public ArrayList<LeaderCardGUI> getCards() {
        return new ArrayList<>(cards);
    }
}
