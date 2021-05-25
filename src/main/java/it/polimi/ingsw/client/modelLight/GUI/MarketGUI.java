package it.polimi.ingsw.client.modelLight.GUI;

import it.polimi.ingsw.client.GUI;
import it.polimi.ingsw.client.GamePanel;
import it.polimi.ingsw.client.Marble;
import it.polimi.ingsw.client.Shape;
import it.polimi.ingsw.client.modelLight.MarketView;

import java.awt.*;

public class MarketGUI extends MarketView {
    private Shape[][] positions;
    private Shape outerShape;
    private final static int startX = 785;
    private final static int startY = 507;
    private final static int width = 23;
    private final static int height = 23;
    private final static int margin = 0;
    private final static int outerX = 870;
    private final static int outerY = 486;

    public MarketGUI(){
        positions = new Shape[3][4];
        for(int r = 0; r < 3; r++){
            for(int c = 0; c < 4; c++){
                positions[r][c] = new Shape((startX + c * (width + margin)),(startY + r * (height + margin)), width, height);
            }
        }
        outerShape = new Shape(outerX, outerY, width, height);
    }

    @Override
    public void show() {

    }

    public void print(Graphics g){
        for(int r = 0; r < 3; r++){
            for(int c = 0; c < 4; c++){
                GamePanel.drawImage(g, marketGrid[r][c].url(), positions[r][c]);
            }
        }
        GamePanel.drawImage(g, outerMarble.url(), outerShape);
    }

    @Override
    public void updateColumn(int position, Marble[] content, Marble outerMarble) {
        super.updateColumn(position, content, outerMarble);
        GUI.getGamePanel().repaint();
    }

    @Override
    public void updateRow(int position, Marble[] content, Marble outerMarble) {
        super.updateRow(position, content, outerMarble);
        GUI.getGamePanel().repaint();
    }

    @Override
    public void setUp(Marble[][] marketGrid, Marble outerMarble) {
        super.setUp(marketGrid, outerMarble);
        GUI.getGamePanel().repaint();
    }
}
