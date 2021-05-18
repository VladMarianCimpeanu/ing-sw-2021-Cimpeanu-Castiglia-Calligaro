package it.polimi.ingsw.client.modelLight;

import it.polimi.ingsw.client.Marble;

import java.util.ArrayList;

public abstract class MarketView {
    protected Marble marketGrid[][];
    protected Marble outerMarble;
    public abstract void show();
    public abstract void showColUpdate();
    public abstract void showRowUpdate();
    public void updateColumn(int position, Marble[] content, Marble outerMarble){
        for(int i = 0; i<3; i++)
            marketGrid[i][position] = content[i];
        this.outerMarble = outerMarble;
    }
    public void updateRow(int position, Marble[] content, Marble outerMarble){
        for(int i = 0; i<4; i++)
            marketGrid[position][i] = content[i];
        this.outerMarble = outerMarble;
    }
    public void setUp(Marble marketGrid[][], Marble outerMarble){
        this.marketGrid = marketGrid;
        this.outerMarble = outerMarble;
    }
}