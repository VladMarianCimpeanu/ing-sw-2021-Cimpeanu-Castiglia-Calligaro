package it.polimi.ingsw.server.model.leaderCards;

import it.polimi.ingsw.server.model.MarketStrategy;
import it.polimi.ingsw.server.model.exceptions.CardActivationException;
import it.polimi.ingsw.server.model.stubs.PlayerStub1;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class MarketSkillTest {
    @Test
    @DisplayName("Normal behaviour")
    void activeCard() {
        ArrayList<Requirement> requirement = new ArrayList<>();
        PlayerStub1 player = new PlayerStub1();
        MarketSkill skill = new MarketSkill(requirement, null, 0, 0);
        try {
            skill.activeCard(player);
        } catch (it.polimi.ingsw.server.model.exceptions.CardActivationException e) {
            e.printStackTrace();
        }
        assertTrue(player.getMarketStrategycheck() instanceof MarketStrategy);
    }
    //really needed?
    @Test
    @DisplayName("Parameter player = null")
    void activeCardNullPlayer() {
        ArrayList<Requirement> requirement = new ArrayList<>();
        PlayerStub1 player = new PlayerStub1();
        MarketSkill skill = new MarketSkill(requirement, null, 0, 0);
        assertThrows(CardActivationException.class,
                () -> skill.activeCard(null));
        assertNull(player.getMarketStrategycheck());
    }
}