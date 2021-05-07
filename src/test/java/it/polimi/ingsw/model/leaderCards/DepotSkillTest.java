package it.polimi.ingsw.model.leaderCards;

import it.polimi.ingsw.model.stubs.DashboardStub1;
import it.polimi.ingsw.model.stubs.PlayerStub1;
import it.polimi.ingsw.model.stubs.WarehouseDepotStub1;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DepotSkillTest {
    @Test
    @DisplayName("Normal behaviour")
    void activeCard() {
        PlayerStub1 player = new PlayerStub1();
        LeaderCard skill = new DepotSkill(null, null, 0, 0);
        try {
            skill.activeCard(player);
        } catch (it.polimi.ingsw.model.exceptions.CardActivationException e) {
            e.printStackTrace();
        }
        DashboardStub1 db = (DashboardStub1) player.getDashboard();
        WarehouseDepotStub1 depot = (WarehouseDepotStub1) db.getWarehouseDepot();
        assertEquals(depot.getCountExtraSlot(), 1);
    }
}