package it.polimi.ingsw.model.leaderCards;

import it.polimi.ingsw.model.Dashboard;

/**
 * A leader card requirement
 */
public interface Requirement {
    /**
     * necessary at the moment of activation(or rejection) of a leader card
     * @return true if the requirement is satisfied
     */
    public boolean isSatisfied(Dashboard dashboard);
}
