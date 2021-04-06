package it.polimi.ingsw.model.market;

import it.polimi.ingsw.model.MarketStrategy;
import it.polimi.ingsw.model.benefit.Benefit;
import it.polimi.ingsw.model.exceptions.OutOfBoundColumnsException;
import it.polimi.ingsw.model.exceptions.OutOfBoundRowException;

import java.util.ArrayList;
import java.util.Stack;

public class Market {

    private Marble[][] market;
    private Marble outerMarble;
    private ArrayList<Marble> selectedMarbles; //this array is update every time a row/column selection occurs
    public Market(){
    }

    /**
     *selects the specified row in the market and gets back the number of white marbles in the selected row.
     * @param row specific row to select in the market: the row on the top is 0.
     * @return the number of white Marbles gotten after the selection
     * @throws OutOfBoundRowException when row < 0 or row > 2
     */
    public int selRow(int row) throws OutOfBoundRowException {
        return 0; // TO DO
    }

    /**
     * selects the specified column in the market and gets back the number of white marbles in the selected column.
     * @param column specific column to select in the market: the column on the left is 0.
     * @return the number of white Marbles gotten after the selection
     * @throws OutOfBoundColumnsException when column < 0 or column > 3
     */
    public int selColumn(int column) throws OutOfBoundColumnsException {
        return 0; // TO DO
    }

    /**
     *
     * @return the actual configuration of the market.
     */
    public Marble[][] getMarket(){
        return null; // TO DO
    }

    /**
     * get the actual Marble that is not in the market
     * @return the actual Marble that is not in the market
     */
    public Marble getOuterMarble(){
        return null;
    }

    /**
     *
     * @param marketStrategies each element of the stack is used to convert white marbles gotten from a previous market's selection in specific benefits
     * @return benefits obtained by the white marbles
     * @throws NullPointerException
     */
    public Benefit[] convertMarbles(Stack<MarketStrategy> marketStrategies) throws NullPointerException{
        return null;
    }
}
