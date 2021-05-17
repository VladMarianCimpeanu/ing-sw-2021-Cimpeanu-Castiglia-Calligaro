package it.polimi.ingsw.server.model;

import it.polimi.ingsw.server.model.benefit.Benefit;
import it.polimi.ingsw.server.model.benefit.Faith;
import it.polimi.ingsw.server.model.benefit.Resource;
import it.polimi.ingsw.server.model.exceptions.*;
import it.polimi.ingsw.server.model.stubs.VirtualViewStub;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


import static it.polimi.ingsw.server.model.Color.*;
import static it.polimi.ingsw.server.model.benefit.Resource.*;
import static org.junit.jupiter.api.Assertions.*;

public class ProductionIntegrationTest {
    private Game game;
    private Player player;
    private Strongbox strongbox;

    @BeforeEach
    void init(){
        ArrayList<Identity> identities = new ArrayList<>();
        identities.add(new Identity("A"));
        identities.add(new Identity("B"));
        try {
            game = new Multiplayer(identities);
        } catch (InvalidStepsException | NoSuchPlayerException | IOException | InvalidReadException e) {
            e.printStackTrace();
        }
        player = game.getPlayers().get(0);
        strongbox = player.getDashboard().getStrongbox();
        VirtualViewStub virtualView = new VirtualViewStub();
        strongbox.subscribe(virtualView);
        game.getFaithPath().subscribe(virtualView);
        game.getDevelopmentCardSet().subscribe(virtualView);
        player.subscribe(virtualView);
        try {
            strongbox.addResource(SHIELD, 20);
            strongbox.addResource(SERVANT, 20);
            strongbox.addResource(STONE, 20);
            strongbox.addResource(COIN, 20);
            strongbox.addProduced();
        }catch (NegativeQuantityException e) {
            e.printStackTrace();
        }
        try {
            player.drawDevelopmentCard(YELLOW, 1, new ArrayList<Discount>());
            player.completePayment();
            player.placeDevelopmentCard(1);
            player.drawDevelopmentCard(GREEN, 1, new ArrayList<Discount>());
            player.completePayment();
            player.placeDevelopmentCard(2);
        } catch (NoCardException | InvalidDiscountException | NotEnoughResourcesException | WrongLevelException e) {
            e.printStackTrace();
        }

    }

    @Test
    void DevProduction(){
        DevelopmentCard card = player.getDashboard().getDevelopmentDecks().get(0).peek();
        Map<Benefit, Integer> out = card.getBenefitsOut();
        try {
            player.getDashboard().selectCardProduction(1);
        } catch (InvalidDeckPositionException | NotEnoughResourcesException | NoCardException | ProductionStartedException | ProductionUsedException e) {
            e.printStackTrace();
        }
        player.getDashboard().automatizePayment();
        assertTrue(player.getDashboard().getResourcesToPay().isEmpty());

        Map<Resource, Integer> strongboxBefore = new HashMap<>();
        for(Resource resource: Resource.values())
            strongboxBefore.put(resource, strongbox.getResourceQuantity(resource));

        try {
            player.getDashboard().activateProduction();
        } catch (NoProductionAvailableException e) {
            e.printStackTrace();
        }
        strongbox.addProduced();
        for(Benefit benefit: out.keySet())
            if(!benefit.equals(Faith.giveFaith()))
                assertEquals(strongboxBefore.get((Resource) benefit) + out.get(benefit), strongbox.getResourceQuantity((Resource) benefit));

        assertThrows(ProductionUsedException.class, ()->{
            player.getDashboard().selectCardProduction(1);
        });
    }

    @Test
    void BaseProd(){
        Map<Resource, Integer> in = new HashMap<>();
        in.put(COIN, 1);
        in.put(SHIELD, 1);
        try {
            player.getDashboard().selectBaseProduction(in, STONE);
            player.getDashboard().automatizePayment();
        } catch (NotEnoughResourcesException | ResourceCostException | ProductionStartedException | ProductionUsedException e) {
            e.printStackTrace();
        }

        Map<Resource, Integer> strongboxBefore = new HashMap<>();
        for(Resource resource: Resource.values())
            strongboxBefore.put(resource, strongbox.getResourceQuantity(resource));

        try {
            player.getDashboard().activateProduction();
        } catch (NoProductionAvailableException e) {
            e.printStackTrace();
        }
        strongbox.addProduced();
        assertEquals(strongboxBefore.get(STONE)+1, strongbox.getResourceQuantity(STONE));
        assertThrows(ProductionUsedException.class, ()->{
            player.getDashboard().selectBaseProduction(in, SERVANT);
        });

    }

    @Test
    void MultipleProductions(){
        Map<Resource, Integer> out = new HashMap<>();
        for(Resource resource:Resource.values())
            out.put(resource, 0);
        Map<Resource, Integer> in = new HashMap<>();
        for(Resource resource:Resource.values())
            in.put(resource, 0);
        Map<Resource, Integer> strongboxBefore = new HashMap<>();
        for(Resource resource: Resource.values())
            strongboxBefore.put(resource, strongbox.getResourceQuantity(resource));

        //Base production
        Map<Resource, Integer> input = new HashMap<>();
        input.put(COIN, 1);
        input.put(SHIELD, 1);
        in.put(COIN, 1);
        in.put(SHIELD, 1);
        try {
            player.getDashboard().selectBaseProduction(input, STONE);
            player.getDashboard().automatizePayment();
            player.getDashboard().activateProduction();
        } catch (NotEnoughResourcesException | ResourceCostException | ProductionStartedException | ProductionUsedException | NoProductionAvailableException e) {
            e.printStackTrace();
        }
        out.put(STONE, out.get(STONE)+1);

        //First DevelopmentCard Production
        DevelopmentCard card = player.getDashboard().getDevelopmentDecks().get(0).peek();

        try {
            player.getDashboard().selectCardProduction(1);
            player.getDashboard().automatizePayment();
            player.getDashboard().activateProduction();
        } catch (InvalidDeckPositionException | NotEnoughResourcesException | NoCardException | ProductionStartedException | ProductionUsedException | NoProductionAvailableException e) {
            e.printStackTrace();
        }
        for(Resource resource: card.getResourceIn().keySet())
            in.put(resource, in.get(resource)+card.getResourceIn().get(resource));
        for(Benefit benefit:card.getBenefitsOut().keySet())
            if(!benefit.equals(Faith.giveFaith()))
                out.put((Resource) benefit, out.get(benefit)+card.getBenefitsOut().get(benefit));

        //Second DevelopmentCard Production
        card = player.getDashboard().getDevelopmentDecks().get(1).peek();

        try {
            player.getDashboard().selectCardProduction(2);
            player.getDashboard().automatizePayment();
            player.getDashboard().activateProduction();
        } catch (InvalidDeckPositionException | NotEnoughResourcesException | NoCardException | ProductionStartedException | ProductionUsedException | NoProductionAvailableException e) {
            e.printStackTrace();
        }
        for(Resource resource: card.getResourceIn().keySet())
            in.put(resource, in.get(resource)+card.getResourceIn().get(resource));
        for(Benefit benefit:card.getBenefitsOut().keySet())
            if(!benefit.equals(Faith.giveFaith()))
                out.put((Resource) benefit, out.get(benefit)+card.getBenefitsOut().get(benefit));

        strongbox.addProduced();
        for(Resource resource: out.keySet())
                assertEquals(strongboxBefore.get(resource) + out.get(resource) - in.get(resource), strongbox.getResourceQuantity(resource));

    }

}
