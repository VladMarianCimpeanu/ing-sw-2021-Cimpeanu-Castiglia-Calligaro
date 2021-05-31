package it.polimi.ingsw.client.panels;

import it.polimi.ingsw.client.Clickable;
import it.polimi.ingsw.client.GUI;
import it.polimi.ingsw.client.Resource;
import it.polimi.ingsw.client.modelLight.GUI.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;


/**
 * This panel shows the dashboard of the current player watched and contains all the panels used during the game:
 * a general action panel, SwitchToPlayerPanel and a ScrollPanel.
 */
public class GamePanel extends JPanel {
    private ScrollPanel scrollPanel;
    private ActionPanel actionPanel;
    private ArrayList<Clickable> gameBoard = new ArrayList<Clickable>();
    private ArrayList<Clickable> action = new ArrayList<Clickable>();
    private boolean gameBoardClickable;
    private static final int X_board = 0;
    private static final int Y_board = 0;
    private static final int widthBoard = 600;
    private static final int heightBoard = 426;
    private static final int leaderActivationMargin = 3;
    private String playerWatched;

    public GamePanel() {
        setLayout(null);

        setBackground(Color.WHITE);
        setBorder(BorderFactory.createLineBorder(Color.black));
        gameBoardClickable = false;
        addMouseListener(new MouseAdapter(){
            public void mousePressed(MouseEvent e){
                activeClick(e.getX(),e.getY());
            }
        });

        scrollPanel = new ScrollPanel();
        add(scrollPanel);

        actionPanel = new FirstTurnPanel();
        add(actionPanel);
    }

    /**
     * activates a clickable object at (X,Y) coordinates when a click event occurs.
     * @param x X coordinate of the click event
     * @param y Y coordinate of the click event
     */
    private void activeClick(int x, int y){
        for (Clickable c: new ArrayList<>(action)){
            if (c.isClicked(x,y)){
                c.click(x, y);
            }
        }
        if (gameBoardClickable) {
            for (Clickable c : new ArrayList<>(gameBoard)) {
                if (c.isClicked(x, y)) {
                    c.click(x, y);
                }
            }
        }
    }

    @Override
    public synchronized void paintComponent(Graphics g) {
        super.paintComponent(g);

        it.polimi.ingsw.client.Shape shape = new it.polimi.ingsw.client.Shape(X_board, Y_board, widthBoard, heightBoard);
        drawImage(g,"images/board/playerBoard.png", shape);
        it.polimi.ingsw.client.Shape shape3 = new it.polimi.ingsw.client.Shape(730,450,200,259);
        drawImage(g,"images/punchboard/portabiglie.png", shape3);

        DevelopmentCardsSetGUI cardsToBuy = (DevelopmentCardsSetGUI) GUI.getClient().getGameView().getCards();
        cardsToBuy.paintCards(g);

        DevelopmentCardDecksGUI devCards = (DevelopmentCardDecksGUI)GUI.getClient().getGameView().getPlayer(playerWatched).getDecks();
        devCards.drawDecks(g);

        LeaderCardSetGUI set = (LeaderCardSetGUI) GUI.getClient().getGameView().getPlayer(playerWatched).getLeaderCards();
        for(LeaderCardGUI card: set.getCards()){
            if(card.isActivated()){
                g.setColor(Color.BLUE);
                g.fillRect(
                        card.getShape().getX() - leaderActivationMargin,
                        card.getShape().getY() - leaderActivationMargin,
                        card.getShape().getWidth() + 2 * leaderActivationMargin,
                        card.getShape().getHeight() + 2 * leaderActivationMargin
                );
            }
            drawImage(g, card.getImage(), card.getShape());
            card.printExtraSlot(g);
        }

        MarketGUI marketGUI = (MarketGUI) GUI.getClient().getGameView().getMarket();
        marketGUI.print(g);


        //Strongbox
        StrongboxGUI strongbox = (StrongboxGUI) GUI.getClient().getGameView().getPlayer(playerWatched).getStrongbox();
        for(Resource resource: Resource.values()){
            if(strongbox.getQuantity(resource) == 0) continue;
            drawImage(g, resource.url(), strongbox.getShape(resource));
            g.setColor(Color.WHITE);
            g.drawString(strongbox.getQuantity(resource)+"x", strongbox.getShape(resource).getX()-19, strongbox.getShape(resource).getY()+10);
        }

        //WarehouseDepot
        DepotGUI depot = (DepotGUI) GUI.getClient().getGameView().getPlayer(playerWatched).getDepot();
        int deltaShelf = 23;    //distance between resources on the same shelf
        for(Resource resource: depot.getShapes().keySet())
            for(int i = 0; i<depot.howMany(resource); i++) {
                it.polimi.ingsw.client.Shape s = depot.getShapes().get(resource);
                drawImage(g, resource.url(), new it.polimi.ingsw.client.Shape(s.getX()+i*deltaShelf, s.getY(), s.getWidth(), s.getHeight()));
            }

        //FaithPath
        FaithPathGUI faithPath = (FaithPathGUI) GUI.getClient().getGameView().getFaithPathView();
        for(String player: faithPath.getShapes().keySet())
            if(player.equals("blackCross"))
                drawImage(g, "images/punchboard/blackCross.png", faithPath.getShapes().get(player));
            else
                drawImage(g, faithPath.getURLCross(player), faithPath.getShapes().get(player));
    }


    public static void drawImage(Graphics g, String path, it.polimi.ingsw.client.Shape shape){
        int x = shape.getX();
        int y = shape.getY();
        int width = shape.getWidth();
        int height = shape.getHeight();

        //ClassLoader cl = this.getClass().getClassLoader();
        InputStream url = GamePanel.class.getResourceAsStream("/" + path);
        BufferedImage img= null;
        try {
            img = ImageIO.read(url);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        g.drawImage(img, x, y, width, height, null);
    }

    /**
     * adds a clickable object to the game board panel
     * @param clickable clickable object to be added.
     */
    public void addGameboard(Clickable clickable){
        if(!gameBoard.contains(clickable))
            gameBoard.add(clickable);
    }

    /**
     * removes a clickable object from the game board panel
     * @param clickable clickable object to be removed.
     */
    public void removeGameboard(Clickable clickable){
        gameBoard.remove(clickable);
    }

    /**
     * adds a component that can be clicked during a dialog with an action panel
     * @param clickable specified clickable object to be added
     */
    public void addAction(Clickable clickable){
        if(!action.contains(clickable))
            action.add(clickable);
    }

    public void removeAction(Clickable clickable){
        action.remove(clickable);
    }

    /**
     * gets the X coordinate of the top left corner of the gameBoard
     * @return an integer representing the X coordinate of top left corner of the gameBoard
     */
    public static int getXBoard(){
        return X_board;
    }

    /**
     * gets the Y coordinate of the top left corner of the gameBoard
     * @return an integer representing the Y coordinate of top left corner of the gameBoard
     */
    public static int getYBoard(){
        return Y_board;
    }

    /**
     * returns the width of the game board.
     * @return integer representing of the width of the game board.
     */
    public static int getWidthBoard(){
        return widthBoard;
    }

    /**
     * returns the height of the game board.
     * @return integer representing of the height of the game board.
     */
    public static int getHeightBoard(){
        return heightBoard;
    }

    //TODO: is it necessary?
    public void addOtherPlayersPanel(){
        this.add(new SwitchToPlayerPanel());
    }

    /**
     * set the dashboard to be watched.
     * @param player specified player that owns the dashboard to watch.
     */
    public synchronized void setPlayerWatched(String player){
        playerWatched = player;
        System.out.println(player);
    }

    /**
     * locks or unlocks the gameBoard
     * @param unlocked if unlocked is true, it unlocks the board, otherwise it locks it.
     */
    public void unlockGameBoard(boolean unlocked){
        gameBoardClickable = unlocked;
    }

    /**
     * changes the current Action panel of the game panel.
     * @param panel specified Action panel to set.
     */
    public void setActionPanel(ActionPanel panel){
        this.remove(actionPanel);
        actionPanel = panel;
        this.add(panel);
        revalidate();
    }

    /**
     * gets the current action panel.
     * @return the current Action panel
     */
    public ActionPanel getActionPanel(){
        return actionPanel;
    }

    /**
     * gets the scroll panel used to represent all the update messages during the game.
     * @return a ScrollPanel.
     */
    public ScrollPanel getScrollPanel() {
        return scrollPanel;
    }

    /**
     * setup the panels when a player rejoin after a crash
     */
    public void setupRejoin(){
        setActionPanel(new DefaultPanel());
        action.clear();
        LeaderCardSetGUI leaderCardSetGUI = (LeaderCardSetGUI) GUI.getClient().getGameView().getPlayer(GUI.getClient().getNickname()).getLeaderCards();
        leaderCardSetGUI.setLeadersToDefaultStrategy();
    }
}
