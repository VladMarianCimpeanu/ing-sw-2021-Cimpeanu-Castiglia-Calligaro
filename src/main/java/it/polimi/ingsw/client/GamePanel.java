package it.polimi.ingsw.client;

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
    private MessagesPanel messagesPanel;
    private ActionPanel actionPanel;
    private ArrayList<Clickable> gameBoard = new ArrayList<Clickable>();
    private ArrayList<Clickable> action = new ArrayList<Clickable>();
    private boolean gameBoardClickable;
    private static final int X_board = 0;
    private static final int Y_board = 0;
    private static final int widthBoard = 600;
    private static final int heightBoard = 426;

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
        JButton button = new JButton("Cliccami!");
        add(button);
        Dimension size = button.getPreferredSize();
        button.setBounds(200, 200,
                size.width, size.height);

        messagesPanel = new MessagesPanel();
        add(messagesPanel);

        actionPanel = new ActionPanel();
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

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Shape shape = new Shape(X_board, Y_board, widthBoard, heightBoard);
        drawImage(g,"images/board/playerBoard.png", shape);
        Shape shape2 = new Shape(630,28,400,370);
        //drawImage(g,"images/devCards.png", shape2);
        Shape shape3 = new Shape(730,450,200,259);
        drawImage(g,"images/punchboard/portabiglie.png", shape3);

        DevelopmentCardsSetGUI cardsToBuy = (DevelopmentCardsSetGUI)GUI.getClient().getGameView().getCards();
        cardsToBuy.paintCards(g);

        DevelopmentCardDecksGUI devCards = (DevelopmentCardDecksGUI)GUI.getClient().getGameView().getPlayer(GUI.getClient().getNickname()).getDecks();
        devCards.drawDecks(g);

        LeaderCardSetGUI set = (LeaderCardSetGUI) GUI.getClient().getGameView().getPlayer(GUI.getClient().getNickname()).getLeaderCards();
        for(LeaderCardGUI card: set.getCards()){
            drawImage(g, card.getImage(), card.getShape());
        }

        MarketGUI marketGUI = (MarketGUI) GUI.getClient().getGameView().getMarket();
        marketGUI.print(g);


        //Strongbox
        StrongboxGUI strongbox = (StrongboxGUI) GUI.getClient().getGameView().getPlayer(GUI.getClient().getNickname()).getStrongbox();
        for(Resource resource: Resource.values()){
            if(strongbox.getQuantity(resource) == 0) continue;
            drawImage(g, resource.url(), strongbox.getShape(resource));
            g.setColor(Color.WHITE);
            g.drawString(strongbox.getQuantity(resource)+"x", strongbox.getShape(resource).getX()-19, strongbox.getShape(resource).getY()+10);
        }

        //WarehouseDepot
        DepotGUI depot = (DepotGUI) GUI.getClient().getGameView().getPlayer(GUI.getClient().getNickname()).getDepot();
        int deltaShelf = 23;    //distance between resources on the same shelf
        for(Resource resource: depot.getShapes().keySet())
            for(int i = 0; i<depot.howMany(resource); i++) {
                Shape s = depot.getShapes().get(resource);
                drawImage(g, resource.url(), new Shape(s.getX()+i*deltaShelf, s.getY(), s.getWidth(), s.getHeight()));
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
    public static void drawImage(Graphics g, String path, Shape shape){
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

}
