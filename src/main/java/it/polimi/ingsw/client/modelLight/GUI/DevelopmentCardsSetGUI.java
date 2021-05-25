package it.polimi.ingsw.client.modelLight.GUI;

import it.polimi.ingsw.client.modelLight.DevelopmentCardSetView;

import java.util.ArrayList;

public class DevelopmentCardsSetGUI extends DevelopmentCardSetView {
    @Override
    public void setDecks(ArrayList<ArrayList<Integer>> decks) {
        this.decks = decks;
    }

    @Override
    public void show() {

    }
}
