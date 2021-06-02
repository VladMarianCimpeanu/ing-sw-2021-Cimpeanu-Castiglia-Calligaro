package it.polimi.ingsw.client.modelLight.GUI;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import it.polimi.ingsw.client.Clickable;
import it.polimi.ingsw.client.Color;
import it.polimi.ingsw.client.GUI;
import it.polimi.ingsw.client.panels.GamePanel;
import it.polimi.ingsw.client.modelLight.DevelopmentCardSetView;

import java.awt.*;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class DevelopmentCardsSetGUI extends DevelopmentCardSetView implements Clickable {
    private static ArrayList<DevelopmentCardGUI> cards;
    private final static int padding = 10;

    public DevelopmentCardsSetGUI(){
        String srcPath = "/developmentCard.json";
        Reader reader = new InputStreamReader(this.getClass().getResourceAsStream(srcPath), StandardCharsets.UTF_8);
        Gson converter = new Gson();
        Type cardsType = new TypeToken<ArrayList<DevelopmentCardGUI>>(){}.getType();
        cards = converter.fromJson(reader, cardsType);
    }


    @Override
    public void setDecks(ArrayList<ArrayList<Integer>> decks) {
        this.decks = decks;
        int leftPadding = GamePanel.getXBoard() + GamePanel.getWidthBoard() + padding;
        for(int level = 0; level < decks.size(); level ++){
            for(int color = 0; color < decks.get(0).size(); color ++){
                if(decks.get(level).get(color) != 0){
                    DevelopmentCardGUI card = cards.get(decks.get(level).get(color) - 1);
                    card.setToBuyable();
                    card.setPosition(leftPadding + color * (padding + DevelopmentCardGUI.getWidth()), padding + level * (DevelopmentCardGUI.getHeight() + padding));
                }
            }
        }
        GUI.getGamePanel().addGameboard(this);
    }

    @Override
    public void show() {
    }

    @Override
    public void updateSet(Color color, int level, int ID){
        decks.get(level - 1).set(color.getIndex(), ID);
        if(ID > 0){
            DevelopmentCardGUI card = cards.get(ID - 1);
            int leftPadding = GamePanel.getXBoard() + GamePanel.getWidthBoard() + padding;
            card.setPosition(leftPadding + color.getIndex() * (padding + DevelopmentCardGUI.getWidth()), padding + (level - 1) * (DevelopmentCardGUI.getHeight() + padding));
            card.setToBuyable();
        }
        GUI.getGamePanel().repaint();
    }

    public void paintCards(Graphics graphics){
        for(int x = 0; x < decks.size(); x ++){
            for(int y = 0; y < decks.get(0).size(); y ++){
                int card = decks.get(x).get(y);
                if(card != 0) GamePanel.drawImage(graphics, cards.get(card - 1).getUrl(), cards.get(card - 1).getShape());
            }
        }
    }

    /**
     * @param Id specified development card ID
     * @return DevelopmentCardGUI that has the given ID
     */
    public static DevelopmentCardGUI getCard(int Id){
        return cards.get(Id - 1);
    }

    @Override
    public boolean isClicked(int x, int y) {
        for(ArrayList<Integer> deck : decks){
            for(Integer id : deck){
                if(id!= 0 && getCard(id).isClicked(x, y)) return true;
            }
        }
        return false;
    }

    @Override
    public void click(int x, int y) {
        boolean found = false;
        for(int i = 0; i < decks.size(); i ++){
            for(int j = 0; j < decks.get(0).size() && !found; j ++){
                if(decks.get(i).get(j) != 0 && getCard(decks.get(i).get(j)).isClicked(x, y)) {
                    found = true;
                    getCard(decks.get(i).get(j)).click(x, y);
                }
            }
        }
    }
}
