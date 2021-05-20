package it.polimi.ingsw.client.modelLight.CLI;

import it.polimi.ingsw.client.AsciiFaithPath;
import it.polimi.ingsw.client.Color;
import it.polimi.ingsw.client.modelLight.FaithPathView;

import java.util.ArrayList;

public class FaithPathCLI extends FaithPathView {
    private AsciiFaithPath asciiFaithPath;

    public FaithPathCLI(){
        asciiFaithPath = new AsciiFaithPath();
    }

    @Override
    public void show() {
        if(positions != null)
            asciiFaithPath.update(positions);
        asciiFaithPath.print();
    }

    @Override
    public void showUpdate() {
        System.out.print("FaithPath has changed. New positions: ");
        boolean firstIteration = true;
        for(String player: positions.keySet()){
            if(!firstIteration) System.out.print(", ");
            System.out.print(player + ": " + positions.get(player));
            firstIteration = false;
        }
        System.out.println(".");
    }
}
