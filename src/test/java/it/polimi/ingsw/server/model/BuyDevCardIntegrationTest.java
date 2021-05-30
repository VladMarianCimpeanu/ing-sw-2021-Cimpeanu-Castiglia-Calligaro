package it.polimi.ingsw.server.model;

import it.polimi.ingsw.server.controller.VirtualView;
import it.polimi.ingsw.server.model.benefit.Resource;
import it.polimi.ingsw.server.model.exceptions.*;
import it.polimi.ingsw.server.model.stubs.VirtualViewStub;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static it.polimi.ingsw.server.model.Color.BLUE;
import static it.polimi.ingsw.server.model.benefit.Resource.*;
import static org.junit.jupiter.api.Assertions.*;

public class BuyDevCardIntegrationTest {
    @Test
    void buy() throws NoSuchPlayerException, InvalidReadException, InvalidStepsException, IOException, InvalidDiscountException, NoCardException, NotEnoughResourcesException, WrongLevelException, NegativeQuantityException, InvalidShelfPosition, InvalidResourceException, ExistingResourceException, MissingExtraSlot, GameEndedException {
        ArrayList<Identity> identities = new ArrayList<>();
        identities.add(new Identity("danilo"));
        identities.add(new Identity("nick"));
        Game game = new Multiplayer(identities);
        VirtualView virtualView = new VirtualViewStub(game);

        Player player = game.getPlayers().get(0);

        assertThrows(NotEnoughResourcesException.class,
                () -> player.drawDevelopmentCard(BLUE, 1, null));
        //<cheat>
        player.getDashboard().getStrongbox().addResource(COIN, 10);
        player.getDashboard().getStrongbox().addResource(STONE, 10);
        player.getDashboard().getStrongbox().addResource(SHIELD, 10);
        player.getDashboard().getStrongbox().addResource(SERVANT, 10);
        player.getDashboard().getStrongbox().addProduced();

        player.getDashboard().getWarehouseDepot().addResource(1,1, COIN);
        player.getDashboard().getWarehouseDepot().addResource(2,2, SERVANT);
        player.getDashboard().getWarehouseDepot().addResource(3, 3, STONE);
        //</cheat>

        player.getDashboard().getWarehouseDepot().addExtraSlot(SHIELD,17);
        player.getDashboard().getWarehouseDepot().addExtraResource(SHIELD, 2);
        player.getDashboard().getWarehouseDepot().addExtraSlot(SERVANT,13);
        player.getDashboard().getWarehouseDepot().addExtraResource(SERVANT, 1);

        assertThrows(WrongLevelException.class,
                () -> player.drawDevelopmentCard(BLUE, 2, null));
        DevelopmentCard card = game.getDevelopmentCardSet().getAvailableDevelopmentCards().get(1 - 1).get(BLUE.getIndex()).peek();

        assertEquals(4, game.getDevelopmentCardSet().getAvailableQuantity(BLUE, 1));
        player.drawDevelopmentCard(BLUE, 1, null);
        assertEquals(3, game.getDevelopmentCardSet().getAvailableQuantity(Color.BLUE, 1));

        assertEquals(player.getDevelopmentCardCost(), card.getResourceCost());

        int cost = card.getResourceCost().getOrDefault(COIN,0);
        while(cost > 0){
            player.payFromStrongbox(COIN);
            cost--;
        }

        Map<Resource, Integer> map = card.getResourceCost();
        map.remove(COIN);
        if(!map.isEmpty()) {
            player.placeDevelopmentCard(2);
            assertTrue( player.getDashboard().getDevelopmentDecks().get(2 - 1).isEmpty());
        }

        cost = card.getResourceCost().getOrDefault(STONE,0);
        int i = 0;
        while(cost > 0 && i < 3){
            player.payFromWarehouseDepot(STONE);
            cost--;
            i++;
        }
        while(cost > 0){
            player.payFromStrongbox(STONE);
            cost--;
        }

        cost = card.getResourceCost().getOrDefault(SHIELD,0);
        i = 0;
        while(cost > 0 && i < 2){
            player.payFromExtraSlot(SHIELD);
            cost--;
            i++;
        }
        while(cost > 0){
            player.payFromStrongbox(SHIELD);
            cost--;
        }

        cost = card.getResourceCost().getOrDefault(SERVANT,0);
        if(cost > 0){
            player.payFromExtraSlot(SERVANT);
        }
        while(cost > 0){
            player.payFromStrongbox(SERVANT);
            cost--;
        }

        player.placeDevelopmentCard(2);

        assertEquals(card, player.getDashboard().getDevelopmentDecks().get(2 - 1).peek());
    }

    @Test
    void BuyWithDiscount() throws NegativeQuantityException, InvalidShelfPosition, InvalidResourceException, ExistingResourceException, MissingExtraSlot, WrongLevelException, NotEnoughResourcesException, NoSuchPlayerException, InvalidReadException, InvalidStepsException, IOException, InvalidDiscountException, NoCardException, GameEndedException {
        ArrayList<Identity> identities = new ArrayList<>();
        identities.add(new Identity("danilo"));
        identities.add(new Identity("nick"));
        Game game = new Multiplayer(identities);
        VirtualView virtualView = new VirtualViewStub(game);

        Player player = game.getPlayers().get(0);

        assertThrows(NotEnoughResourcesException.class,
                () -> player.drawDevelopmentCard(BLUE, 1, null));
        //<cheat>
        player.getDashboard().getStrongbox().addResource(COIN, 10);
        player.getDashboard().getStrongbox().addResource(STONE, 10);
        player.getDashboard().getStrongbox().addResource(SHIELD, 10);
        player.getDashboard().getStrongbox().addResource(SERVANT, 10);
        player.getDashboard().getStrongbox().addProduced();

        player.getDashboard().getWarehouseDepot().addResource(1,1, COIN);
        player.getDashboard().getWarehouseDepot().addResource(2,2, SERVANT);
        player.getDashboard().getWarehouseDepot().addResource(3, 3, STONE);
        //</cheat>

        player.getDashboard().getWarehouseDepot().addExtraSlot(SHIELD,17);
        player.getDashboard().getWarehouseDepot().addExtraResource(SHIELD, 2);
        player.getDashboard().getWarehouseDepot().addExtraSlot(SERVANT,13);
        player.getDashboard().getWarehouseDepot().addExtraResource(SERVANT, 1);

        ArrayList<Discount> discounts = new ArrayList<>();
        Discount discount = new Discount(SERVANT, 7);
        discounts.add(discount);

        assertThrows(InvalidDiscountException.class,
                () -> player.drawDevelopmentCard(BLUE, 1, discounts));
        DevelopmentCard card = game.getDevelopmentCardSet().getAvailableDevelopmentCards().get(1 - 1).get(BLUE.getIndex()).peek();

        player.addDiscount(discount);
        assertEquals(4, game.getDevelopmentCardSet().getAvailableQuantity(BLUE, 1));
        player.drawDevelopmentCard(BLUE, 1, discounts);
        assertEquals(3, game.getDevelopmentCardSet().getAvailableQuantity(Color.BLUE, 1));

        Map<Resource, Integer> costs = card.getResourceCost();
        if(costs.getOrDefault(SERVANT, 0) <= 1) costs.remove(SERVANT);
        else costs.replace(SERVANT, costs.get(SERVANT) - 1);
        assertEquals(player.getDevelopmentCardCost(), costs);

        int cost = card.getResourceCost().getOrDefault(COIN,0);
        while(cost > 0){
            player.payFromStrongbox(COIN);
            cost--;
        }

        Map<Resource, Integer> map = card.getResourceCost();
        map.remove(COIN);
        if(!map.isEmpty()) {
            player.placeDevelopmentCard(2);
            assertTrue( player.getDashboard().getDevelopmentDecks().get(2 - 1).isEmpty());
        }

        cost = card.getResourceCost().getOrDefault(STONE,0);
        int i = 0;
        while(cost > 0 && i < 3){
            player.payFromWarehouseDepot(STONE);
            cost--;
            i++;
        }
        while(cost > 0){
            player.payFromStrongbox(STONE);
            cost--;
        }

        cost = card.getResourceCost().getOrDefault(SHIELD,0);
        i = 0;
        while(cost > 0 && i < 2){
            player.payFromExtraSlot(SHIELD);
            cost--;
            i++;
        }
        while(cost > 0){
            player.payFromStrongbox(SHIELD);
            cost--;
        }

        cost = card.getResourceCost().getOrDefault(SERVANT,0);
        if(cost > 0){
            player.payFromExtraSlot(SERVANT);
        }
        while(cost > 0){
            player.payFromStrongbox(SERVANT);
            cost--;
        }

        player.placeDevelopmentCard(2);

        assertEquals(card, player.getDashboard().getDevelopmentDecks().get(2 - 1).peek());
    }
}
