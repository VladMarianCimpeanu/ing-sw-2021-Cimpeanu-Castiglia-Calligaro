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
    private final int deltapixel = 23;
    private final int heightRes = 14;
    private final int widthRes = 14;

    public DepotGUI(){
        super();
        setStrategyMove();
        shapes = new HashMap<>();
        GUI.getGamePanel().addAction(this);
    }

    public Map<Resource, Shape> getShapes() {
        return shapes;
    }

    public int getDeltapixel() {
        return deltapixel;
    }

    @Override
    public void show() {
        int y = 188;
        int x = 74;

        //just put the position of the first resource of each shelf
        for(int i = 0; i<3; i++) {
            if(quantity[i] != 0)
                shapes.put(resources[i], new Shape(x, y, widthRes, heightRes));
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
        return !(whichClicked(x, y) == null);
    }

    public Resource whichClicked(int x, int y){
        int yIn = 188;
        int xIn = 74;
        for(int i = 0; i<3; i++) {
            int delta = 0;
            for (int j = 0; j < quantity[j]; j++) {
                if(xIn+delta <= x && x <= xIn+widthRes+delta && yIn <= y && y <= yIn+heightRes)
                    return resources[i];
                delta += deltapixel;
            }
            xIn -= 11+(1-i)*6;
            yIn += 37;
        }
        return null;
    }

    @Override
    public void click(int x, int y) {
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
