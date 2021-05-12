package it.polimi.ingsw.server.model.leaderCards;

import it.polimi.ingsw.server.model.ExtraProduction;
import it.polimi.ingsw.server.model.stubs.DashboardStub1;
import it.polimi.ingsw.server.model.stubs.PlayerStub1;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProductionSkillTest {

    @Test
    @DisplayName("Normal behaviour")
    void activeCard() {
        PlayerStub1 player = new PlayerStub1();
        LeaderCard skill = new ProductionSkill(null, null, 0, 0);
        try {
            skill.activeCard(player);
        } catch (it.polimi.ingsw.server.model.exceptions.CardActivationException e) {
            e.printStackTrace();
        }
        DashboardStub1 db = (DashboardStub1) player.getDashboard();
        assertTrue(db.getExtraProduction() instanceof ExtraProduction);
    }

}