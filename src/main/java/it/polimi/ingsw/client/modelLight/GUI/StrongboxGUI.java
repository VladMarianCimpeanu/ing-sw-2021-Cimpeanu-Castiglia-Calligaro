package it.polimi.ingsw.client.modelLight.GUI;

import it.polimi.ingsw.client.Clickable;
import it.polimi.ingsw.client.GUI;
import it.polimi.ingsw.client.Resource;
import it.polimi.ingsw.client.Shape;
import it.polimi.ingsw.client.modelLight.StrongboxView;

import java.util.HashMap;
import java.util.Map;

public class StrongboxGUI extends StrongboxView implements Clickable {
    private Map<Resource, Shape> shapes;
    private final int deltaX = 40;
    private final int deltaY = 25;
    private final int heightRes = 14;
    private final int widthRes = 14;

    public StrongboxGUI(){
        super();
        shapes = new HashMap<>();
        GUI.getGamePanel().addAction(this);
    }

    @Override
    public void show() {
        shapes = new HashMap<>();
        int x = 45;
        int y = 335;
        int many = 0;
        for(Resource resource: content.keySet()){
            shapes.put(resource, new Shape(x, y, 14 ,14));
            x += deltaX;
            many++;
            if(many == 2){
                x = 45;
                y += deltaY;
            }
        }
        GUI.getGamePanel().repaint();
    }

    public Shape getShape(Resource resource) {
        return shapes.get(resource);
    }

    @Override
    public boolean isClicked(int x, int y) {
        return !(whichClicked(x, y) == null);
    }

    public Resource whichClicked(int x, int y) {
        int xIn = 45;
        int yIn = 335;
        int many = 0;
        for(Resource resource: content.keySet()){
            if(xIn <= x && x <= xIn+widthRes && yIn <= y && y <= yIn+heightRes)
                return resource;
            xIn += deltaX;
            many++;
            if(many == 2){
                xIn = 45;
                yIn += deltaY;
            }
        }
        return null;
    }

        @Override
    public void click(int x, int y) {
            System.out.println("nnerfnwenr");
    }
}
