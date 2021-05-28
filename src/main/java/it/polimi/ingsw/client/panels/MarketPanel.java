package it.polimi.ingsw.client.panels;

import it.polimi.ingsw.client.GUI;
import it.polimi.ingsw.client.MessageFromServer.ErrorMessage;
import it.polimi.ingsw.client.MessageToServer.DiscardResource;
import it.polimi.ingsw.client.MessageToServer.PutResPos;
import it.polimi.ingsw.client.Resource;
import it.polimi.ingsw.client.modelLight.GUI.LeaderCardSetGUI;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

/**
 * panel dialog that appears when a player has selected a column or a row at the market.
 */
public class MarketPanel extends ActionPanel implements ActionListener {
    private JLabel textLabel;
    private ArrayList<JLabel> imagesToPrint;
    private Map<Resource, ResourceButton> resources;
    private boolean resourcesDisplayed;
    private boolean isTakingResources;
    private Resource selectedResource;
    private JLabel selectionLabel;
    private JComboBox placeBox;
    private JLabel errorLabel;

    public MarketPanel(){
        imagesToPrint = new ArrayList<>();
        resourcesDisplayed = false;
        isTakingResources = false;
        selectedResource = null;
        textLabel = new JLabel("");
        resources = new TreeMap<>();
        errorLabel = new JLabel("");
        setLayout(new GridLayout(8, 1));
        this.add(textLabel);
        for(Resource res : Resource.values()){
            if (res != Resource.FAITH){
                ResourceButton button = new ResourceButton(res);
                button.setIcon(convertURL(res.url(), 30, 30));
                button.setText("x0");
                resources.put(res, button);
            }
        }
    }



    @Override
    public void displayError(ErrorMessage error) {
        errorLabel.setText(error.getCaption());
    }

    /**
     * prepares this panel to a dialog used to manage resources obtained by the market.
     */
    public void setToSelectResources(){
        isTakingResources = true;
        System.out.println(imagesToPrint.size());
        if (!imagesToPrint.isEmpty()){
            for(JLabel label : imagesToPrint) this.remove(label);
        }
        this.remove(errorLabel);
        this.revalidate();
    }

    /**
     * update the remaining marbles to convert and the strategies used by the player.
     * @param whiteMarbles marbles remaining
     * @param id id of the new leader card used.
     */
    public void updateStrategies(int whiteMarbles, int id){
        if(imagesToPrint.isEmpty()) {
            this.add(errorLabel);
        }
        textLabel.setText("Remains " + whiteMarbles + " to convert, which leader card do you want to use?");
        if(id != 0){
            JLabel leaderCard = new JLabel(convertURL(LeaderCardSetGUI.getLeaderCard(id).getImage(), 33, 50));
            this.add(leaderCard);
            imagesToPrint.add(leaderCard);
        }
        this.revalidate();

    }

    /**
     * updates the resources that remains to manage.
     */
    public void updateRemainingResources() {
        if (!resourcesDisplayed) {
            displayResources();
            resourcesDisplayed = true;
        }
        textLabel.setText("Resources to take");
        Map<Resource, Integer> buffer = GUI.getClient().getGameView().getResBuffer();
        System.out.println(buffer);
        clearButtons();
        for (Resource resource : buffer.keySet()){
            resources.get(resource).setText("x" + buffer.get(resource).toString());
        }
        this.revalidate();
    }

    /**
     * add all the components needed for a dialog with the player in order to manage the resources
     */
    private void displayResources(){
        this.setLayout(new GridLayout(8, 1));
        for(JButton button : resources.values()){
            button.addActionListener(this);
            this.add(button);
        }
        selectionLabel = new JLabel("nothing has been selected");
        this.add(selectionLabel);
        placeBox = new JComboBox(new String[]{"shelf 1", "shelf 2", "shelf 3", "leader", "discard"});
        placeBox.addActionListener(this);
        this.add(placeBox);
        this.add(errorLabel);
    }

    /**
     * @return true if the panel is in resource management mode, else false.
     */
    public boolean isTakingResources(){
        return isTakingResources;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        for(ResourceButton button : resources.values()){
            if(e.getSource() == button) {
                selectedResource = button.getResource();
                selectionLabel.setText("selected " + selectedResource);
            }
        }
        if(e.getSource() == placeBox && selectedResource != null){
            switch (placeBox.getSelectedItem().toString()){
                case "shelf 1":
                    GUI.sendMessage(new PutResPos(selectedResource, "depot", 1 ));
                    break;
                case "shelf 2":
                    GUI.sendMessage(new PutResPos(selectedResource, "depot", 2 ));
                    break;
                case "shelf 3":
                    GUI.sendMessage(new PutResPos(selectedResource, "depot", 3 ));
                    break;
                case "leader":
                    GUI.sendMessage(new PutResPos(selectedResource, "extra", 0 ));
                    break;
                case "discard":
                    GUI.sendMessage(new DiscardResource(selectedResource));
                    break;
                default:
                    break;
            }
            selectedResource = null;
            selectionLabel.setText("Nothing has been selected");
            errorLabel.setText("");
        }
    }

    /**
     * reset all the buttons to quantity 0.
     */
    private void clearButtons(){
        for(ResourceButton button : resources.values())
            button.setText("x0");
    }

    /**
     *
     * @param URL
     * @return
     */
    private ImageIcon convertURL(String URL, int width, int height){
        InputStream url = GamePanel.class.getResourceAsStream("/" + URL);
        BufferedImage img= null;
        try {
            img = ImageIO.read(url);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        Image dimg = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(dimg);
    }

}

/**
 * a Button associated to a specific resource
 */
class ResourceButton extends JButton{
    private Resource resource;
    ResourceButton(Resource resource){
        super();
        this.resource = resource;
    }
    Resource getResource(){
        return resource;
    }
}