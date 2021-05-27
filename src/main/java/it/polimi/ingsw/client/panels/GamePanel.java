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
        gameBoardClickable = true;
        addMouseListener(new MouseAdapter(){
            public void mousePressed(MouseEvent e){
                activeClick(e.getX(),e.getY());
            }
        });


        //Example of absolute positioning
//        JButton button = new JButton("Cliccami!");
//        add(button);
//        Dimension size = button.getPreferredSize();
//        button.setBounds(200, 200,
//                size.width, size.height);

        scrollPanel = new ScrollPanel();
        add(scrollPanel);

        actionPanel = new FirstTurnPanel();
        add(actionPanel);

//        addMouseMotionListener(new MouseAdapter(){
//            public void mouseDragged(MouseEvent e){
//                moveSquare(e.getX(),e.getY());
//            }
//        });
    }

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

//    public void addImage(String url){
//        clickables.add();
//        repaint();
//    }

//    private void moveSquare(int x, int y){
//
//        // Current square state, stored as final variables
//        // to avoid repeat invocations of the same methods.
//        final int CURR_X = redSquare.getX();
//        final int CURR_Y = redSquare.getY();
//
//        if ((CURR_X!=x) || (CURR_Y!=y)) {
//            // Update coordinates.
//            redSquare.setX(x);
//            redSquare.setY(y);
//
//            repaint();
//        }
//    }


    @Override
    public synchronized void paintComponent(Graphics g) {
        super.paintComponent(g);

        it.polimi.ingsw.client.Shape shape = new it.polimi.ingsw.client.Shape(X_board, Y_board, widthBoard, heightBoard);
        drawImage(g,"images/board/playerBoard.png", shape);
        it.polimi.ingsw.client.Shape shape2 = new it.polimi.ingsw.client.Shape(630,28,400,370);
        //drawImage(g,"images/devCards.png", shape2);
        it.polimi.ingsw.client.Shape shape3 = new it.polimi.ingsw.client.Shape(730,450,200,259);
        drawImage(g,"images/punchboard/portabiglie.png", shape3);

        DevelopmentCardsSetGUI cardsToBuy = (DevelopmentCardsSetGUI) GUI.getClient().getGameView().getCards();
        cardsToBuy.paintCards(g);

        DevelopmentCardDecksGUI devCards = (DevelopmentCardDecksGUI)GUI.getClient().getGameView().getPlayer(playerWatched).getDecks();
        devCards.drawDecks(g);

//        LeaderCardSetGUI set = (LeaderCardSetGUI) GUI.getClient().getGameView().getPlayer(GUI.getClient().getNickname()).getLeaderCards();
//        for(LeaderCardGUI card: set.getCards()){
//            if(card.isActivated()){
//                g.setColor(Color.BLUE);
//                g.fillRect(
//                        card.getShape().getX() - leaderActivationMargin,
//                        card.getShape().getY() - leaderActivationMargin,
//                        card.getShape().getWidth() + 2 * leaderActivationMargin,
//                        card.getShape().getHeight() + 2 * leaderActivationMargin
//                );
//            }
//            drawImage(g, card.getImage(), card.getShape());
//        }

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


        g.drawString("Attiva LeaderCard", 1200, 20);
        g.drawString("Scarta LeaderCard", 1200, 50);
    }

    //TODO public? it would be nicer if the logic around development cards was inside their classes
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

    public void addGameboard(Clickable clickable){
        if(!gameBoard.contains(clickable))
            gameBoard.add(clickable);
    }

    public void removeGameboard(Clickable clickable){
        gameBoard.remove(clickable);
    }

    public void addAction(Clickable clickable){
        if(!action.contains(clickable))
            action.add(clickable);
    }

    public static int getXBoard(){
        return X_board;
    }

    public static int getYBoard(){
        return Y_board;
    }

    public static int getWidthBoard(){
        return widthBoard;
    }

    public static int getHeightBoard(){
        return heightBoard;
    }

    public void addOtherPlayersPanel(){
        this.add(new SwitchToPlayerPanel());
    }

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

    public void setActionPanel(ActionPanel panel){
        this.remove(actionPanel);
        actionPanel = panel;
        this.add(panel);
        revalidate();
    }

    public ActionPanel getActionPanel(){
        return actionPanel;
    }
    public ScrollPanel getScrollPanel() {
        return scrollPanel;
    }
}
