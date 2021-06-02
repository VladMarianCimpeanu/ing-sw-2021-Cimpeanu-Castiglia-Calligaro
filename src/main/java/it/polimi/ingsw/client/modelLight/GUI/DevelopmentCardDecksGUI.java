package it.polimi.ingsw.client.modelLight.GUI;


import it.polimi.ingsw.client.Clickable;
import it.polimi.ingsw.client.GUI;
import it.polimi.ingsw.client.MessageToServer.PlaceCard;
import it.polimi.ingsw.client.modelLight.PlayerView;
import it.polimi.ingsw.client.panels.DefaultPanel;
import it.polimi.ingsw.client.panels.GamePanel;
import it.polimi.ingsw.client.modelLight.DevelopmentCardDecksView;

import java.awt.*;

public class DevelopmentCardDecksGUI extends DevelopmentCardDecksView implements Clickable {

    public DevelopmentCardDecksGUI(){
        super();
        paddingBoardX = 245;
        paddingY = 210;
        paddingCards = 35;
        GUI.getGamePanel().addGameboard(this);
    }


    /**
     * padding between the top of a card and the top of the board.
     */
    private final int paddingY;

    /**
     * padding between the first card and the left edge of the board.
     */
    private final int paddingBoardX;

    /**
     * padding between each deck
     */
    private final int paddingCards;

    /**
     * add a new development card to the specified deck.
     * @param deckIndex deck to place the new card.
     * @param ID id of the new development card.
     * @param nickname nickname of the new owner.
     */
    @Override
    public void addCard(int deckIndex, int ID, String nickname) {
        if(playerCards.get(deckIndex - 1) != 0) GUI.getGamePanel().removeGameboard(DevelopmentCardsSetGUI.getCard(playerCards.get(deckIndex - 1)));
        playerCards.set(deckIndex - 1, ID);
        placeCard(deckIndex, ID, nickname);
        GUI.getGamePanel().repaint();
        GUI.print(nickname + " has placed a new development card in his " + deckIndex + " deck");
        //TODO remove listeners old action panel?
        GUI.getGamePanel().setActionPanel(new DefaultPanel());
        setToProduction();
        ((DepotGUI)GUI.getClient().getGameView().getPlayer(GUI.getClient().getNickname()).getDepot()).setStrategyMove();
        ((LeaderCardSetGUI)GUI.getClient().getGameView().getPlayer(GUI.getClient().getNickname()).getLeaderCards()).setLeadersToDefaultStrategy();
        PlayerView p = GUI.getClient().getGameView().getPlayer(GUI.getClient().getNickname());
        GUI.getGamePanel().removeAction((Clickable) p.getLeaderCards());
        GUI.getGamePanel().removeAction((Clickable) p.getDepot());
        GUI.getGamePanel().removeAction((Clickable) p.getStrongbox());
        GUI.getGamePanel().removeAction((Clickable) p.getDecks());
        GUI.getGamePanel().unlockGameBoard(true);
    }


    /**
     * add a development card that was owned by the player.
     * @param deckIndex deck to place the card to reload.
     * @param ID id of the card to reload.
     */
    @Override
    public void reloadCard(int deckIndex, int ID, String nickname) {
        if (ID != 0) addCard(deckIndex, ID, nickname);
    }

    @Override
    public void show() {
    }

    /**
     * sets to the selected card the position on the screen and adds it to the clickable objects of the game.
     * @param deckIndex deck to place the new card.
     * @param ID id of the card to place.
     * @param nickname nickname of the owner.
     */
    private void placeCard(int deckIndex, int ID, String nickname){
        DevelopmentCardGUI newCard = DevelopmentCardsSetGUI.getCard(ID);
        newCard.setPosition(paddingBoardX + (deckIndex - 1) * (paddingCards+DevelopmentCardGUI.getWidth()), paddingY);
        if(nickname.equals(GUI.getClient().getNickname())) {
            newCard.setToProduction();
        }
    }

    /**
     * draws the development cards of the player.
     * @param graphics graphics object.
     */
    public void drawDecks(Graphics graphics){
        for(int deck = 0; deck < 3; deck ++){
            if(playerCards.get(deck) != 0) {
                DevelopmentCardGUI card = DevelopmentCardsSetGUI.getCard(playerCards.get(deck));
                GamePanel.drawImage(graphics, card.getUrl(), card.getShape());
            }
        }
    }

    /**
     * checks if a deck has been clicked
     * @param x x coordinate of the click
     * @param y y coordinate of the click
     * @return true if a deck has been clicked;
     */
    private boolean isADeckClicked(int x, int y){
        return isYPanelClicked(y) && (isFirstDeckClicked(x) || isSecondDeckClicked(x) || isThirdDeckClicked(x));
    }


    /**
     * checks if the X coordinate corresponds to the first deck position
     * @param x x coordinate of the click
     * @return true if the deck occupies the x coordinate.
     */
    private boolean isFirstDeckClicked(int x){
        return paddingBoardX <= x && x<= paddingBoardX + DevelopmentCardGUI.getWidth();
    }

    /**
     * checks if the X coordinate corresponds to the second deck position
     * @param x x coordinate of the click
     * @return true if the deck occupies the x coordinate.
     */
    private boolean isSecondDeckClicked(int x){
        return paddingBoardX + DevelopmentCardGUI.getWidth() + paddingCards <= x &&
                x <= paddingBoardX + 2 * DevelopmentCardGUI.getWidth() + paddingCards;
    }

    /**
     * checks if the X coordinate corresponds to the third deck position
     * @param x x coordinate of the click
     * @return true if the deck occupies the x coordinate.
     */
    private boolean isThirdDeckClicked(int x){
        return paddingBoardX + 2 * (DevelopmentCardGUI.getWidth() + paddingCards) <= x &&
                x <= paddingBoardX + 3 * DevelopmentCardGUI.getWidth() + 2 *  paddingCards;
    }

    /**
     * checks if Y coordinate is compatible with the decks positions.
     * @param y y coordinate of the click
     * @return true if a deck occupies the y coordinate.
     */
    private boolean isYPanelClicked(int y){
        return paddingY <= y && y <= paddingY + DevelopmentCardGUI.getHeight();
    }
    /**
     * checks if any development card of the player has been clicked
     * @param x x coordinate of the click
     * @param y y coordinate of the click
     * @return true if a cards has been clicked
     */
    @Override
    public boolean isClicked(int x, int y) {
        return isADeckClicked(x, y);
    }


    /**
     * clicks the card at the specified position
     * @param x x coordinate of the click
     * @param y y coordinate of the click
     */
    @Override
    public void click(int x, int y) {
        for(Integer id : playerCards){
            if(id != 0){
                DevelopmentCardGUI card = DevelopmentCardsSetGUI.getCard(id);
                if(card.isClicked(x, y)) card.click(x, y);
            }
        }

        if(isADeckClicked(x,y)) {
            //if it is clicked an empty deck, I assume a place card action is happening
            if (isFirstDeckClicked(x) && playerCards.get(0) == 0) GUI.getClient().send(new PlaceCard(1));
            else if(isSecondDeckClicked(x) && playerCards.get(1) == 0) GUI.getClient().send(new PlaceCard(2));
            else if(isThirdDeckClicked(x) && playerCards.get(2) == 0) GUI.getClient().send(new PlaceCard(3));
        }
    }

    /**
     * sets every card owned by the player to replaceable.
     */
    public void setToReplaceable(){
        for (Integer id: playerCards){
            if(id != 0) DevelopmentCardsSetGUI.getCard(id).setToReplaceable();
        }
    }

    /**
     * sets every card owned by the player to production ready.
     */
    public void setToProduction(){
        for (Integer id: playerCards){
            if(id != 0) DevelopmentCardsSetGUI.getCard(id).setToProduction();
        }
    }

}
