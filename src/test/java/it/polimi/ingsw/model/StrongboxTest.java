package it.polimi.ingsw.model;

import it.polimi.ingsw.model.benefit.Resource;
import it.polimi.ingsw.model.exceptions.NegativeQuantityException;
import it.polimi.ingsw.model.stubs.VirtualViewStub;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StrongboxTest {

    @Test
    void addResource() throws NegativeQuantityException {
        Resource resource = Resource.COIN;
        Strongbox strongbox = new Strongbox();
        strongbox.subscribe(new VirtualViewStub());
        strongbox.addResource(resource, 7);
        strongbox.addProduced();
        int result = strongbox.getResourceQuantity(resource);
        assertTrue(result == 7);
    }

    @Test
    void doubleAddResource() throws NegativeQuantityException {
        Resource resource = Resource.COIN;
        Strongbox strongbox = new Strongbox();
        strongbox.subscribe(new VirtualViewStub());
        strongbox.addResource(resource, 2);
        strongbox.addResource(resource, 4);
        strongbox.addProduced();
        int result = strongbox.getResourceQuantity(resource);
        assertTrue(result == 6);
    }

    @Test
    void negativeAddResource(){
        Resource resource = Resource.SERVANT;
        Strongbox strongbox = new Strongbox();
        strongbox.subscribe(new VirtualViewStub());
        assertThrows(NegativeQuantityException.class,
                () -> strongbox.addResource(resource,-3));
        strongbox.addProduced();
        int result = strongbox.getResourceQuantity(resource);
        assertTrue(result == 0);
    }

    @Test
    void zeroAddResource() throws NegativeQuantityException {
        Resource resource = Resource.SHIELD;
        Strongbox strongbox = new Strongbox();
        strongbox.subscribe(new VirtualViewStub());
        strongbox.addResource(resource, 0);
        strongbox.addProduced();
        int result = strongbox.getResourceQuantity(resource);
        assertTrue(result == 0);
    }

    @Test
    void partiallyAddedResources() throws NegativeQuantityException {
        Resource resource = Resource.STONE;
        Strongbox strongbox = new Strongbox();
        strongbox.subscribe(new VirtualViewStub());
        strongbox.addResource(resource, 130);
        int result = strongbox.getResourceQuantity(resource);
        assertTrue(result == 0);
    }

    @Test
    void notAddedResources(){
        Resource resource = Resource.STONE;
        Strongbox strongbox = new Strongbox();
        strongbox.subscribe(new VirtualViewStub());
        strongbox.addProduced();
        int result = strongbox.getResourceQuantity(resource);
        assertTrue(result == 0);
    }

    @Test
    void removeResource() throws NegativeQuantityException {
        Resource resource = Resource.COIN;
        Strongbox strongbox = new Strongbox();
        strongbox.subscribe(new VirtualViewStub());
        strongbox.addResource(resource, 5);
        strongbox.addProduced();
        strongbox.removeResource(resource, 3);
        int result = strongbox.getResourceQuantity(resource);
        assertTrue(result == 2);
    }

    @Test
    void negativeRemoveResource() throws NegativeQuantityException {
        Resource resource = Resource.COIN;
        Strongbox strongbox = new Strongbox();
        strongbox.subscribe(new VirtualViewStub());
        strongbox.addResource(resource, 5);
        strongbox.addProduced();
        assertThrows(NegativeQuantityException.class,
                () -> strongbox.removeResource(resource, -4));
        int result = strongbox.getResourceQuantity(resource);
        assertTrue(result == 5);
    }

    @Test
    void zeroRemoveResource() throws NegativeQuantityException {
        Resource resource = Resource.COIN;
        Strongbox strongbox = new Strongbox();
        strongbox.subscribe(new VirtualViewStub());
        strongbox.addResource(resource, 5);
        strongbox.addProduced();
        strongbox.removeResource(resource, 0);
        int result = strongbox.getResourceQuantity(resource);
        assertTrue(result == 5);
    }

    @Test
    void notRemoveResource() throws NegativeQuantityException {
        Resource resource = Resource.COIN;
        Strongbox strongbox = new Strongbox();
        strongbox.subscribe(new VirtualViewStub());
        strongbox.addResource(resource, 5);
        strongbox.addProduced();
        strongbox.addResource(resource, 2);
        strongbox.removeResource(resource, 4);
        int result = strongbox.getResourceQuantity(resource);
        assertTrue(result == 1);
    }
}