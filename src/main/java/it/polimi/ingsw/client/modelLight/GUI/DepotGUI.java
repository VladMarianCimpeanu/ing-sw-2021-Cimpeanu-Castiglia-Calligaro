package it.polimi.ingsw.client.modelLight.GUI;

import it.polimi.ingsw.client.Clickable;
import it.polimi.ingsw.client.GUI;
import it.polimi.ingsw.client.Resource;
import it.polimi.ingsw.client.Shape;
import it.polimi.ingsw.client.modelLight.DepotView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DepotGUI extends DepotView implements Clickable {
    private Runnable strategy;
    private Map<Resource, Shape> shapes;

    public DepotGUI(){
        super();
        setStrategyMove();
        shapes = new HashMap<>();
    }

    public Map<Resource, Shape> getShapes() {
        return shapes;
    }

    @Override
    public void show() {
        int y = 188;
        int x = 74;

        //just put the position of the first resource of each shelf
        for(int i = 0; i<3; i++) {
            if(quantity[i] != 0)
                shapes.put(resources[i], new Shape(x, y, 14, 14));
            x -= 11+(1-i)*6;
            y += 37;
        }

        GUI.getGamePanel().repaint();
    }

    @Override
    public void showUpdate() {
        show();
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
