package it.polimi.ingsw.model.leaderCards;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.exceptions.CardActivationException;
import it.polimi.ingsw.model.stubs.DashboardStub1;
import it.polimi.ingsw.model.stubs.RequirementStub1;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class LeaderCardTest {

    @Test
    @DisplayName("Some requirements not satisfied")
    void isSatisfiedFalse1() {
        ArrayList<Requirement> requirements = new ArrayList<>();
        DashboardStub1 db = new DashboardStub1();
        for(int i = 0; i<5; i++)
            if(i%2 == 0) requirements.add(new RequirementStub1(false));
            else requirements.add(new RequirementStub1(true));
        LeaderCard leaderCard = new LeaderCard(requirements, null, 0, 0) { //only first parameter interesting for this test
            @Override
            public void activeCard(Player player) throws CardActivationException {
                //Not interesting for this test
            }
        };
        assertFalse(leaderCard.isSatisfied(db));
    }

    @Test
    @DisplayName("All requirements not satisfied")
    void isSatisfiedFalse2() {
        ArrayList<Requirement> requirements = new ArrayList<>();
        DashboardStub1 db = new DashboardStub1();
        for(int i = 0; i<5; i++)
            requirements.add(new RequirementStub1(false));
        LeaderCard leaderCard = new LeaderCard(requirements, null, 0, 0) { //only first parameter interesting for this test
            @Override
            public void activeCard(Player player) throws CardActivationException {
                //Not interesting for this test
            }
        };
        assertFalse(leaderCard.isSatisfied(db));
    }

    @Test
    @DisplayName("All requirements not satisfied")
    void isSatisfiedTrue() {
        ArrayList<Requirement> requirements = new ArrayList<>();
        DashboardStub1 db = new DashboardStub1();
        for(int i = 0; i<5; i++)
            requirements.add(new RequirementStub1(true));
        LeaderCard leaderCard = new LeaderCard(requirements, null, 0, 0) { //only first parameter interesting for this test
            @Override
            public void activeCard(Player player) throws CardActivationException {
                //Not interesting for this test
            }
        };
        assertTrue(leaderCard.isSatisfied(db));
    }

    @Test
    @DisplayName("Empty list of requirements")
    void isSatisfiedEmpty() {
        ArrayList<Requirement> requirements = new ArrayList<>();
        DashboardStub1 db = new DashboardStub1();
        LeaderCard leaderCard = new LeaderCard(requirements, null, 0, 0) { //only first parameter interesting for this test
            @Override
            public void activeCard(Player player) throws CardActivationException {
                //Not interesting for this test
            }
        };
        assertTrue(leaderCard.isSatisfied(db));
    }
    @Test
    @DisplayName("Dashboard parameter = null")
    void isSatisfiedNullDb() {
        ArrayList<Requirement> requirements = new ArrayList<>();
        for(int i = 0; i<5; i++)
            requirements.add(new RequirementStub1(true));
        LeaderCard leaderCard = new LeaderCard(requirements, null, 0, 0) { //only first parameter interesting for this test
            @Override
            public void activeCard(Player player) throws CardActivationException {
                //Not interesting for this test
            }
        };
        assertFalse(leaderCard.isSatisfied(null));
    }

}