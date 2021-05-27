package it.polimi.ingsw.client.modelLight.GUI;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import it.polimi.ingsw.client.GUI;
import it.polimi.ingsw.client.Shape;
import it.polimi.ingsw.client.modelLight.LeaderCardSetView;
import it.polimi.ingsw.client.panels.ActionPanel;
import it.polimi.ingsw.client.panels.DefaultPanel;

import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class LeaderCardSetGUI extends LeaderCardSetView {
    private Map<Integer, LeaderCardGUI> cards;
    private final static int startX = 20;
    private final static int startY = 450;
    private final static int cardWidth = 133;
    private final static int cardHeight = 200;
    private final static int margin = 17;

    public LeaderCardSetGUI() {
        playerCards = new ArrayList<>();
        cards = new HashMap<>();
        ArrayList<LeaderCardGUI> read;
        String srcPath = "/leaderCards.json";
        Reader reader = new InputStreamReader(this.getClass().getResourceAsStream(srcPath), StandardCharsets.UTF_8);
        Gson converter = new Gson();
        Type cardsType = new TypeToken<ArrayList<LeaderCardGUI>>(){}.getType();
        read = converter.fromJson(reader, cardsType);
        for(LeaderCardGUI lc: read){
            cards.put(lc.getID(), lc);
        }
    }

    @Override
    public void show() {

    }

    @Override
    public void update(ArrayList<Integer> idS){
        super.update(idS);
        for(int id: playerCards){
            GUI.getGamePanel().addGameboard(cards.get(id));
        }
        updateGUI();
    }

    @Override
    public void keep(ArrayList<Integer> idS) {
        for(int id: playerCards){
            GUI.getGamePanel().removeGameboard(cards.get(id));
        }
        super.keep(idS);
        for(int id: playerCards){
            GUI.getGamePanel().addGameboard(cards.get(id));
        }
        updateGUI();
    }

    @Override
    public void remove(int idRemove) {
        super.remove(idRemove);
        GUI.getGamePanel().removeGameboard(cards.get(idRemove));
        updateGUI();
        GUI.getGamePanel().setActionPanel(new DefaultPanel());
    }

    private void updateGUI() {
        int x = 0;
        for(int id: playerCards){
            cards.get(id).setShape(new Shape((startX + x), startY, cardWidth, cardHeight));
            x += (cardWidth + margin);
            GUI.getGamePanel().addGameboard(cards.get(id));
        }
        GUI.getGamePanel().repaint();
    }

    @Override
    public void activate(int id) {
        GUI.getGamePanel().setActionPanel(new DefaultPanel());
    }

    @Override
    public void dumpMessage(String message) {

    }

    public ArrayList<LeaderCardGUI> getCards() {
        ArrayList<LeaderCardGUI> array = new ArrayList<>();
        for(int id: playerCards){
            array.add(cards.get(id));
        }
        return array;
    }
}
