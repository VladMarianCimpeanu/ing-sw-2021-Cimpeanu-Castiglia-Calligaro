package it.polimi.ingsw.model.leaderCards;

import it.polimi.ingsw.model.stubs.DashboardStub1;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static it.polimi.ingsw.model.benefit.Resource.COIN;
import static org.junit.jupiter.api.Assertions.*;

class ResourceRequirementTest {
    @Test
    @DisplayName("Simple call to the method using a legit Dashboard")
    void isSatisfied(){
        Requirement requirement = new ResourceRequirement(3, COIN);
        DashboardStub1 db = new DashboardStub1();
        assertTrue(requirement.isSatisfied(db));
        assertEquals(3, db.getTestresourceNumber());
        assertEquals(COIN, db.getTestresource());
    }

    @Test
    @DisplayName("Simple call to method using a null value as Dashboard")
    void isSatisfiedNull(){
        Requirement requirement = new ResourceRequirement(3, COIN);
        assertFalse(requirement.isSatisfied(null));
    }


}