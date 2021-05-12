package it.polimi.ingsw.server.model.leaderCards;

import it.polimi.ingsw.server.model.stubs.DashboardStub1;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static it.polimi.ingsw.server.model.Color.YELLOW;
import static org.junit.jupiter.api.Assertions.*;

class DevelopmentRequirementTest {

    @Test
    @DisplayName("Simple call to the method using a legit Dashboard")
    void isSatisfied() {
        Requirement requirement = new DevelopmentRequirement(2,2 , YELLOW);
        DashboardStub1 db = new DashboardStub1();
        assertTrue(requirement.isSatisfied(db));
        assertEquals(2, db.getTestcardsNumber());
        assertEquals(2, db.getTestcardsLevel());
        assertEquals(YELLOW, db.getTestcolor());
    }

    @Test
    @DisplayName("Simple call to method using a null value as Dashboard")
    void isSatisfiedNull(){
        Requirement requirement = new DevelopmentRequirement(2, 2, YELLOW);
        assertFalse(requirement.isSatisfied(null));
    }
}