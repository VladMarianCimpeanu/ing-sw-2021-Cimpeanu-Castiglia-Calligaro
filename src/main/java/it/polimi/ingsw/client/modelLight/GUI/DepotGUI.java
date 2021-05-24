package it.polimi.ingsw.client.modelLight.GUI;

import it.polimi.ingsw.client.Clickable;
import it.polimi.ingsw.client.modelLight.DepotView;

import java.util.ArrayList;

public class DepotGUI extends DepotView implements Clickable {
    private Runnable strategy;

    @Override
    public void show() {

    }

    @Override
    public void showUpdate() {

    }

    @Override
    public boolean isClicked(int x, int y) {
        return false;
    }

    @Override
    public void click() {
        strategy.run();
        setStrategyMove();
    }

    //default
    private void setStrategyMove(){
        strategy = () -> {
            System.out.println("move strategy");
        };
    }

    public void setStrategyPut(){
        strategy = () -> {
            System.out.println("put strategy");
        };
    }

    public void setStrategyTake(){

    }
}
