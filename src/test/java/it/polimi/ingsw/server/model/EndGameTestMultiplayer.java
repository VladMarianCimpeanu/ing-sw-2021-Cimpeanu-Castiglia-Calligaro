package it.polimi.ingsw.server.model;

import it.polimi.ingsw.server.controller.VirtualView;
import it.polimi.ingsw.server.model.exceptions.*;
import it.polimi.ingsw.server.model.stubs.VirtualViewStub;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class EndGameTestMultiplayer {
    private static Game game;
    private static Player player;
    private static ArrayList<DevelopmentCard> cards;
    @BeforeEach
    void init() throws NoSuchPlayerException, InvalidReadException, InvalidStepsException, IOException {
        ArrayList<Identity> ids = new ArrayList<>();
        VirtualView vv = new VirtualViewStub();
        for(int id = 0; id < 2; id ++)
            ids.add(new Identity(Integer.valueOf(id).toString()));
        game = new Multiplayer(ids);
        game.subscribe(vv);
        player = game.getPlayers().get(0);
        game.getDevelopmentCardSet().subscribe(vv);
        game.getFaithPath().subscribe(vv);
        game.getMarket().subscribe(vv);
        for(Player p: game.getPlayers()){
            p.subscribe(vv);
            p.getDashboard().getWarehouseDepot().subscribe(vv);
            p.getDashboard().getStrongbox().subscribe(vv);
        }
        cards = DevelopmentCardDeck.getDevelopmentCardDeck();
    }

    @Test
    void sevenCardsBought() throws GameEndedException, WrongLevelException, InvalidDeckPositionException {
        player.getDashboard().addDevelopmentCard(cards.get(0), 1);
        player.getDashboard().addDevelopmentCard(cards.get(1), 2);
        player.getDashboard().addDevelopmentCard(cards.get(2), 3);
        player.getDashboard().addDevelopmentCard(cards.get(19), 1);
        player.getDashboard().addDevelopmentCard(cards.get(22), 2);
        player.getDashboard().addDevelopmentCard(cards.get(24), 3);
        assertThrows(GameEndedException.class,
                () -> player.getDashboard().addDevelopmentCard(cards.get(45), 1));
    }

}