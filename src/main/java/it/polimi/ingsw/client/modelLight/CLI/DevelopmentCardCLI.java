package it.polimi.ingsw.client.modelLight.CLI;

import it.polimi.ingsw.client.Color;
import it.polimi.ingsw.client.Resource;
import it.polimi.ingsw.client.modelLight.DevelopmentCardView;

import java.util.Map;

public class DevelopmentCardCLI extends DevelopmentCardView {
    private int ID;
    private int VictoryPoints;
    private int Level;
    private Color Color;
    private Map<Resource, Integer> ResourceCost;
    private Map<Resource, Integer> ResourceIn;
    private Map<Resource, Integer> BenefitOut;

    public int getID() {
        return ID;
    }

    public int getVictoryPoints() {
        return VictoryPoints;
    }

    public it.polimi.ingsw.client.Color getColor() {
        return Color;
    }

    public int getLevel() {
        return Level;
    }

    public Map<Resource, Integer> getOutput() {
        return BenefitOut;
    }

    public Map<Resource, Integer> getInput() {
        return ResourceIn;
    }

    public Map<Resource, Integer> getCost() {
        return ResourceCost;
    }

    @Override
    public void show(){

    }
}
