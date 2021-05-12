package it.polimi.ingsw.server.model.leaderCards;

import it.polimi.ingsw.server.model.stubs.DashboardStub1;
import it.polimi.ingsw.server.model.stubs.PlayerStub1;
import it.polimi.ingsw.server.model.stubs.WarehouseDepotStub1;
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
        } catch (it.polimi.ingsw.server.model.exceptions.CardActivationException e) {
            e.printStackTrace();
        }
        DashboardStub1 db = (DashboardStub1) player.getDashboard();
        WarehouseDepotStub1 depot = (WarehouseDepotStub1) db.getWarehouseDepot();
        assertEquals(depot.getCountExtraSlot(), 1);
    }
}