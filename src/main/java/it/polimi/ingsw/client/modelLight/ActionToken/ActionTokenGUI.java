package it.polimi.ingsw.client.modelLight.ActionToken;

import it.polimi.ingsw.client.GUI;
import it.polimi.ingsw.client.Shape;
import it.polimi.ingsw.client.exceptions.NoUrlException;

public class ActionTokenGUI extends ActionTokenView{
    private static final Shape shape = new Shape(450,500, 100, 100);
    private String url;

    @Override
    public void show(int id) {
        url = getActionToken(id).getUrl();
        GUI.getGamePanel().repaint();
    }

    public Shape getShape() {
        return shape;
    }

    public String getUrl() throws NoUrlException {
        if(url == null) throw new NoUrlException();
        return url;
    }
}
