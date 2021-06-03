package it.polimi.ingsw.client.modelLight.GUI;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import it.polimi.ingsw.client.Clickable;
import it.polimi.ingsw.client.GUI;
import it.polimi.ingsw.client.Resource;
import it.polimi.ingsw.client.Shape;
import it.polimi.ingsw.client.modelLight.LeaderCardSetView;
import it.polimi.ingsw.client.panels.DefaultPanel;
import it.polimi.ingsw.client.panels.FirstTurnPanel;

import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class LeaderCardSetGUI extends LeaderCardSetView implements Clickable {
    private static Map<Integer, LeaderCardGUI> cards;
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

    public static LeaderCardGUI getLeaderCard(int id){
        return cards.getOrDefault(id, null);
    }

    @Override
    public void show() {
    }

    @Override
    public void update(ArrayList<Integer> idS){
        super.update(idS);
//        for(int id: playerCards){
//            GUI.getGamePanel().addGameboard(cards.get(id));
//        }
        updateGUI();
    }

    /**
     * assign leader cards to this player
     * @param idS array list containing the IDs of the leader cards to assign to this player.
     */
    @Override
    public void keep(ArrayList<Integer> idS) {
        super.keep(idS);
        GUI.getGamePanel().setActionPanel(new FirstTurnPanel());
        GUI.getGamePanel().addAction(this);
        setLeadersToFirstTurn();
        updateGUI();
    }

    /**
     * discards a leader card of the player and refresh the action panel.
     * @param idRemove
     */
    @Override
    public void remove(int idRemove) {
        super.remove(idRemove);
        updateGUI();
        GUI.getGamePanel().setActionPanel(new DefaultPanel());
    }

    @Override
    public void updateExtraSlot(int id, Resource resource, int quantity) {
        dumpMessage(quantity + " " +  resource + " on leader card " + id);
        cards.get(id).updateExtraSlot(resource, quantity);
        GUI.getGamePanel().repaint();
    }

    /**
     * repaint the leader cards owned by the player.
     */
    private void updateGUI() {
        int x = 0;
        for(int id: playerCards){
            cards.get(id).setShape(new Shape((startX + x), startY, cardWidth, cardHeight));
            x += (cardWidth + margin);
        }
        GUI.getGamePanel().repaint();
    }

    /**
     * activates the specified leader card and change its default behaviour.
     * @param id ID of the leader card to activate.
     */
    @Override
    public void activate(int id) {
        cards.get(id).activate();
        GUI.getGamePanel().setActionPanel(new DefaultPanel());
    }

    @Override
    public void dumpMessage(String message) {

    }

    /**
     * gives the leader cards owned by the player.
     * @return an ArrayList of LeaderCardGUI objects owned by the player.
     */
    public ArrayList<LeaderCardGUI> getCards() {
        ArrayList<LeaderCardGUI> array = new ArrayList<>();
        for(int id: playerCards){
            array.add(cards.get(id));
        }
        return array;
    }

    /**
     * sets all the leader cards to the converting marbles behaviour.
     */
    public void setLeadersToMarketStrategy(){
        for(LeaderCardGUI card : cards.values()) card.setMarketStrategy();
    }

    public void setLeadersToDiscountStrategy(){
        for(LeaderCardGUI card : cards.values()) card.setDiscountStrategy();
    }

    /**
     * sets all the leader cards owned by the player to their default behaviour.
     */
    public void setLeadersToDefaultStrategy(){
        for(LeaderCardGUI card : cards.values()) card.setStrategyDefault();
    }

    public void setLeadersToFirstTurn(){
        for(LeaderCardGUI card : cards.values()) card.setStrategyFirst();
    }


    /**
     * checks if a leader card of the player has been clicked.
     * @param x
     * @param y
     * @return
     */
    @Override
    public boolean isClicked(int x, int y) {
        for(Integer card : playerCards){
            if (cards.get(card).isClicked(x, y)) return true;
        }
        return false;
    }

    /**
     * if a LeaderCard of the player has been clicked, it activate the effect of the clicked card.
     * @param x X coordinate of the click.
     * @param y Y coordinate of the click.
     */
    @Override
    public void click(int x, int y) {
        for(Integer card : playerCards){
            LeaderCardGUI leaderCard = cards.get(card);
            if(leaderCard.isClicked(x, y)) leaderCard.click(x, y);
        }
    }

    public void setLeadersToBuyStrategy(){
        for(LeaderCardGUI card : cards.values()) card.setBuyStrategy();
    }
}
