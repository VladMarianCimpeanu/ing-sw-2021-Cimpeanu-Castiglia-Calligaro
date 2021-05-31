package it.polimi.ingsw.server.controller.states;

import it.polimi.ingsw.server.ClientHandler;
import it.polimi.ingsw.server.Server;
import it.polimi.ingsw.server.controller.Controller;
import it.polimi.ingsw.server.controller.stubs.EchoStub;
import it.polimi.ingsw.server.model.*;
import it.polimi.ingsw.server.model.benefit.Resource;
import it.polimi.ingsw.server.model.exceptions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static it.polimi.ingsw.server.model.benefit.Resource.*;
import static org.junit.jupiter.api.Assertions.*;

class BuyDevStateTest {
    private static Controller controller;

    @BeforeEach
    void init() {
        ArrayList<Identity> identities = new ArrayList<>();
        identities.add(new Identity("ThreadPitt"));
        identities.add(new Identity("RomCruise"));
        ClientHandler handler = new EchoStub();
        Server.addNickname("ThreadPitt", handler);
        Server.addNickname("RomCruise", handler);
        controller = new Controller(identities);
        controller.startGame();
        controller.setCurrentState(new SelectionState(controller));
    }

    void addResources(Strongbox strongbox, int quantity) throws NegativeQuantityException {
        for(Resource res : Resource.values()) strongbox.addResource(res, quantity);
        strongbox.addProduced();
    }
    String getState() {
        return controller.getCurrentState().getClass().getName();
    }

    @Test
    void fromSelectionStateToPayDevState() throws NegativeQuantityException, WrongLevelException, NoCardException {
        Player player = controller.getCurrentPlayer();
        addResources(player.getDashboard().getStrongbox(), 50);
        Color color = Color.PURPLE;
        int level = 1;
        controller.getCurrentState().buyDevCard(color, level, new ArrayList<>());
        assertEquals(getState(), BuyDevState.class.getName());
    }

    /**
     * checks that if in a normal situation, complete turn is called, the payment completes correctly and the game ends in SelectionState.
     * @throws NegativeQuantityException
     * @throws WrongLevelException
     * @throws NoCardException
     * @throws InvalidDeckPositionException
     */
    @Test
    void completeTurn() throws NegativeQuantityException, WrongLevelException, NoCardException, InvalidDeckPositionException, GameEndedException {
        Player player = controller.getCurrentPlayer();
        int quantityToAdd = 50;
        addResources(player.getDashboard().getStrongbox(), quantityToAdd);
        Color color = Color.GREEN;
        int level = 1;
        DevelopmentCard devToBuy = controller.getGame().getDevelopmentCardSet().peekCard(color, level);
        controller.getCurrentState().buyDevCard(color, level, new ArrayList<>());
        controller.getCurrentState().completeTurn();
        assertEquals(getState(), SelectionState.class.getName());
        assertEquals(devToBuy, player.getDashboard().getCardOnTop(1));
        Map<Resource, Integer> total = new HashMap<>(devToBuy.getResourceCost());
        for (Resource resource: total.keySet()) total.put(resource, quantityToAdd - total.get(resource));
        assertEquals(player.getDashboard().getStrongbox().getResourceQuantity(COIN), total.getOrDefault(COIN, quantityToAdd));
        assertEquals(player.getDashboard().getStrongbox().getResourceQuantity(SHIELD), total.getOrDefault(SHIELD, quantityToAdd));
        assertEquals(player.getDashboard().getStrongbox().getResourceQuantity(SERVANT), total.getOrDefault(SERVANT, quantityToAdd));
        assertEquals(player.getDashboard().getStrongbox().getResourceQuantity(STONE), total.getOrDefault(STONE, quantityToAdd));
    }

    @Test
    void notEnoughResourcesToBuy() {
        Player player = controller.getCurrentPlayer();
        controller.getCurrentState().buyDevCard(Color.YELLOW, 1, new ArrayList<>());
        assertEquals(getState(), SelectionState.class.getName());
        assertThrows(NoCardException.class, () ->
                player.getDashboard().getCardOnTop(1));
        assertThrows(NoCardException.class, () ->
                player.getDashboard().getCardOnTop(2));
        assertThrows(NoCardException.class, () ->
                player.getDashboard().getCardOnTop(3));
    }

    @Test
    void cardNotPlaceable() throws NegativeQuantityException, WrongLevelException, NoCardException {
        Player player = controller.getCurrentPlayer();
        addResources(player.getDashboard().getStrongbox(), 50);
        Color color = Color.GREEN;
        int level = 2;
        controller.getGame().getDevelopmentCardSet().peekCard(color, level);
        controller.getCurrentState().buyDevCard(color, level, new ArrayList<>());
        assertEquals(getState(), SelectionState.class.getName());
    }

    @Test
    void normalPayment() throws NegativeQuantityException, WrongLevelException, NoCardException {
       Player player = controller.getCurrentPlayer();
       String from = "strongbox";
       addResources(player.getDashboard().getStrongbox(), 50);
       DevelopmentCard developmentCard = controller.getGame().getDevelopmentCardSet().peekCard(Color.BLUE, 1);
       controller.getCurrentState().buyDevCard(Color.BLUE, 1, new ArrayList<>());
       ArrayList<Resource> resourcesToPay = new ArrayList<>();
       for(Resource res : developmentCard.getResourceCost().keySet())
           for(int index = 0; index < developmentCard.getResourceCost().get(res); index ++) resourcesToPay.add(res);
       for(Resource res: resourcesToPay){
           assertEquals(getState(), BuyDevState.class.getName());
           controller.getCurrentState().pay(res, from);
       }
       assertEquals(getState(), PlaceDevState.class.getName());
       controller.getCurrentState().placeDevCard(1);
       assertEquals(getState(), EndTurnState.class.getName());
       controller.getCurrentState().end();
       assertEquals(getState(), SelectionState.class.getName());
    }

    @Test
    void wrongPlace() throws NegativeQuantityException, WrongLevelException, NoCardException {
        Player player = controller.getCurrentPlayer();
        addResources(player.getDashboard().getStrongbox(), 10);
        Color color = Color.GREEN;
        String from = "strongbox";
        int level = 1;
        DevelopmentCard developmentCard = controller.getGame().getDevelopmentCardSet().peekCard(color, level);
        controller.getCurrentState().buyDevCard(color, level, new ArrayList<>());
        ArrayList<Resource> resourcesToPay = new ArrayList<>();
        for(Resource res : developmentCard.getResourceCost().keySet())
            for(int index = 0; index < developmentCard.getResourceCost().get(res); index ++)
                resourcesToPay.add(res);
        for(Resource res: resourcesToPay)
            controller.getCurrentState().pay(res, from);
        assertEquals(PlaceDevState.class.getName(), getState());
        controller.getCurrentState().placeDevCard(0);
        assertEquals(PlaceDevState.class.getName(), getState());
        controller.getCurrentState().placeDevCard(1);
        assertEquals(EndTurnState.class.getName(), getState()) ;
    }

    @Test
    void wrongPlace2() throws NegativeQuantityException, WrongLevelException, NoCardException {
        Player player = controller.getCurrentPlayer();
        addResources(player.getDashboard().getStrongbox(), 10);
        Color color = Color.BLUE;
        String from = "strongbox";
        int level = 1;
        DevelopmentCard developmentCard = controller.getGame().getDevelopmentCardSet().peekCard(color, level);
        controller.getCurrentState().buyDevCard(color, level, new ArrayList<>());
        ArrayList<Resource> resourcesToPay = new ArrayList<>();
        for(Resource res : developmentCard.getResourceCost().keySet())
            for(int index = 0; index < developmentCard.getResourceCost().get(res); index ++)
                resourcesToPay.add(res);
        for(Resource res: resourcesToPay)
            controller.getCurrentState().pay(res, from);
        assertEquals(PlaceDevState.class.getName(), getState());
        controller.getCurrentState().placeDevCard(4);
        assertEquals(PlaceDevState.class.getName(), getState());
        controller.getCurrentState().placeDevCard(3);
        assertEquals(EndTurnState.class.getName(), getState());
    }

    @Test
    void multipleCards1() throws NegativeQuantityException, WrongLevelException, NoCardException {
        Player player = controller.getCurrentPlayer();
        String currentNickname = player.getNickName();
        addResources(player.getDashboard().getStrongbox(), 20);
        Color color = Color.BLUE;
        String from = "strongbox";
        DevelopmentCard developmentCard = controller.getGame().getDevelopmentCardSet().peekCard(color, 1);
        controller.getCurrentState().buyDevCard(color, 1, new ArrayList<>());
        ArrayList<Resource> resourcesToPay = new ArrayList<>();
        //PAYMENT OF THE FIRST CARD
        for(Resource res : developmentCard.getResourceCost().keySet())
            for(int index = 0; index < developmentCard.getResourceCost().get(res); index ++)
                resourcesToPay.add(res);
        for(Resource res: resourcesToPay)
            controller.getCurrentState().pay(res, from);
        controller.getCurrentState().placeDevCard(1);
        controller.getCurrentState().end();
        controller.setCurrentUser(currentNickname);
        developmentCard = controller.getGame().getDevelopmentCardSet().peekCard(color, 2);
        controller.getCurrentState().buyDevCard(color, 2, new ArrayList<>());
        resourcesToPay = new ArrayList<>();
        //PAYMENT OF THE SECOND CARD
        for(Resource res : developmentCard.getResourceCost().keySet())
            for(int index = 0; index < developmentCard.getResourceCost().get(res); index ++)
                resourcesToPay.add(res);
        for(Resource res: resourcesToPay)
            controller.getCurrentState().pay(res, from);
        controller.getCurrentState().placeDevCard(2);
        assertEquals(PlaceDevState.class.getName(), getState());
        controller.getCurrentState().placeDevCard(1);
        assertEquals(EndTurnState.class.getName(), getState());
    }

    @Test
    void notExistingLevel() throws NegativeQuantityException {
        Player player = controller.getCurrentPlayer();
        addResources(player.getDashboard().getStrongbox(), 20);
        controller.getCurrentState().buyDevCard(Color.PURPLE, 0, new ArrayList<>());
        assertEquals(SelectionState.class.getName(), getState());
        controller.getCurrentState().buyDevCard(Color.PURPLE, 1, new ArrayList<>());
        assertEquals(BuyDevState.class.getName(), getState());
    }

    @Test
    void notExistingLevel2() throws NegativeQuantityException {
        Player player = controller.getCurrentPlayer();
        addResources(player.getDashboard().getStrongbox(), 20);
        controller.getCurrentState().buyDevCard(Color.PURPLE, 4, new ArrayList<>());
        assertEquals(SelectionState.class.getName(), getState());
        controller.getCurrentState().buyDevCard(Color.PURPLE, 1, new ArrayList<>());
        assertEquals(BuyDevState.class.getName(), getState());
    }
}