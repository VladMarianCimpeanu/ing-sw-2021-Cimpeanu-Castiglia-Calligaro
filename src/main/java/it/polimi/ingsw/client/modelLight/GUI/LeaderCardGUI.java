package it.polimi.ingsw.client.modelLight.GUI;

import it.polimi.ingsw.client.Clickable;
import it.polimi.ingsw.client.GUI;
import it.polimi.ingsw.client.MessageToServer.*;
import it.polimi.ingsw.client.Shape;
import it.polimi.ingsw.client.modelLight.LeaderCardView;
import it.polimi.ingsw.client.modelLight.PlayerView;
import it.polimi.ingsw.client.panels.*;

import java.awt.*;

public class LeaderCardGUI extends LeaderCardView implements Clickable {

    private int ID;
    private String image;
    private String type;
    private Shape shape;
    private Runnable strategy = () ->{};
    private boolean activated = false;
    private Runnable defaultStrategy = () -> GUI.getGamePanel().setActionPanel(new LeaderCardsPanel(ID));
    private static final int extraSlotWidth = 20;
    private static final int extraSlotHeight = 20;
    private static final int extraSlot1X = 31;
    private static final int extraSlot2X = 81;
    private static final int extraSlotY = 165;


    public void setShape(Shape shape) {
        this.shape = shape;
    }

    @Override
    public void show() {

    }

    @Override
    public boolean isClicked(int x, int y) {
        return((shape.getX() <= x && x <= (shape.getX() + shape.getWidth())) && (shape.getY() <= y && y <= (shape.getY() + shape.getHeight())));
    }

    @Override
    public void click(int x, int y) {
        strategy.run();
    }

    public Shape getShape() {
        return shape;
    }

    public String getImage() {
        return image;
    }

    @Override
    public int getID() {
        return ID;
    }

    @Override
    public void activate(){
        activated = true;
        if(type.equals("production")) defaultStrategy = () ->{
            GUI.getClient().send(new ActivateExtraProduction(ID));
            ((GameGUI)GUI.getClient().getGameView()).setProdPanel("extra");
        };
        else if(type.equals("depot")) defaultStrategy = () -> {
            PlayerView player = GUI.getClient().getGameView().getPlayer(GUI.getClient().getNickname());
            if(((DepotGUI)player.getDepot()).getShelfFrom() == -1){
                GUI.getGamePanel().setActionPanel(new MoveExtraPanel(extraSlot.size(), GUI.getGamePanel().getActionPanel()));
                ((DepotGUI)player.getDepot()).setExtraFromMove(this);
            }else
                GUI.getGamePanel().setActionPanel(new MoveToExtraPanel(this, ((MovePanel)GUI.getGamePanel().getActionPanel()).getLastPanel()));
            GUI.getGamePanel().repaint();
        };
        else {
            defaultStrategy = () -> { };
        }
        setStrategyDefault();
        GUI.getGamePanel().repaint();
    }

    public void setStrategyFirst(){
        strategy = () -> {
            GUI.keepLeader(getID());
        };
    }

    public void setStrategyDefault(){
        strategy = defaultStrategy;
    }

    public void setBuyStrategy(){
        strategy = () -> {
            if(extraSlot.size() > 0)
                GUI.getClient().send(new TakeResPos(extraSlot.get(0), "extra"));
        };
    }

    public void setMarketStrategy(){
        strategy = () -> GUI.getClient().send(new Strategy(ID));
    }

    /**
     * if this card is activated and offers a depot effect, than it is added to the clickable objects of the current
     * dialog
     */
    public void addSlot(){
        if (activated && this.type.equals("depot")) GUI.getGamePanel().addAction(this);
    }

    public void setDiscountStrategy(){
        strategy = () -> {
            if(type.equals("buyCard") && isActivated()) {
                ((DiscountPanel) GUI.getGamePanel().getActionPanel()).addDiscount(ID);
                setStrategyDefault();
            }
        };
    }

    /**
     * resets the last panel used before a move action.
     */
    public void revalidateActionPanel(){
        MovePanel currentPanel = (MovePanel)GUI.getGamePanel().getActionPanel();
        currentPanel.restoreClickable();
        GUI.getGamePanel().unlockGameBoard(currentPanel.wasBoardUnlocked());
        GUI.getGamePanel().setActionPanel(currentPanel.getLastPanel());
        GUI.getGamePanel().repaint();
    }

    public boolean isActivated(){
        return activated;
    }

    /**
     * Print the extra slot if the card type is ExtraSlot
     * Doesn't do anything if the card type is not ExtraSlot
     * @param g GamePanel related graphic
     */
    public void printExtraSlot(Graphics g){
        if(type.equals("depot")){
            if(extraSlot.size() >= 1){
                Shape extraSlot1 = new Shape(
                        shape.getX() + extraSlot1X,
                        shape.getY() + extraSlotY,
                        extraSlotWidth,
                        extraSlotHeight
                );
                GamePanel.drawImage(g, extraSlot.get(0).url(),extraSlot1 );
                if(extraSlot.size() >= 2){
                    Shape extraSlot2 = new Shape(
                            shape.getX() + extraSlot2X,
                            shape.getY() + extraSlotY,
                            extraSlotWidth,
                            extraSlotHeight
                    );
                    GamePanel.drawImage(g, extraSlot.get(0).url(),extraSlot2);
                }
            }
        }
    }
}
