package it.polimi.ingsw.client.modelLight.GUI;

import it.polimi.ingsw.client.GUI;
import it.polimi.ingsw.client.Resource;
import it.polimi.ingsw.client.Shape;
import it.polimi.ingsw.client.modelLight.StrongboxView;

import java.util.HashMap;
import java.util.Map;

public class StrongboxGUI extends StrongboxView {
    private Map<Resource, Shape> shapes;

    public StrongboxGUI(){
        super();
        shapes = new HashMap<>();
    }

    @Override
    public void show() {
        shapes = new HashMap<>();
        int x = 45;
        int y = 335;
        int many = 0;
        for(Resource resource: content.keySet()){
            shapes.put(resource, new Shape(x, y, 14 ,14));
            x += 40;
            many++;
            if(many == 2){
                x = 45;
                y = 360;
            }
        }
        GUI.getGamePanel().repaint();
    }

    public Shape getShape(Resource resource) {
        return shapes.get(resource);
    }
}
