package it.polimi.ingsw.server.model;

import it.polimi.ingsw.server.model.benefit.Resource;
import it.polimi.ingsw.server.model.exceptions.*;
import it.polimi.ingsw.server.model.stubs.VirtualViewStub;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class WarehouseDepotTest {
    private WarehouseDepot depot;

    //------------- TEST MOVE --------------------

    @BeforeEach
    void init(){
        depot = new WarehouseDepot();
        depot.subscribe(new VirtualViewStub());
    }

    @Test
    @DisplayName("Checking move can handle not existing shelves as input *1*")
    void moveWrongShelves1() {
        assertThrows(InvalidShelfPosition.class,
                () -> depot.move(0,4));
    }

    @Test
    @DisplayName("Checking move can handle not existing shelves as input *2*")
    void moveWrongShelves2() throws ExistingResourceException, InvalidResourceException, InvalidShelfPosition, NegativeQuantityException {
        depot.addResource(1,1, Resource.SERVANT);
        assertThrows(InvalidShelfPosition.class,
                () -> depot.move(1,4));
        assertEquals(1, depot.getShelfQuantity(1));
        assertEquals(Resource.SERVANT, depot.getShelfResource(1));
    }

    @Test
    @DisplayName("Checking move can handle not existing shelves as input *3*")
    void moveWrongShelves3() throws ExistingResourceException, InvalidResourceException, InvalidShelfPosition, NegativeQuantityException {


        depot.addResource(3, 2, Resource.COIN);
        assertThrows(InvalidShelfPosition.class,
                () -> depot.move(0,3));
        assertEquals(2, depot.getShelfQuantity(3));
        assertEquals(Resource.COIN, depot.getShelfResource(3));
    }

    @Test
    @DisplayName("Checking move can handle movements of quantities too big for their destination shelf *1*")
    void moveBigQuantity1() throws ExistingResourceException, InvalidResourceException, InvalidShelfPosition, NegativeQuantityException {

        depot.addResource(1,1, Resource.SHIELD);
        depot.addResource(2, 2,Resource.STONE);
        assertThrows(NotEnoughSpaceException.class,
                () -> depot.move(1,2));
    }

    @Test
    @DisplayName("Checking move can handle movements of quantities too big for their destination shelf *2*")
    void moveBigQuantity2() throws ExistingResourceException, InvalidResourceException, InvalidShelfPosition, NegativeQuantityException {

        depot.addResource(1,1, Resource.STONE);
        depot.addResource(3, 3, Resource.SHIELD);
        assertThrows(NotEnoughSpaceException.class,
                () -> depot.move(3,1));
    }

    @Test
    @DisplayName("Checking move can work correctly with correct inputs")
    void moveTest() throws InvalidShelfPosition, ExistingResourceException, InvalidResourceException, NotEnoughSpaceException, NegativeQuantityException {


        int shelfTest;
        Resource resourceTest;
        depot.addResource(1,1, Resource.COIN);
        depot.addResource(2,1, Resource.SHIELD);
        depot.addResource(3,2, Resource.SERVANT);
        depot.move(2,3);
        shelfTest = depot.getShelfQuantity(3);
        assertEquals(1,shelfTest);
        shelfTest = depot.getShelfQuantity(2);
        assertEquals(2, shelfTest);
        resourceTest = depot.getShelfResource(2);
        assertEquals(Resource.SERVANT,resourceTest);
        resourceTest = depot.getShelfResource(3);
        assertEquals(Resource.SHIELD,resourceTest);
        depot.move(3,1);
        resourceTest = depot.getShelfResource(1);
        assertEquals(Resource.SHIELD,resourceTest);
        resourceTest = depot.getShelfResource(3);
        assertEquals(Resource.COIN,resourceTest);
    }

    //------------- TEST ADD RESOURCE--------------------

    @Test
    @DisplayName("Checking addResource can handle same resources on different shelves")
    void addDoubleResource() throws InvalidShelfPosition, ExistingResourceException, InvalidResourceException, NegativeQuantityException {

        depot.addResource(2, 1, Resource.STONE);
        assertThrows(ExistingResourceException.class,
                () -> depot.addResource(1, 1, Resource.STONE ));
        assertEquals(0, depot.getShelfQuantity(1));
    }

    @Test
    @DisplayName("Checking addResource can handle different resources at the same shelf")
    void addDifferentResource() throws InvalidShelfPosition, ExistingResourceException, InvalidResourceException, NegativeQuantityException {

        depot.addResource(2, 1, Resource.COIN);
        assertThrows(InvalidResourceException.class,
                () -> depot.addResource(2, 1, Resource.SHIELD));
        assertEquals(Resource.COIN, depot.getShelfResource(2));
        assertEquals(0, depot.getShelfQuantity(1));
        assertEquals(1, depot.getShelfQuantity(2));
        assertEquals(0, depot.getShelfQuantity(3));
    }

    @Test
    @DisplayName("Checking addResource can handle more Resources than actual storage")
    void addTooManyResources1() throws InvalidShelfPosition, InvalidResourceException, ExistingResourceException {

        assertEquals(1, depot.addResource(2,3, Resource.COIN));
        assertEquals(2, depot.getShelfQuantity(2));
        assertEquals(Resource.COIN, depot.getShelfResource(2));
    }
    @Test
    @DisplayName("Checking addResource can handle more Resources than actual storage *2*")
    void addTooManyResources2() throws InvalidShelfPosition, ExistingResourceException, InvalidResourceException{

        depot.addResource(3, 1, Resource.STONE);
        depot.addResource(3, 2, Resource.STONE);
        assertEquals(2, depot.addResource(3,2, Resource.STONE));
        assertEquals(3, depot.getShelfQuantity(3));
    }

    @Test
    @DisplayName("Checking addResource can handle negative quantities of resources")
    void addNegativeQuantities() throws InvalidShelfPosition, ExistingResourceException, InvalidResourceException {

        depot.addResource(2, -1, Resource.COIN);
        assertEquals(0, depot.getShelfQuantity(2));
    }

    @Test
    @DisplayName("Checking addResource can handle not existing shelves")
    void addToNotExistingShelf1() throws InvalidShelfPosition {

        assertThrows(InvalidShelfPosition.class,
                () -> depot.addResource(0, 2, Resource.SERVANT));
        assertEquals(0, depot.getShelfQuantity(1));
        assertEquals(0, depot.getShelfQuantity(2));
        assertEquals(0, depot.getShelfQuantity(3));
    }

    @Test
    @DisplayName("Checking addResource can handle not existing shelves *2*")
    void addToNotExistingShelf2() throws InvalidShelfPosition {

        assertThrows(InvalidShelfPosition.class,
                () -> depot.addResource(4, 2, Resource.COIN));
        assertEquals(0, depot.getShelfQuantity(1));
        assertEquals(0, depot.getShelfQuantity(2));
        assertEquals(0, depot.getShelfQuantity(3));
    }

    @Test
    @DisplayName("Checking addResource can work with correct parameters")
    void addResourceTest() throws ExistingResourceException, InvalidResourceException, InvalidShelfPosition {


        assertEquals(0, depot.addResource(2, 1, Resource.COIN));
        assertEquals(0, depot.addResource(1, 1, Resource.SERVANT));
        assertEquals(0, depot.addResource(3, 2, Resource.SHIELD));
        assertEquals(0, depot.addResource(2, 1, Resource.COIN));
        assertEquals(0, depot.addResource(3, 1, Resource.SHIELD));

        assertEquals(1, depot.getShelfQuantity(1));
        assertEquals(2, depot.getShelfQuantity(2));
        assertEquals(3, depot.getShelfQuantity(3));

        assertEquals(Resource.SERVANT, depot.getShelfResource(1));
        assertEquals(Resource.COIN, depot.getShelfResource(2));
        assertEquals(Resource.SHIELD, depot.getShelfResource(3));
    }

    //------------- TEST REMOVE RESOURCE--------------------


    @Test
    @DisplayName("Checking removeResource can work with normal removals")
    void removeResourceTest() throws ExistingResourceException, InvalidResourceException, InvalidShelfPosition{

        depot.addResource(1,1, Resource.SHIELD);
        depot.addResource(2, 2, Resource.SERVANT);
        depot.addResource(3, 3, Resource.STONE);

        depot.removeResource(Resource.SHIELD, 0);
        depot.removeResource(Resource.STONE, 2);
        depot.removeResource(Resource.SERVANT, 1);
        depot.removeResource(Resource.SERVANT, 1);

        assertEquals(1, depot.getShelfQuantity(1));
        assertEquals(0, depot.getShelfQuantity(2));
        assertEquals(1, depot.getShelfQuantity(3));

        assertEquals(Resource.SHIELD, depot.getShelfResource(1));
        assertEquals(Resource.SERVANT, depot.getShelfResource(2));
        assertEquals(Resource.STONE, depot.getShelfResource(3));
    }

    @Test
    @DisplayName("Checking removeResource can handle negative values")
    void removeNegativeResources() throws ExistingResourceException, InvalidResourceException, InvalidShelfPosition {

        depot.addResource(2,1, Resource.SHIELD);
        assertEquals(0, depot.removeResource(Resource.SHIELD, -1));

        assertEquals(1, depot.getShelfQuantity(2));
        assertEquals(Resource.SHIELD, depot.getShelfResource(2));
    }

    @Test
    @DisplayName("Checking removeResource can handle values bigger than actual storage")
    void removeTooManyResources() throws ExistingResourceException, InvalidResourceException, InvalidShelfPosition{

        depot.addResource(2,2, Resource.COIN);

        assertEquals(2, depot.removeResource(Resource.COIN, 4));
        assertEquals(0, depot.getShelfQuantity(2));
        assertEquals(0, depot.getShelfQuantity(2));
        assertEquals(0, depot.getShelfQuantity(2));

    }
    /*
    @Test
    @DisplayName("Checking removeResource can work when extra slots are used *1*")
    void removeResidual1() throws MissingExtraSlot, InvalidShelfPosition, InvalidResourceException, ExistingResourceException {

        depot.addExtraSlot(Resource.SHIELD);
        depot.addExtraResource(Resource.SHIELD, 2);
        depot.addResource(1, 1, Resource.SHIELD);
        depot.removeResource(Resource.SHIELD, 2);   //it will remove only from depot
        assertEquals(1, depot.getShelfQuantity(1));
    }

    @Test
    @DisplayName("Checking removeResource can work when extra slots are used *2*")
    void removeResidual2() throws MissingExtraSlot, InvalidShelfPosition, InvalidResourceException, ExistingResourceException{

        depot.addExtraSlot(Resource.COIN);
        depot.addExtraResource(Resource.COIN, 1);
        depot.addResource(3, 3, Resource.COIN);
        depot.removeResource(Resource.COIN, 3);
        assertEquals(1, depot.getShelfQuantity(3));     //same as above
    }
    */

    @Test
    @DisplayName("Checking removeResource can handle removal of not existing resources")
    void removeNotExistingResource() throws ExistingResourceException, InvalidResourceException, InvalidShelfPosition {

        depot.addResource(1, 1, Resource.STONE);
        depot.addResource(2,2, Resource.COIN);
        depot.addResource(3, 1, Resource.SHIELD);

        assertEquals(4, depot.removeResource(Resource.SERVANT, 4));
        assertEquals(1, depot.getShelfQuantity(1));
        assertEquals(2, depot.getShelfQuantity(2));
        assertEquals(1, depot.getShelfQuantity(3));

        assertEquals(Resource.STONE, depot.getShelfResource(1));
        assertEquals(Resource.COIN, depot.getShelfResource(2));
        assertEquals(Resource.SHIELD, depot.getShelfResource(3));
    }

    //------------- TEST ADD EXTRA SLOT--------------------


    @Test
    @DisplayName("Checking addExtraResource can handle not existing Extra Slot")
    void addExtraResourceToNotExistingSlot() {

        assertThrows(MissingExtraSlot.class,
                () -> depot.addExtraResource(Resource.COIN, 2));
    }

    //------------- TEST MOVE FROM SHELF TO SLOT--------------------


    @Test
    @DisplayName("Checking moveFromShelfToSlot can handle negative quantities")
    void moveNegativeQuantity() throws ExistingResourceException, InvalidResourceException, NotEnoughSpaceException, InvalidShelfPosition, MissingExtraSlot {

        depot.addResource(2, 1, Resource.SERVANT);
        depot.addExtraSlot(Resource.SERVANT, 0);
        depot.moveFromShelfToSlot(2, -1);
        assertEquals(1, depot.getShelfQuantity(2));
        ArrayList<ExtraSlot> depotSlots = depot.getExtraSlotList();
        ExtraSlot mySlot;
        mySlot = null;
        for(ExtraSlot slot : depotSlots) {
            if (slot.getResource() == Resource.SERVANT) mySlot = slot;
        }
        assert mySlot != null;
        assertEquals(0, mySlot.getQuantity());
    }

    @Test
    @DisplayName("Checking moveFromShelfToSlot can not move resources to not existing slots")
    void moveToNull() throws ExistingResourceException, InvalidResourceException, InvalidShelfPosition {

        depot.addResource(3, 3, Resource.STONE);
        assertThrows(MissingExtraSlot.class,
                () -> depot.moveFromShelfToSlot(3, 2));
    }

    @Test
    @DisplayName("Checking moveFromShelfToSlot can not move resources from an not existing shelf")
    void moveFromNull() throws ExistingResourceException, InvalidResourceException, InvalidShelfPosition {

        depot.addResource(2,2, Resource.SERVANT);
        depot.addExtraSlot(Resource.SERVANT, 0);
        assertThrows(InvalidShelfPosition.class,
                () -> depot.moveFromShelfToSlot(4, 1));

        ArrayList<ExtraSlot> depotSlots = depot.getExtraSlotList();
        ExtraSlot mySlot;
        mySlot = null;
        for(ExtraSlot slot : depotSlots) {
            if (slot.getResource() == Resource.SERVANT) mySlot = slot;
        }
        assert mySlot != null;
        assertEquals(0, mySlot.getQuantity());
    }

    @Test
    @DisplayName("Checking moveFromShelfToSlot can handle overflow resources movement")
    void moveTooMuch() throws ExistingResourceException, InvalidResourceException, InvalidShelfPosition{

        depot.addResource(3, 3, Resource.SERVANT);
        depot.addExtraSlot(Resource.SERVANT, 0);
        assertThrows(NotEnoughSpaceException.class,
                () -> depot.moveFromShelfToSlot(3, 3));
        assertEquals(1, depot.getShelfQuantity(3));

        ArrayList<ExtraSlot> depotSlots = depot.getExtraSlotList();
        ExtraSlot mySlot;
        mySlot = null;
        for(ExtraSlot slot : depotSlots) {
            if (slot.getResource() == Resource.SERVANT) mySlot = slot;
        }
        assert mySlot != null;
        assertEquals(2, mySlot.getQuantity());
    }

    @Test
    @DisplayName("Checking moveFromShelfToSlot's normal behaviour")
    void moveFromShelfToSlotTest() throws ExistingResourceException, InvalidResourceException, NotEnoughSpaceException, InvalidShelfPosition, MissingExtraSlot {


        depot.addResource(3, 3, Resource.SHIELD);
        depot.addExtraSlot(Resource.SHIELD, 0);
        depot.moveFromShelfToSlot(3, 2);

        assertEquals(1, depot.getShelfQuantity(3));
        ArrayList<ExtraSlot> depotSlots = depot.getExtraSlotList();
        ExtraSlot mySlot;
        mySlot = null;
        for(ExtraSlot slot : depotSlots) {
            if (slot.getResource() == Resource.SHIELD) mySlot = slot;
        }
        assert mySlot != null;
        assertEquals(2, mySlot.getQuantity());
    }

    //------------- TEST ADD EXTRA RESOURCE --------------------


    @Test
    @DisplayName("Checking addExtraResource can handle overflow resources *1*")
    void addExtraOverflow1() {

        assertThrows(MissingExtraSlot.class,
                () -> depot.addExtraResource(Resource.SERVANT, 1));
    }

    @Test
    @DisplayName("Checking addExtraResource can handle overflow resources *2*")
    void addExtraOverflow2() throws MissingExtraSlot {

        depot.addExtraSlot(Resource.SHIELD, 0);
        assertEquals(1, depot.addExtraResource(Resource.SHIELD, 3));
        ArrayList<ExtraSlot> depotSlots = depot.getExtraSlotList();
        ExtraSlot mySlot;
        mySlot = null;
        for(ExtraSlot slot : depotSlots) {
            if (slot.getResource() == Resource.SHIELD) mySlot = slot;
        }
        assert mySlot != null;
        assertEquals(2, mySlot.getQuantity());
    }

}