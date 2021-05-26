package it.polimi.ingsw.client.modelLight.GUI;


import it.polimi.ingsw.client.GUI;
import it.polimi.ingsw.client.panels.GamePanel;
import it.polimi.ingsw.client.modelLight.DevelopmentCardDecksView;

import java.awt.*;

public class DevelopmentCardDecksGUI extends DevelopmentCardDecksView {

    public DevelopmentCardDecksGUI(){
        super();
        paddingBoardX = 245;
        paddingY = 210;
        paddingCards = DevelopmentCardGUI.getWidth() + 35;
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
    }


    /**
     * add a development card that was owned by the player.
     * @param deckIndex deck to place the card to reload.
     * @param ID id of the card to reload.
     */
    @Override
    public void reloadCard(int deckIndex, int ID, String nickname) {
        placeCard(deckIndex, ID, nickname);
    }

    @Override
    public void show() {
    }

    /**
     * set to the selected card the position on the screen and adds it to the clickable objects of the game.
     * @param deckIndex deck to place the new card.
     * @param ID id of the card to place.
     * @param nickname nickname of the owner.
     */
    private void placeCard(int deckIndex, int ID, String nickname){
        DevelopmentCardGUI newCard = DevelopmentCardsSetGUI.getCard(ID);
        newCard.setPosition(paddingBoardX + (deckIndex - 1) * paddingCards, paddingY);
        if(nickname.equals(GUI.getClient().getNickname())) {
            GUI.getGamePanel().addGameboard(newCard);
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
}
