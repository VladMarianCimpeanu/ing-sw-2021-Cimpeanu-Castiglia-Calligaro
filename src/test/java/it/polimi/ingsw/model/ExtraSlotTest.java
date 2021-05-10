package it.polimi.ingsw.model;

import it.polimi.ingsw.model.benefit.Resource;
import it.polimi.ingsw.model.exceptions.NotEnoughResourcesException;
import it.polimi.ingsw.model.exceptions.NotEnoughSpaceException;
import it.polimi.ingsw.model.stubs.VirtualViewStub;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ExtraSlotTest {

    //------------- TEST ADD RESOURCE --------------------


    @Test
    @DisplayName("Checking addResource can handle negative values")
    void addNegativeQuantity() {
        ExtraSlot slot = new ExtraSlot(Resource.COIN, 0);
        assertEquals(0, slot.addResource(- 1));
        assertEquals(0, slot.getQuantity());
    }

    @Test
    @DisplayName("Checking addResource can handle storages over the slot's limit *1*")
    void addOverQuantity1() {
        ExtraSlot slot = new ExtraSlot(Resource.COIN, 0);
        slot.subscribe(new VirtualViewStub());
        assertEquals(2, slot.addResource(4));
        assertEquals(2, slot.getQuantity());
    }

    @Test
    @DisplayName("Checking addResource can handle storages over the slot's limit *2*")
    void addOverQuantity2() {
        ExtraSlot slot = new ExtraSlot(Resource.COIN, 0);
        slot.subscribe(new VirtualViewStub());
        slot.addResource(2);
        assertEquals(1, slot.addResource(1));
        assertEquals(2, slot.getQuantity());
    }

    @Test
    @DisplayName("Checking addResource can work in normal conditions")
    void addQuantity() {
        ExtraSlot slot = new ExtraSlot(Resource.COIN, 0);
        slot.subscribe(new VirtualViewStub());
        assertEquals(0, slot.addResource(1));
        assertEquals(1, slot.getQuantity());
        assertEquals(0, slot.addResource(1));
        assertEquals(2, slot.getQuantity());
    }


    //------------- TEST REMOVE RESOURCE --------------------

    @Test
    @DisplayName("Checking removeResource can handle negative quantities")
    void removeNegativeQuantity() {
        ExtraSlot slot = new ExtraSlot(Resource.SHIELD, 0);
        assertEquals(0, slot.removeResource(-5));
        assertEquals(0, slot.getQuantity());
    }

    @Test
    @DisplayName("Checking removeResource can handle overRequests *1*")
    void removeTooManyResources1() {
        ExtraSlot slot = new ExtraSlot(Resource.SHIELD, 0);
        slot.subscribe(new VirtualViewStub());
        assertEquals(1, slot.removeResource(1));
        assertEquals(0, slot.getQuantity());
    }

    @Test
    @DisplayName("Checking removeResource can handle overRequests *2*")
    void removeTooManyResources2() {
        ExtraSlot slot = new ExtraSlot(Resource.STONE, 0);
        slot.subscribe(new VirtualViewStub());
        slot.addResource(1);
        slot.addResource(1);
        assertEquals(2, slot.removeResource(4));
        assertEquals(0, slot.getQuantity());
    }
    @Test
    @DisplayName("Checking removeResource can work in normal conditions")
    void removeResources() throws NotEnoughSpaceException, NotEnoughResourcesException {
        ExtraSlot slot = new ExtraSlot(Resource.SERVANT, 0);
        slot.subscribe(new VirtualViewStub());
        slot.addResource(1);
        slot.addResource(1);
        slot.removeResource(2);
        assertEquals(0, slot.getQuantity());
        slot.addResource(2);
        slot.removeResource(1);
        assertEquals(1, slot.getQuantity());
        slot.removeResource(1);
        assertEquals(0, slot.getQuantity());
    }
}