package it.polimi.ingsw.client.modelLight.GUI;

import it.polimi.ingsw.client.*;
import it.polimi.ingsw.client.Shape;
import it.polimi.ingsw.client.modelLight.MarketView;

import java.awt.*;

public class MarketGUI extends MarketView implements Clickable {
    //The entire market
    private final static Shape dimension = new Shape(730,450,200,259);
    private final static int arrowSize = 24;//
    private final static Shape horizontalArrowDimension = new Shape(900,507,30,3 * arrowSize);//
    private final static Shape verticalArrowDimension = new Shape(786,600,4 * arrowSize,40);//
    private final Shape[][] positions;
    private final Shape outerShape;
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
        GUI.getGamePanel().addGameboard(this);
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

    @Override
    public boolean isClicked(int x, int y) {
        return((dimension.getX() <= x && x <= (dimension.getX() + dimension.getWidth())) && (dimension.getY() <= y && y <= (dimension.getY() + dimension.getHeight())));
    }

    @Override
    public void click(int x, int y) {
        if (horizontalArrowDimension.getX() <= x && x <= (horizontalArrowDimension.getX() + horizontalArrowDimension.getWidth())
        && (horizontalArrowDimension.getY() <= y && y <= (horizontalArrowDimension.getY() + horizontalArrowDimension.getHeight()))) {
            int arrow = y - horizontalArrowDimension.getY();
            if(0 <= arrow && arrow <= arrowSize){
                System.out.println("Select row 1");
            }else if(arrowSize <= arrow && arrow <= 2 * arrowSize){
                System.out.println("Select row 2");
            }else if(2 * arrowSize <= arrow && arrow <= 3 * arrowSize){
                System.out.println("Select row 3");
            }
        }
        if (verticalArrowDimension.getX() <= x && x <= (verticalArrowDimension.getX() + verticalArrowDimension.getWidth())
        && (verticalArrowDimension.getY() <= y && y <= (verticalArrowDimension.getY() + verticalArrowDimension.getHeight()))) {
            int arrow = x - verticalArrowDimension.getX();
            if(0 <= arrow && arrow <= arrowSize){
                System.out.println("Select column 1");
            }else if(arrowSize <= arrow && arrow <= 2 * arrowSize){
                System.out.println("Select column 2");
            }else if(2 * arrowSize <= arrow && arrow <= 3 * arrowSize){
                System.out.println("Select column 3");
            }else if(3 * arrowSize <= arrow && arrow <= 4 * arrowSize){
                System.out.println("Select column 4");
            }
        }
    }
}
