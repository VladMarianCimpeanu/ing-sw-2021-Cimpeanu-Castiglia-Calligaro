package it.polimi.ingsw.client.panels;

import it.polimi.ingsw.client.GUI;
import it.polimi.ingsw.client.MessageToServer.Login;
import it.polimi.ingsw.client.MessageToServer.Mode;

import javax.swing.*;
import java.awt.*;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginPanel extends JPanel implements ActionListener {
    private JButton loginButton;
    private TextField nicknameField;
    private JLabel titleLabel;
    private JLabel errorLabel;
    private String error;
    private JComboBox modeBox;
    private JButton modeButton;

    public LoginPanel() {
        this.setBackground(Color.white);
        error = "";

        titleLabel = new JLabel("Enter a valid nickname");
        titleLabel.setSize(100, 20);
        errorLabel = new JLabel(error);
        errorLabel.setSize(100, 20);
        nicknameField = new TextField();
        nicknameField.setSize(100, 20);
        loginButton = new JButton("Login");
        loginButton.setSize(100, 20);
        loginButton.addActionListener(this);

        this.add(titleLabel);
        this.add(nicknameField);
        this.add(loginButton);
        this.add(errorLabel);


        this.setLayout(new GridLayout(5, 1, 25, 5));
    }

    //TODO: delete this main, it's just for testing.
    public static void main(String[] args) {
        JFrame f = new JFrame("Maestri del rinascimento");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        LoginPanel loginpanel = new LoginPanel();
        f.add(loginpanel);
        f.setSize(400, 190);
        f.setVisible(true);
    }


    public String getString(){
        return this.nicknameField.getText();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == loginButton){
            GUI.getClient().send(new Login(nicknameField.getText()));
        }
        else if(e.getSource() == modeButton){
            GUI.getClient().send(new Mode(Integer.parseInt(modeBox.getSelectedItem().toString())));
        }
    }

    public void setError(String content){
        errorLabel.setText(content);
        nicknameField.setText("");
    }

    public void requireMode(){
        this.setVisible(false);
        this.remove(loginButton);
        this.remove(nicknameField);
        titleLabel.setText("How many players?");
        errorLabel.setText("");
        this.remove(errorLabel);
        this.remove(titleLabel);

        modeBox = new JComboBox(new String[]{"1", "2", "3", "4"});
        modeButton = new JButton("Ok");
        modeButton.addActionListener(this);
        this.add(titleLabel);
        this.add(modeBox);
        this.add(modeButton);
        this.setVisible(true);
    }
}
