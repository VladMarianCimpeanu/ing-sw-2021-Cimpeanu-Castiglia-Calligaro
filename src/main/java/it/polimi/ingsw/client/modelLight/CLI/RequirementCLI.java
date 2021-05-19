package it.polimi.ingsw.client.modelLight.CLI;

import it.polimi.ingsw.client.Color;
import it.polimi.ingsw.client.Resource;

public class RequirementCLI {
    private Resource resource;
    private int numberResource;

    private int numberCards;
    private int levelCard;
    private Color color;

    public String toString(){
        if(resource != null) return (numberResource + " " + resource.escape() + Resource.RESET);
        if(levelCard == 0) return (numberCards + color.escape() + " ▓   " + Color.RESET);
        return (numberCards + color.escape() + " ▓ " + Color.RESET + "lvl " + levelCard);
    }

    public int size(){
        if(resource != null) return 3;
        if(levelCard == 0) return 6;
        return 9;
    }
}
