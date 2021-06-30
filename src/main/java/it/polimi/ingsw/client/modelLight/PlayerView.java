package it.polimi.ingsw.client.modelLight;

/**
 * This class has all the information about a specified player client side.
 */
public abstract class PlayerView {
    protected DevelopmentCardDecksView decks;
    protected DepotView depot;
    protected StrongboxView strongbox;
    protected String nickname;
    protected LeaderCardSetView leaderCards;
    protected boolean[] popeMeetings;

    public String getNickname() {
        return nickname;
    }

    public abstract void dumpPlayer(String player, String objectUpdated);

    public DepotView getDepot() {
        return depot;
    }

    public DevelopmentCardDecksView getDecks() {
        return decks;
    }

    public StrongboxView getStrongbox() {
        return strongbox;
    }

    public LeaderCardSetView getLeaderCards() {
        return leaderCards;
    }

    public boolean hasAttended(int i){
        return popeMeetings[i-1];
    }

    public abstract void attendPopeMeeting(int victoryPoints);


}
