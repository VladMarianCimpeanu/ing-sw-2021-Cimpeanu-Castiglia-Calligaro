package it.polimi.ingsw.MessageFromClient;

import it.polimi.ingsw.model.Color;

import java.util.ArrayList;

public class BuyDevCard extends MessageFromClient {
    private int level;
    private Color color;
    private ArrayList<Integer> discountsID;
    @Override
    public void activate() {

    }
}
