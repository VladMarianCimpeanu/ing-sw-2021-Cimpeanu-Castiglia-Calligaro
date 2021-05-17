package it.polimi.ingsw.client.modelLight;

import java.util.ArrayList;

public abstract class DevelopmentCardDecksView {
    protected ArrayList<Integer> playerCards;

    public DevelopmentCardDecksView(){
        playerCards = new ArrayList<>();
        for(int index = 0; index < 3; index ++) playerCards.add(0);
    }

    public void addCard(int deckIndex, int ID){
        playerCards.set(deckIndex - 1, ID);
    }

    public ArrayList<Integer> getCards(){
        return new ArrayList<>(playerCards);
    }

    public abstract void show();
    public void update(){}
}
