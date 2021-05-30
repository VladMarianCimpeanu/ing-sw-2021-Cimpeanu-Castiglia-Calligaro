package it.polimi.ingsw.server.model;

import it.polimi.ingsw.server.model.benefit.Resource;
import it.polimi.ingsw.server.model.exceptions.*;
import it.polimi.ingsw.server.model.leaderCards.LeaderCard;
import it.polimi.ingsw.server.model.stubs.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static it.polimi.ingsw.server.model.Color.PURPLE;
import static it.polimi.ingsw.server.model.Color.YELLOW;
import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {
    private static ArrayList<Identity> idS;

    @BeforeAll
    static void init(){
        idS = new ArrayList<>();
        idS.add(new Identity("Steve"));

    }

    @Test
    @DisplayName("(Try to)Activate a LeaderCard not satisfying requirements")
    void activateLeaderCardException() {
        ArrayList<LeaderCard> cards = new ArrayList<>();
        cards.add(new LeaderCardStub1(false));
        Player player = new Player(null, null, cards, null);
        player.subscribe(new VirtualViewStub());
        int pre_dim = player.getLeaderCards().size();
        assertThrows(RequirementException.class, () -> {
            player.activateLeaderCard(player.getLeaderCards().get(0));
        });
        int post_dim = player.getLeaderCards().size();
        assertEquals(pre_dim, post_dim);
    }

    @Test
    @DisplayName("Activate a LeaderCard satisfying requirements")
    void activateLeaderCard() throws RequirementException {
        ArrayList<LeaderCard> cards = new ArrayList<>();
        cards.add(new LeaderCardStub1(true));
        cards.add(new LeaderCardStub1(true));
        Player player = new Player(null, null, cards, null);
        player.subscribe(new VirtualViewStub());
        LeaderCard card = cards.get(0);
        int dim = cards.size();
        player.activateLeaderCard(card);
        assertEquals(dim-1, player.getLeaderCards().size());
        assertNotEquals(card, player.getLeaderCards().get(0));
    }

    @Test
    @DisplayName("Discard a LeaderCard not satisfying requirements")
    void discardLeaderCardException() throws IOException, InvalidReadException, NoSuchPlayerException {
        ArrayList<LeaderCard> cards = new ArrayList<>();
        cards.add(new LeaderCardStub1(false));
        Player player = new Player(null, new GameStub1(idS), cards, null);
        player.subscribe(new VirtualViewStub());
        int pre_dim = player.getLeaderCards().size();
        int post_dim = player.getLeaderCards().size();
        assertEquals(pre_dim, post_dim);
    }

    @Test
    @DisplayName("Discard a LeaderCard satisfying requirements")
    void discardLeaderCard() throws RequirementException, IOException, InvalidReadException, NoSuchPlayerException, GameEndedException {
        GameStub1 stub = new GameStub1(idS);
        ArrayList<LeaderCard> cards = new ArrayList<>();
        cards.add(new LeaderCardStub1(true));
        cards.add(new LeaderCardStub1(true));
        Player player = new Player(null, stub, cards, null);
        player.subscribe(new VirtualViewStub());
        LeaderCard card = player.getLeaderCards().get(0);
        int pre_dim = player.getLeaderCards().size();
        player.discardLeaderCard(card);
        int post_dim = player.getLeaderCards().size();
        FaithPathStub1 faithpath = (FaithPathStub1) stub.getFaithPath();
        assertFalse(faithpath.isHaveMovedOpponent());
        assertEquals(pre_dim-1, post_dim);
        assertNotEquals(card, player.getLeaderCards().get(0));
    }

    @Test
    @DisplayName("Everything about goToMarket")
    void goToMarket() throws InvalidDirectionSelection, OutOfBoundRowException, OutOfBoundColumnsException, IOException, InvalidReadException, NoSuchPlayerException {
        Player player = new Player(null, new GameStub1(idS), null, null);
        player.subscribe(new VirtualViewStub());
        assertThrows(InvalidDirectionSelection.class, () ->{
            player.goToMarket("Illegal String", 1);
        });
        assertThrows(InvalidDirectionSelection.class, () ->{
            player.goToMarket(null, 1);
        });
        assertEquals(1, player.goToMarket("Column", 1));
        assertEquals(2, player.goToMarket("Row", 2));
    }

    @Test
    @DisplayName("Add a positive value of victory points")
    void addVictoryPointsPositive() {
        Player player = new Player(null, null, null, null);
        player.subscribe(new VirtualViewStub());
        int pre_vp = player.getVictoryPoints();
        player.addVictoryPoints(2);
        int post_vp = player.getVictoryPoints();
        assertEquals(pre_vp+2, post_vp);
    }

    @Test
    @DisplayName("Add a negative value of victory points")
    void addVictoryPointsNegative(){
        Player player = new Player(null, null, null, null);
        player.subscribe(new VirtualViewStub());
        int pre_vp = player.getVictoryPoints();
        player.addVictoryPoints(-2);
        int post_vp = player.getVictoryPoints();
        assertEquals(pre_vp, post_vp);
    }

    @Test
    @DisplayName("Add a non relevant value of faithPoints to the calling player")
    void addFaithPoint() throws IOException, InvalidReadException, NoSuchPlayerException, GameEndedException {
        GameStub1 game = new GameStub1(idS);
        Player player = new Player(null, game, null, null );
        player.subscribe(new VirtualViewStub());
        int fp = 2;
        player.addFaithPoint(fp);
        FaithPathStub1 faithPath = (FaithPathStub1) game.getFaithPath();
        assertEquals(fp, faithPath.getStepsPlayer());
        assertEquals(player, faithPath.getPlayerPlaying());
    }

    @Test
    @DisplayName("Add a legit MarketStrategy")
    void addMarketStrategyLegit() throws NoSuchStrategyException {
        Player player = new Player(null, null, null, null);
        player.subscribe(new VirtualViewStub());
        int pre_dim = player.getMarketStrategies().size();
        MarketStrategy strategy = new MarketStrategy(null, 0);
        player.addMarketStrategy(strategy);
        int post_dim = player.getMarketStrategies().size();
        assertEquals(pre_dim+1, post_dim);
        assertTrue(player.getMarketStrategies().contains(strategy));
    }

    @Test
    @DisplayName("Add null instead of a legit MarketStrategy")
    void addMarketStrategyNull() throws NoSuchStrategyException {
        Player player = new Player(null, null, null, null);
        player.subscribe(new VirtualViewStub());
        int pre_dim = player.getMarketStrategies().size();
        assertThrows(NoSuchStrategyException.class, () -> {
            player.addMarketStrategy(null);
        });
        int post_dim = player.getMarketStrategies().size();
        assertEquals(pre_dim, post_dim);
        assertFalse(player.getMarketStrategies().contains(null));   //legit?
    }

    @Test
    @DisplayName("(Try to) Buy a DevelopmentCard not satisfying requirements")
    void buyDevelopmentCardRequirementException() throws IOException, InvalidReadException, NoSuchPlayerException {
        GameStub1 game = new GameStub1(idS);
        DashboardStub1 db = new DashboardStub1(false);
        Player player = new Player(null, game, null, db);
        player.subscribe(new VirtualViewStub());
        assertThrows(NotEnoughResourcesException.class, () -> {
           player.drawDevelopmentCard(YELLOW, 1, null);
        });
        assertEquals(0, db.getCardAdded());
        DevelopmentCardSetStub1 cardSet = (DevelopmentCardSetStub1) game.getDevelopmentCardSet();
        assertEquals(-1, cardSet.getLevel());
        assertEquals(null, cardSet.getColor());
    }

    @Test
    @DisplayName("(Try to) Buy a DevelopmentCard from an empty Deck")
    void buyDevelopmentCardNoCardException() throws IOException, InvalidReadException, NoSuchPlayerException {
        GameStub1 game = new GameStub1(idS, new DevelopmentCardSetStub1(0));
        DashboardStub1 db = new DashboardStub1();
        Player player = new Player(null, game, null, db);
        player.subscribe(new VirtualViewStub());
        assertThrows(NoCardException.class, () -> {
            player.drawDevelopmentCard(YELLOW, 1, null);
        });
        assertEquals(0, db.getCardAdded());
        DevelopmentCardSetStub1 cardSet = (DevelopmentCardSetStub1) game.getDevelopmentCardSet();
        assertEquals(-1, cardSet.getLevel());
        assertEquals(null, cardSet.getColor());
    }

    @Test
    @DisplayName("Buy a DevelopmentCard satisfying requirements")
    void buyDevelopmentCard() throws NoCardException, WrongLevelException, IOException, InvalidReadException, NoSuchPlayerException, NotEnoughResourcesException, InvalidDiscountException {
        GameStub1 game = new GameStub1(idS);
        DashboardStub1 db = new DashboardStub1(true);
        Player player = new Player(null, game, null, db);
        player.subscribe(new VirtualViewStub());
        player.drawDevelopmentCard(YELLOW, 1, null);

        DevelopmentCardSetStub1 cardSet = (DevelopmentCardSetStub1) game.getDevelopmentCardSet();
        assertEquals(1, cardSet.getLevel());
        assertEquals(YELLOW, cardSet.getColor());
    }

    @Test
    void passStrategiesToMarket() throws IOException, InvalidReadException, NoSuchPlayerException, InvalidStrategyException {
        GameStub1 stub = new GameStub1(idS);
        Player player = new Player(null, stub, null, null);
        player.subscribe(new VirtualViewStub());
        player.passStrategiesToMarket();
        MarketStub1 market = (MarketStub1) stub.getMarket();
        assertFalse(market.isPassedStrategies());
    }

    //This test was originally in dashboard -> moved to player because discounts are now saved in player
    @Test
    void addDiscount() throws NoSuchPlayerException, IOException, InvalidReadException {
        Player player = new Player(null, new GameStub1(idS), null, null);
        player.subscribe(new VirtualViewStub());
        DiscountStub discountStub = new DiscountStub(Resource.COIN);
        player.addDiscount(discountStub);
        Discount result = player.getDiscountList().get(0);

        assertTrue(discountStub.equals(result));
    }

    @Test
    void autoCompletePayment() throws NoSuchPlayerException, IOException, InvalidReadException, InvalidDiscountException, NoCardException, NotEnoughResourcesException, WrongLevelException, InvalidStepsException, NegativeQuantityException, GameEndedException {
        ArrayList<Identity> identities = new ArrayList<>();
        identities.add(new Identity("danilo"));
        identities.add(new Identity("nick"));
        Game game = new Multiplayer(identities);
        Player player = game.getPlayers().get(0);
        player.subscribe(new VirtualViewStub());
        Strongbox strongbox = player.getDashboard().getStrongbox();
        strongbox.subscribe(new VirtualViewStub());
        game.getDevelopmentCardSet().subscribe(new VirtualViewStub());

        strongbox.addResource(Resource.COIN, 100);
        strongbox.addResource(Resource.SERVANT, 100);
        strongbox.addResource(Resource.SHIELD, 100);
        strongbox.addResource(Resource.STONE, 100);
        strongbox.addProduced();
        DevelopmentCard card = game.getDevelopmentCardSet().getAvailableDevelopmentCards().get(0).get(3).peek();
        player.drawDevelopmentCard(PURPLE, 1, null);
        player.completePayment();
        assertEquals(player.getDevelopmentCardCost(), new HashMap<>());
        player.autoPlace();
        assertEquals(player.getDashboard().getActivableDevCards().get(0), card);
    }
}