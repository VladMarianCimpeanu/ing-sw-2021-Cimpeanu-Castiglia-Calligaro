package it.polimi.ingsw.client.modelLight.GUI;

import it.polimi.ingsw.client.GUI;
import it.polimi.ingsw.client.Shape;
import it.polimi.ingsw.client.modelLight.FaithPathView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FaithPathGUI extends FaithPathView {
    private Map<String, Shape> shapes;
    private Map<String, String> crosses;

    public FaithPathGUI(){
        super();
        shapes = new HashMap<>();
        crosses = new HashMap<>();
    }

    public Map<String, Shape> getShapes() {
        return shapes;
    }

    public String getURLCross(String player){
        return crosses.get(player);
    }

    @Override
    public void show() {
        int inX = -6;
        int inY = 84;
        int dimCell = 29;
        ArrayList<Integer> pos = new ArrayList<>();
        for(String player: positions.keySet()) {
            int p = positions.get(player);
            int x = 0;
            int y = 0;
            if(p<3) {
                x = inX + p * dimCell;
                y = inY;
            }else if(p<5){
                x = inX+ 2*dimCell;
                y = inY-(p-2)*dimCell;
            }else if(p<10){
                x = inX+(p-2)*dimCell;
                y = inY-2*dimCell;
            }else if(p<12){
                x = inX+7*dimCell;
                y = inY-(11-p)*dimCell;
            }else if(p<17){
                x = inX+(p-4)*dimCell;
                y = inY;
            }else if(p<19){
                x = inX+12*dimCell;
                y = inY-(p-16)*dimCell;
            }else{
                x = inX+(p-6)*dimCell;
                y = inY-2*dimCell;
            }

            int howMany = 0;
            for(int i:pos)
                if(i == p) howMany++;
            switch(howMany){
                case 1:
                    x += dimCell/2;
                    break;
                case 2:
                    y += dimCell/2;
                    break;
                case 3:
                    x += dimCell/2;
                    y += dimCell/2;
                    break;
                default: break;
            }
            if(positions.size() > 2)
                shapes.put(player, new Shape(x + dimCell, y, 10, 15));
            else
                shapes.put(player, new Shape(x + dimCell, y, 14, 23));
            pos.add(p);
        }
        GUI.getGamePanel().repaint();
    }

    @Override
    public void showUpdate() {
        show();
    }

    @Override
    public void addPlayer(String player) {
        switch(crosses.size()){
            case 0:
                crosses.put(player, "images/punchboard/faith.png");
                break;
            case 1:
                crosses.put(player, "images/punchboard/faithGreen.png");
                break;
            case 2:
                crosses.put(player, "images/punchboard/faithBlue.png");
                break;
            case 3:
                crosses.put(player, "images/punchboard/faithYellow.png");
                break;
            default:
                break;
        }
    }

    public Map<String, String> getCrosses() {
        return crosses;
    }
}
