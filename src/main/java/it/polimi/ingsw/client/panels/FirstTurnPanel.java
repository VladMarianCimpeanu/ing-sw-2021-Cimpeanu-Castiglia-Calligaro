package it.polimi.ingsw.client.panels;

import it.polimi.ingsw.client.MessageFromServer.ErrorMessage;
import it.polimi.ingsw.client.Resource;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class FirstTurnPanel extends ActionPanel{
    private int numberRes;
    private ArrayList<String> res;
    private ArrayList<String> pos;

    public FirstTurnPanel() {
        super();
        add(new JLabel("Select 2 Leader Cards."));
        res = new ArrayList<>();
        pos = new ArrayList<>();
        numberRes = 0;
    }

    @Override
    public void displayError(ErrorMessage error) {
        //TODO
    }

    public void selectRes(int position, int numberRes) {
        this.numberRes = numberRes;
        removeAll();
        if(numberRes > 0){
            add(new JLabel("Select " + numberRes + " resources"));



            //prints the Resources
            for(Resource r: Resource.values()){
                if(!r.equals(Resource.FAITH)){
                    InputStream url = GamePanel.class.getResourceAsStream("/" + r.url());
                    BufferedImage img= null;
                    try {
                        img = ImageIO.read(url);
                    } catch (IOException e) {
                        e.printStackTrace();
                        return;
                    }
                    Image dimg = img.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
                    ImageIcon x = new ImageIcon(dimg);
                    JLabel label = new JLabel(x);
                    label.addMouseListener(new MouseAdapter() {
                        public void mouseClicked(MouseEvent e) {
                            if(res.size() == pos.size() && res.size() < numberRes)
                                //does it work? (r.name)
                                res.add(r.name());
                        }
                    });
                    add(label);
                }
            }
            revalidate();
        }
    }
    //TODO: aspetto push di nick
    public void selectPos(){

    }
}
