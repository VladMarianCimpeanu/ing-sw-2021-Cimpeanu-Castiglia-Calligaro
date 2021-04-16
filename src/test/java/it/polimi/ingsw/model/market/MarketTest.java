package it.polimi.ingsw.model.market;

import it.polimi.ingsw.model.exceptions.OutOfBoundColumnsException;
import it.polimi.ingsw.model.exceptions.OutOfBoundRowException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledIf;

import static org.junit.jupiter.api.Assertions.*;

class MarketTest {

    @Test
    @DisplayName("Corner cases throw OutOfBoundColumnsException")
    void selColumnOutofBound() {
        Market market = new Market();
        assertThrows(OutOfBoundColumnsException.class, () ->{
            market.selColumn(4);
        });
        assertThrows(OutOfBoundColumnsException.class, () ->{
            market.selColumn(-1);
        });
        assertThrows(OutOfBoundColumnsException.class, () ->{
            market.selColumn(5);
        });
        assertThrows(OutOfBoundColumnsException.class, () ->{
            market.selColumn(6);
        });
    }
    @Test
    @DisplayName("Corner cases throw OutOfBoundRowException")
    void selRowOutofBound() {
        Market market = new Market();
        assertThrows(OutOfBoundRowException.class, () ->{
            market.selRow(3);
        });
        assertThrows(OutOfBoundRowException.class, () ->{
            market.selRow(-1);
        });
        assertThrows(OutOfBoundRowException.class, () ->{
            market.selRow(4);
        });
        assertThrows(OutOfBoundRowException.class, () ->{
            market.selRow(5);
        });
    }

    @Test
    @DisplayName("Column selection without exceptions")
    void selCol() throws OutOfBoundColumnsException {
        Market market = new Market();
        Marble [][] pre_grid = market.getMarket();
        Marble pre_outerMarble = market.getOuterMarble();
        int check_white = 0;
        for(int i = 0; i<3; i++)
            if(pre_grid[i][0].isWhite()) check_white++;
        int whiteMarbles = market.selColumn(0);
        assertEquals(check_white, whiteMarbles);
        Marble [][] post_grid = market.getMarket();
        Marble post_outerMarble = market.getOuterMarble();
        assertEquals(pre_outerMarble, post_grid[2][0]);
        assertEquals(pre_grid[0][0], post_outerMarble);
        assertEquals(pre_grid[1][0], post_grid[0][0]);
        assertEquals(pre_grid[2][0], post_grid[1][0]);
    }

    @Test
    @DisplayName("Row selection without exceptions")
    void selRow() throws OutOfBoundRowException {
        Market market = new Market();
        Marble [][] pre_grid = market.getMarket();
        Marble pre_outerMarble = market.getOuterMarble();
        int check_white = 0;
        for(int i = 0; i<4; i++)
            if(pre_grid[0][i].isWhite()) check_white++;
        int whiteMarbles = market.selRow(0);
        assertEquals(check_white, whiteMarbles);
        Marble [][] post_grid = market.getMarket();
        Marble post_outerMarble = market.getOuterMarble();
        assertEquals(pre_grid[0][0], post_outerMarble);
        assertEquals(pre_grid[0][1], post_grid[0][0]);
        assertEquals(pre_grid[0][2], post_grid[0][1]);
        assertEquals(pre_grid[0][3], post_grid[0][2]);
        assertEquals(pre_outerMarble, post_grid[0][3]);
    }

    @Test
    void convertMarbles() {
        //really needed: seems a sort of getter
    }
}