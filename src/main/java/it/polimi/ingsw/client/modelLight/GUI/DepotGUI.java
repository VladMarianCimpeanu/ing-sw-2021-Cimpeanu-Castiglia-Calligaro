package it.polimi.ingsw.client.modelLight.GUI;

import it.polimi.ingsw.client.Clickable;
import it.polimi.ingsw.client.GUI;
import it.polimi.ingsw.client.MessageToServer.MoveExtraToWarehouse;
import it.polimi.ingsw.client.MessageToServer.MoveResource;
import it.polimi.ingsw.client.MessageToServer.PutResPos;
import it.polimi.ingsw.client.MessageToServer.TakeResPos;
import it.polimi.ingsw.client.Resource;
import it.polimi.ingsw.client.Shape;
import it.polimi.ingsw.client.modelLight.DepotView;
import it.polimi.ingsw.client.panels.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class DepotGUI extends DepotView implements Clickable {
    private Consumer<Integer> strategy;
    private Map<Resource, Shape> shapes;
    private final int deltapixel = 23;
    private final int heightRes = 20;
    private final int widthRes = 20;
    private int shelfFrom;
    private LeaderCardGUI extraFromMove;

    public DepotGUI(){
        super();
        setStrategyFirstTurn();
        shapes = new HashMap<>();
        shelfFrom = -1;
        extraFromMove = null;
    }

    public int getShelfFrom() {
        return shelfFrom;
    }

    public void resetShelfFrom(){
        shelfFrom = -1;
    }

    public void setExtraFromMove(LeaderCardGUI extraFromMove) {
        this.extraFromMove = extraFromMove;
    }

    public Map<Resource, Shape> getShapes() {
        return shapes;
    }

    public int getDeltapixel() {
        return deltapixel;
    }

    public int getQuantity(int shelf){
        return quantity[shelf-1];
    }

    @Override
    public void show() {
        int y = 188;
        int x = 74;

        //just put the position of the first resource of each shelf
        for(int i = 0; i<3; i++) {
            if(quantity[i] != 0)
                shapes.put(resources[i], new Shape(x, y, widthRes, heightRes));
            x -= 11+(1-i)*6;
            y += 37;
        }
        GUI.getGamePanel().repaint();
    }

    @Override
    public void showUpdate() {
        show();
        StringBuilder row = new StringBuilder();
        for(int i = 0; i<3; i++)
            if(quantity[i] != 0){
                row.append(quantity[i]).append("x").append(resources[i].toString()).append(" ");
            }
        GUI.print(row.toString());
    }

    @Override
    public boolean isClicked(int x, int y) {
        return !(whichClicked(x, y) == -1);
    }

    public int whichClicked(int x, int y){
        int yIn = 188;
        int xIn = 74;
        for(int i = 0; i<3; i++) {
                if(xIn <= x && x <= xIn+widthRes+deltapixel*i && yIn <= y && y <= yIn+heightRes)
                    return i+1;
            xIn -= 11+(1-i)*6;
            yIn += 37;
        }
        return -1;
    }

    public Resource whichResource(int shelf){
        shelf--;
        if(quantity[shelf] == 0) return null;
        return resources[shelf];
    }

    @Override
    public void click(int x, int y) {
        strategy.accept(whichClicked(x, y));
    }

    //default
    public void setStrategyMove(){
        strategy = (shelf) -> {
            if(extraFromMove != null){
                int howMany = ((MoveExtraPanel)GUI.getGamePanel().getActionPanel()).getHowMany();
                GUI.getClient().send(new MoveExtraToWarehouse(shelf, extraFromMove.getID(), howMany));
                this.extraFromMove = null;
                revalidateActionPanel();
                GUI.getGamePanel().repaint();
            }else if(shelfFrom != -1){
                GUI.getClient().send(new MoveResource(shelfFrom, shelf));
                this.shelfFrom = -1;
                revalidateActionPanel();
                GUI.getGamePanel().repaint();
            }else{
                this.shelfFrom = shelf;
                GUI.getGamePanel().setActionPanel(new MoveResPanel(GUI.getGamePanel().getActionPanel()));
                GUI.getGamePanel().repaint();
            }
        };
    }

    /**
     * resets the last panel used before a move action.
     */
    private void revalidateActionPanel(){
        MovePanel currentPanel = (MovePanel)GUI.getGamePanel().getActionPanel();
        currentPanel.restoreClickable();
        GUI.getGamePanel().unlockGameBoard(currentPanel.wasBoardUnlocked());
        GUI.getGamePanel().setActionPanel(currentPanel.getLastPanel());
    }

    public void setStrategyPut(){
        strategy = (shelf) -> {
            if(whichResource(shelf) != null) return;
            GUI.getClient().send(new PutResPos(resources[shelf], "depot", shelf));
        };
    }

    public void setStrategyTake(){
        strategy = (shelf) ->{
            if(whichResource(shelf) == null) return;
            GUI.getClient().send(new TakeResPos(resources[shelf - 1], "depot"));
        };
    }

    public void setStrategyFirstTurn(){
        strategy = (shelf) ->{
            FirstTurnPanel panel = (FirstTurnPanel) GUI.getGamePanel().getActionPanel();
            panel.selectPos(shelf);
        };
    }
}
