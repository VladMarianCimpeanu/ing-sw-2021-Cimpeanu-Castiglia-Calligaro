package it.polimi.ingsw.server.model;

import it.polimi.ingsw.server.controller.VirtualView;
import it.polimi.ingsw.server.model.benefit.Resource;
import it.polimi.ingsw.server.model.exceptions.*;
import it.polimi.ingsw.server.model.market.*;
import it.polimi.ingsw.server.model.stubs.VirtualViewStub;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class GoToMarketIntegrationTest {
    private static Player currPlayer;
    private static Market market;
    private static WarehouseDepot depot;

    @BeforeEach
    void init() throws NoSuchPlayerException, InvalidReadException, InvalidStepsException, IOException {
        ArrayList<Identity> ids = new ArrayList<>();
        ids.add(new Identity("RomCruise"));
        ids.add(new Identity("ThreadPitt"));
        Game game = new Multiplayer(ids);
        market = game.getMarket();
        VirtualView vv = new VirtualViewStub();
        game.getMarket().subscribe(vv);
        game.getFaithPath().subscribe(vv);
        for(Player player : game.getPlayers()) {
            player.subscribe(vv);
            player.getDashboard().getStrongbox().subscribe(vv);
            player.getDashboard().getWarehouseDepot().subscribe(vv);
        }
        currPlayer = game.getPlayers().get(0);
        depot = currPlayer.getDashboard().getWarehouseDepot();
    }

    @Test
    void selRowTest1() throws InvalidDirectionSelection, OutOfBoundColumnsException, OutOfBoundRowException {
        int pos = 2;
        Marble preOuter = market.getOuterMarble();
        ArrayList<Marble> rowOld = new ArrayList<>();
        for (int col = 0; col < 4; col++) rowOld.add(market.getMarket()[pos][col]);
        currPlayer.goToMarket("row", pos);
        ArrayList<Marble> rowNew = new ArrayList<>();
        for (int col = 0; col < 4; col++) rowNew.add(market.getMarket()[pos][col]);
        for(int index = 3; index > 0; index --) assertEquals(rowNew.get(index - 1), rowOld.get(index));
        assertEquals(rowNew.get(3), preOuter);
        assertEquals(rowOld.get(0), market.getOuterMarble());
    }

    @Test
    void selColTest1() throws InvalidDirectionSelection, OutOfBoundColumnsException, OutOfBoundRowException {
        int pos = 3;
        Marble preOuter = market.getOuterMarble();
        ArrayList<Marble> oldColumn = new ArrayList<>();
        for (int row = 0; row < 3; row++) oldColumn.add(market.getMarket()[row][pos]);
        currPlayer.goToMarket("column", pos);
        ArrayList<Marble> newColumn = new ArrayList<>();
        for (int row = 0; row < 3; row++) newColumn.add(market.getMarket()[row][pos]);
        for(int index = 2; index > 0; index --) assertEquals(newColumn.get(index - 1), oldColumn.get(index));
        assertEquals(newColumn.get(2), preOuter);
        assertEquals(oldColumn.get(0), market.getOuterMarble());
    }

    @Test
    void selRowTest2() throws InvalidDirectionSelection, OutOfBoundColumnsException, OutOfBoundRowException {
        int pos = 0;
        Marble preOuter = market.getOuterMarble();
        ArrayList<Marble> rowOld = new ArrayList<>();
        for (int col = 0; col < 4; col++) rowOld.add(market.getMarket()[pos][col]);
        currPlayer.goToMarket("row", pos);
        ArrayList<Marble> rowNew = new ArrayList<>();
        for (int col = 0; col < 4; col++) rowNew.add(market.getMarket()[pos][col]);
        for(int index = 3; index > 0; index --) assertEquals(rowNew.get(index - 1), rowOld.get(index));
        assertEquals(rowNew.get(3), preOuter);
        assertEquals(rowOld.get(0), market.getOuterMarble());
    }

    @Test
    void selColTest2() throws InvalidDirectionSelection, OutOfBoundColumnsException, OutOfBoundRowException {
        int pos = 0;
        Marble preOuter = market.getOuterMarble();
        ArrayList<Marble> oldColumn = new ArrayList<>();
        for (int row = 0; row < 3; row++) oldColumn.add(market.getMarket()[row][pos]);
        currPlayer.goToMarket("column", pos);
        ArrayList<Marble> newColumn = new ArrayList<>();
        for (int row = 0; row < 3; row++) newColumn.add(market.getMarket()[row][pos]);
        for(int index = 2; index > 0; index --) assertEquals(newColumn.get(index - 1), oldColumn.get(index));
        assertEquals(newColumn.get(2), preOuter);
        assertEquals(oldColumn.get(0), market.getOuterMarble());
    }

    @Test
    void selWrongColumn2(){
        assertThrows(OutOfBoundColumnsException.class, () ->
                currPlayer.goToMarket("column", -1));
    }

    @Test
    void selWrongColumn1(){
        assertThrows(OutOfBoundColumnsException.class, () ->
                currPlayer.goToMarket("column", 4));
    }

    @Test
    void selWrongRow1(){
        assertThrows(OutOfBoundRowException.class, () ->
                currPlayer.goToMarket("row", 3));
    }

    @Test
    void selWrongRow2(){
        assertThrows(OutOfBoundRowException.class, () ->
                currPlayer.goToMarket("row", -1));
    }

    @Test
    void receivedTheRightResources() throws InvalidDirectionSelection, OutOfBoundColumnsException, OutOfBoundRowException, InvalidStrategyException {
        currPlayer.goToMarket("column", 2);
        ArrayList<Marble> selected = market.getSelectedMarbles();
        currPlayer.passStrategiesToMarket();
        ArrayList<Resource> received = currPlayer.getReceivedFromMarket();
        selected.removeIf(marble -> marble.isWhite() || marble instanceof Red);
        assertEquals(selected.size(), received.size());
        assertEquals(selected.stream().filter(x -> x instanceof Blue).count(), received.stream().filter(x -> x == Resource.SHIELD).count());
        assertEquals(selected.stream().filter(x -> x instanceof Yellow).count(), received.stream().filter(x -> x == Resource.COIN).count());
        assertEquals(selected.stream().filter(x -> x instanceof Grey).count(), received.stream().filter(x -> x == Resource.STONE).count());
        assertEquals(selected.stream().filter(x -> x instanceof Purple).count(), received.stream().filter(x -> x == Resource.SERVANT).count());
    }

    @Test
    void positionOnTheRightShelf() throws InvalidDirectionSelection, OutOfBoundColumnsException, OutOfBoundRowException, InvalidStrategyException, InvalidShelfPosition, InvalidResourceException, ExistingResourceException, NotEnoughSpaceException {
        currPlayer.goToMarket("column", 2);
        currPlayer.passStrategiesToMarket();
        ArrayList<Resource> received = currPlayer.getReceivedFromMarket();
        Map<Resource, Integer> shelves = new HashMap<>();
        for(Resource resource : received) {
            for(int shelf = 3; shelf > 0 ; shelf --){
                if (depot.getShelfResource(shelf) == resource || depot.getShelfQuantity(shelf) == 0) {
                    currPlayer.putInWarehouseDepot(resource, shelf);
                    shelves.put(resource, shelf);
                    break;
                }
            }
        }
        for(Resource resource : shelves.keySet()) assertEquals(resource, depot.getShelfResource(shelves.get(resource)));
    }

    @Test
    void youMustUseYourStrategies() throws InvalidDirectionSelection, OutOfBoundColumnsException, OutOfBoundRowException, NoSuchStrategyException {
        currPlayer.addMarketStrategy(new MarketStrategy(Resource.COIN, 1));
        int whiteFound = currPlayer.goToMarket("column", 1);
        if(whiteFound > 0){
            assertThrows(InvalidStrategyException.class, () ->
                    currPlayer.passStrategiesToMarket());
        }
    }

    @Test
    void useStrategy() throws NoSuchStrategyException, InvalidDirectionSelection, OutOfBoundColumnsException, OutOfBoundRowException, InvalidStrategyException {
        Marble[][] grid = market.getMarket();
        currPlayer.addMarketStrategy(new MarketStrategy(Resource.COIN, 1));
        boolean foundWhite = false;
        int column = 0;
        for(; column < 4 && !foundWhite; column ++){
            for(int row = 0; row < 3 && !foundWhite; row ++) {
                if(grid[row][column].isWhite()) foundWhite = true;
            }
        }
        int white = currPlayer.goToMarket("column", column);
        long coins = white + market.getSelectedMarbles().stream().filter(x -> x instanceof Yellow).count();
        for(int added = 0; added < white; added ++) currPlayer.addInMarketStrategyStack(currPlayer.getMarketStrategies().get(0));
        currPlayer.passStrategiesToMarket();
        assertEquals(coins, currPlayer.getReceivedFromMarket().stream().filter(x -> x == Resource.COIN).count());
    }

    @Test
    void putInExtraSlot() throws InvalidDirectionSelection, OutOfBoundColumnsException, OutOfBoundRowException, InvalidStrategyException, InvalidResourceException, NotEnoughSpaceException, MissingExtraSlot {
        currPlayer.goToMarket("row",1 );
        currPlayer.passStrategiesToMarket();
        int firstSize = currPlayer.getReceivedFromMarket().size();
        if(firstSize > 0){
            Resource resToKeep = currPlayer.getReceivedFromMarket().get(0);
            depot.addExtraSlot(resToKeep, 1);
            currPlayer.putInExtraSlot(resToKeep);
            assertEquals(firstSize, currPlayer.getReceivedFromMarket().size() + 1);
            assertEquals(1, depot.getExtraQuantity(resToKeep));
        }

    }
}