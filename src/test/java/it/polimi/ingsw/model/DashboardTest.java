package it.polimi.ingsw.model;

import it.polimi.ingsw.model.benefit.Resource;
import it.polimi.ingsw.model.exceptions.InvalidDeckPositionException;
import it.polimi.ingsw.model.exceptions.InvalidExtraPositionException;
import it.polimi.ingsw.model.exceptions.NotEnoughResourcesException;
import it.polimi.ingsw.model.exceptions.WrongLevelException;
import it.polimi.ingsw.model.stubs.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.NoSuchFileException;
import java.util.ArrayList;
import java.util.Stack;

import static org.junit.jupiter.api.Assertions.*;

class DashboardTest {
    Dashboard dashboard;
    WarehouseDepotStub warehouseDepotStub;
    StrongboxStub strongboxStub;

    @BeforeEach
    void init() {
        warehouseDepotStub = new WarehouseDepotStub();
        strongboxStub = new StrongboxStub();
        dashboard = new Dashboard(strongboxStub,warehouseDepotStub);
    }

    @Test
    void addDevelopmentCard() throws NoSuchFileException, InvalidDeckPositionException, WrongLevelException, NotEnoughResourcesException {
        warehouseDepotStub.content.put(Resource.COIN,10);

        DevelopmentCardStub card = new DevelopmentCardStub(1);
        dashboard.addDevelopmentCard(card,1, null);
        DevelopmentCard result = dashboard.getDevelopmentDecks().get(0).peek();
        assertTrue(result.equals(card) && dashboard.getDevelopmentDecks().get(0).empty() && dashboard.getDevelopmentDecks().get(1).empty());
    }

    @Test
    void zeroAddDevelopmentCard() throws NoSuchFileException, InvalidDeckPositionException, WrongLevelException {
        warehouseDepotStub.content.put(Resource.COIN,10);

        DevelopmentCardStub card = new DevelopmentCardStub(1);
        assertThrows(InvalidDeckPositionException.class,
                () -> dashboard.addDevelopmentCard(card,0, null));
        assertTrue(dashboard.getDevelopmentDecks().get(0).empty() && dashboard.getDevelopmentDecks().get(1).empty() && dashboard.getDevelopmentDecks().get(2).empty());
    }

    @Test
    void invalidAddDevelopmentCard() throws NoSuchFileException, InvalidDeckPositionException, WrongLevelException {
        warehouseDepotStub.content.put(Resource.COIN,10);

        DevelopmentCardStub card = new DevelopmentCardStub(1);
        assertThrows(InvalidDeckPositionException.class,
                () -> dashboard.addDevelopmentCard(card,4, null));
        assertTrue(dashboard.getDevelopmentDecks().get(0).empty() && dashboard.getDevelopmentDecks().get(1).empty() && dashboard.getDevelopmentDecks().get(2).empty());

    }

    @Test
    void addTwoDevelopmentCards() throws NoSuchFileException, InvalidDeckPositionException, WrongLevelException, NotEnoughResourcesException {
        warehouseDepotStub.content.put(Resource.COIN,10);

        DevelopmentCardStub card1 = new DevelopmentCardStub(1,1);
        DevelopmentCardStub card2 = new DevelopmentCardStub(2,2);

        dashboard.addDevelopmentCard(card1,2, null);
        dashboard.addDevelopmentCard(card2,2, null);

        DevelopmentCard result2 = dashboard.getDevelopmentDecks().get(1).pop();
        //To check that this method returns a copy of the Stack and not a reference
        Stack<DevelopmentCard> result = dashboard.getDevelopmentDecks().get(1);
        result.pop();
        DevelopmentCard result1 = result.pop();

        assertTrue(result1.equals(card1));
        assertTrue(result2.equals(card2));
    }

    @Test
    void wrongLevelDevelopmentCards() throws NoSuchFileException, InvalidDeckPositionException, WrongLevelException {
        warehouseDepotStub.content.put(Resource.COIN,10);

        DevelopmentCardStub card = new DevelopmentCardStub(1,2);

        assertThrows(WrongLevelException.class,
                () -> dashboard.addDevelopmentCard(card,1, null));
        DevelopmentCard result = dashboard.getDevelopmentDecks().get(0).peek();
        assertTrue(dashboard.getDevelopmentDecks().get(0).empty() && dashboard.getDevelopmentDecks().get(1).empty() && dashboard.getDevelopmentDecks().get(2).empty());
    }

    @Test
    void twoWrongLevelDevelopmentCards() throws NoSuchFileException, InvalidDeckPositionException, WrongLevelException, NotEnoughResourcesException {
        warehouseDepotStub.content.put(Resource.COIN,10);

        DevelopmentCardStub card1 = new DevelopmentCardStub(1,1);
        DevelopmentCardStub card2 = new DevelopmentCardStub(2,3);

        dashboard.addDevelopmentCard(card1,3, null);

        assertThrows(WrongLevelException.class,
                () -> dashboard.addDevelopmentCard(card2,3, null));

        DevelopmentCard result1 = dashboard.getDevelopmentDecks().get(2).pop();
        Stack<DevelopmentCard> result = dashboard.getDevelopmentDecks().get(2);
        result.pop();

        assertTrue(result1.equals(card1));
        assertTrue(result.empty());
    }

    @Test
    void buyDevelopmentCard() throws NoSuchFileException, InvalidDeckPositionException, NotEnoughResourcesException, WrongLevelException {
        warehouseDepotStub.content.put(Resource.COIN,10);

        DevelopmentCardStub card = new DevelopmentCardStub(1);
        dashboard.addDevelopmentCard(card,3, null);
        int result = warehouseDepotStub.content.get(Resource.COIN);

        assertTrue(result == 7);
    }

    @Test
    void buyDevelopmentCardStrongbox() throws NoSuchFileException, InvalidDeckPositionException, NotEnoughResourcesException, WrongLevelException {
        warehouseDepotStub.content.put(Resource.COIN,1);
        strongboxStub.content.put(Resource.COIN,10);

        DevelopmentCardStub card = new DevelopmentCardStub(1);
        dashboard.addDevelopmentCard(card,3, null);

        int result1 = warehouseDepotStub.content.get(Resource.COIN);
        int result2 = strongboxStub.content.get(Resource.COIN);

        assertTrue(result1 == 0 && result2 == 8);
    }

    @Test
    void notEnoughResources() throws NoSuchFileException, InvalidDeckPositionException, NotEnoughResourcesException, WrongLevelException {
        warehouseDepotStub.content.put(Resource.COIN,1);
        strongboxStub.content.put(Resource.COIN,1);

        DevelopmentCardStub card = new DevelopmentCardStub(1);
        assertThrows(NotEnoughResourcesException.class,
                () -> dashboard.addDevelopmentCard(card,3, null));

        int result1 = warehouseDepotStub.content.get(Resource.COIN);
        int result2 = strongboxStub.content.get(Resource.COIN);

        assertTrue(result1 == 1 && result2 == 1);
    }

    @Test
    void zeroResources() throws NoSuchFileException, InvalidDeckPositionException, NotEnoughResourcesException, WrongLevelException {
        warehouseDepotStub.content.put(Resource.COIN,0);
        strongboxStub.content.put(Resource.COIN,3);

        DevelopmentCardStub card = new DevelopmentCardStub(1);
        dashboard.addDevelopmentCard(card,3, null);

        int result1 = warehouseDepotStub.content.get(Resource.COIN);
        int result2 = strongboxStub.content.get(Resource.COIN);

        assertTrue(result1 == 0 && result2 == 0);
    }

    @Test
    void buyAndAddDevelopmentCard() throws NoSuchFileException, InvalidDeckPositionException, NotEnoughResourcesException, WrongLevelException {
        warehouseDepotStub.content.put(Resource.COIN,1);
        strongboxStub.content.put(Resource.COIN,10);

        DevelopmentCardStub card = new DevelopmentCardStub(2);
        dashboard.addDevelopmentCard(card,3, null);

        DevelopmentCard resultCard = dashboard.getDevelopmentDecks().get(1).peek();

        int result1 = warehouseDepotStub.content.get(Resource.COIN);
        int result2 = strongboxStub.content.get(Resource.COIN);

        assertTrue(result1 == 0 && result2 == 8 && resultCard.equals(card));
    }

    @Test
    void addDevelopmentCardDiscount() throws NoSuchFileException, InvalidDeckPositionException, NotEnoughResourcesException, WrongLevelException {
        warehouseDepotStub.content.put(Resource.COIN,10);

        Discount discount = new Discount(Resource.COIN);
        ArrayList<Discount> discounts = new ArrayList<Discount>();
        discounts.add(discount);

        DevelopmentCardStub card = new DevelopmentCardStub(1);
        dashboard.addDevelopmentCard(card,3, discounts);
        int result = warehouseDepotStub.content.get(Resource.COIN);

        assertTrue(result == 8);
    }

    @Test
    void addDevelopmentCardDiscountStrongbox() throws NoSuchFileException, InvalidDeckPositionException, NotEnoughResourcesException, WrongLevelException {
        warehouseDepotStub.content.put(Resource.COIN,1);
        strongboxStub.content.put(Resource.COIN,10);

        Discount discount = new Discount(Resource.COIN);
        ArrayList<Discount> discounts = new ArrayList<Discount>();
        discounts.add(discount);

        DevelopmentCardStub card = new DevelopmentCardStub(1);
        dashboard.addDevelopmentCard(card,3, discounts);

        int result1 = warehouseDepotStub.content.get(Resource.COIN);
        int result2 = strongboxStub.content.get(Resource.COIN);

        assertTrue(result1 == 0 && result2 == 9);
    }

    @Test
    void addDevelopmentCardWrongDiscount() throws NoSuchFileException, InvalidDeckPositionException, NotEnoughResourcesException, WrongLevelException {
        warehouseDepotStub.content.put(Resource.COIN,10);

        Discount discount = new Discount(Resource.SERVANT);
        ArrayList<Discount> discounts = new ArrayList<Discount>();
        discounts.add(discount);

        DevelopmentCardStub card = new DevelopmentCardStub(1);
        dashboard.addDevelopmentCard(card,3, discounts);
        int result = warehouseDepotStub.content.get(Resource.COIN);

        assertTrue(result == 7);
    }

    @Test
    void discountAndAdd() throws NoSuchFileException, InvalidDeckPositionException, NotEnoughResourcesException, WrongLevelException {
        warehouseDepotStub.content.put(Resource.COIN,10);

        Discount discount = new Discount(Resource.COIN);
        ArrayList<Discount> discounts = new ArrayList<Discount>();
        discounts.add(discount);

        DevelopmentCardStub card = new DevelopmentCardStub(3);
        dashboard.addDevelopmentCard(card,3, discounts);

        DevelopmentCard resultCard = dashboard.getDevelopmentDecks().get(2).peek();
        int result = warehouseDepotStub.content.get(Resource.COIN);

        assertTrue(result == 8 && resultCard.equals(card));
    }

    @Test
    void addExtraProduction() {
        ExtraProductionStub extraProductionStub = new ExtraProductionStub(1,Resource.STONE);
        dashboard.addExtraProduction(extraProductionStub);
        ExtraProduction result = dashboard.getExtraProductions().get(0);

        assertTrue(extraProductionStub.equals(result));
    }

    @Test
    void addDiscount() {
        DiscountStub discountStub = new DiscountStub(Resource.COIN);
        dashboard.addDiscount(discountStub);
        Discount result = dashboard.getDiscounts().get(0);

        assertTrue(discountStub.equals(result));
    }

    @Test
    void produce() throws NoSuchFileException, InvalidDeckPositionException, NotEnoughResourcesException, WrongLevelException {
        warehouseDepotStub.content.put(Resource.COIN,10);
        warehouseDepotStub.content.put(Resource.STONE,10);

        DevelopmentCardStub card1 = new DevelopmentCardStub(1,1);
        DevelopmentCardStub card2 = new DevelopmentCardStub(2,2,Resource.STONE,Resource.SHIELD);
        dashboard.addDevelopmentCard(card1,1,null);
        dashboard.addDevelopmentCard(card2,1,null);

        int faithPoints = dashboard.activateProduction(1);

        int stones = warehouseDepotStub.content.get(Resource.STONE);
        int shields = strongboxStub.content.get(Resource.SHIELD);

        assertTrue(stones == 8 && shields == 1 && faithPoints == 1);
    }

    @Test
    void produceStrongbox() throws NoSuchFileException, InvalidDeckPositionException, NotEnoughResourcesException, WrongLevelException {
        warehouseDepotStub.content.put(Resource.COIN,10);
        warehouseDepotStub.content.put(Resource.STONE,1);
        strongboxStub.content.put(Resource.STONE,10);

        DevelopmentCardStub card1 = new DevelopmentCardStub(1,1);
        DevelopmentCardStub card2 = new DevelopmentCardStub(2,2,Resource.STONE,Resource.SHIELD);
        dashboard.addDevelopmentCard(card1,1,null);
        dashboard.addDevelopmentCard(card2,1,null);

        int faithPoints = dashboard.activateProduction(1);

        int stones1 = warehouseDepotStub.content.get(Resource.STONE);
        int stones2 = strongboxStub.content.get(Resource.STONE);
        int shields = strongboxStub.content.get(Resource.SHIELD);

        assertTrue(stones1 == 0 && stones2 == 9 && shields == 1 && faithPoints == 1);
    }

    @Test
    void produceWithoutResources() throws NoSuchFileException, InvalidDeckPositionException, NotEnoughResourcesException, WrongLevelException {
        warehouseDepotStub.content.put(Resource.COIN,10);
        warehouseDepotStub.content.put(Resource.STONE,1);

        DevelopmentCardStub card1 = new DevelopmentCardStub(1,1);
        DevelopmentCardStub card2 = new DevelopmentCardStub(2,2,Resource.STONE,Resource.SHIELD);
        dashboard.addDevelopmentCard(card1,1,null);
        dashboard.addDevelopmentCard(card2,1,null);

        assertThrows(NotEnoughResourcesException.class,
                () -> dashboard.activateProduction(1));

        int stones = warehouseDepotStub.content.get(Resource.STONE);
        int shields = strongboxStub.content.get(Resource.SHIELD);

        assertTrue(stones == 1 && shields == 0);
    }

    @Test
    void invalidDeckProduce() throws NoSuchFileException, InvalidDeckPositionException, NotEnoughResourcesException, WrongLevelException {
        warehouseDepotStub.content.put(Resource.COIN,10);

        DevelopmentCardStub card = new DevelopmentCardStub(1,1);
        dashboard.addDevelopmentCard(card,1,null);
        assertThrows(InvalidDeckPositionException.class,
                () -> dashboard.activateProduction(3));
    }

    @Test
    void invalidDeckProduce2() throws NoSuchFileException, InvalidDeckPositionException, NotEnoughResourcesException, WrongLevelException {
        assertThrows(InvalidDeckPositionException.class,
                () -> dashboard.activateProduction(4));
    }

    @Test
    void extraProduction() throws InvalidExtraPositionException {
        warehouseDepotStub.content.put(Resource.STONE,10);

        ExtraProductionStub extra = new ExtraProductionStub(1, Resource.STONE);
        dashboard.addExtraProduction(extra);
        int faithPoints = dashboard.activateExtraProduction(1,Resource.SERVANT);

        int result1 = warehouseDepotStub.content.get(Resource.STONE);
        int result2 = strongboxStub.content.get(Resource.SERVANT);

        assertTrue(result1 == 9 && result2 == 1 && faithPoints == 1 );
    }

    @Test
    void extraProductionStrongbox() throws InvalidExtraPositionException {
        strongboxStub.content.put(Resource.STONE,10);

        ExtraProductionStub extra = new ExtraProductionStub(1, Resource.STONE);
        dashboard.addExtraProduction(extra);
        int faithPoints = dashboard.activateExtraProduction(1,Resource.SERVANT);

        int result1 = strongboxStub.content.get(Resource.STONE);
        int result2 = strongboxStub.content.get(Resource.SERVANT);

        assertTrue(result1 == 9 && result2 == 1 && faithPoints == 1 );
    }

    @Test
    void invalidExtraProduction() {
        assertThrows(InvalidExtraPositionException.class,
                () -> dashboard.activateProduction(0));
    }

    @Test
    void invalidExtraProduction2() {
        assertThrows(InvalidExtraPositionException.class,
                () -> dashboard.activateProduction(1));
    }

    @Test
    void checkDevRequirement() throws NoSuchFileException, InvalidDeckPositionException, NotEnoughResourcesException, WrongLevelException {
        DevelopmentCardStub card = new DevelopmentCardStub(1,1);
        dashboard.addDevelopmentCard(card,3,null);

        assertTrue(dashboard.checkDevRequirement(1,1,Color.BLUE));
    }

    @Test
    void checkDevRequirementFalse() throws NoSuchFileException, InvalidDeckPositionException, NotEnoughResourcesException, WrongLevelException {
        DevelopmentCardStub card = new DevelopmentCardStub(1,1);
        dashboard.addDevelopmentCard(card,3,null);

        assertFalse(dashboard.checkDevRequirement(1,1,Color.GREEN));
    }

    @Test
    void wrongLevelRequirement() {
        assertThrows(WrongLevelException.class,
                () -> dashboard.checkDevRequirement(1,4,Color.BLUE));
    }

    @Test
    void zeroLevelRequirement() {
        assertThrows(WrongLevelException.class,
                () -> dashboard.checkDevRequirement(1,0,Color.YELLOW));
    }

    @Test
    void checkResRequirement() {
        warehouseDepotStub.content.put(Resource.SHIELD,2);
        strongboxStub.content.put(Resource.SHIELD,3);

        assertTrue(dashboard.checkResRequirement(5,Resource.SHIELD));
    }

    @Test
    void checkLessResRequirement() {
        warehouseDepotStub.content.put(Resource.SHIELD,2);
        strongboxStub.content.put(Resource.SHIELD,3);

        assertTrue(dashboard.checkResRequirement(2,Resource.SHIELD));
    }

    @Test
    void checkZeroResRequirement() {
        assertTrue(dashboard.checkResRequirement(0,Resource.COIN));
    }

    @Test
    void checkNegativeResRequirement() {
        assertTrue(dashboard.checkResRequirement(-3,Resource.COIN));
    }

    @Test
    void checkFalseResRequirement() {
        warehouseDepotStub.content.put(Resource.SHIELD,2);
        strongboxStub.content.put(Resource.SHIELD,3);

        assertFalse(dashboard.checkResRequirement(2,Resource.SERVANT));
    }

    @Test
    void baseProduction() throws NotEnoughResourcesException {
        warehouseDepotStub.content.put(Resource.COIN,2);
        strongboxStub.content.put(Resource.SERVANT,3);

        dashboard.baseProduction(Resource.COIN,Resource.SERVANT,Resource.SHIELD);

        int coins = warehouseDepotStub.content.get(Resource.COIN);
        int servants = strongboxStub.content.get(Resource.SERVANT);
        int shields = strongboxStub.content.get(Resource.SHIELD);

        assertTrue(coins == 1 && servants == 2 && shields == 1 );
    }

    @Test
    void notEnoughBaseProduction() {
        warehouseDepotStub.content.put(Resource.COIN,2);

        assertThrows(NotEnoughResourcesException.class,
                () -> dashboard.baseProduction(Resource.COIN,Resource.SERVANT,Resource.SHIELD));

        int coins = warehouseDepotStub.content.get(Resource.COIN);
        assertTrue(coins == 2);
    }

    @Test
    void getActivableDevCards() throws NoSuchFileException, InvalidDeckPositionException, NotEnoughResourcesException, WrongLevelException {
        warehouseDepotStub.content.put(Resource.COIN,100);

        DevelopmentCardStub card1 = new DevelopmentCardStub(1,1);
        DevelopmentCardStub card2 = new DevelopmentCardStub(2,2);
        dashboard.addDevelopmentCard(card1,1, null);
        dashboard.addDevelopmentCard(card2,1, null);
        DevelopmentCard result = dashboard.getActivableDevCards().get(0);
        int length = dashboard.getActivableDevCards().size();
        assertTrue(length == 1 && card2.equals(result));
    }

    @Test
    void emptyActivableDevCards() {
        assertTrue(dashboard.getActivableDevCards().isEmpty());
    }
}