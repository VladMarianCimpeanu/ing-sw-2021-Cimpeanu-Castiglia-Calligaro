package it.polimi.ingsw.model.leaderCards;

import it.polimi.ingsw.model.ExtraProduction;
import it.polimi.ingsw.model.stubs.DashboardStub1;
import it.polimi.ingsw.model.stubs.PlayerStub1;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProductionSkillTest {

    @Test
    @DisplayName("Normal behaviour")
    void activeCard() {
        PlayerStub1 player = new PlayerStub1();
        LeaderCard skill = new ProductionSkill(null, null, 0);
        try {
            skill.activeCard(player);
        } catch (it.polimi.ingsw.model.exceptions.CardActivationException e) {
            e.printStackTrace();
        }
        DashboardStub1 db = (DashboardStub1) player.getDashboard();
        assertTrue(db.getExtraProduction() instanceof ExtraProduction);
    }

}