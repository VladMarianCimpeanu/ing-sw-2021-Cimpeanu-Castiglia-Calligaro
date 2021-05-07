package it.polimi.ingsw.model;

import it.polimi.ingsw.model.benefit.Benefit;
import it.polimi.ingsw.model.benefit.Faith;
import it.polimi.ingsw.model.benefit.Resource;
import it.polimi.ingsw.model.exceptions.*;
import it.polimi.ingsw.model.stubs.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.NoSuchFileException;
import java.util.HashMap;
import java.util.Map;
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
    void addDevelopmentCard() throws NoSuchFileException, InvalidDeckPositionException, WrongLevelException {
        warehouseDepotStub.content.put(Resource.COIN,10);

        DevelopmentCardStub card = new DevelopmentCardStub(1);
        dashboard.addDevelopmentCard(card,1);
        DevelopmentCard result = dashboard.getDevelopmentDecks().get(0).peek();
        assertTrue(result.equals(card) && dashboard.getDevelopmentDecks().get(1).empty() && dashboard.getDevelopmentDecks().get(2).empty());
    }

    @Test
    void zeroAddDevelopmentCard() throws NoSuchFileException {
        warehouseDepotStub.content.put(Resource.COIN,10);

        DevelopmentCardStub card = new DevelopmentCardStub(1);
        assertThrows(InvalidDeckPositionException.class,
                () -> dashboard.addDevelopmentCard(card,0));
        assertTrue(dashboard.getDevelopmentDecks().get(0).empty() && dashboard.getDevelopmentDecks().get(1).empty() && dashboard.getDevelopmentDecks().get(2).empty());
    }

    @Test
    void invalidAddDevelopmentCard() throws NoSuchFileException {
        warehouseDepotStub.content.put(Resource.COIN,10);

        DevelopmentCardStub card = new DevelopmentCardStub(1);
        assertThrows(InvalidDeckPositionException.class,
                () -> dashboard.addDevelopmentCard(card,4));
        assertTrue(dashboard.getDevelopmentDecks().get(0).empty() && dashboard.getDevelopmentDecks().get(1).empty() && dashboard.getDevelopmentDecks().get(2).empty());

    }

    @Test
    void addTwoDevelopmentCards() throws NoSuchFileException, InvalidDeckPositionException, WrongLevelException {
        warehouseDepotStub.content.put(Resource.COIN,10);

        DevelopmentCardStub card1 = new DevelopmentCardStub(1,1);
        DevelopmentCardStub card2 = new DevelopmentCardStub(2,2);

        dashboard.addDevelopmentCard(card1,2);
        dashboard.addDevelopmentCard(card2,2);

        DevelopmentCard result2 = dashboard.getDevelopmentDecks().get(1).pop();
        //To check that this method returns a copy of the Stack and not a reference
        Stack<DevelopmentCard> result = dashboard.getDevelopmentDecks().get(1);
        result.pop();
        DevelopmentCard result1 = result.pop();

        assertEquals(card1, result1);
        assertEquals(card2, result2);
    }

    @Test
    void wrongLevelDevelopmentCards() throws NoSuchFileException {
        warehouseDepotStub.content.put(Resource.COIN,10);

        DevelopmentCardStub card = new DevelopmentCardStub(1,2);

        assertThrows(WrongLevelException.class,
                () -> dashboard.addDevelopmentCard(card,1));
        assertTrue(dashboard.getDevelopmentDecks().get(0).isEmpty() && dashboard.getDevelopmentDecks().get(1).isEmpty() && dashboard.getDevelopmentDecks().get(2).isEmpty());
    }

    @Test
    void twoWrongLevelDevelopmentCards() throws NoSuchFileException, InvalidDeckPositionException, WrongLevelException {
        warehouseDepotStub.content.put(Resource.COIN,10);

        DevelopmentCardStub card1 = new DevelopmentCardStub(1,1);
        DevelopmentCardStub card2 = new DevelopmentCardStub(2,3);

        dashboard.addDevelopmentCard(card1,3);

        assertThrows(WrongLevelException.class,
                () -> dashboard.addDevelopmentCard(card2,3));

        DevelopmentCard result1 = dashboard.getDevelopmentDecks().get(2).pop();
        Stack<DevelopmentCard> result = dashboard.getDevelopmentDecks().get(2);
        result.pop();

        assertEquals(card1, result1);
        assertTrue(result.empty());
    }

    @Test
    void addExtraProduction() {
        ExtraProductionStub extraProductionStub = new ExtraProductionStub(1,Resource.STONE);
        dashboard.addExtraProduction(extraProductionStub);
        ExtraProduction result = dashboard.getExtraProductions().get(0);

        assertEquals(result, extraProductionStub);
    }

    @Test
    void checkDevRequirement() throws NoSuchFileException, InvalidDeckPositionException, WrongLevelException {
        DevelopmentCardStub card = new DevelopmentCardStub(1,1);
        dashboard.addDevelopmentCard(card,3);

        assertTrue(dashboard.checkDevRequirement(1,1,Color.BLUE));
    }

    @Test
    void checkDevRequirementFalse() throws NoSuchFileException, InvalidDeckPositionException, WrongLevelException {
        DevelopmentCardStub card = new DevelopmentCardStub(1,1);
        dashboard.addDevelopmentCard(card,3);

        assertFalse(dashboard.checkDevRequirement(1,1,Color.GREEN));
    }

    @Test
    void wrongLevelRequirement() {
        assertThrows(WrongLevelException.class,
                () -> dashboard.checkDevRequirement(1,4,Color.BLUE));
    }

    @Test
    void zeroLevelRequirement() throws NoSuchFileException, InvalidDeckPositionException, WrongLevelException {
        DevelopmentCardStub card1 = new DevelopmentCardStub(1,1);
        DevelopmentCardStub card2 = new DevelopmentCardStub(1,1);
        DevelopmentCardStub card3 = new DevelopmentCardStub(1,2);
        DevelopmentCardStub card4 = new DevelopmentCardStub(2,3);
        dashboard.addDevelopmentCard(card1, 1);
        dashboard.addDevelopmentCard(card2, 2);
        dashboard.addDevelopmentCard(card3, 2);
        dashboard.addDevelopmentCard(card4, 2);
        assertTrue(dashboard.checkDevRequirement(1,0, Color.BLUE));
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
    void getActivableDevCards() throws NoSuchFileException, InvalidDeckPositionException, WrongLevelException {
        warehouseDepotStub.content.put(Resource.COIN,100);

        DevelopmentCardStub card1 = new DevelopmentCardStub(1,1);
        DevelopmentCardStub card2 = new DevelopmentCardStub(2,2);
        dashboard.addDevelopmentCard(card1,1);
        dashboard.addDevelopmentCard(card2,1);
        DevelopmentCard result = dashboard.getActivableDevCards().get(0);
        int length = dashboard.getActivableDevCards().size();
        assertTrue(length == 1 && card2.equals(result));
    }

    @Test
    void emptyActivableDevCards() {
        assertTrue(dashboard.getActivableDevCards().isEmpty());
    }


    //------------ isDevCardPlaceable() ----------------

    @Test
    void lowerBoundLevelPlaceable(){
        assertThrows(WrongLevelException.class,
                () -> dashboard.isDevCardPlaceable(0));
    }

    @Test
    void negativeLevelPlaceable(){
        assertThrows(WrongLevelException.class,
                () -> dashboard.isDevCardPlaceable(-3));
    }

    @Test
    void higherBoundLevelPlaceable(){
        assertThrows(WrongLevelException.class,
                () -> dashboard.isDevCardPlaceable( 4));
    }

    @Test
    void firstCardPlaceable() throws WrongLevelException {
        assertTrue(dashboard.isDevCardPlaceable(1));
        assertFalse(dashboard.isDevCardPlaceable(2));
        assertFalse(dashboard.isDevCardPlaceable(3));
    }

    @Test
    void isDevCardPlaceableTest1() throws NoSuchFileException, InvalidDeckPositionException, WrongLevelException {
        DevelopmentCardStub card1 = new DevelopmentCardStub(1, 1);
        DevelopmentCardStub card2 = new DevelopmentCardStub(2, 1);
        DevelopmentCardStub card3 = new DevelopmentCardStub(3, 1);

        dashboard.addDevelopmentCard(card1, 1);
        dashboard.addDevelopmentCard(card2, 2);
        dashboard.addDevelopmentCard(card3, 3);

        assertTrue(dashboard.isDevCardPlaceable(2));
        assertFalse(dashboard.isDevCardPlaceable(3));
        assertFalse(dashboard.isDevCardPlaceable(1));
    }

    @Test
    void isDevCardPlaceableTest2() throws NoSuchFileException, InvalidDeckPositionException, WrongLevelException {
        DevelopmentCardStub card1 = new DevelopmentCardStub(1, 1);
        DevelopmentCardStub card2 = new DevelopmentCardStub(2, 1);

        dashboard.addDevelopmentCard(card1, 1);
        dashboard.addDevelopmentCard(card2, 2);

        assertTrue(dashboard.isDevCardPlaceable(2));
        assertFalse(dashboard.isDevCardPlaceable(3));
        assertTrue(dashboard.isDevCardPlaceable(1));
    }


    //------------ selectExtraProduction() ----------------

    @Test
    void nullExtraProduction(){
        dashboard.addExtraProduction(new ExtraProduction(Resource.COIN));
        assertThrows(NullPointerException.class,
                () -> dashboard.selectExtraProduction( 0, null));
    }

    @Test
    void negativeIndexExtraProduction(){
        assertThrows(IndexOutOfBoundsException.class,
                () -> dashboard.selectExtraProduction( -1, Resource.COIN));
        assertTrue(dashboard.getBenefitsToProduce().isEmpty());
        assertTrue(dashboard.getResourcesToPay().isEmpty());
    }

    @Test
    void notExistingIndexExtraProduction1(){
        assertThrows(IndexOutOfBoundsException.class,
                () -> dashboard.selectExtraProduction(0,Resource.SERVANT));
        assertTrue(dashboard.getBenefitsToProduce().isEmpty());
        assertTrue(dashboard.getResourcesToPay().isEmpty());
    }

    @Test
    void notExistingIndexExtraProduction2(){
        dashboard.addExtraProduction(new ExtraProduction(Resource.COIN));
        dashboard.addExtraProduction(new ExtraProduction(Resource.STONE));

        assertThrows(IndexOutOfBoundsException.class,
                () -> dashboard.selectExtraProduction(2, Resource.SHIELD));
        assertTrue(dashboard.getBenefitsToProduce().isEmpty());
        assertTrue(dashboard.getResourcesToPay().isEmpty());
    }

    @Test
    void extraProductionNotAffordable() {
        warehouseDepotStub.content.put(Resource.COIN, 2);
        dashboard.addExtraProduction(new ExtraProduction(Resource.STONE));

        assertThrows(NotEnoughResourcesException.class,
                () -> dashboard.selectExtraProduction(0, Resource.SHIELD));
        assertTrue(dashboard.getBenefitsToProduce().isEmpty());
        assertTrue(dashboard.getResourcesToPay().isEmpty());
    }

    @Test
    void selectExtraProductionTest1() throws NotEnoughResourcesException, ProductionStartedException, ProductionUsedException {
        warehouseDepotStub.content.put(Resource.COIN, 1);
        dashboard.addExtraProduction(new ExtraProduction(Resource.COIN));
        dashboard.selectExtraProduction(0, Resource.SERVANT);

        assertEquals(1, dashboard.getBenefitsToProduce().get(Faith.giveFaith()));
        assertEquals(1, dashboard.getBenefitsToProduce().get(Resource.SERVANT));
        assertEquals(2, dashboard.getBenefitsToProduce().size());
        assertEquals(Resource.COIN, dashboard.getResourcesToPay().get(0) );
        assertEquals(1, dashboard.getResourcesToPay().size());
    }

    @Test
    void selectExtraProductionTest2() throws NotEnoughResourcesException, ProductionStartedException, ProductionUsedException {
        warehouseDepotStub.content.put(Resource.COIN, 1);
        warehouseDepotStub.content.put(Resource.SHIELD, 1);
        dashboard.addExtraProduction(new ExtraProduction(Resource.COIN));
        dashboard.addExtraProduction(new ExtraProduction(Resource.SHIELD));
        dashboard.selectExtraProduction(0, Resource.STONE);
        dashboard.selectExtraProduction(1, Resource.SERVANT);

        assertEquals(1, dashboard.getBenefitsToProduce().get(Faith.giveFaith()));
        assertEquals(1, dashboard.getBenefitsToProduce().get(Resource.SERVANT));
        assertEquals(2, dashboard.getBenefitsToProduce().size());
        assertEquals(Resource.SHIELD, dashboard.getResourcesToPay().get(0) );
        assertEquals(1, dashboard.getResourcesToPay().size());
    }

    @Test
    void selectExtraProduction3() throws NotEnoughResourcesException, ProductionStartedException, RequirementsSatisfiedException, InvalidResourceException, ProductionUsedException {
        warehouseDepotStub.content.put(Resource.COIN, 1);
        warehouseDepotStub.content.put(Resource.SHIELD, 1);
        dashboard.addExtraProduction(new ExtraProduction(Resource.COIN));
        dashboard.addExtraProduction(new ExtraProduction(Resource.SHIELD));
        dashboard.selectExtraProduction(0, Resource.STONE);
        dashboard.takeFromDepot(Resource.COIN);

        assertThrows(ProductionStartedException.class,
                () -> dashboard.selectExtraProduction(1, Resource.SERVANT));
        assertEquals(1, dashboard.getBenefitsToProduce().get(Faith.giveFaith()));
        assertEquals(1, dashboard.getBenefitsToProduce().get(Resource.STONE));
        assertEquals(2, dashboard.getBenefitsToProduce().size());
        assertEquals(0, dashboard.getResourcesToPay().size());
    }


    //------------ selectBaseProduction() ----------------

    @Test
    void baseProduction1InputBigger(){
        Map<Resource, Integer> tempMap = new HashMap<>();
        tempMap.put(Resource.SHIELD, 3);
        assertThrows(ResourceCostException.class,
                () -> dashboard.selectBaseProduction(tempMap, Resource.COIN));
        assertTrue(dashboard.getBenefitsToProduce().isEmpty());
        assertTrue(dashboard.getResourcesToPay().isEmpty());
    }

    @Test
    void baseProduction2InputBigger(){
        Map<Resource, Integer> tempMap = new HashMap<>();
        tempMap.put(Resource.SHIELD, 2);
        tempMap.put(Resource.SERVANT, 1);
        assertThrows(ResourceCostException.class,
                () -> dashboard.selectBaseProduction(tempMap, Resource.COIN));
        assertTrue(dashboard.getBenefitsToProduce().isEmpty());
        assertTrue(dashboard.getResourcesToPay().isEmpty());
    }

    @Test
    void baseProductionTooManyResources(){
        Map<Resource, Integer> tempMap = new HashMap<>();
        tempMap.put(Resource.SHIELD, 1);
        tempMap.put(Resource.SERVANT, 1);
        tempMap.put(Resource.COIN, 1);
        assertThrows(ResourceCostException.class,
                () -> dashboard.selectBaseProduction(tempMap, Resource.COIN));
        assertTrue(dashboard.getBenefitsToProduce().isEmpty());
        assertTrue(dashboard.getResourcesToPay().isEmpty());
    }

    @Test
    void baseProductionNullInput(){
        assertThrows(NullPointerException.class,
                () -> dashboard.selectBaseProduction(null, Resource.COIN));
        assertTrue(dashboard.getBenefitsToProduce().isEmpty());
        assertTrue(dashboard.getResourcesToPay().isEmpty());
    }

    @Test
    void baseProductionNullInput2(){
        Map<Resource, Integer> tempMap = new HashMap<>();
        tempMap.put(Resource.SHIELD, 1);
        tempMap.put(Resource.SERVANT, 1);
        assertThrows(NullPointerException.class,
                () -> dashboard.selectBaseProduction(tempMap, null));
        assertTrue(dashboard.getBenefitsToProduce().isEmpty());
        assertTrue(dashboard.getResourcesToPay().isEmpty());
    }

    @Test
    void baseProductionDoubleResourcesInput() throws ResourceCostException, NotEnoughResourcesException, ProductionStartedException, ProductionUsedException {
        warehouseDepotStub.content.put(Resource.SHIELD, 2);
        Map<Resource, Integer> tempMap = new HashMap<>();
        tempMap.put(Resource.SHIELD, 2);
        dashboard.selectBaseProduction(tempMap, Resource.STONE);

        assertFalse(dashboard.getBenefitsToProduce().containsKey(Faith.giveFaith()));
        assertEquals(1, dashboard.getBenefitsToProduce().get(Resource.STONE));
        assertEquals(1, dashboard.getBenefitsToProduce().size());
        assertEquals(2, dashboard.getResourcesToPay().size());
        assertEquals(Resource.SHIELD, dashboard.getResourcesToPay().get(0));
        assertEquals(Resource.SHIELD, dashboard.getResourcesToPay().get(1));
    }

    @Test
    void notEnoughResources(){
        warehouseDepotStub.content.put(Resource.SHIELD, 1);
        Map<Resource, Integer> tempMap = new HashMap<>();
        tempMap.put(Resource.SHIELD, 2);
        assertThrows(NotEnoughResourcesException.class,
                () -> dashboard.selectBaseProduction(tempMap, Resource.STONE));
        assertTrue(dashboard.getBenefitsToProduce().isEmpty());
        assertTrue(dashboard.getResourcesToPay().isEmpty());
    }

    @Test
    void notEnoughResources2(){
        warehouseDepotStub.content.put(Resource.SHIELD, 1);
        Map<Resource, Integer> tempMap = new HashMap<>();
        tempMap.put(Resource.SHIELD, 1);
        tempMap.put(Resource.COIN, 1);
        assertThrows(NotEnoughResourcesException.class,
                () -> dashboard.selectBaseProduction(tempMap, Resource.STONE));
        assertTrue(dashboard.getBenefitsToProduce().isEmpty());
        assertTrue(dashboard.getResourcesToPay().isEmpty());
    }

    @Test
    void startedProductionException() throws ResourceCostException, NotEnoughResourcesException, ProductionStartedException, RequirementsSatisfiedException, InvalidResourceException, ProductionUsedException {
        warehouseDepotStub.content.put(Resource.COIN, 1);
        warehouseDepotStub.content.put(Resource.SHIELD, 1);
        warehouseDepotStub.content.put(Resource.SERVANT, 2);
        Map<Resource, Integer> tempMap = new HashMap<>();
        tempMap.put(Resource.SHIELD, 1);
        tempMap.put(Resource.COIN, 1);
        dashboard.selectBaseProduction(tempMap, Resource.COIN);
        dashboard.takeFromDepot(Resource.COIN);
        tempMap.clear();
        tempMap.put(Resource.SERVANT, 2);

        assertThrows(ProductionStartedException.class,
                () -> dashboard.selectBaseProduction(tempMap, Resource.STONE));
        assertFalse(dashboard.getBenefitsToProduce().containsKey(Faith.giveFaith()));
        assertEquals(1, dashboard.getBenefitsToProduce().get(Resource.COIN));
        assertEquals(1, dashboard.getBenefitsToProduce().size());
        assertEquals(1, dashboard.getResourcesToPay().size());
        assertEquals(Resource.SHIELD, dashboard.getResourcesToPay().get(0));
    }


    //------------ selectCardProduction() ----------------

    @Test
    void higherBoundDeckIndex(){
        assertThrows(InvalidDeckPositionException.class,
                () -> dashboard.selectCardProduction(4));
        assertTrue(dashboard.getBenefitsToProduce().isEmpty());
        assertTrue(dashboard.getResourcesToPay().isEmpty());
    }

    @Test
    void lowerBoundDeckIndex() {
        assertThrows(InvalidDeckPositionException.class,
                () -> dashboard.selectCardProduction(0));
        assertTrue(dashboard.getBenefitsToProduce().isEmpty());
        assertTrue(dashboard.getResourcesToPay().isEmpty());
    }

    @Test
    void emptyDevelopmentDeck() throws NoSuchFileException, InvalidDeckPositionException, WrongLevelException {
        dashboard.addDevelopmentCard((new DevelopmentCardStub(1)), 1);
        assertThrows(NoCardException.class,
                () -> dashboard.selectCardProduction(2));
        assertTrue(dashboard.getBenefitsToProduce().isEmpty());
        assertTrue(dashboard.getResourcesToPay().isEmpty());
    }

    @Test
    void notEnoughResourcesForDevelopmentCard() throws InvalidDeckPositionException, WrongLevelException {
        Map<Resource, Integer> resIn = new HashMap<>();
        Map<Benefit, Integer> benefitOut = new HashMap<>();
        resIn.put(Resource.SHIELD, 1);
        resIn.put(Resource.COIN, 2);
        benefitOut.put(Faith.giveFaith(), 2);
        benefitOut.put(Resource.STONE, 1);
        DevelopmentCard card = new DevelopmentCard(1,1, 1, Color.BLUE, null, resIn, benefitOut);
        dashboard.addDevelopmentCard(card, 2);
        warehouseDepotStub.content.put(Resource.COIN, 2);

        assertThrows(NotEnoughResourcesException.class,
                () -> dashboard.selectCardProduction(2));
        assertTrue(dashboard.getBenefitsToProduce().isEmpty());
        assertTrue(dashboard.getResourcesToPay().isEmpty());
    }

    @Test
    void productionAlreadyStartedDevCard() throws InvalidDeckPositionException, WrongLevelException, ResourceCostException, NotEnoughResourcesException, ProductionStartedException, RequirementsSatisfiedException, InvalidResourceException, ProductionUsedException {
        Map<Resource, Integer> resIn = new HashMap<>();
        Map<Benefit, Integer> benefitOut = new HashMap<>();
        Map<Resource, Integer> resBaseProd = new HashMap<>();
        resIn.put(Resource.SHIELD, 1);
        resIn.put(Resource.COIN, 2);
        resBaseProd.put(Resource.COIN, 2);
        benefitOut.put(Faith.giveFaith(), 2);
        benefitOut.put(Resource.STONE, 1);
        DevelopmentCard card = new DevelopmentCard(1, 1, 1, Color.BLUE, null, resIn, benefitOut);
        dashboard.addDevelopmentCard(card, 2);
        warehouseDepotStub.content.put(Resource.COIN, 4);
        warehouseDepotStub.content.put(Resource.SHIELD, 3);
        dashboard.selectBaseProduction(resBaseProd, Resource.SERVANT);
        dashboard.takeFromDepot(Resource.COIN);

        assertThrows(ProductionStartedException.class,
                () -> dashboard.selectCardProduction(2));
        assertFalse(dashboard.getBenefitsToProduce().containsKey(Faith.giveFaith()));
        assertEquals(1, dashboard.getBenefitsToProduce().get(Resource.SERVANT));
        assertEquals(1, dashboard.getBenefitsToProduce().size());
        assertEquals(1, dashboard.getResourcesToPay().size());
        assertEquals(Resource.COIN, dashboard.getResourcesToPay().get(0));
    }


    //------------ takeFromDepot ----------------


    @Test
    void nullFromDepot() {
        assertThrows(NullPointerException.class,
                () -> dashboard.takeFromDepot(null));
    }

    @Test
    void queriedTooManyResources() throws MissingExtraSlot, ResourceCostException, NotEnoughResourcesException, ProductionStartedException, ProductionUsedException {
        warehouseDepotStub.content.put(Resource.SHIELD, 1);
        warehouseDepotStub.content.put(Resource.STONE, 1);
        warehouseDepotStub.addExtraSlot(Resource.COIN);
        warehouseDepotStub.addExtraResource(Resource.COIN, 2);
        Map<Resource, Integer> resBaseProd = new HashMap<>();
        resBaseProd.put(Resource.COIN, 2);
        dashboard.selectBaseProduction(resBaseProd, Resource.SERVANT);
        int prevSize = dashboard.getResourcesToPay().size();

        assertThrows(NotEnoughResourcesException.class,
                () -> dashboard.takeFromDepot(Resource.COIN));
        assertEquals(prevSize, dashboard.getResourcesToPay().size());
    }

    @Test
    void takeResourceWithNoProduction() {
        warehouseDepotStub.content.put(Resource.SHIELD, 1);
        warehouseDepotStub.content.put(Resource.STONE, 1);

        assertThrows(RequirementsSatisfiedException.class,
                () -> dashboard.takeFromDepot(Resource.COIN));
        assertTrue(dashboard.getBenefitsToProduce().isEmpty());
        assertTrue(dashboard.getResourcesToPay().isEmpty());
    }

    @Test
    void takeResourcesNotNeeded1() throws ResourceCostException, NotEnoughResourcesException, ProductionStartedException, RequirementsSatisfiedException, InvalidResourceException, ProductionUsedException {
        warehouseDepotStub.content.put(Resource.SHIELD, 1);
        warehouseDepotStub.content.put(Resource.STONE, 1);
        warehouseDepotStub.content.put(Resource.COIN, 3);
        Map<Resource, Integer> resBaseProd = new HashMap<>();
        resBaseProd.put(Resource.COIN, 2);
        dashboard.selectBaseProduction(resBaseProd, Resource.SERVANT);
        dashboard.takeFromDepot(Resource.COIN);
        dashboard.takeFromDepot(Resource.COIN);

        assertThrows(RequirementsSatisfiedException.class,
                () -> dashboard.takeFromDepot(Resource.COIN));
        assertFalse(dashboard.getBenefitsToProduce().isEmpty());
        assertTrue(dashboard.getResourcesToPay().isEmpty());
    }

    @Test
    void takeResourcesNotNeeded2() throws ResourceCostException, NotEnoughResourcesException, ProductionStartedException, ProductionUsedException {
        warehouseDepotStub.content.put(Resource.SHIELD, 1);
        warehouseDepotStub.content.put(Resource.STONE, 1);
        warehouseDepotStub.content.put(Resource.COIN, 3);
        Map<Resource, Integer> resBaseProd = new HashMap<>();
        resBaseProd.put(Resource.COIN, 2);
        dashboard.selectBaseProduction(resBaseProd, Resource.SERVANT);

        assertThrows(InvalidResourceException.class,
                () -> dashboard.takeFromDepot(Resource.SHIELD));
        assertFalse(dashboard.getBenefitsToProduce().isEmpty());
        assertEquals(2, dashboard.getResourcesToPay().size());
    }


    //------------ takeFromStrongbox ----------------

    @Test
    void queriedTooManyResourcesStrongbox() throws ResourceCostException, NotEnoughResourcesException, ProductionStartedException, RequirementsSatisfiedException, InvalidResourceException, ProductionUsedException {
        strongboxStub.content.put(Resource.SHIELD, 1);
        strongboxStub.content.put(Resource.STONE, 1);
        warehouseDepotStub.content.put(Resource.SHIELD, 2);
        Map<Resource, Integer> resBaseProd = new HashMap<>();
        resBaseProd.put(Resource.SHIELD, 2);
        dashboard.selectBaseProduction(resBaseProd, Resource.SERVANT);
        dashboard.takeFromStrongbox(Resource.SHIELD);
        int prevSize = dashboard.getResourcesToPay().size();

        assertThrows(NotEnoughResourcesException.class,
                () -> dashboard.takeFromStrongbox(Resource.SHIELD));
        assertEquals(prevSize, dashboard.getResourcesToPay().size());
    }

    @Test

    void nullFromStrongbox() {
        assertThrows(NullPointerException.class,
                () -> dashboard.takeFromStrongbox(null));
    }

    @Test
    void takeResourceWithNoProductionStrongbox() {
        strongboxStub.content.put(Resource.SHIELD, 1);
        strongboxStub.content.put(Resource.STONE, 1);

        assertThrows(RequirementsSatisfiedException.class,
                () -> dashboard.takeFromStrongbox(Resource.COIN));
        assertTrue(dashboard.getBenefitsToProduce().isEmpty());
        assertTrue(dashboard.getResourcesToPay().isEmpty());
    }

    @Test
    void takeResourcesNotNeeded1Strongbox() throws ResourceCostException, NotEnoughResourcesException, ProductionStartedException, RequirementsSatisfiedException, InvalidResourceException, ProductionUsedException {
        strongboxStub.content.put(Resource.SHIELD, 1);
        strongboxStub.content.put(Resource.STONE, 1);
        strongboxStub.content.put(Resource.COIN, 3);
        Map<Resource, Integer> resBaseProd = new HashMap<>();
        resBaseProd.put(Resource.COIN, 2);
        dashboard.selectBaseProduction(resBaseProd, Resource.SERVANT);
        dashboard.takeFromStrongbox(Resource.COIN);
        dashboard.takeFromStrongbox(Resource.COIN);

        assertThrows(RequirementsSatisfiedException.class,
                () -> dashboard.takeFromStrongbox(Resource.COIN));
        assertFalse(dashboard.getBenefitsToProduce().isEmpty());
        assertTrue(dashboard.getResourcesToPay().isEmpty());
    }

    @Test
    void takeResourcesNotNeeded2Strongbox() throws ResourceCostException, NotEnoughResourcesException, ProductionStartedException, ProductionUsedException {
        strongboxStub.content.put(Resource.SHIELD, 1);
        strongboxStub.content.put(Resource.STONE, 1);
        strongboxStub.content.put(Resource.COIN, 3);
        Map<Resource, Integer> resBaseProd = new HashMap<>();
        resBaseProd.put(Resource.COIN, 2);
        dashboard.selectBaseProduction(resBaseProd, Resource.SERVANT);

        assertThrows(InvalidResourceException.class,
                () -> dashboard.takeFromStrongbox(Resource.SHIELD));
        assertFalse(dashboard.getBenefitsToProduce().isEmpty());
        assertEquals(2, dashboard.getResourcesToPay().size());
    }


    //------------ takeFromExtraSlot ----------------


    @Test
    void queriedTooManyResourcesExtraSlot() throws ResourceCostException, NotEnoughResourcesException, ProductionStartedException, RequirementsSatisfiedException, InvalidResourceException, MissingExtraSlot, ProductionUsedException {
        strongboxStub.content.put(Resource.SHIELD, 1);
        strongboxStub.content.put(Resource.STONE, 1);
        warehouseDepotStub.addExtraSlot(Resource.SHIELD);
        warehouseDepotStub.addExtraResource(Resource.SHIELD, 1);
        Map<Resource, Integer> resBaseProd = new HashMap<>();
        resBaseProd.put(Resource.SHIELD, 2);
        dashboard.selectBaseProduction(resBaseProd, Resource.SERVANT);
        dashboard.takeFromExtraSlot(Resource.SHIELD);
        int prevSize = dashboard.getResourcesToPay().size();

        assertThrows(NotEnoughResourcesException.class,
                () -> dashboard.takeFromExtraSlot(Resource.SHIELD));
        assertEquals(prevSize, dashboard.getResourcesToPay().size());
    }

    @Test

    void nullFromExtraSlot() {
        assertThrows(NullPointerException.class,
                () -> dashboard.takeFromExtraSlot(null));
    }

    @Test
    void takeResourceWithNoProductionExtraSlot() throws MissingExtraSlot {
        warehouseDepotStub.addExtraSlot(Resource.SHIELD);
        warehouseDepotStub.addExtraResource(Resource.SHIELD, 2);

        assertThrows(RequirementsSatisfiedException.class,
                () -> dashboard.takeFromStrongbox(Resource.SHIELD));
        assertTrue(dashboard.getBenefitsToProduce().isEmpty());
        assertTrue(dashboard.getResourcesToPay().isEmpty());
    }

    @Test
    void takeResourcesNotNeeded1ExtraSlot() throws ResourceCostException, NotEnoughResourcesException, ProductionStartedException, RequirementsSatisfiedException, InvalidResourceException, MissingExtraSlot, ProductionUsedException {
        strongboxStub.content.put(Resource.SHIELD, 1);
        strongboxStub.content.put(Resource.STONE, 1);
        strongboxStub.content.put(Resource.COIN, 3);
        warehouseDepotStub.addExtraSlot(Resource.SHIELD);
        warehouseDepotStub.addExtraResource(Resource.SHIELD, 1);
        Map<Resource, Integer> resBaseProd = new HashMap<>();
        resBaseProd.put(Resource.COIN, 2);
        dashboard.selectBaseProduction(resBaseProd, Resource.SERVANT);
        dashboard.takeFromStrongbox(Resource.COIN);
        dashboard.takeFromStrongbox(Resource.COIN);

        assertThrows(RequirementsSatisfiedException.class,
                () -> dashboard.takeFromExtraSlot(Resource.SHIELD));
        assertFalse(dashboard.getBenefitsToProduce().isEmpty());
        assertTrue(dashboard.getResourcesToPay().isEmpty());
    }

    @Test
    void takeResourcesNotNeeded2ExtraSlot() throws ResourceCostException, NotEnoughResourcesException, ProductionStartedException, MissingExtraSlot, ProductionUsedException {
        warehouseDepotStub.addExtraSlot(Resource.SHIELD);
        warehouseDepotStub.addExtraResource(Resource.SHIELD, 2);
        strongboxStub.content.put(Resource.COIN, 3);
        Map<Resource, Integer> resBaseProd = new HashMap<>();
        resBaseProd.put(Resource.COIN, 2);
        dashboard.selectBaseProduction(resBaseProd, Resource.SERVANT);

        assertThrows(InvalidResourceException.class,
                () -> dashboard.takeFromExtraSlot(Resource.SHIELD));
        assertFalse(dashboard.getBenefitsToProduce().isEmpty());
        assertEquals(2, dashboard.getResourcesToPay().size());
    }


    //------------ checkResources ----------------

    @Test
    void checkNull() {
        assertThrows(NullPointerException.class,
                () -> dashboard.checkResources(null));
    }

    @Test
    void checkEmptyResources() {
        assertTrue(dashboard.checkResources(new HashMap<>()));
    }

    @Test
    void checkResourcesInDifferentPlaces() throws MissingExtraSlot {
        warehouseDepotStub.content.put(Resource.STONE, 1);
        strongboxStub.content.put(Resource.COIN, 1);
        warehouseDepotStub.addExtraSlot(Resource.SHIELD);
        warehouseDepotStub.addExtraResource(Resource.SHIELD, 2);
        Map<Resource, Integer> toBeChecked = new HashMap<>();
        toBeChecked.put(Resource.STONE, 1);
        toBeChecked.put(Resource.SHIELD, 2);
        toBeChecked.put(Resource.COIN, 1);

        assertTrue(dashboard.checkResources(toBeChecked));
    }

    @Test
    void checkResourcesInDifferentPlaces2() throws MissingExtraSlot {
        warehouseDepotStub.content.put(Resource.SHIELD, 1);
        strongboxStub.content.put(Resource.SHIELD, 1);
        warehouseDepotStub.addExtraSlot(Resource.SHIELD);
        warehouseDepotStub.addExtraResource(Resource.SHIELD, 2);
        Map<Resource, Integer> toBeChecked = new HashMap<>();
        toBeChecked.put(Resource.SHIELD, 4);

        assertTrue(dashboard.checkResources(toBeChecked));
    }

    @Test
    void checkInsufficientResources(){
        warehouseDepotStub.content.put(Resource.SHIELD, 1);
        strongboxStub.content.put(Resource.SHIELD, 1);
        Map<Resource, Integer> toBeChecked = new HashMap<>();
        toBeChecked.put(Resource.SHIELD, 4);

        assertFalse(dashboard.checkResources(toBeChecked));
    }

    @Test
    void checkInsufficientResources2() {
        warehouseDepotStub.content.put(Resource.SHIELD, 1);
        strongboxStub.content.put(Resource.STONE, 1);
        warehouseDepotStub.addExtraSlot(Resource.COIN);
        Map<Resource, Integer> toBeChecked = new HashMap<>();
        toBeChecked.put(Resource.STONE, 1);
        toBeChecked.put(Resource.SHIELD, 1);
        toBeChecked.put(Resource.COIN, 1);

        assertFalse(dashboard.checkResources(toBeChecked));
    }


    //------------ activateProduction ----------------

    @Test
    void noProductionSelected() {
        assertThrows(NoProductionAvailableException.class,
                () -> dashboard.activateProduction());
    }

    @Test
    void notAllResourcesPaid() throws ResourceCostException, NotEnoughResourcesException, ProductionStartedException, RequirementsSatisfiedException, InvalidResourceException, ProductionUsedException {
        warehouseDepotStub.content.put(Resource.SHIELD, 1);
        strongboxStub.content.put(Resource.STONE, 1);
        Map<Resource, Integer> tempMap = new HashMap<>();
        tempMap.put(Resource.SHIELD, 1);
        tempMap.put(Resource.STONE, 1);
        dashboard.selectBaseProduction(tempMap, Resource.COIN);
        dashboard.takeFromStrongbox(Resource.STONE);

        assertThrows(NoProductionAvailableException.class,
                () -> dashboard.activateProduction());
        assertEquals(0, strongboxStub.getResourceQuantity(Resource.COIN));
    }

    @Test
    void doubleProduction() throws NotEnoughResourcesException, ProductionStartedException, RequirementsSatisfiedException, InvalidResourceException, NoProductionAvailableException, ProductionUsedException {
        warehouseDepotStub.content.put(Resource.SHIELD, 1);
        strongboxStub.content.put(Resource.STONE, 1);
        dashboard.addExtraProduction(new ExtraProduction(Resource.STONE));
        dashboard.selectExtraProduction(0, Resource.COIN);
        dashboard.takeFromStrongbox(Resource.STONE);

        assertEquals(1, dashboard.activateProduction());
        Map<Resource, Integer> baseProdIn = new HashMap<>();
        baseProdIn.put(Resource.COIN, 1);
        baseProdIn.put(Resource.SHIELD, 1);
        assertThrows(NotEnoughResourcesException.class,
                () -> dashboard.selectBaseProduction(baseProdIn, Resource.COIN));
    }
}
