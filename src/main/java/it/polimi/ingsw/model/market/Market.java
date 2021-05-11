package it.polimi.ingsw.model.market;

import it.polimi.ingsw.controller.VirtualView;
import it.polimi.ingsw.model.MarketStrategy;
import it.polimi.ingsw.model.benefit.Benefit;
import it.polimi.ingsw.model.exceptions.InvalidStrategyException;
import it.polimi.ingsw.model.exceptions.OutOfBoundColumnsException;
import it.polimi.ingsw.model.exceptions.OutOfBoundRowException;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Stack;
import java.util.stream.IntStream;

/**
 * A market is a 3x4 array of marbles (+ 1 outerMarble), it is randomized in the constructor.
 * You can go to market using selRow() or selColumn(): with selRow you select a row of the market
 * and pick all the marbles, converting them with convertMarbles(); after that the selected row
 * is shifted of 1 position from right to left, putting the outerMarble in the first position from right.
 */
public class Market {
    private final int rows = 3;
    private final int columns = 4;
    private Marble[][] market;
    private Marble outerMarble;
    private ArrayList<Marble> selectedMarbles; //this array is update every time a row/column selection occurs
    private VirtualView virtualView;

    public Market(){
        selectedMarbles = new ArrayList<>();
        ArrayList<Pair<Marble,Integer>> availableMarbles = new ArrayList<Pair<Marble,Integer>>();
        availableMarbles.add(new Pair<>(new Blue(), 2));
        availableMarbles.add(new Pair<>(new Purple(), 2));
        availableMarbles.add(new Pair<>(new Yellow(), 2));
        availableMarbles.add(new Pair<>(new Grey(), 2));
        availableMarbles.add(new Pair<>(new White(), 4));
        availableMarbles.add(new Pair<>(new Red(), 1));

        market = new Marble[rows][columns];

        //random initialize market
        Random generator = new Random();
        for(int i = 0; i < rows; i++){
            for(int j = 0; j < columns; j++){
                int random = generator.nextInt(availableMarbles.size());
                Pair<Marble, Integer> selected = availableMarbles.get(random);
                market[i][j] = selected.getKey();
                if(selected.getValue() > 1){
                    availableMarbles.set(random, new Pair<>(selected.getKey(),selected.getValue()-1));
                }else{
                    availableMarbles.remove(random);
                }
            }
        }
        outerMarble = availableMarbles.get(0).getKey();
    }

    /**
     *selects the specified row in the market and gets back the number of white marbles in the selected row.
     * @param row specific row to select in the market: the row on the top is 0.
     * @return the number of white Marbles gotten after the selection
     * @throws OutOfBoundRowException when row < 0 or row > 2
     */
    public int selRow(int row) throws OutOfBoundRowException {
        if(row < 0 || row >= rows) throw new OutOfBoundRowException(Integer.toString(row));
        for(int i = 0; i < columns; i++){
            selectedMarbles.add(market[row][i]);
        }
        //marbles shift
        Marble oldOuter = outerMarble;
        outerMarble = market[row][0];
        for(int i = 0; i < columns - 1; i++){
            market[row][i] = market[row][i+1];
        }
        market[row][columns - 1] = oldOuter;
        virtualView.updateMarketRow(market[row], outerMarble, row);
        return (int) selectedMarbles.stream().filter(Marble::isWhite).count();
    }

    /**
     * selects the specified column in the market and gets back the number of white marbles in the selected column.
     * @param column specific column to select in the market: the column on the left is 0.
     * @return the number of white Marbles gotten after the selection
     * @throws OutOfBoundColumnsException when column < 0 or column > 3
     */
    public int selColumn(int column) throws OutOfBoundColumnsException {
        if(column < 0 || column >= columns) throw new OutOfBoundColumnsException(Integer.toString(column));
        for(int i = 0; i < rows; i++){
            selectedMarbles.add(market[i][column]);
        }
        //marbles shift
        Marble oldOuter = outerMarble;
        Marble[] newcolumn = new Marble[rows];
        outerMarble = market[0][column];
        for(int i = 0; i < rows - 1; i++){
            market[i][column] = market[i+1][column];
            newcolumn[i] = market[i][column];
        }
        market[rows-1][column] = oldOuter;
        newcolumn[rows-1] = oldOuter;
        virtualView.updateMarketColumn(newcolumn, outerMarble, column);
        return (int) selectedMarbles.stream().filter(Marble::isWhite).count();
    }

    /**
     *
     * @return the actual configuration of the market.
     */
    public Marble[][] getMarket(){
        Marble[][] clonedArray = new Marble[rows][columns];
        for(int i = 0; i < rows; i++){
            clonedArray[i] = market[i].clone();
        }
        return clonedArray;
    }

    public String[][] getMarketString(){
        String[][] clonedArray = new String[rows][columns];
        for(int i = 0; i < rows; i++){
            for(int j = 0; j < columns; j++)
                clonedArray[i][j] = market[i][j].toString();
        }
        return clonedArray;
    }

    /**
     * get the actual Marble that is not in the market
     * @return the actual Marble that is not in the market
     */
    public Marble getOuterMarble(){
        return outerMarble;
    }

    /**
     *
     * @param marketStrategies each element of the stack is used to convert white marbles gotten from a previous market's selection in specific benefits
     * @return benefits obtained by all the marbles
     * @throws NullPointerException
     * @throws InvalidStrategyException if the number of strategies is different from the number of white marbles
     */
    public ArrayList<Benefit> convertMarbles(Stack<MarketStrategy> marketStrategies) throws NullPointerException, InvalidStrategyException {
        if(marketStrategies == null || marketStrategies.contains(null)) throw new NullPointerException();
        if(marketStrategies.size() != selectedMarbles.stream().filter(Marble::isWhite).count()) throw new InvalidStrategyException();
        Stack<MarketStrategy> strategies = (Stack<MarketStrategy>) marketStrategies.clone();
        ArrayList<Benefit> converted = new ArrayList<>();
        for(Marble m: selectedMarbles){
            Benefit benefitReceived = m.getBenefit(strategies);
            converted.add(benefitReceived);
        }
        return converted;
    }

    /**
     * This method is used to convertMarbles without MarketStrategies
     * @return benefits obtained by all the marbles
     */
    public ArrayList<Benefit> convertMarbles(){
        Stack<MarketStrategy> strategies = new Stack<>();
        ArrayList<Benefit> converted = new ArrayList<>();
        for(Marble m: selectedMarbles){
            Benefit benefitReceived = m.getBenefit(strategies);
            converted.add(benefitReceived);
        }
        return converted;
    }

    public void subscribe(VirtualView virtualView){
        this.virtualView = virtualView;
    }
}
