package it.polimi.ingsw.client.modelLight;

import it.polimi.ingsw.client.Marble;

/**
 * This class contains all the information about the current market of a specific game, in order to display it.
 */
public abstract class MarketView {
    protected Marble marketGrid[][];
    protected Marble outerMarble;
    public abstract void show();
    public void updateColumn(int position, Marble[] content, Marble outerMarble){
        for(int i = 0; i<3; i++)
            marketGrid[i][position] = content[i];
        this.outerMarble = outerMarble;
        show();
    }
    public void updateRow(int position, Marble[] content, Marble outerMarble){
        for(int i = 0; i<4; i++)
            marketGrid[position][i] = content[i];
        this.outerMarble = outerMarble;
        show();
    }
    public void setUp(Marble marketGrid[][], Marble outerMarble){
        this.marketGrid = marketGrid;
        this.outerMarble = outerMarble;
    }
}