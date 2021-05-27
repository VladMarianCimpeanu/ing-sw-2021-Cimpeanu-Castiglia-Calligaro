package it.polimi.ingsw.client.modelLight;

import java.util.ArrayList;

public abstract class DevelopmentCardDecksView {
    protected ArrayList<Integer> playerCards;

    public DevelopmentCardDecksView(){
        playerCards = new ArrayList<>();
        for(int index = 0; index < 3; index ++) playerCards.add(0);
    }

    public abstract void addCard(int deckIndex, int ID, String nickname);

    public void reloadCard(int deckIndex, int ID, String nickname){
        playerCards.set(deckIndex - 1, ID);
    }

    public ArrayList<Integer> getCards(){
        return new ArrayList<>(playerCards);
    }

    public int getPos(int ID){
        return playerCards.indexOf(ID)+1;
    }

    public abstract void show();
    public void update(){}
}
