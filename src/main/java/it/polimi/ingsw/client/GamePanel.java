package it.polimi.ingsw.client;

import it.polimi.ingsw.client.modelLight.GUI.LeaderCardGUI;
import it.polimi.ingsw.client.modelLight.GUI.LeaderCardSetGUI;

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
    private ArrayList<Clickable> gameBoard = new ArrayList<Clickable>();
    private ArrayList<Clickable> action = new ArrayList<Clickable>();
    private boolean gameBoardClickable;

    public GamePanel() {
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createLineBorder(Color.black));
        gameBoardClickable = true;

        addMouseListener(new MouseAdapter(){
            public void mousePressed(MouseEvent e){
                activeClick(e.getX(),e.getY());
            }
        });

//        addMouseMotionListener(new MouseAdapter(){
//            public void mouseDragged(MouseEvent e){
//                moveSquare(e.getX(),e.getY());
//            }
//        });
    }

    private void activeClick(int x, int y){
        for (Clickable c: new ArrayList<>(action)){
            if (c.isClicked(x,y)){
                c.click();
            }
        }
        if (gameBoardClickable) {
            for (Clickable c : new ArrayList<>(gameBoard)) {
                if (c.isClicked(x, y)) {
                    c.click();
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
        //g.drawString("This is my custom Panel!",10,20);

//        LeaderCardSetGUI set = (LeaderCardSetGUI) GUI.getClient().getGameView().getPlayer(GUI.getClient().getNickname()).getLeaderCards();
//        for(LeaderCardGUI card: set.getCards()){
//            drawImage(g, card.getUrl(), card.getShape());
//        }


        Shape shape = new Shape(0,0,600,426);
        drawImage(g,"images/board/playerBoard.png", shape);
        Shape shape2 = new Shape(630,28,400,370);
        drawImage(g,"images/devCards.png", shape2);
        Shape shape3 = new Shape(730,450,200,259);
        drawImage(g,"images/punchboard/portabiglie.png", shape3);

        Shape shape4 = new Shape(20,450,133,200);
        drawImage(g,"images/front/frontCard60.png", shape4);
        Shape shape5 = new Shape(170,450,133,200);
        drawImage(g,"images/front/frontCard63.png", shape5);
        Shape shape6 = new Shape(320,450,133,200);
        drawImage(g,"images/front/frontCard60.png", shape6);
        Shape shape7 = new Shape(470,450,133,200);
        drawImage(g,"images/front/frontCard63.png", shape7);

        g.drawString("Attiva LeaderCard", 1200, 20);
        g.drawString("Scarta LeaderCard", 1200, 50);
    }

    private void drawImage(Graphics g, String path, Shape shape){
        int x = shape.getX();
        int y = shape.getY();
        int width = shape.getWidth();
        int height = shape.getHeight();

        ClassLoader cl = this.getClass().getClassLoader();
        InputStream url = cl.getResourceAsStream(path);
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

    public void addAction(Clickable clickable){
        if(!action.contains(clickable))
            action.add(clickable);
    }
}
