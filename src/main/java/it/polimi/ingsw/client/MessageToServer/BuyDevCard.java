package it.polimi.ingsw.client.MessageToServer;

import it.polimi.ingsw.server.controller.Controller;
import it.polimi.ingsw.server.controller.states.TurnState;
import it.polimi.ingsw.server.model.Color;

import java.util.ArrayList;

public class BuyDevCard implements MessageToServer {
    private String type;
    private int level;
    private Color color;
    private ArrayList<Integer> discountsID;

    public BuyDevCard(int level, Color color, ArrayList<Integer> discountsID) {
        this.type = "BuyDevCard";
        this.level = level;
        this.color = color;
        this.discountsID = discountsID;
    }

    public BuyDevCard(int level, Color color){
        this.type = "BuyDevCard";
        this.level = level;
        this.color = color;
        discountsID = new ArrayList<>();
    }

    public BuyDevCard(int level, Color color, int id1){
        this(level, color);
        discountsID.add(id1);
    }

    public BuyDevCard(int level, Color color, int id1, int id2){
        this(level, color, id1);
        discountsID.add(id2);
    }

}
