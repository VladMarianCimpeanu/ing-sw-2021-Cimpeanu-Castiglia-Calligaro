package it.polimi.ingsw.model;

import it.polimi.ingsw.model.exceptions.*;
import it.polimi.ingsw.model.leaderCards.LeaderCard;
import it.polimi.ingsw.model.stubs.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static it.polimi.ingsw.model.Color.YELLOW;
import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    @Test
    @DisplayName("(Try to)Activate a LeaderCard not satisfying requirements")
    void activateLeaderCardException() {
        ArrayList<LeaderCard> cards = new ArrayList<>();
        cards.add(new LeaderCardStub1(false));
        Player player = new Player(null, null, cards, null);
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
        LeaderCard card = cards.get(0);
        int dim = cards.size();
        player.activateLeaderCard(card);
        assertEquals(dim-1, player.getLeaderCards().size());
        assertNotEquals(card, player.getLeaderCards().get(0));
    }

    @Test
    @DisplayName("(Try to)Discard a LeaderCard not satisfying requirements")
    void discardLeaderCardException() {
        ArrayList<LeaderCard> cards = new ArrayList<>();
        cards.add(new LeaderCardStub1(false));
        Player player = new Player(null, new GameStub1(), cards, null);
        int pre_dim = player.getLeaderCards().size();
        assertThrows(RequirementException.class, () ->{
            player.discardLeaderCard(player.getLeaderCards().get(0));
        });
        int post_dim = player.getLeaderCards().size();
        assertEquals(pre_dim, post_dim);
    }

    @Test
    @DisplayName("Discard a LeaderCard satisfying requirements")
    void discardLeaderCard() throws RequirementException {
        ArrayList<LeaderCard> cards = new ArrayList<>();
        cards.add(new LeaderCardStub1(true));
        cards.add(new LeaderCardStub1(true));
        Player player = new Player(null, new GameStub1(), cards, null);
        LeaderCard card = player.getLeaderCards().get(0);
        int pre_dim = player.getLeaderCards().size();
        player.discardLeaderCard(card);
        int post_dim = player.getLeaderCards().size();
        FaithPathStub1 faithpath = (FaithPathStub1) player.getGame().getFaithPath();
        assertTrue(faithpath.isHaveMovedOpponent());
        assertEquals(pre_dim-1, post_dim);
        assertNotEquals(card, player.getLeaderCards().get(0));
    }

    @Test
    @DisplayName("Everything about goToMarket")
    void goToMarket() throws InvalidDirectionSelection, OutOfBoundRowException, OutOfBoundColumnsException {
        Player player = new Player(null, new GameStub1(), null, null);
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
        int pre_vp = player.getVictoryPoints();
        player.addVictoryPoints(2);
        int post_vp = player.getVictoryPoints();
        assertEquals(pre_vp+2, post_vp);
    }

    @Test
    @DisplayName("Add a negative value of victory points")
    void addVictoryPointsNegative(){
        Player player = new Player(null, null, null, null);
        int pre_vp = player.getVictoryPoints();
        player.addVictoryPoints(-2);
        int post_vp = player.getVictoryPoints();
        assertEquals(pre_vp, post_vp);
    }

    @Test
    @DisplayName("Add a non relevant value of faithPoints to the calling player")
    void addFaithPoint() {
        GameStub1 game = new GameStub1();
        Player player = new Player(null, game, null, null );
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
        int pre_dim = player.getMarketStrategies().size();
        MarketStrategy strategy = new MarketStrategy(null);
        player.addMarketStrategy(strategy);
        int post_dim = player.getMarketStrategies().size();
        assertEquals(pre_dim+1, post_dim);
        assertTrue(player.getMarketStrategies().contains(strategy));
    }

    @Test
    @DisplayName("Add null instead of a legit MarketStrategy")
    void addMarketStrategyNull() throws NoSuchStrategyException {
        Player player = new Player(null, null, null, null);
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
    void buyDevelopmentCardRequirementException() {
        GameStub1 game = new GameStub1();
        DashboardStub1 db = new DashboardStub1(false);
        Player player = new Player(null, game, null, db);
        assertThrows(RequirementException.class, () -> {
           player.buyDevelopmentCard(YELLOW, 1);
        });
        assertEquals(0, db.getCardAdded());
        DevelopmentCardSetStub1 cardSet = (DevelopmentCardSetStub1) player.getGame().getDevelopmentCardSet();
        assertEquals(-1, cardSet.getLevel());
        assertEquals(null, cardSet.getColor());
    }

    @Test
    @DisplayName("(Try to) Buy a DevelopmentCard from an empty Deck")
    void buyDevelopmentCardNoCardException(){
        GameStub1 game = new GameStub1(new DevelopmentCardSetStub1(0));
        DashboardStub1 db = new DashboardStub1();
        Player player = new Player(null, game, null, db);
        assertThrows(NoCardException.class, () -> {
            player.buyDevelopmentCard(YELLOW, 1);
        });
        assertEquals(0, db.getCardAdded());
        DevelopmentCardSetStub1 cardSet = (DevelopmentCardSetStub1) player.getGame().getDevelopmentCardSet();
        assertEquals(-1, cardSet.getLevel());
        assertEquals(null, cardSet.getColor());
    }

    @Test
    @DisplayName("Buy a DevelopmentCard satisfying requirements")
    void buyDevelopmentCard() throws RequirementException, NoCardException, WrongLevelException {
        GameStub1 game = new GameStub1();
        DashboardStub1 db = new DashboardStub1(true);
        Player player = new Player(null, game, null, db);
        player.buyDevelopmentCard(YELLOW, 1);
        assertEquals(1, db.getCardAdded());
        DevelopmentCardSetStub1 cardSet = (DevelopmentCardSetStub1) player.getGame().getDevelopmentCardSet();
        assertEquals(1, cardSet.getLevel());
        assertEquals(YELLOW, cardSet.getColor());
    }

    @Test
    void passStrategiesToMarket() {
        Player player = new Player(null, new GameStub1(), null, null);
        player.passStrategiesToMarket();
        MarketStub1 market = (MarketStub1) player.getGame().getMarket();
        assertTrue(market.isPassedStrategies());
    }
}