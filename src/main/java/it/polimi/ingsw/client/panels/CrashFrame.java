package it.polimi.ingsw.client.panels;

import javax.swing.*;
import java.awt.*;

public class CrashFrame extends JFrame {
    public CrashFrame(){
        this.setTitle("Maestri del Rinascimento");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLayout(new FlowLayout());
        this.setSize(new Dimension(500, 300));
        this.add(new JLabel("Ops... it seems there is something strange with the connection."));
        this.add(new JLabel("Close the window and try again"));
        this.setVisible(true);
    }
}
